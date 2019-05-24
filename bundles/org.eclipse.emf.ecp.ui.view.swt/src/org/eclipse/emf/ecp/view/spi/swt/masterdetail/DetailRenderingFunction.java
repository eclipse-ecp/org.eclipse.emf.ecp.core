/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.masterdetail;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.swt.widgets.Composite;

/**
 * Rendering function for detail views.
 *
 * @since 1.22
 */
@FunctionalInterface
public interface DetailRenderingFunction {

	/**
	 * Creates a view with the attributes of the domain object. The layout of the view is specified by the view
	 * model set in the view model context.
	 *
	 * @param parent the parent SWT composite to render the view on
	 * @param viewModelContext the {@link ViewModelContext} to use
	 * @return an ECPSWTView providing an interface to the rendered view
	 * @throws ECPRendererException if there is an exception during rendering
	 */
	ECPSWTView render(Composite parent, ViewModelContext viewModelContext) throws ECPRendererException;

}
