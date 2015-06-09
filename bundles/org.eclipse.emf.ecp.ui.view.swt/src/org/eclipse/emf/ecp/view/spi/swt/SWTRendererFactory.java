/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

/**
 * A RendererFactory for SWT controls.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@Deprecated
public interface SWTRendererFactory {

	/**
	 * Searches for a fitting renderer for the passed {@link VElement}.
	 *
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param vElement the {@link VElement} to render
	 * @return the list for {@link AbstractSWTRenderer} the fitting render or null
	 * @since 1.6
	 */
	AbstractSWTRenderer<VElement> getRenderer(VElement vElement, ViewModelContext viewContext);

	/**
	 * Returns a collection of all additional renderer which contribute controls for the provided {@link VElement}.
	 *
	 * @param vElement the {@link VElement} to get additional renderer for
	 * @param viewModelContext the {@link ViewModelContext} to check
	 * @return the Collection of additional renderer
	 */
	Collection<AbstractAdditionalSWTRenderer<VElement>> getAdditionalRenderer(VElement vElement,
		ViewModelContext viewModelContext);
}
