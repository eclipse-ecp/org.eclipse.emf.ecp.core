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
package org.eclipse.emfforms.internal.spreadsheet.core.renderer.table;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link EMFFormsSpreadsheetRendererService} for {@link VTableControl}.
 *
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsSpreadsheetTableControlRendererService implements
	EMFFormsSpreadsheetRendererService<VTableControl> {

	private EMFFormsDatabindingEMF emfformsDatabinding;
	private EMFFormsLabelProvider emfformsLabelProvider;
	private ReportService reportService;
	private EMFFormsSpreadsheetRendererFactory emfformsSpreadsheetRendererFactory;
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
	@Reference(unbind = "-")
	protected void setVTViewTemplateProvider(VTViewTemplateProvider vtViewTemplateProvider) {
		this.vtViewTemplateProvider = vtViewTemplateProvider;
	}

	/**
	 * The ReportService to use.
	 *
	 * @param reportService the reportService to set
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Set the EMFFormsDatabindingEMF to use.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabindingEMF to use
	 */
	@Reference(unbind = "-")
	protected void setEmfformsDatabinding(EMFFormsDatabindingEMF emfformsDatabinding) {
		this.emfformsDatabinding = emfformsDatabinding;
	}

	/**
	 * Set the EMFFormsLabelProvider to use.
	 *
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 */
	@Reference(unbind = "-")
	protected void setEmfformsLabelProvider(EMFFormsLabelProvider emfformsLabelProvider) {
		this.emfformsLabelProvider = emfformsLabelProvider;
	}

	/**
	 * The EMFFormsIdProvider to use.
	 *
	 * @param emfFormsIdProvider the EMFFormsIdProvider to set
	 */
	@Reference(unbind = "-")
	protected void setEmfFormsIdProvider(EMFFormsIdProvider emfFormsIdProvider) {
		this.emfFormsIdProvider = emfFormsIdProvider;
	}

	/**
	 * The EMFFormsSpreadsheetValueConverterRegistry to use.
	 *
	 * @param converterRegistry the converter registry
	 */
	@Reference(unbind = "-")
	public void setConverterRegistry(EMFFormsSpreadsheetValueConverterRegistry converterRegistry) {
		this.converterRegistry = converterRegistry;
	}

	/**
	 * The EMFFormsSpreadsheetFormatDescriptionProvider to use.
	 *
	 * @param formatDescriptionProvider the formatDescriptionProvider
	 */
	@Reference(unbind = "-")
	public void setFormatDescriptionProvider(EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider) {
		this.formatDescriptionProvider = formatDescriptionProvider;
	}

	/**
	 * The EMFFormsDomainExpander to use.
	 *
	 * @param domainExpander the EMFFormsDomainExpander
	 */
	@Reference(unbind = "-")
	public void setEMFFormsDomainExpander(EMFFormsDomainExpander domainExpander) {
		this.domainExpander = domainExpander;
	}

	private ServiceReference<EMFFormsSpreadsheetRendererFactory> serviceReference;

	/**
	 * The activate method.
	 *
	 * @param bundleContext The BundleContext
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		serviceReference = bundleContext
			.getServiceReference(EMFFormsSpreadsheetRendererFactory.class);
		emfformsSpreadsheetRendererFactory = bundleContext
			.getService(serviceReference);
	}

	/**
	 * The deactivate method.
	 *
	 * @param bundleContext The BundleContext
	 */
	@Deactivate
	public void deactivate(BundleContext bundleContext) {
		bundleContext.ungetService(serviceReference);
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
		if (VTableControl.class.isInstance(vElement)) {
			return 2;
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
	public EMFFormsAbstractSpreadsheetRenderer<VTableControl> getRendererInstance(
		VTableControl vElement, ViewModelContext viewModelContext) {
		return new EMFFormsSpreadsheetTableControlRenderer(emfformsDatabinding, emfformsLabelProvider, reportService,
			emfformsSpreadsheetRendererFactory, vtViewTemplateProvider, emfFormsIdProvider, converterRegistry,
			formatDescriptionProvider, domainExpander);
	}

}
