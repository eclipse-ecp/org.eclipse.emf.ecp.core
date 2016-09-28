/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.view.internal.editor.controls.LinkFeatureControlRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * A control renderer which is used for selecting a DMR. To retrieve the root EClass this renderer uses the resource to
 * get the selected view model.
 *
 * @author Eugen
 *
 */
@SuppressWarnings("restriction")
public class RuleRepositoryLinkFeatureControlRenderer extends LinkFeatureControlRenderer {

	/**
	 * Default constructor.
	 * 
	 * @param vElement The {@link VControl} of this renderer
	 * @param viewContext The {@link ViewModelContext} of this renderer
	 * @param reportService The {@link ReportService} of this renderer
	 */
	@Inject
	public RuleRepositoryLinkFeatureControlRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected EClass getRootEClass(Notifier notifier) {
		final EObject eObject = (EObject) notifier;

		final EList<Resource> resources = eObject.eResource().getResourceSet().getResources();
		for (final Resource resource : resources) {
			final EObject object = resource.getContents().get(0);
			if (VView.class.isInstance(object)) {
				return VView.class.cast(object).getRootEClass();
			}
		}
		throw new IllegalStateException("Please select a view model first."); //$NON-NLS-1$
	}

}
