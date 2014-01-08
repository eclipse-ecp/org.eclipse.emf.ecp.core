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

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

/**
 * This class controls all aspects of the application's execution
 * and is contributed through the plugin.xml.
 */
public class Application implements EntryPoint {

	// public Object start(IApplicationContext context) throws Exception {
	// Display display = PlatformUI.createDisplay();
	// WorkbenchAdvisor advisor = new ApplicationWorkbenchAdvisor();
	// return PlatformUI.createAndRunWorkbench(display, advisor);
	// }
	//
	// public void stop() {
	// // Do nothing
	// }

	public int createUI() {
		final Display display = PlatformUI.createDisplay();
		final WorkbenchAdvisor advisor = new ApplicationWorkbenchAdvisor();
		return PlatformUI.createAndRunWorkbench(display, advisor);
	}
}
