/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.rap;


/**
 * Creates, adds and disposes actions for the menus and action bars of
 * each workbench window.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	// Actions - important to allocate these only in makeActions, and then use
	// them in the fill methods. This ensures that the actions aren't recreated
	// in the fill methods.
	private IWorkbenchAction exitAction;

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		// Creates the actions and registers them. Registering also
		// provides automatic disposal of the actions when the window is closed.
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		final MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
		fileMenu.add(exitAction);
	}

}
