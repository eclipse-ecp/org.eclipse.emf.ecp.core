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
package org.eclipse.emf.ecp.ui.view.swt;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.internal.swt.ECPSWTViewRendererImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.swt.widgets.Composite;

/**
 * Renders a view which displays the attributes of an domain objects.
 *
 * @author Jonas
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPSWTViewRenderer {

	/** Provides access to the stateless renderer. */
	ECPSWTViewRenderer INSTANCE = new ECPSWTViewRendererImpl();

	/**
	 * Creates a view with the attributes of the domain object. The layout of the view can either be describes by a
	 * registered view model, or, if none view model is registered for the domainObject, will be the default layout.
	 *
	 * @param parent the parent SWT composite to render the view on
	 * @param domainObject The domainObject to show in the view
	 * @return an ECPSWTView providing an interface to the rendered view
	 * @throws ECPRendererException if there is an exception during rendering
	 */
	ECPSWTView render(Composite parent, EObject domainObject) throws ECPRendererException;

	/**
	 * Creates a view with the attributes of the domain object. The layout of the view is specified by the given view
	 * model.
	 *
	 * @param parent the parent SWT composite to render the view on
	 * @param domainObject The domainObject to show in the view
	 * @param viewModel the view model describing the layout of the view
	 * @return an ECPSWTView providing an interface to the rendered view
	 * @throws ECPRendererException if there is an exception during rendering
	 * @since 1.2
	 */
	ECPSWTView render(Composite parent, EObject domainObject, VView viewModel) throws ECPRendererException;

	/**
	 * Creates a view with the attributes of the domain object. The layout of the view is specified by the view
	 * model set in the view model context.
	 *
	 * @param parent the parent SWT composite to render the view on
	 * @param viewModelContext the {@link ViewModelContext} to use
	 * @return an ECPSWTView providing an interface to the rendered view
	 * @throws ECPRendererException if there is an exception during rendering
	 * @since 1.2
	 */
	ECPSWTView render(Composite parent, ViewModelContext viewModelContext) throws ECPRendererException;

	/**
	 * Creates a view with the attributes of the domain object. The layout of the view can either be describes by a
	 * registered view model, or, if none view model is registered for the domainObject, will be the default layout.
	 *
	 * @param parent the parent SWT composite to render the view on
	 * @param domainObject The domainObject to show in the view
	 * @param context a key-value-map from String to Object
	 * @return an ECPSWTView providing an interface to the rendered view
	 * @throws ECPRendererException if there is an exception during rendering
	 * @since 1.4
	 */
	ECPSWTView render(Composite parent, EObject domainObject, Map<String, Object> context) throws ECPRendererException;
}
