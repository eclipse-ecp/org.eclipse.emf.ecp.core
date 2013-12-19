package org.eclipse.emf.ecp.ui.e4.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

public class NewProjectHandler {

	@Execute
	public void execute(Shell shell){
		ECPHandlerHelper.createProject(shell);
	}
	@CanExecute
	public boolean canExecute(){
		return true;
	}
}
