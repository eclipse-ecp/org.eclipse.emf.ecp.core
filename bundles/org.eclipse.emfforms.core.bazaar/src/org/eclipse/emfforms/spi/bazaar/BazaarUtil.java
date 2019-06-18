/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.bazaar;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.StaticBid;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Utility class providing common functionality for the creation of {@link Bazaar Bazaars}.
 *
 * @author Lucas Koehler
 *
 */
public final class BazaarUtil {
	/**
	 * Prevent instantiation of this utility class.
	 */
	private BazaarUtil() {
	}

	/**
	 * Creates a new {@link Bazaar} initialized with a given default product. The created bazaar has a vendor which can
	 * always provide the default product. Thereby, this vendor always bids the lowest valid amount.
	 *
	 * @param defaultProduct The default product which can always be created
	 * @param <T> The type of the bazaar's created products.
	 * @return The created {@link Bazaar}
	 */
	public static <T> Bazaar<T> createBazaar(T defaultProduct) {
		final Bazaar.Builder<T> builder = builder(defaultProduct);
		return builder.build();
	}

	private static <T> Bazaar.Builder<T> builder(final T defaultStrategy) {
		/**
		 * A vendor of the default, that tries to lose every bid.
		 */
		@StaticBid(bid = Double.NEGATIVE_INFINITY)
		class DefaultVendor implements Vendor<T> {
			/**
			 * Return the default strategy.
			 *
			 * @return the default strategy
			 */
			@Create
			public T createDefault() {
				return defaultStrategy;
			}
		}

		final Bazaar.Builder<T> result = Bazaar.Builder.empty();
		return result.threadSafe().add(new DefaultVendor());
	}

	/**
	 * Creates a basic {@link org.eclipse.emfforms.bazaar.BazaarContext.Builder Bazaar Context Builder} with the given
	 * initial values.
	 *
	 * @param properties {@link Dictionary} containing the initial values
	 * @return The created {@link org.eclipse.emfforms.bazaar.BazaarContext.Builder BazaarContext.Builder}
	 */
	public static BazaarContext.Builder createBaseContext(Dictionary<String, ?> properties) {
		return BazaarContext.Builder.with(adapt(properties));
	}

	/**
	 * Adapt a {@code dictionary} as a map.
	 *
	 * @param dictionary a dictionary
	 * @return the {@code dictionary}, as a map
	 */
	private static Map<String, ?> adapt(Dictionary<String, ?> dictionary) {
		// The OSGi implementation of the read-only properties for all sorts of
		// things is a map
		if (dictionary instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			final Map<String, ?> result = (Map<String, ?>) dictionary;
			return result;
		}

		final Map<String, Object> result = new HashMap<String, Object>();
		for (final Enumeration<String> keys = dictionary.keys(); keys.hasMoreElements();) {
			final String next = keys.nextElement();
			result.put(next, dictionary.get(next));
		}
		return result;
	}
}
