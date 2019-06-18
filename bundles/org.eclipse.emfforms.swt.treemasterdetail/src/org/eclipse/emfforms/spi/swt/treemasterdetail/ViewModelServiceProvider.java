/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * Interface to influence which {@link ViewModelService ViewModelServices} will be added when a new detail view is
 * rendered.
 *
 * @author Johannes Faltermeier
 *
 */
public interface ViewModelServiceProvider {

	/**
	 * Returns newly created view model services which will be used when the given view for the given object is
	 * rendered.
	 *
	 * @param view the view to be rendered
	 * @param eObject the object to be rendered
	 * @return the services
	 */
	ViewModelService[] getViewModelServices(VView view, EObject eObject);

}