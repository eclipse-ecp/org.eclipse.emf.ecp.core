/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

/**
 * Requesting an instance of this will read in {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService
 * ViewModelServices} registered using an extension point and provide
 * them as {@link org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory
 * EMFFormsScopedServiceProviders}.
 * There are no methods defined as this only is a marker so that the service doesn't start by itself.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
public interface EMFFormsLegacyServicesManager {

	/**
	 * Instantiates the ViewModelServices.
	 */
	void instantiate();
}
