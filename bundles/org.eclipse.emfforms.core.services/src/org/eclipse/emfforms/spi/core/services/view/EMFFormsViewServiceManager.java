/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

import java.util.Set;

import org.eclipse.emfforms.common.Optional;

/**
 * This Factory provides access methods to retrieve a service of a specific scope.
 *
 * @author Eugen Neufeld
 * @since 1.8
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface EMFFormsViewServiceManager {

	/**
	 * Return a local service which should be activated immediately. If no service of the requested type is available
	 * null will be returned.
	 *
	 * @param <T> The type parameter of the service
	 * @param type The Type of the requested service
	 * @param emfFormsViewContext The {@link EMFFormsViewContext} to use
	 * @return An optional instance of the requested service registered for this scope.
	 */
	<T> Optional<T> createLocalImmediateService(Class<T> type, EMFFormsViewContext emfFormsViewContext);

	/**
	 * Return a local service which should be activated on request. If no service of the requested type is available
	 * null will be returned.
	 *
	 * @param <T> The type parameter of the service
	 * @param type The Type of the requested service
	 * @param emfFormsViewContext The {@link EMFFormsViewContext} to use
	 * @return An optional instance of the requested service registered for this scope.
	 */
	<T> Optional<T> createLocalLazyService(Class<T> type, EMFFormsViewContext emfFormsViewContext);

	/**
	 * Return a global service which should be activated immediately. If no service of the requested type is available
	 * null will be returned.
	 *
	 * @param <T> The type parameter of the service
	 * @param type The Type of the requested service
	 * @param emfFormsViewContext The {@link EMFFormsViewContext} to use
	 * @return An optional instance of the requested service registered for this scope.
	 */
	<T> Optional<T> createGlobalImmediateService(Class<T> type, EMFFormsViewContext emfFormsViewContext);

	/**
	 * Return a global service which should be activated on request. If no service of the requested type is available
	 * null will be returned.
	 *
	 * @param <T> The type parameter of the service
	 * @param type The Type of the requested service
	 * @param emfFormsViewContext The {@link EMFFormsViewContext} to use
	 * @return An optional instance of the requested service registered for this scope.
	 */
	<T> Optional<T> createGlobalLazyService(Class<T> type, EMFFormsViewContext emfFormsViewContext);

	/**
	 * Returns all registered services which are global immediate ordered by the priority.
	 *
	 * @return The Set of all services which are global immediate. This set cannot be null
	 */
	Set<Class<?>> getAllGlobalImmediateServiceTypes();

	/**
	 * Returns all registered services which are local immediate ordered by the priority.
	 *
	 * @return The Set of all services which are local immediate. This set cannot be null
	 */
	Set<Class<?>> getAllLocalImmediateServiceTypes();
}
