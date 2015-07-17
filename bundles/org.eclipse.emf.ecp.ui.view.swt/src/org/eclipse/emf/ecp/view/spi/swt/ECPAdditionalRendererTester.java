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
package org.eclipse.emf.ecp.view.spi.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * EPCRendererTester is used by the framework to find the best fitting renderer for a specific {@link ViewModelContext}.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public interface ECPAdditionalRendererTester {

	/**
	 * Check whether the provided {@link VElement} and {@link ViewModelContext} are fitting for the provided renderer.
	 *
	 * @param vElement the {@link VElement} to check
	 * @param viewModelContext the {@link ViewModelContext} to check
	 * @return false if the corresponding renderer should not be used,true otherwise.
	 */
	boolean isApplicable(VElement vElement, ViewModelContext viewModelContext);
}
