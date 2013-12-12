/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

/**
 * The AbstractViewService is a common super type for all View Services. This way the {@link ViewModelContext} is
 * responsible for managing the lifecycle of all attached services. This is important, so that no service tries to do
 * something while the model is not the current one anymore.
 * 
 * @author Eugen Neufeld
 */
public interface ViewModelService {

	/**
	 * Instantiate the view service.
	 * 
	 * @param context the {@link ViewModelContext}
	 */
	void instantiate(ViewModelContext context);

	/**
	 * Dispose.
	 */
	void dispose();

	/**
	 * Returns the priority for this view service.
	 * 
	 * @return the priority
	 */
	int getPriority();
}
