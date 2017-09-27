/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.core.swt.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecp.view.internal.core.swt.TimeBoundStringBuffer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link TimeBoundStringBuffer}.
 *
 */
public class TimeBoundStringBuffer_Test {

	private TimeBoundStringBuffer buffer;

	@Before
	public void beforeEach() {
		buffer = new TimeBoundStringBuffer(TimeUnit.MILLISECONDS.toMillis(10));
	}

	@Test
	public void asString() {
		buffer.addLast('a');
		buffer.addLast('b');
		buffer.addLast('c');
		assertEquals("abc", buffer.asString());
	}

	@Test
	public void isEmpty() {
		assertTrue(buffer.isEmpty());
	}

	@Test
	public void isNonEmpty() {
		buffer.addLast('a');
		assertFalse(buffer.isEmpty());
	}

	@Test
	public void removeLast() {
		buffer.addLast('a');
		buffer.removeLast();
		assertTrue(buffer.isEmpty());
	}

	@Test
	public void removeLastNoop() {
		buffer.removeLast();
	}

	@Test
	public void reset() {
		buffer.addLast('a');
		buffer.reset();
		assertTrue(buffer.isEmpty());
	}

	@Test
	public void didNotTimeOut() {
		buffer.addLast('a');
		assertFalse(buffer.timedOut());
	}

	@Test
	public void timeOut() throws InterruptedException {
		buffer.addLast('a');
		TimeUnit.MILLISECONDS.sleep(11);
		assertTrue(buffer.timedOut());
	}

	@Test
	public void doNotResetIfNotTimedOut() throws InterruptedException {
		buffer.addLast('a');
		TimeUnit.MILLISECONDS.sleep(5);
		buffer.resetIfTimedOut();
		assertFalse(buffer.isEmpty());
	}

	@Test
	public void resetIfTimedOut() throws InterruptedException {
		buffer.addLast('a');
		TimeUnit.MILLISECONDS.sleep(11);
		buffer.resetIfTimedOut();
		assertFalse(buffer.timedOut());
		assertTrue(buffer.isEmpty());
	}

	@Test
	public void defaultBound() throws InterruptedException {
		buffer = new TimeBoundStringBuffer();
		buffer.addLast('a');
		TimeUnit.MILLISECONDS.sleep(100);
		assertFalse(buffer.timedOut());
	}
}
