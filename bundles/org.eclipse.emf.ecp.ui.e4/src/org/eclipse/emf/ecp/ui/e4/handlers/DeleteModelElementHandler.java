package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

public class DeleteModelElementHandler {

	@Execute
	public void execute(
			Shell shell,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional EObject eObject,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EObject> eObjects) {
		ECPProjectManager ecpProjectManager = ECPUtil.getECPProjectManager();
		if (eObject != null) {
			deleteModelElement(ecpProjectManager, eObject);
		} else if (eObjects != null) {
			for (EObject eObjectInList : eObjects) {
				deleteModelElement(ecpProjectManager, eObjectInList);
			}
		}
	}

	private void deleteModelElement(ECPProjectManager ecpProjectManager,
			EObject eObject) {
		ECPHandlerHelper.deleteModelElement(
				ecpProjectManager.getProject(eObject),
				(List<Object>) (List<?>) Arrays.asList(eObject));
	}

	@CanExecute
	public boolean canExecute(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional EObject eObject,
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EObject> eObjects) {
		return eObject != null || eObjects != null;
	}
}
