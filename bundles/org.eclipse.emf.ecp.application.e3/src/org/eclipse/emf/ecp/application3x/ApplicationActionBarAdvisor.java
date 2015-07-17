/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.application3x;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * ApplicationActionBarAdvisor class.
 *
 * @author Eugen Neufeld
 *
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	/**
	 * Convinient constructor.
	 *
	 * @param configurer the {@link IActionBarConfigurer} to use
	 */
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/** {@inheritDoc} */
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		// nothing todo
	}

	/** {@inheritDoc} */
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		// nothing todo
	}

}
