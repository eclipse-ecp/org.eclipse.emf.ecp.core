/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.ui.e4.editor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Opens a model element in {@link ECPE4Editor}.
 * 
 * @author Jonas
 * 
 */
public class E4ModelElementOpener implements ECPModelElementOpener {

	private final String partId = "org.eclipse.emf.ecp.e4.application.partdescriptor.editor"; //$NON-NLS-1$

	/**
	 * Opens a model element in {@link ECPE4Editor}. {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.util.ECPModelElementOpener#openModelElement(java.lang.Object,
	 *      org.eclipse.emf.ecp.core.ECPProject)
	 */
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

	/**
	 * Retrieves the part service.
	 * 
	 * @return the part service of the current window.
	 */
	public static EPartService getEPartService() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(E4ModelElementOpener.class).getBundleContext();
		final ServiceReference<?> serviceReference = bundleContext.getServiceReference(IEclipseContext.class);
		final IEclipseContext context = (IEclipseContext) bundleContext.getService(serviceReference);
		final EPartService ePartService = context.get(EPartService.class);
		return ePartService;
	}
}
