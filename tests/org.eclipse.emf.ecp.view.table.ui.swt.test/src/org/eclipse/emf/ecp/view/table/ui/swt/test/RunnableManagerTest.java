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
 * edgar - initial API and implementation
 * Christian W. Damus - bug 545686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.ecp.view.internal.table.swt.RunnableManager;
import org.eclipse.emf.ecp.view.internal.table.swt.RunnableManager.BackgroundStage;
import org.eclipse.swt.widgets.Display;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class RunnableManagerTest {

	private static final int NR_RUNNABLES = 1000;
	private static final ScheduledExecutorService POOL = Executors.newScheduledThreadPool(1);
	private static final ScheduledExecutorService ASYNC_POOL = Executors.newScheduledThreadPool(1);
	private static final Display DISPLAY = mock(Display.class);

	private Runnable createRunnable(final CountDownLatch latch,
		final AtomicInteger counter) {

		return new Runnable() {
			@Override
			public void run() {
				counter.getAndIncrement();
				latch.countDown();
			}
		};
	}

	@BeforeClass
	public static void beforeClass() {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				ASYNC_POOL.submit(
					Runnable.class.cast(invocation.getArguments()[0]));
				return null;
			}
		}).when(DISPLAY).asyncExec(any(Runnable.class));

	}

	@AfterClass
	public static void afterClass() {
		ASYNC_POOL.shutdown();
		POOL.shutdown();
	}

	@Test
	public void managedRunnable() throws InterruptedException {
		// setup
		final AtomicInteger asyncCounter = new AtomicInteger(0);
		final AtomicInteger counter = new AtomicInteger(0);
		final CountDownLatch latch = new CountDownLatch(NR_RUNNABLES);
		final RunnableManager runnableManager = new RunnableManager(DISPLAY);

		// act
		for (int x = 0; x < NR_RUNNABLES; x++) {
			runnableManager.executeAsync(createRunnable(latch, asyncCounter));
			POOL.submit(createRunnable(latch, counter));
		}
		latch.await();

		// assert
		// we expect more runnables to be executed via the fixed pool since
		// the RunnableManager only lets a single Runnable to be executed
		assertTrue(counter.intValue() > asyncCounter.intValue());
	}

	@Test
	public void backgroundStage() throws InterruptedException {
		final AtomicInteger stageCount = new AtomicInteger();
		final Stage stage = new Stage(stageCount::incrementAndGet);

		final RunnableManager runnableManager = new RunnableManager(DISPLAY);

		runnableManager.executeAsync(stage);
		stage.join();

		assertThat("Multi-step execution not completed", stageCount.get(), greaterThanOrEqualTo(3));
	}

	@Test
	public void waitForIdle() throws InterruptedException {
		final AtomicInteger countdown = new AtomicInteger(Stage.STAGE_COUNT);
		final Runnable callback = () -> {
			try {
				Thread.sleep(100L);
			} catch (final InterruptedException e) {
				fail("Call-back interrupted");
			}
			countdown.decrementAndGet();
		};

		final Stage stage = new Stage(callback);

		final RunnableManager runnableManager = new RunnableManager(DISPLAY);

		runnableManager.executeAsync(stage);

		runnableManager.waitForIdle();

		assertThat("Wait returned early", countdown.get(), lessThanOrEqualTo(0));
	}

	@Test
	public void waitForIdle_time() throws InterruptedException {
		final AtomicInteger countdown = new AtomicInteger(Stage.STAGE_COUNT);
		final Runnable callback = () -> {
			try {
				Thread.sleep(100L);
			} catch (final InterruptedException e) {
				fail("Call-back interrupted");
			}
			countdown.decrementAndGet();
		};

		final Stage stage = new Stage(callback);

		final RunnableManager runnableManager = new RunnableManager(DISPLAY);

		runnableManager.executeAsync(stage);

		assertThat("Timed out", runnableManager.waitForIdle(5L, TimeUnit.SECONDS), is(true));

		assertThat("Wait returned early", countdown.get(), lessThanOrEqualTo(0));
	}

	@Test
	public void waitForIdle_timeout() throws InterruptedException {
		final AtomicInteger countdown = new AtomicInteger(Stage.STAGE_COUNT);
		final Runnable callback = () -> {
			try {
				Thread.sleep(1000L);
			} catch (final InterruptedException e) {
				fail("Call-back interrupted");
			}
			countdown.decrementAndGet();
		};

		final Stage stage = new Stage(callback);

		final RunnableManager runnableManager = new RunnableManager(DISPLAY);

		runnableManager.executeAsync(stage);

		assertThat("Not timed out", runnableManager.waitForIdle(1L, TimeUnit.SECONDS), is(false));

		assertThat("Task completed", countdown.get(), greaterThan(0));
	}

	//
	// Nested types
	//

	private static class Stage implements Runnable, BackgroundStage {
		static final int STAGE_COUNT = 3;

		private final Runnable callback;

		private int backgroundStages = STAGE_COUNT - 1;
		private Runnable next;

		private final CountDownLatch done = new CountDownLatch(1);

		Stage(Runnable callback) {
			super();

			this.callback = callback;
		}

		@Override
		public void run() {
			// This is a stage
			try {
				callback.run();
			} finally {
				// This is a background stage
				backgroundStages--;

				if (backgroundStages <= 0) {
					// Last stage is foreground
					next = () -> {
						try {
							callback.run();
						} finally {
							done.countDown();
						}
					};
				} else {
					// Next stage is background
					next = this;
				}
			}
		}

		@Override
		public Runnable getNextStage() {
			return next;
		}

		void join() throws InterruptedException {
			done.await();
		}
	}

}
