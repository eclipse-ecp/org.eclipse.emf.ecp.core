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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * An {@link IViewProvider} which loads view models from extension points.
 *
 * @author Jonas Helming
 *
 */
public class ExtensionXMIViewModelProvider implements IViewProvider {

	@Override
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		if (ViewModelFileExtensionsManager.getInstance().hasViewModelFor(eObject, properties)) {
			return 2d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		return ViewModelFileExtensionsManager.getInstance().createView(eObject, properties);
	}

}
