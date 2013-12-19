package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

public class DeleteProjectHandler {

	@Execute
	public void execute(
			Shell shell,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<ECPProject> ecpProjects) {
		if (ecpProject != null) {
			ECPHandlerHelper
			.deleteHandlerHelper((List<ECPContainer>) (List<?>) Arrays
					.asList(ecpProject), shell);
		}
		else if (ecpProjects != null) {
			ECPHandlerHelper.deleteHandlerHelper((List<ECPContainer>)(List<?>)ecpProjects, shell);
		}
	}

	@CanExecute
	public boolean canExecute(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<ECPProject> ecpProjects) {
		return ecpProject != null || ecpProjects != null;
	}
}
