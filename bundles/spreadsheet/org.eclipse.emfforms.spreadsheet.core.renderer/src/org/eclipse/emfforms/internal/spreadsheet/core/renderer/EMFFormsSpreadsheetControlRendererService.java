/*******************************************************************************
 * Copyright (c) 2015 EclipseSource Muenchen GmbH and others.
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
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;
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

	private EMFFormsDatabindingEMF emfformsDatabinding;
	private EMFFormsLabelProvider emfformsLabelProvider;
	private ReportService reportService;
	private VTViewTemplateProvider vtViewTemplateProvider;
	private EMFFormsIdProvider emfFormsIdProvider;
	private EMFFormsSpreadsheetValueConverterRegistry converterRegistry;
	private EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider;
	private EMFFormsDomainExpander domainExpander;

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
	public void setEmfformsDatabinding(EMFFormsDatabindingEMF emfformsDatabinding) {
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
	 * The EMFFormsIdProvider to use.
	 *
	 * @param emfFormsIdProvider the EMFFormsIdProvider to set
	 */
	@Reference
	protected void setEmfFormsIdProvider(EMFFormsIdProvider emfFormsIdProvider) {
		this.emfFormsIdProvider = emfFormsIdProvider;
	}

	/**
	 * The EMFFormsSpreadsheetValueConverterRegistry to use.
	 *
	 * @param converterRegistry the converter registry
	 */
	@Reference
	public void setConverterRegistry(EMFFormsSpreadsheetValueConverterRegistry converterRegistry) {
		this.converterRegistry = converterRegistry;
	}

	/**
	 * The EMFFormsSpreadsheetFormatDescriptionProvider to use.
	 *
	 * @param formatDescriptionProvider the formatDescriptionProvider
	 */
	@Reference
	public void setFormatDescriptionProvider(EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider) {
		this.formatDescriptionProvider = formatDescriptionProvider;
	}

	/**
	 * The EMFFormsDomainExpander to use.
	 *
	 * @param domainExpander the EMFFormsDomainExpander
	 */
	@Reference
	public void setEMFFormsDomainExpander(EMFFormsDomainExpander domainExpander) {
		this.domainExpander = domainExpander;
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
			vtViewTemplateProvider, emfFormsIdProvider, converterRegistry, formatDescriptionProvider, domainExpander);
	}

}
