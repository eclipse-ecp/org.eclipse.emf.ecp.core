/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.application.e4.editor;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.ui.e4.editor.ECPE4Editor;
import org.eclipse.emf.ecp.ui.e4.util.EPartServiceHelper;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpener;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
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
	@Override
	public void openModelElement(Object modelElement, ECPProject ecpProject) {
		final EPartService partService = EPartServiceHelper.getEPartService();
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
		if (part == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
			final ServiceReference<ReportService> serviceReference = bundleContext
				.getServiceReference(ReportService.class);
			final ReportService reportService = bundleContext.getService(serviceReference);
			reportService
				.report(new AbstractReport("There is no partdescription with the id " + partId + " available!")); //$NON-NLS-1$ //$NON-NLS-2$
			bundleContext.ungetService(serviceReference);
			return;
		}
		partService.showPart(part, PartState.ACTIVATE);
		part.getContext().set(ECPProject.class, ecpProject);
		part.getContext().set(ECPE4Editor.INPUT, modelElement);
	}

}
