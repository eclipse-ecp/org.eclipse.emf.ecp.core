/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Christian W. Damus - bug 547787
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.provider.xmi;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IFilteredViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * An {@link IViewProvider} which loads view models from extension points.
 *
 * @author Jonas Helming
 *
 */
public class ExtensionXMIViewModelProvider implements IFilteredViewProvider {

	@Override
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties,
		Collection<String> requiredKeys) {

		if (ViewModelFileExtensionsManager.getInstance().hasViewModelFor(eObject, properties, requiredKeys)) {
			return 2d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties, Collection<String> requiredKeys) {
		return ViewModelFileExtensionsManager.getInstance().createView(eObject, properties, requiredKeys);
	}

}
