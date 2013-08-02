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
