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
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * TableControlSWTRendererService which provides the TableControlSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class TableControlDetailPanelSWTRendererService implements EMFFormsRendererService<VTableControl> {

	private EMFFormsDatabinding databindingService;
	private EMFFormsLabelProvider labelProvider;
	private ReportService reportService;
	private VTViewTemplateProvider vtViewTemplateProvider;
	private ImageRegistryService imageRegistryService;

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
	 * Called by the initializer to set the EMFFormsLabelProvider.
	 *
	 * @param labelProvider The EMFFormsLabelProvider
	 */
	protected void setEMFFormsLabelProvider(EMFFormsLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * Called by the initializer to unset the EMFFormsLabelProvider.
	 *
	 * @param labelProvider The EMFFormsLabelProvider
	 */
	protected void unsetEMFFormsLabelProvider(EMFFormsLabelProvider labelProvider) {
		this.labelProvider = null;
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
		this.vtViewTemplateProvider = null;
	}

	/**
	 * Called by the initializer to set the ImageRegistryService.
	 *
	 * @param imageRegistryService The ImageRegistryService
	 */
	protected void setImageRegistryService(ImageRegistryService imageRegistryService) {
		this.imageRegistryService = imageRegistryService;
	}

	/**
	 * Called by the initializer to unset the ImageRegistryService.
	 *
	 * @param imageRegistryService The ImageRegistryService
	 */
	protected void unsetImageRegistryService(ImageRegistryService imageRegistryService) {
		this.imageRegistryService = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext context) {
		if (!VTableControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		if (DetailEditing.WITH_PANEL == VTableControl.class.cast(vElement).getDetailEditing()) {
			return 10;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public AbstractSWTRenderer<VTableControl> getRendererInstance(VTableControl vElement,
		ViewModelContext viewModelContext) {
		return new TableControlDetailPanelRenderer(vElement, viewModelContext, reportService, databindingService,
			labelProvider, vtViewTemplateProvider, imageRegistryService);
	}

}
