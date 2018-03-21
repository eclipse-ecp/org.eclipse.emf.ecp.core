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

import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Enumeration of {@link Bazaar} variants, especially for creation of
 * test subjects.
 *
 * @author Christian W. Damus
 */
public enum BazaarVariant {
	/** The base implementation. */
	BASE,
	/** The thread-safe implementation. */
	THREAD_SAFE;

	/**
	 * Create an internal bazaar implementation to test.
	 *
	 * @return the bazaar implementation
	 *
	 * @throws IllegalArgumentException if my variant is not a kind of {@link BazaarImpl}
	 *             but some other kind of {@link Bazaar}
	 */
	public <T> BazaarImpl<T> createBazaarImpl() {
		switch (this) {
		case BASE:
			return new BazaarImpl<T>();
		case THREAD_SAFE:
			return new ThreadSafeBazaar<T>();
		default:
			throw new IllegalArgumentException("no such BazaarImpl: " + this); //$NON-NLS-1$
		}
	}

	/**
	 * Create a bazaar builder to test.
	 *
	 * @return the bazaar builder
	 */
	public <T> Bazaar.Builder<T> builder() {
		switch (this) {
		case BASE:
			return Bazaar.Builder.empty();
		case THREAD_SAFE:
			return Bazaar.Builder.<T> empty().threadSafe();
		default:
			throw new IllegalArgumentException("no such builder: " + this); //$NON-NLS-1$
		}
	}

	/**
	 * Create a bazaar builder to test, with initial vendors.
	 *
	 * @return the bazaar builder
	 */
	public <T> Bazaar.Builder<T> builder(Collection<? extends Vendor<? extends T>> vendors) {
		switch (this) {
		case BASE:
			return Bazaar.Builder.with(vendors);
		case THREAD_SAFE:
			return Bazaar.Builder.with(vendors).threadSafe();
		default:
			throw new IllegalArgumentException("no such builder: " + this); //$NON-NLS-1$
		}
	}

	@Override
	public String toString() {
		return name().toLowerCase().replace('_', '-');
	}
}
