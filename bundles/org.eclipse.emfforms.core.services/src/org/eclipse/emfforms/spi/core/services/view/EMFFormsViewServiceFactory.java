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

/**
 * An {@link EMFFormsViewServiceFactory} defines where and how the service will be available. It also creates the
 * concrete service instance on demand.
 *
 * @param <T> The type of the provided service
 *
 * @author Eugen Neufeld
 * @since 1.8
 */
public interface EMFFormsViewServiceFactory<T> {

	/**
	 * Defines when the service should be activated.
	 *
	 * @return The {@link EMFFormsViewServicePolicy} describing when the service should be activated
	 */
	EMFFormsViewServicePolicy getPolicy();

	/**
	 * Defines where the service should be activated.
	 *
	 * @return The {@link EMFFormsViewServiceScope} describing where the service should be activated
	 */
	EMFFormsViewServiceScope getScope();

	/**
	 * The priority of the service. A service with a higher priority is more likely to be used if more than one service
	 * of the same type is registered.
	 *
	 * @return The priority of this service
	 */
	double getPriority();

	/**
	 * The type of the actual service provided by this provider.
	 *
	 * @return The Class of the actual service
	 */
	Class<T> getType();

	/**
	 * Creates a new instance of the provided service.
	 *
	 * @param emfFormsViewContext The {@link EMFFormsViewContext} to use during the creation of the service
	 * @return A new instance of the provided service
	 */
	T createService(EMFFormsViewContext emfFormsViewContext);

}
