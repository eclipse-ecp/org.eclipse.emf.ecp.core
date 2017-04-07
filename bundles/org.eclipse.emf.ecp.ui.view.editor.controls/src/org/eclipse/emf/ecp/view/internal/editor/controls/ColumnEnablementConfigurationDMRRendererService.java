/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * {@link EMFFormsDIRendererService} for configuring the enabled state of column.
 *
 * @author Edgar Mueller
 *
 */
@Component(name = "ColumnEnablementConfigurationDMRRendererService", service = EMFFormsDIRendererService.class)
public class ColumnEnablementConfigurationDMRRendererService implements EMFFormsDIRendererService<VControl> {

	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	/**
	 * @param databinding {@link EMFFormsDatabindingEMF}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	public void setEMFFormsDatabindingEMF(EMFFormsDatabindingEMF databinding) {
		this.databinding = databinding;
	}

	/**
	 * @param reportService {@link ReportService}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	public void setreportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VDomainModelReference domainModelReference = VControl.class.cast(vElement).getDomainModelReference();
		if (domainModelReference == null) {
			return NOT_APPLICABLE;
		}
		try {
			final IEMFValueProperty valueProperty = databinding.getValueProperty(
				domainModelReference,
				viewModelContext.getDomainModel());
			if (valueProperty.getStructuralFeature() == VTablePackage.eINSTANCE
				.getEnablementConfiguration_ColumnDomainModelReference()) {
				return 3d;
			}
		} catch (final DatabindingFailedException ex) {
			reportService.report(new AbstractReport(ex));
		}

		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return ColumnConfigurationDMRRenderer.class;
	}

}
