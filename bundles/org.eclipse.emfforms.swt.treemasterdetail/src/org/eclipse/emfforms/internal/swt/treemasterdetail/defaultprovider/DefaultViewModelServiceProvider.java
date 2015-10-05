/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ViewModelServiceProvider;

/**
 * @author jfaltermeier
 *
 */
public final class DefaultViewModelServiceProvider implements ViewModelServiceProvider {
	@Override
	public ViewModelService[] getViewModelServices(VView view2, EObject eObject) {
		return new ViewModelService[0];
	}
}