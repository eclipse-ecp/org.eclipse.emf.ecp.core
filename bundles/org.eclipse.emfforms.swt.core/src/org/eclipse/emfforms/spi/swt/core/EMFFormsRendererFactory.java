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

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This is the factory which selects the most fitting renderer for the provided {@link VElement} and
 * {@link ViewModelContext}.
 *
 * @author Eugen Neufeld
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface EMFFormsRendererFactory {

	/**
	 * Returns the renderer which fits the provided {@link VElement} and {@link ViewModelContext} the most.
	 *
	 * @param vElement the {@link VElement} to find the renderer for
	 * @param viewModelContext the {@link ViewModelContext} to find the renderer for
	 * @param <VELEMENT> The VElement type
	 * @return the renderer
	 * @throws EMFFormsNoRendererException is thrown when no renderer can be found
	 */
	<VELEMENT extends VElement> AbstractSWTRenderer<VElement> getRendererInstance(VELEMENT vElement,
		ViewModelContext viewModelContext) throws EMFFormsNoRendererException;

	/**
	 * Returns a collection of all additional renderer which contribute controls for the provided {@link VElement}.
	 *
	 * @param vElement the {@link VElement} to get additional renderer for
	 * @param viewModelContext the {@link ViewModelContext} to check
	 * @param <VELEMENT> The VElement type
	 * @return the Collection of additional renderer
	 */
	<VELEMENT extends VElement> Collection<AbstractAdditionalSWTRenderer<VElement>> getAdditionalRendererInstances(
		VELEMENT vElement, ViewModelContext viewModelContext);
}
