/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.di;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

/**
 * This class defines a renderer service interface for dependency injection renderer services.
 * It defines the {@link #isApplicable(VElement, ViewModelContext)} method to check if the represented renderer is
 * suitable for the given {@link VElement} and {@link ViewModelContext} and the {@link #getRendererClass()} method that
 * returns the {@link Class} of the renderer.
 *
 * @author Lucas Koehler
 *
 * @param <VELEMENT>
 */
public interface EMFFormsDIRendererService<VELEMENT extends VElement> {

	/**
	 * Constant for {@link #isApplicable(VElement, ViewModelContext)} to indicate, that the EMFFormsDIRendererService
	 * cannot provide a fitting renderer for the provided VElement.
	 */
	double NOT_APPLICABLE = Double.NaN;

	/**
	 * Check whether the provided {@link VElement} can be rendered by the renderer defined by
	 * {@link #getRendererClass()}.
	 *
	 * @param vElement The {@link VElement} to check
	 * @param viewModelContext The {@link ViewModelContext} to use for the renderer
	 * @return {@link #NOT_APPLICABLE} if the renderer doesn't fit, a positive value otherwise
	 */
	double isApplicable(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the {@link Class} for the renderer of this service.
	 *
	 * @return the {@link Class}
	 */
	Class<? extends AbstractSWTRenderer<VELEMENT>> getRendererClass();
}
