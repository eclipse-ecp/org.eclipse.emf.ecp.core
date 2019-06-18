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
 * nicole.behlen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecp.view.internal.validation.ConcurrentLinkedSetQueue;
import org.junit.Test;

/**
 * JUnit to test the uniqueness functionality of {@link ConcurrentLinkedSetQueue}.
 *
 * @author nicole.behlen
 */
public class ConcurrentLinkedSetQueue_Test {

	@Test(expected = NullPointerException.class)
	public void offer_Null() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.offer(null);
		/* assert */
		// null pointer exception
	}

	@Test
	public void offer_NoRepeat() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		/* assert */
		assertEquals(3, queue.size());
	}

	@Test
	public void offer_Repeat() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.offer(1);
		queue.offer(2);
		queue.offer(2);
		/* assert */
		assertEquals(2, queue.size());
	}

	@Test
	public void offerPollAndOfferAgain() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.offer(1);
		queue.offer(2);
		queue.offer(2);
		final Integer firstPoll = queue.poll();
		queue.offer(1);
		final Integer secondPoll = queue.poll();
		final Integer thirdPoll = queue.poll();
		final Integer fourthPoll = queue.poll();
		/* assert */
		assertEquals((Integer) 1, firstPoll);
		assertEquals((Integer) 2, secondPoll);
		assertEquals((Integer) 1, thirdPoll);
		assertNull(fourthPoll);

		assertEquals(0, queue.size());
	}

	@Test
	public void addAll_RepeatedValues() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.addAll(Arrays.asList(1, 2, 3));
		queue.addAll(Arrays.asList(2, 3, 4));
		/* assert */
		assertEquals(4, queue.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAll_itself() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.addAll(queue);
		/* assert */
		// illegal argument exception
	}

	@Test
	public void removeObject_emptyNull() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		final boolean result = queue.remove(null);
		/* assert */
		assertFalse(result);
	}

	@Test
	public void removeObject_andAddAgain() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2));
		/* act */
		final boolean result = queue.remove(1);
		// to ensure that the value is added again
		queue.add(1);
		final Integer firstPoll = queue.poll(); // 2
		final Integer secondPoll = queue.poll(); // 1
		final Integer thirdPoll = queue.poll(); // null

		/* assert */
		assertTrue(result);
		assertEquals((Integer) 2, firstPoll);
		assertEquals((Integer) 1, secondPoll);
		assertNull(thirdPoll);
	}

	@Test
	public void remove_andAddAgain() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2));
		/* act */
		final Integer remove = queue.remove();
		// to ensure that the value is added again
		queue.add(1);
		final Integer firstPoll = queue.poll(); // 2
		final Integer secondPoll = queue.poll(); // 1
		final Integer thirdPoll = queue.poll(); // null

		/* assert */
		assertEquals((Integer) 1, remove);
		assertEquals((Integer) 2, firstPoll);
		assertEquals((Integer) 1, secondPoll);
		assertNull(thirdPoll);
	}

	@Test
	public void clear() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2));
		/* act */
		queue.clear();
		// to ensure that the value is added again
		queue.add(1);
		final Integer firstPoll = queue.poll(); // 1
		final Integer secondPoll = queue.poll(); // null
		/* assert */
		assertEquals((Integer) 1, firstPoll);
		assertNull(secondPoll);
	}

	@Test
	public void removeAll() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2));
		/* act */
		queue.removeAll(Arrays.asList(1));
		// to ensure that the value is added again
		queue.add(1);
		final Integer firstPoll = queue.poll(); // 2
		final Integer secondPoll = queue.poll(); // 1
		final Integer thirdPoll = queue.poll(); // null
		/* assert */
		assertEquals((Integer) 2, firstPoll);
		assertEquals((Integer) 1, secondPoll);
		assertNull(thirdPoll);
	}

	@Test
	public void retainAll() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2));
		/* act */
		queue.retainAll(Arrays.asList(2));
		// to ensure that the value is not added again
		queue.add(2);
		// to ensure that the value is added again
		queue.add(1);
		final Integer firstPoll = queue.poll(); // 2
		final Integer secondPoll = queue.poll(); // 1
		final Integer thirdPoll = queue.poll(); // null
		/* assert */
		assertEquals((Integer) 2, firstPoll);
		assertEquals((Integer) 1, secondPoll);
		assertNull(thirdPoll);
	}

	@Test(expected = IllegalStateException.class)
	public void iterator_remove_withoutNext() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		queue.addAll(Arrays.asList(1, 2, 3));
		/* act */
		final Iterator<Integer> iter = queue.iterator();
		iter.remove();
		/* assert */
		// illegal state exception
	}

	@Test(expected = NoSuchElementException.class)
	public void iterator_next_empty() {
		/* setup */
		final ConcurrentLinkedSetQueue<Integer> queue = new ConcurrentLinkedSetQueue<>();
		/* act */
		queue.iterator().next();
	}

}
