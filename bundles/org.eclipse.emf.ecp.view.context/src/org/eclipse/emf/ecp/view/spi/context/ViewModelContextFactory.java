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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bugs 527740, 545686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.context.ArrayOnceViewModelServiceProvider;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This Factory can be used to instantiate {@link ViewModelContext ViewModelContexts}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public final class ViewModelContextFactory {
	/**
	 * The singleton instance of the factory.
	 */
	public static final ViewModelContextFactory INSTANCE = new ViewModelContextFactory();

	private ViewModelContextFactory() {
	}

	/**
	 * Instantiates a new view model context.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @return the created {@link ViewModelContext}
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject) {
		return new ViewModelContextImpl(view, domainObject);
	}

	/**
	 * Instantiates a new view model context with specific services. Note that this is useful for
	 * services that are not registered externally (via extension point or OSGi). If any of these
	 * services locally override registered implementations of the same interface, then it is better
	 * to use a {@link ViewModelServiceProvider} that can propagate the override to child contexts.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 * @return the created {@link ViewModelContext}
	 *
	 * @see #createViewModelContext(VElement, EObject, ViewModelServiceProvider)
	 * @see ViewModelContext#getChildContext(EObject, VElement, org.eclipse.emf.ecp.view.spi.model.VView,
	 *      ViewModelService...)
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject,
		ViewModelService... modelServices) {
		return createViewModelContext(view, domainObject, new ArrayOnceViewModelServiceProvider(modelServices));
	}

	/**
	 * Instantiates a new view model context with a provider of local service overrides.
	 * The {@code serviceProvider} is propagated to child contexts to override registered
	 * services in their scope, too.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param serviceProvider a provider of local view-model services to override any
	 *            statically registered services of the same types. May be {@code null} if
	 *            local service overrides are not needed
	 * @return the created {@link ViewModelContext}
	 *
	 * @since 1.16
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject,
		ViewModelServiceProvider serviceProvider) {
		return new ViewModelContextImpl(view, domainObject, serviceProvider);
	}

	/**
	 * Instantiates a new view model context with initial {@linkplain ViewModelContext#getContextValue(String) context
	 * values}.
	 * The {@code serviceProvider} is propagated to child contexts to override registered
	 * services in their scope, too.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param contextValues initial context values to set
	 * @return the created {@link ViewModelContext}
	 *
	 * @since 1.21
	 * @see ViewModelContext#getContextValue(String)
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject,
		Map<String, ?> contextValues) {

		return new ViewModelContextImpl(view, domainObject, contextValues);
	}

	/**
	 * Instantiates a new view model context with a provider of local service overrides and
	 * initial {@linkplain ViewModelContext#getContextValue(String) context values}.
	 * The {@code serviceProvider} is propagated to child contexts to override registered
	 * services in their scope, too.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param serviceProvider a provider of local view-model services to override any
	 *            statically registered services of the same types. May be {@code null} if
	 *            local service overrides are not needed
	 * @param contextValues initial context values to set
	 * @return the created {@link ViewModelContext}
	 *
	 * @since 1.21
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject,
		ViewModelServiceProvider serviceProvider, Map<String, ?> contextValues) {

		return new ViewModelContextImpl(view, domainObject, serviceProvider, contextValues);
	}

}
