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
package org.eclipse.emf.ecp.view.internal.control.multireference;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * MultiReferenceSWTRendererService which provides the MultiReferenceSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class MultiReferenceSWTRendererService implements EMFFormsRendererService<VControl> {

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
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement)
	 */
	@Override
	public double isApplicable(VElement vElement) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl vControl = (VControl) vElement;
		final VDomainModelReference domainModelReference = vControl.getDomainModelReference();
		if (domainModelReference == null) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;

		IValueProperty valueProperty;
		try {
			valueProperty = databindingService
				.getValueProperty(control.getDomainModelReference());
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}
		if (EAttribute.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}
		return 5;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public AbstractSWTRenderer<VControl> getRendererInstance(VControl vElement, ViewModelContext viewModelContext) {
		return new MultiReferenceSWTRenderer(vElement, viewModelContext, reportService, databindingService,
			labelProvider, vtViewTemplateProvider, imageRegistryService);
	}

}
