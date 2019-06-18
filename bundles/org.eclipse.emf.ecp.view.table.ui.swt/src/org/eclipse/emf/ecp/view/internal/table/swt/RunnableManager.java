/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Christian W. Damus - bugs 544116, 545686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * A utility class that is capable of maintaining the running
 * state of a {@link Runnable}, i.e. frequent calls of the {@link #executeAsync(Runnable)}
 * with long running {@link Runnable}s will not result in each {@link Runnable} being called.
 * This is useful, for instance, if the same {@link Runnable} is
 * submitted multiple times unnecessarily.
 * </p>
 * <p>
 * If a runnable additionally implements the {@link BackgroundStage} interface, then
 * it will be invoked on a background thread and when complete will be asked to
 * {@linkplain BackgroundStage#getNextStage() provide a subsequent stage} to execute.
 * This may similarly be a background stage, iterating the process, or may be a terminal
 * runnable that then is posted on the UI thread to update the UI.
 * </p>
 */
public class RunnableManager {

	private final Lock lock = new ReentrantLock();
	private final AtomicBoolean isRunning = new AtomicBoolean(false);
	private final Condition runningCond = lock.newCondition();
	private final AtomicReference<Runnable> pending = new AtomicReference<>();
	private final Display display;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} that is used to submit a runnable
	 */
	public RunnableManager(Display display) {
		this.display = display;
	}

	private void finish(Runnable runnable) {
		lock.lock();

		try {
			// task has finished
			isRunning.compareAndSet(true, false);

			// Running state has changed
			runningCond.signalAll();

			// trigger next task if we have a pending request
			final Runnable next = pending.getAndSet(null);
			if (next != null) {
				executeAsync(next);
			}
		} finally {
			lock.unlock();
		}
	}

	private Runnable createWrapperRunnable(final Runnable runnable) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {
					finish(runnable);
				}
			}
		};
	}

	/**
	 * Execute the given {@link Runnable} via {@link Display#asyncExec(Runnable)}.
	 *
	 * @param runnable the {@link Runnable} to be executed asynchronously
	 */
	public void executeAsync(final Runnable runnable) {
		lock.lock();

		try {
			if (isRunning.compareAndSet(false, true)) {
				doExecuteAsync(runnable);

				// Running state has changed
				runningCond.signalAll();
			} else {
				pending.compareAndSet(null, runnable);
			}
		} finally {
			lock.unlock();
		}
	}

	private void doExecuteAsync(final Runnable runnable) {
		if (runnable instanceof BackgroundStage) {
			runStage(runnable, (BackgroundStage) runnable);
		} else {
			getDisplay().asyncExec(createWrapperRunnable(runnable));
		}
	}

	/**
	 * @return the display
	 */
	public synchronized Display getDisplay() {
		return display;
	}

	/**
	 * Query whether a runnable is in progress (asynchronously) on the display thread.
	 * The implication is that posting a new runnable at this instant would be redundant.
	 *
	 * @return {@code true} if a runnable is currently running on the display thread
	 *         or waiting to run on the display thread; {@code false}, otherwise
	 * @since 1.20
	 */
	public boolean isRunning() {
		return isRunning.get();
	}

	/**
	 * Wait until no task is running. Returns immediately if there is currently
	 * no task running.
	 *
	 * @throws InterruptedException if interrupted while waiting
	 * @since 1.21
	 */
	public final void waitForIdle() throws InterruptedException {
		if (!isRunning()) {
			// Short-circuit
			return;
		}

		final boolean busyWait = Display.getCurrent() == display;

		lock.lock();

		try {
			out: while (isRunning()) {
				if (busyWait) {
					try {
						lock.unlock();

						// We can only finish on the display thread, which is
						// performed by a runnable in this queue
						do {
							if (!isRunning()) {
								break out;
							}
						} while (display.readAndDispatch());

						display.sleep();
					} finally {
						lock.lock();
					}
				} else {
					runningCond.await();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Wait until no task is running or the given {@code time} elapses.
	 * Returns immediately if there is currently no task running. Note that if called on the
	 * UI thread, then the actual wait time in case of time-out can be longer than requested
	 * because of UI event queue processing.
	 *
	 * @param time the amount of time to wait, or a non-positive amount to wait indefinitely
	 *            as in {@link #waitForIdle()}
	 * @param unit the unit of measure of the {@code time} to wait
	 * @return {@code true} if on return there is no task running; {@code false} on time-out
	 *         (which does not mean that since the time-out occurred the manager did not become idle)
	 *
	 * @throws InterruptedException if interrupted while waiting
	 * @since 1.21
	 */
	public final boolean waitForIdle(long time, TimeUnit unit) throws InterruptedException {
		if (time <= 0L) {
			waitForIdle();
			return true;
		}

		final long deadline = System.nanoTime() + unit.toNanos(time);

		if (!isRunning()) {
			// Short-circuit
			return true;
		}

		final boolean busyWait = Display.getCurrent() == display;

		lock.lock();

		try {
			out: while (isRunning()) {
				final long remaining = deadline - System.nanoTime();
				if (remaining <= 0L) {
					return false;
				}

				if (busyWait) {
					try {
						lock.unlock();

						// We can only finish on the display thread, which is
						// performed by a runnable in this queue
						do {
							if (!isRunning()) {
								break out;
							}
						} while (display.readAndDispatch());
					} finally {
						lock.lock();
					}

					runningCond.await(50L, TimeUnit.MILLISECONDS);
				} else {
					runningCond.awaitNanos(remaining);
				}
			}
		} finally {
			lock.unlock();
		}

		return true;
	}

	private void runStage(Runnable computation, BackgroundStage stage) {
		CompletableFuture.runAsync(computation)
			.whenComplete((result, exception) -> {
				if (exception != null) {
					// Computation failed. Just finish
					finish(computation);
					Activator.getInstance().log(exception);
				} else {
					// Send the next stage
					final Runnable next = stage.getNextStage();
					if (next == null) {
						// Okay, then. Just finish
						finish(computation);
					} else {
						doExecuteAsync(next);
					}
				}
			});
	}

	//
	// Nested types
	//

	/**
	 * An optional mix-in interface for a {@link Runnable} scheduled on the
	 * {@link RunnableManager} that should be run in a background thread and which
	 * produces a subsequent stage for further execution.
	 *
	 * @since 1.21
	 */
	public interface BackgroundStage {
		/**
		 * Provides the next stage of computation. If the result is another
		 * {@code BackgroundStage}, then it, too, will run in the background.
		 * The final stage is some {@link Runnable} that is not a {@code BackgroundStage}
		 * which then is posted on the display thread to update the UI.
		 *
		 * @return the next stage of computation/update
		 */
		Runnable getNextStage();
	}

}
