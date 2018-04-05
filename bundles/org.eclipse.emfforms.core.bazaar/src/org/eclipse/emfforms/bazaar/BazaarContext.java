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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfforms.bazaar.internal.BazaarContextImpl;

/**
 * Provides the necessary parameters, the {@link Vendor} of a {@link Bazaar} might request. Parameters are provided
 * in a {@link Map} from their key to their value. Those parameters can be adapted to other ones by
 * {@link BazaarContextFunction}s
 *
 */
public interface BazaarContext {

	/**
	 * Returns a map containing the parameters available for {@link Vendor}s on a {@link Bazaar}. The key is either
	 * an id or the full class name. the value is the parameter which will be provided to {@link Vendor}s
	 *
	 * @return A {@link Map} containing the parameters for {@link Vendor} from (key|full class name) to the object
	 *         as value.
	 */
	Map<String, Object> getContextMap();

	//
	// Nested types
	//

	/**
	 * A fluent <em>builder</em> of {@link BazaarContext}s.
	 *
	 * @author Christian W. Damus
	 */
	final class Builder {
		private final Map<String, Object> contextMap = new HashMap<String, Object>();

		/**
		 * Creates a new empty context builder.
		 */
		private Builder() {
			super();
		}

		/**
		 * Creates a new context builder with the given initial {@code values}.
		 *
		 * @param values initial context values
		 */
		private Builder(Map<String, ?> values) {
			super();

			contextMap.putAll(values);
		}

		/**
		 * Creates a new empty context builder.
		 *
		 * @return an initially empty builder
		 */
		public static Builder empty() {
			return new Builder();
		}

		/**
		 * Creates a new context builder with the given initial {@code values}.
		 *
		 * @param values initial context values
		 * @return an initialized builder
		 */
		public static Builder with(Map<String, ?> values) {
			return new Builder(values);
		}

		/**
		 * Adds a context value of the given type.
		 *
		 * @param key the context {@code value}'s type
		 * @param value the context value
		 *
		 * @return this builder
		 *
		 * @param <T> the value type
		 */
		public <T> Builder put(Class<T> key, T value) {
			return put(key.getName(), value);
		}

		/**
		 * Adds a self-typed context value.
		 *
		 * @param value the context value to add
		 *
		 * @return this builder
		 *
		 * @see #put(Class, Object)
		 *
		 * @throws NullPointerException if the {@code value} is {@code null}
		 */
		public Builder add(Object value) {
			return put(value.getClass().getName(), value);
		}

		/**
		 * Adds a context value for the given name, such as might be used with
		 * {@linkplain javax.inject.Named named injection}.
		 *
		 * @param name the context {@code value}'s name
		 * @param value the context value
		 *
		 * @return this builder
		 *
		 * @see javax.inject.Named
		 */
		public Builder put(String name, Object value) {
			contextMap.put(name, value);
			return this;
		}

		/**
		 * Create the bazaar context. Further updates to the builder will have
		 * no effect on the resulting context.
		 *
		 * @return the bazaar context
		 */
		public BazaarContext build() {
			return new BazaarContextImpl(contextMap);
		}
	}
}
