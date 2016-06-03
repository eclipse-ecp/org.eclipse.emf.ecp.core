/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * This interface defines a generic way to provide a {@link VView}. First the can render method is called. The provider
 * with the highest priority is then asked to {@link #provideViewModel(EObject, VViewModelProperties)} a {@link VView}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public interface IViewProvider {

	/**
	 * Constant indicating, that the provider cannot provide a {@link VView} for a specific {@link EObject}.
	 */
	double NOT_APPLICABLE = -1;

	/**
	 * Called to check whether the provider can provide a {@link VView} for an {@link EObject}.
	 *
	 * @param eObject the {@link EObject} to create a
	 * @param properties the {@link VViewModelProperties properties} for providing the view
	 * @return a <b>positive</b> double indicating how well this provider is fitted to provide a {@link VView} for the
	 *         provided {@link EObject} or {@link #NOT_APPLICABLE} if it doesn't fit
	 * @since 1.7
	 */
	double canProvideViewModel(EObject eObject, VViewModelProperties properties);

	/**
	 * This method is only called if {@link #canProvideViewModel(EObject, VViewModelProperties)} returned the highest
	 * positive
	 * number of all {@link IViewProvider IViewProviders}.
	 * It must then return a {@link VView} to the {@link EObject}.
	 *
	 * @param eObject the {@link EObject} to generate the {@link VView} for
	 * @param properties the {@link VViewModelProperties properties} for providing the view
	 * @return the generated {@link VView}
	 * @since 1.7
	 */
	VView provideViewModel(EObject eObject, VViewModelProperties properties);
}
