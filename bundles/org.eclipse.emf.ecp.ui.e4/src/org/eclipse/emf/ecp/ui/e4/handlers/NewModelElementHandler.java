package org.eclipse.emf.ecp.ui.e4.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

public class NewModelElementHandler {

	@Execute
	public void execute(Shell shell,@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject){
		if(ecpProject==null)
			return;
		ECPHandlerHelper.addModelElement(ecpProject, shell, false);
	}
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject){
		return ecpProject!=null;
	}
}
