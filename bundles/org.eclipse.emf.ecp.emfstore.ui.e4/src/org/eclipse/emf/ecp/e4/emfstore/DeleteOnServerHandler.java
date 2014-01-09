package org.eclipse.emf.ecp.e4.emfstore;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProjectWrapper;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.DeleteOnServerHelper;
import org.eclipse.swt.widgets.Shell;

public class DeleteOnServerHandler {
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EMFStoreProjectWrapper> projectWrappers) {
		DeleteOnServerHelper.deleteOnServer(projectWrappers.get(0), shell);
	}

	@CanExecute
	public boolean canExecute(
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EMFStoreProjectWrapper> projectWrappers) {
		return projectWrappers.size() == 1;
	}
}