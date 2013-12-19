package org.eclipse.emf.ecp.ui.e4.editor;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class E4ModelElementOpener implements ECPModelElementOpener {

	final String partId = "org.eclipse.emf.ecp.e4.application.partdescriptor.editor";

	public void openModelElement(Object modelElement, ECPProject ecpProject) {
		final EPartService partService = getEPartService();
		for (final MPart existingPart : partService.getParts()) {
			if (!partId.equals(existingPart.getElementId())) {
				continue;
			}

			if (existingPart.getContext() == null) {
				continue;
			}

			if (existingPart.getContext().get(ECPE4Editor.INPUT) == modelElement) {
				if (!existingPart.isVisible() || !existingPart.isOnTop()) {
					partService.showPart(existingPart, PartState.ACTIVATE);
				}
				return;
			}
		}

		final MPart part = partService.createPart(partId);
		partService.showPart(part, PartState.ACTIVATE);
		part.getContext().set(ECPProject.class, ecpProject);
		part.getContext().set(ECPE4Editor.INPUT, modelElement);
	}

	private EPartService getEPartService() {
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference<?> service;
		try {
			service = bundleContext.getServiceReferences(
					IContextFunction.class.getName(), "(service.context.key="
							+ EPartService.class.getName() + ")")[0];
			// TODO a "bit" ugly
			return (EPartService) ((IContextFunction) bundleContext.getService(service))
					.compute(E4Workbench.getServiceContext(), null);
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
