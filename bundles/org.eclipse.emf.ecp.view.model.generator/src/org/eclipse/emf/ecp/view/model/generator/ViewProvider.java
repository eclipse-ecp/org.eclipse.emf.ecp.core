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
 * Edgar - initial API and implementation
 * Christian W. Damus - bug 547271
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * View Provider.
 */
public class ViewProvider implements IViewProvider {

	private final ViewCache cache = new ViewCache();

	/**
	 * Initializes me.
	 */
	public ViewProvider() {
		super();
	}

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		final VView result = cache.getView(eObject);
		result.setLoadingProperties(EcoreUtil.copy(properties));
		return result;
	}

	@Override
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		return 1d;
	}
}
