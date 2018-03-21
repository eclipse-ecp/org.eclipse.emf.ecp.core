/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 527740
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A provider of local {@link ViewModelService}s to inject into the {@link ViewModelContext}
 * that override statically registered services in it and its children, recursively down
 * the context hierarchy.
 *
 * @since 1.16
 */
public interface ViewModelServiceProvider {

	/**
	 * A provider of no local service overrides.
	 */
	ViewModelServiceProvider NULL = new ViewModelServiceProvider() {
		@Override
		public Collection<? extends ViewModelService> getViewModelServices(VElement view, EObject eObject) {
			return Collections.emptySet();
		}
	};

	/**
	 * Returns newly created view model services which will be used when the given view for the given object is
	 * rendered.
	 *
	 * @param view the view to be rendered
	 * @param eObject the object to be rendered
	 * @return the services, or an empty collection if none (not {@code null})
	 */
	Collection<? extends ViewModelService> getViewModelServices(VElement view, EObject eObject);

	/**
	 * A composition of two {@link ViewModelServiceProvider}s. Any services provided
	 * by the first composed provider override services of the same type provided by the second.
	 * Services in the second made redundant by this mechanism are immediately
	 * {@linkplain ViewModelService#dispose() disposed}.
	 */
	final class Composed implements ViewModelServiceProvider {
		private final ViewModelServiceProvider first;
		private final ViewModelServiceProvider second;

		/**
		 * Initializes me with two service providers to compose. Any services provided
		 * by the {@code first} override services of the same type provided by the {@code second}.
		 * Services in the {@code second} made redundant by this mechanism are immediately
		 * {@linkplain ViewModelService#dispose() disposed}.
		 *
		 * @param first a provider of view-model services to compose
		 * @param second another provider
		 */
		public Composed(ViewModelServiceProvider first, ViewModelServiceProvider second) {
			super();

			this.first = first;
			this.second = second;
		}

		@Override
		public Collection<? extends ViewModelService> getViewModelServices(VElement view, EObject eObject) {
			final Map<Class<?>, ViewModelService> result = new LinkedHashMap<Class<?>, ViewModelService>();

			provide(first, view, eObject, result);
			provide(second, view, eObject, result);

			return result.values();
		}

		private void provide(ViewModelServiceProvider provider, VElement view, EObject eObject,
			Map<Class<?>, ViewModelService> result) {
			for (final ViewModelService next : provider.getViewModelServices(view, eObject)) {
				if (result.containsKey(next.getClass())) {
					next.dispose();
				} else {
					result.put(next.getClass(), next);
				}
			}
		}
	}
}