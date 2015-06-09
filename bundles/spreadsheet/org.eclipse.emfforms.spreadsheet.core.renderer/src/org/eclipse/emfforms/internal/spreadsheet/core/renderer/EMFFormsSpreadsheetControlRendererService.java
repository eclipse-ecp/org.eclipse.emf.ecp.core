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
package org.eclipse.emfforms.internal.spreadsheet.core.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link EMFFormsSpreadsheetRendererService} for {@link VControl}.
 *
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsSpreadsheetControlRendererService implements
	EMFFormsSpreadsheetRendererService<VControl> {

	private EMFFormsDatabinding emfformsDatabinding;
	private EMFFormsLabelProvider emfformsLabelProvider;
	private ReportService reportService;
	private VTViewTemplateProvider vtViewTemplateProvider;

	/**
	 * The VTViewTemplateProvider to use.
	 *
	 * @param vtViewTemplateProvider the VTViewTemplateProvider to set
	 */
	@Reference
	protected void setVTViewTemplateProvider(VTViewTemplateProvider vtViewTemplateProvider) {
		this.vtViewTemplateProvider = vtViewTemplateProvider;
	}

	/**
	 * The ReportService to use.
	 *
	 * @param reportService the reportService to set
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Set the EMFFormsDatabinding to use.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabinding to use
	 */
	@Reference
	public void setEmfformsDatabinding(EMFFormsDatabinding emfformsDatabinding) {
		this.emfformsDatabinding = emfformsDatabinding;
	}

	/**
	 * Set the EMFFormsLabelProvider to use.
	 *
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 */
	@Reference
	public void setEmfformsLabelProvider(EMFFormsLabelProvider emfformsLabelProvider) {
		this.emfformsLabelProvider = emfformsLabelProvider;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement,
		ViewModelContext viewModelContext) {
		if (VControl.class.isInstance(vElement)) {
			return 1;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public EMFFormsAbstractSpreadsheetRenderer<VControl> getRendererInstance(
		VControl vElement, ViewModelContext viewModelContext) {
		return new EMFFormsSpreadsheetControlRenderer(emfformsDatabinding, emfformsLabelProvider, reportService,
			vtViewTemplateProvider);
	}

}
