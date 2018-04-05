/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A thread-safe implementation of the {@link Bazaar}.
 * It supports any number of threads concurrently creating products,
 * to the exclusion of at most one thread changing the bazaar's configuration
 * (adding/removing vendors, context functions, and so on).
 *
 * @param <T> the type of product produced by this {@link Bazaar}
 *
 * @author Christian W. Damus
 */
public final class ThreadSafeBazaar<T> extends BazaarImpl<T> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Initializes me.
	 */
	public ThreadSafeBazaar() {
		super();
	}

	/**
	 * Initializes me with an initial configuration.
	 *
	 * @param vendors optional vendors to add (may be {@code null} if not needed)
	 * @param contextFunctions optional context functions to add (may be {@code null} if not needed)
	 * @param priorityOverlapCallBack optional overlap call-back to set (may be {@code null} if not needed)
	 */
	public ThreadSafeBazaar(Collection<? extends Vendor<? extends T>> vendors,
		Map<String, ? extends BazaarContextFunction> contextFunctions,
		PriorityOverlapCallBack<? super T> priorityOverlapCallBack) {

		super();

		// Don't need synchronization for an object that is under construction
		// as it is not yet available to other threads
		if (vendors != null) {
			for (final Vendor<? extends T> next : vendors) {
				super.addVendor(next);
			}
		}
		if (contextFunctions != null) {
			for (final Map.Entry<String, ? extends BazaarContextFunction> next : contextFunctions.entrySet()) {
				super.addContextFunction(next.getKey(), next.getValue());
			}
		}
		super.setPriorityOverlapCallBack(priorityOverlapCallBack);
	}

	//
	// Queries
	//

	@Override
	public T createProduct(BazaarContext bazaarContext) {
		lock.readLock().lock();

		try {
			return super.createProduct(bazaarContext);
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public List<T> createProducts(BazaarContext bazaarContext) {
		lock.readLock().lock();

		try {
			return super.createProducts(bazaarContext);
		} finally {
			lock.readLock().unlock();
		}
	}

	//
	// Mutations
	//

	@Override
	public void addVendor(Vendor<? extends T> vendor) {
		lock.writeLock().lock();

		try {
			super.addVendor(vendor);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void removeVendor(Vendor<? extends T> vendor) {
		lock.writeLock().lock();

		try {
			super.removeVendor(vendor);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void addContextFunction(String key, BazaarContextFunction contextFunction) {
		lock.writeLock().lock();

		try {
			super.addContextFunction(key, contextFunction);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void setPriorityOverlapCallBack(PriorityOverlapCallBack<? super T> priorityOverlapCallBack) {
		lock.writeLock().lock();

		try {
			super.setPriorityOverlapCallBack(priorityOverlapCallBack);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
