/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.service;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * {@link ViewModelService} for registering and retrieving the {@link IEclipseContext} associated with a view.
 *
 * @author jfaltermeier
 *
 */
public interface EclipseContextViewService extends ViewModelService {

	/**
	 * Sets the context for the {@link VElement}.
	 *
	 * @param element the element associated with the context
	 * @param context the {@link IEclipseContext}.
	 */
	void putContext(VElement element, IEclipseContext context);

	/**
	 * Returns the context associated with the given {@link VElement}.
	 *
	 * @param element the element for which the {@link IEclipseContext} is to be returned.
	 *
	 * @return the {@link IEclipseContext}
	 */
	IEclipseContext getContext(VElement element);

}
