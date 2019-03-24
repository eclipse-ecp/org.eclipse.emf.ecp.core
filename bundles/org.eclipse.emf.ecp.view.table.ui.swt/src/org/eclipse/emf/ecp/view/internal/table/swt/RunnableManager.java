/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Christian W. Damus - bug 544116
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.swt.widgets.Display;

/**
 * A utility class that is capable of maintaining the running
 * state of a {@link Runnable}, i.e. frequent calls of the {@link #executeAsync(Runnable)}
 * with long running {@link Runnable}s will not result in each {@link Runnable} being called.
 * This is useful, for instance, if the same {@link Runnable} is
 * submitted multiple times unnecessarily.
 *
 */
public class RunnableManager {

	private final AtomicBoolean isRunning = new AtomicBoolean(false);
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
		// validation finished
		isRunning.compareAndSet(true, false);
		// re-trigger validation if we have a pending request
		final Runnable next = pending.getAndSet(null);
		if (next != null) {
			executeAsync(next);
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
		if (isRunning.compareAndSet(false, true)) {
			getDisplay().asyncExec(createWrapperRunnable(runnable));
		} else {
			pending.compareAndSet(null, runnable);
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

}
