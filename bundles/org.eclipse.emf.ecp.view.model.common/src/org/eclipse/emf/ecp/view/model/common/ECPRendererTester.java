/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * EPCRendererTester is used by the framework to find the best fitting renderer for a specific {@link ViewModelContext}.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPRendererTester {

	/**
	 * Return this whenever the renderer should not be used for the tested {@link ViewModelContext}.
	 */
	int NOT_APPLICABLE = -1;

	/**
	 * Check whether the provided {@link VElement} and {@link ViewModelContext} are fitting for the provided renderer.
	 *
	 * @param vElement the {@link VElement} to check
	 * @param viewModelContext the {@link ViewModelContext} to check
	 * @return {@link #NOT_APPLICABLE} if the corresponding renderer should not be used, a positive integer value
	 *         otherwise. The renderer with the highest priority will be taken.
	 */
	int isApplicable(VElement vElement, ViewModelContext viewModelContext);
}
