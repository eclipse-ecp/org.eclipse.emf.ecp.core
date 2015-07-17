package org.eclipse.emf.ecp.e4.emfstore;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.EMFStoreLogInHelper;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.swt.widgets.Shell;

public class LogInHandler {
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional InternalRepository ecpRepository) {
		if (ecpRepository != null) {
			EMFStoreLogInHelper.login(ecpRepository, shell);
		}
	}

}