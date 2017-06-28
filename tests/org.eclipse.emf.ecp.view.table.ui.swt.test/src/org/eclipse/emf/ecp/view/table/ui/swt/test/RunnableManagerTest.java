/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.ecp.view.internal.table.swt.RunnableManager;
import org.eclipse.swt.widgets.Display;
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

}
