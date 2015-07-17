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
package org.eclipse.emf.ecp.view.model.provider.xmi;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * An {@link IViewProvider} which loads view models from extension points.
 *
 * @author Jonas Helming
 *
 */
public class ExtensionXMIViewModelProvider implements IViewProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.IViewProvider#canRender(EObject, Map)
	 */
	@Override
	public int canRender(EObject eObject, Map<String, Object> context) {
		if (ViewModelFileExtensionsManager.getInstance().hasViewModelFor(eObject, context)) {
			return 2;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.IViewProvider#generate(EObject, Map)
	 */
	@Override
	public VView generate(EObject eObject, Map<String, Object> context) {
		return ViewModelFileExtensionsManager.getInstance().createView(eObject, context);
	}

}
