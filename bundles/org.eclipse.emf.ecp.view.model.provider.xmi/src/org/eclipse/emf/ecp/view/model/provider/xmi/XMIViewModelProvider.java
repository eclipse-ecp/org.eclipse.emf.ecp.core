/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.provider.xmi;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceImpl;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * Abstract class to implement a view provider reading the view model from an xmi file.
 *
 * @author Jonas Helming
 *
 */
public abstract class XMIViewModelProvider implements IViewProvider {

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		final VViewResourceImpl resource = ViewModelFileExtensionsManager.loadResource(getXMIPath());
		final VView result = EcoreUtil.copy((VView) resource.getContents().get(0));
		ViewModelFileExtensionsManager.setUUIDAsElementId(resource, result);
		result.setLoadingProperties(EcoreUtil.copy(properties));
		return result;
	}

	/**
	 *
	 * @return the URI of the xmi file containing the view model
	 */
	protected abstract URI getXMIPath();
}
