/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.label.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.swt.LabelSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * GroupSWTRendererService which provides the GroupSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class LabelSWTRendererService implements EMFFormsRendererService<VLabel> {

	private EMFFormsDatabinding databindingService;
	private ReportService reportService;
	private VTViewTemplateProvider vtViewTemplateProvider;

	/**
	 * Called by the initializer to set the EMFFormsDatabinding.
	 *
	 * @param databindingService The EMFFormsDatabinding
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabinding databindingService) {
		this.databindingService = databindingService;
	}

	/**
	 * Called by the initializer to unset the EMFFormsDatabinding.
	 *
	 * @param databindingService The EMFFormsDatabinding
	 */
	protected void unsetEMFFormsDatabinding(EMFFormsDatabinding databindingService) {
		this.databindingService = null;
	}

	/**
	 * Called by the initializer to set the ReportService.
	 *
	 * @param reportService The ReportService
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Called by the initializer to unset the ReportService.
	 *
	 * @param reportService The ReportService
	 */
	protected void unsetReportService(ReportService reportService) {
		this.reportService = null;
	}

	/**
	 * Called by the initializer to set the VTViewTemplateProvider.
	 *
	 * @param vtViewTemplateProvider The VTViewTemplateProvider
	 */
	protected void setVTViewTemplateProvider(VTViewTemplateProvider vtViewTemplateProvider) {
		this.vtViewTemplateProvider = vtViewTemplateProvider;
	}

	/**
	 * Called by the initializer to unset the VTViewTemplateProvider.
	 *
	 * @param vtViewTemplateProvider The VTViewTemplateProvider
	 */
	protected void unsetVTViewTemplateProvider(VTViewTemplateProvider vtViewTemplateProvider) {
		vtViewTemplateProvider = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VLabel.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		return 1d;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public AbstractSWTRenderer<VLabel> getRendererInstance(VLabel vElement, ViewModelContext viewModelContext) {
		return new LabelSWTRenderer(vElement, viewModelContext, reportService, databindingService,
			vtViewTemplateProvider);
	}

}
