/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.fx;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.model.internal.fx.ECPFXViewRendererImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * @author Jonas JavaFX Renderer.
 */

public interface ECPFXViewRenderer {

	/**
	 * Provides access to the stateless renderer.
	 */
	ECPFXViewRenderer INSTANCE = new ECPFXViewRendererImpl();

	/**
	 * Creates a view with the attributes of the domain object. The layout of
	 * the view is specified by the given view model.
	 *
	 * @param domainObject
	 *            The domainObject to show in the view
	 * @param viewModel
	 *            the view model describing the layout of the view
	 * @return an ECPFXView providing an interface to the rendered view
	 * @throws ECPRendererException
	 *             if there is an exception during rendering
	 */
	ECPFXView render(VView viewModel, EObject domainObject, ViewModelService... services); // throws
	// ECPRendererException

	ECPFXView render(EObject domainObject, ViewModelService... services);

}
