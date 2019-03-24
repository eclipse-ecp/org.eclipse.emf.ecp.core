/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * nicole.behlen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * An extension of {@link ConcurrentLinkedQueue} that additionally contains a set to avoid unnecessary
 * duplicated entries in cases of non-concurrent additions.
 * Duplication may still occur if multiple threads enqueue the same objects.
 *
 * @author nicole.behlen
 *
 * @param <E> the generic type of the elements to be queued.
 */
public class ConcurrentLinkedSetQueue<E> extends ConcurrentLinkedQueue<E> {

	private static final long serialVersionUID = 290189755090394151L;

	private final Set<Object> concurrentSet = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());

	/**
	 * Adds the object to the tail of this queue, if not already contained.
	 *
	 * @param e adds if not already contained the object to the tail of this queue
	 * @return <code>false</code> if object is already contained in queue, <code>true</code> otherwise.
	 * @throws NullPointerException - if the specified element is null
	 */
	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (!concurrentSet.contains(e)) {
			concurrentSet.add(e);
			return super.offer(e);
		}
		return false;
	}

	@Override
	public E poll() {
		final E next = super.poll();
		if (next != null) {
			concurrentSet.remove(next);
		}
		return next;
	}

	@Override
	public boolean remove(Object o) {
		if (o != null) {
			concurrentSet.remove(o);
		}
		return super.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == this) {
			// As historically specified in AbstractQueue#addAll
			throw new IllegalArgumentException();
		}

		final List<E> newColl = new ArrayList<>(c);
		newColl.removeAll(concurrentSet);
		concurrentSet.addAll(newColl);
		return super.addAll(newColl);
	}

	@Override
	public Iterator<E> iterator() {
		final Iterator<E> iter = super.iterator();
		return new IterWithRemove<>(iter);
	}

	/**
	 *
	 * Iterator over the elements of this queue in proper sequence, that removes
	 * the elements from the {@link ConcurrentLinkedSetQueue#concurrentSet}.
	 *
	 * @author nicole.behlen
	 *
	 * @param <I> the generic type of the elements to iterate.
	 */
	private class IterWithRemove<I> implements Iterator<I> {

		private final Iterator<I> delegate;

		private I lastReturnedElement;

		/**
		 * Constructor setting the original iterator.
		 */
		IterWithRemove(Iterator<I> delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean hasNext() {
			return this.delegate.hasNext();
		}

		@Override
		public I next() {
			if (this.delegate.hasNext()) {
				lastReturnedElement = this.delegate.next();
				return lastReturnedElement;
			}
			lastReturnedElement = null;
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			if (lastReturnedElement != null) {
				concurrentSet.remove(lastReturnedElement);
				lastReturnedElement = null;
			}
			delegate.remove();
		}
	}
}