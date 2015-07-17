package org.eclipse.emf.ecp.e4.emfstore;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.ImportProjectHelper;
import org.eclipse.swt.widgets.Shell;

public class ImportProjectHandler {
	@Execute
	public void execute(Shell shell) {
		ImportProjectHelper.importProject(shell);
	}

}