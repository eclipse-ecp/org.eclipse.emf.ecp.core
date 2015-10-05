/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * The EMFFormsViewService allows to add and remove {@link IViewProvider} as well as to retrieve a {@link VView} based
 * on an {@link EObject} and a {@link VViewModelProperties}.
 *
 * @author Eugen Neufeld
 * @since 1.7
 *
 */
public interface EMFFormsViewService {

	/**
	 * Add an {@link IViewProvider}.
	 *
	 * @param viewProvider The {@link IViewProvider} to be added
	 */
	void addProvider(IViewProvider viewProvider);

	/**
	 * Remove an {@link IViewProvider}.
	 *
	 * @param viewProvider The {@link IViewProvider} to be removed
	 */
	void removeProvider(IViewProvider viewProvider);

	/**
	 * This allows to retrieve a {@link VView} based on an {@link EObject}. This method reads all {@link IViewProvider
	 * IViewProviders} and searches for the best fitting. If none can be found, then null is returned.
	 *
	 * @param eObject the {@link EObject} to find a {@link VView} for
	 * @param properties the {@link VViewModelProperties properties}
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 */
	VView getView(EObject eObject, VViewModelProperties properties);

}
