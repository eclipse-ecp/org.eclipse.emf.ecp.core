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
package org.eclipse.emfforms.spi.swt.core;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;

/**
 * The EMFFormsRendererService encapsulates the tester and the access to the {@link AbstractSWTRenderer}.
 *
 * @author Eugen Neufeld
 *
 * @param <VELEMENT>
 */
public interface EMFFormsRendererService<VELEMENT extends VElement> {

	/**
	 * Constant for {@link #isApplicable(VElement, ViewModelContext)} to indicate, that the EMFFormsRendererService
	 * cannot provide a fitting renderer for the provided VElement.
	 */
	double NOT_APPLICABLE = Double.NaN;

	/**
	 * Check whether the provided {@link VElement} can be rendered by the {@link AbstractSWTRenderer} provided by
	 * {@link #getRendererInstance(VElement, ViewModelContext)}.
	 *
	 * @param vElement The {@link VElement} to check
	 * @param viewModelContext The {@link ViewModelContext} to use for the renderer service
	 * @return {@link #NOT_APPLICABLE} if the renderer doesn't fit, a positive value otherwise
	 */
	double isApplicable(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns a renderer.
	 * 
	 * @param vElement The {@link VElement} to create the renderer instance for
	 * @param viewModelContext The {@link ViewModelContext} to use for the renderer instance
	 * @return The AbstractSWTRenderer
	 */
	AbstractSWTRenderer<VELEMENT> getRendererInstance(VELEMENT vElement, ViewModelContext viewModelContext);
}
