/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emfforms.bazaar.internal.BazaarImpl;
import org.eclipse.emfforms.bazaar.internal.ThreadSafeBazaar;

/**
 * A Bazaar is a flexible registry for factories to create specific objects of type T, called "product". To create a
 * product,
 * an arbitrary number of {@link Vendor} is queried. {@link Vendor} request certain parameters to create a product.
 * This is queried from the {@link BazaarContext}. If a {@link AllPermission} parameters requested by a
 * {@link Vendor} are present in the {@link BazaarContext}, a {@link Vendor} will do a {@link Bid}. The
 * {@link Vendor} with the highest {@link Bid} will finally create a product.
 *
 * If two or more {@link Vendor} do the same bid, the registered {@link PriorityOverlapCallBack} will be notified,
 * an arbitrary {@link Vendor} with the same {@link Bid} will create the product in this case.
 *
 * To transform parameter in the {@link BazaarContext} into parameters {@link Vendor}s expect, you can register
 * {@link BazaarContextFunction}s at the {@link Bazaar}.
 *
 * @param <T> the type of product create by this Bazaar
 * @author jonas
 *
 */
public interface Bazaar<T> {

	/**
	 * Adds a {@link Vendor} to the bazaar. Will be queried if a product is requested, the best fitting will create the
	 * product. Has no effect if the {@code vendor} is already present in this bazaar.
	 *
	 * @param vendor the {@link Vendor}
	 */
	void addVendor(Vendor<? extends T> vendor);

	/**
	 * Removes a {@link Vendor} from the bazaar. Has no effect if the {@code vendor} is not present in this bazaar.
	 *
	 * @param vendor the {@link Vendor} to remove
	 */
	void removeVendor(Vendor<? extends T> vendor);

	/**
	 * Adds a {@link BazaarContextFunction} to this {@link Bazaar} to exchange existing parameters to a parameter
	 * requested by a {@link Vendor}.
	 *
	 * @param key the key of a requested parameter, which can be exchanged from other available parameters in the
	 *            {@link BazaarContext} by this {@link BazaarContextFunction}
	 * @param contextFunction the {@link BazaarContextFunction} being able to exchange to the requested parameter
	 */
	void addContextFunction(String key, BazaarContextFunction contextFunction);

	/**
	 * Creates a product of type T, provided by the {@link Vendor} with the highest {@link Bid} and which is statisfied
	 * by the parameters in the {@link BazaarContext}. In case of tied bids, the {@link PriorityOverlapCallBack}
	 * if one is set will be notified of which vendor is chosen to break the tie. Note that during the
	 * bidding process, ties may have to be broken in this way that are later defeated by a higher bid.
	 *
	 * @param context the {@link BazaarContext}, which is used to provide requested parameters for {@link Vendor}
	 * @return the product provided by the best {@link Vendor}
	 *
	 * @see #setPriorityOverlapCallBack(PriorityOverlapCallBack)
	 */
	T createProduct(BazaarContext context);

	/**
	 * Creates a list of products of type T, provided by {@link Vendor}s which are statisfied by the parameters in the
	 * {@link BazaarContext}, ordered by their {@link Bid}. Ties are not broken as all bids are successful
	 * and are used only for ordering.
	 *
	 * @param context the {@link BazaarContext}, which is used to provide requested parameters for {@link Vendor}
	 * @return a list of products ordered by the highest {@link Bid}
	 */
	List<T> createProducts(BazaarContext context);

	/**
	 * Adds a {@link PriorityOverlapCallBack}, see {@link PriorityOverlapCallBack}.
	 *
	 * @param priorityOverlapCallBack a PriorityOverlapCallBack
	 */
	void setPriorityOverlapCallBack(PriorityOverlapCallBack<? super T> priorityOverlapCallBack);

	//
	// Nested types
	//

	/**
	 * If two or more {@link Vendor}s make the same bid, the registered
	 * {@link org.eclipse.emfforms.bazaar.Bazaar.PriorityOverlapCallBack} will be
	 * notified,
	 * an arbitrary {@link Vendor} with the same {@link Bid} will create the product in this case.
	 *
	 * @param <T> the type of product create by this Bazaar
	 * @author jonas
	 *
	 */
	public interface PriorityOverlapCallBack<T> {
		/**
		 * Will be called if two {@link Vendor}s do the same bid.
		 *
		 * @param winner The {@link Vendor} who will win and create the product
		 * @param overlapping Another {@link Vendor} with the same bid, but who will not create the product
		 */

		void priorityOverlap(Vendor<? extends T> winner, Vendor<? extends T> overlapping);

	}

	/**
	 * A fluent <em>builder</em> of {@link Bazaar}s.
	 *
	 * @author Christian W. Damus
	 *
	 * @param <T> the type of product provided by the bazaar
	 */
	final class Builder<T> {
		private final List<Vendor<? extends T>> vendors = new ArrayList<Vendor<? extends T>>();
		private final Map<String, BazaarContextFunction> contextFunctions = new HashMap<String, BazaarContextFunction>();
		private PriorityOverlapCallBack<? super T> overlapHandler;
		private boolean isThreadSafe;

		/**
		 * Creates a new empty bazaar builder.
		 */
		private Builder() {
			super();
		}

		/**
		 * Creates a new context builder with the given initial {@code vendors}.
		 *
		 * @param vendors initial vendors
		 */
		private Builder(Collection<? extends Vendor<? extends T>> vendors) {
			super();

			this.vendors.addAll(vendors);
		}

		/**
		 * Creates a new empty bazaar builder.
		 *
		 * @param <T> the type of product provided by the {@link Bazaar}
		 * @return an initially empty builder
		 */
		public static <T> Builder<T> empty() {
			return new Builder<T>();
		}

		/**
		 * Creates a new bazaar builder with the given initial {@code vendors}.
		 *
		 * @param <T> the type of product provided by the {@link Bazaar}
		 *
		 * @param vendors initial vendors
		 * @return an initialized builder
		 */
		public static <T> Builder<T> with(Collection<? extends Vendor<? extends T>> vendors) {
			return new Builder<T>(vendors);
		}

		/**
		 * Adds a vendor.
		 *
		 * @param vendor the vendor to add
		 *
		 * @return this builder
		 */
		public Builder<T> add(Vendor<? extends T> vendor) {
			vendors.add(vendor);
			return this;
		}

		/**
		 * Adds vendors.
		 *
		 * @param vendor1 a vendor to add
		 * @param vendor2 another vendor to add
		 * @param more optional additional vendors to add
		 *
		 * @return this builder
		 */
		// @SafeVarargs (not available in JDK 1.6)
		public Builder<T> add(Vendor<? extends T> vendor1, Vendor<? extends T> vendor2, Vendor<? extends T>... more) {
			vendors.add(vendor1);
			vendors.add(vendor2);
			if (more.length > 0) {
				vendors.addAll(Arrays.asList(more));
			}
			return this;
		}

		/**
		 * Adds vendors.
		 *
		 * @param vendors vendors to add
		 *
		 * @return this builder
		 */
		public Builder<T> addAll(Collection<? extends Vendor<? extends T>> vendors) {
			this.vendors.addAll(vendors);
			return this;
		}

		/**
		 * Add a context function.
		 *
		 * @param type the context value type provided by the function
		 * @param contextFunction the context function to add
		 * @return this {@link org.eclipse.emfforms.bazaar.Bazaar.Builder}
		 */
		public Builder<T> addContextFunction(Class<?> type, BazaarContextFunction contextFunction) {
			return addContextFunction(type.getName(), contextFunction);
		}

		/**
		 * Add a context function.
		 *
		 * @param key the context key for the function
		 * @param contextFunction the context function to add
		 * @return this {@link org.eclipse.emfforms.bazaar.Bazaar.Builder}
		 */
		public Builder<T> addContextFunction(String key, BazaarContextFunction contextFunction) {
			contextFunctions.put(key, contextFunction);
			return this;
		}

		/**
		 * Set the handler for priority overlaps. This may only be set once.
		 *
		 * @param overlapHandler the overlap handler to set
		 *
		 * @return this builder
		 */
		public Builder<T> onPriorityOverlap(PriorityOverlapCallBack<? super T> overlapHandler) {
			if (this.overlapHandler != null) {
				throw new IllegalStateException("overlap handler already set"); //$NON-NLS-1$
			}
			this.overlapHandler = overlapHandler;
			return this;
		}

		/**
		 * Request that the bazaar be thread-safe. This is useful for bazaars that
		 * may be accessed arbitrarily by concurrent threads. By default, the builder
		 * creates bazaars that are not thread-safe.
		 *
		 * @return this builder
		 */
		public Builder<T> threadSafe() {
			this.isThreadSafe = true;
			return this;
		}

		/**
		 * Create the bazaar. Further updates to the builder will have
		 * no effect on the resulting bazaar.
		 *
		 * @return the bazaar
		 */
		public Bazaar<T> build() {
			final Bazaar<T> result;
			if (isThreadSafe) {
				result = new ThreadSafeBazaar<T>(vendors, contextFunctions, overlapHandler);
			} else {
				result = new BazaarImpl<T>();

				for (final Vendor<? extends T> vendor : vendors) {
					result.addVendor(vendor);
				}
				for (final Map.Entry<String, BazaarContextFunction> entry : contextFunctions.entrySet()) {
					result.addContextFunction(entry.getKey(), entry.getValue());
				}
				if (overlapHandler != null) {
					result.setPriorityOverlapCallBack(overlapHandler);
				}
			}

			return result;
		}
	}

}
