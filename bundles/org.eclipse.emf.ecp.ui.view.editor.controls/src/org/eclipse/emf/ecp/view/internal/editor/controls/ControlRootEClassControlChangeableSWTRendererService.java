/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * DI renderer service for {@link ControlRootEClassControlChangeableSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "ControlRootEClassControlChangeableSWTRendererService")
public class ControlRootEClassControlChangeableSWTRendererService implements EMFFormsDIRendererService<VControl> {

	private EMFFormsDatabinding databindingService;
	private ReportService reportService;

	/**
	 * Called by the framework to set the {@link EMFFormsDatabinding}.
	 *
	 * @param databindingService The {@link EMFFormsDatabinding}
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(EMFFormsDatabinding databindingService) {
		this.databindingService = databindingService;
	}

	/**
	 * Called by the framework to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		if (control.getDomainModelReference() == null) {
			return NOT_APPLICABLE;
		}
		IValueProperty valueProperty;
		try {
			valueProperty = databindingService.getValueProperty(control.getDomainModelReference(),
				viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();

		return isApplicable(feature);
	}

	/**
	 * Test if the structural feature and the corresponding EObject contain the correct data.
	 *
	 * @param feature The {@link EStructuralFeature} to check
	 * @return the priority of the control
	 */
	private double isApplicable(EStructuralFeature feature) {
		if (VViewPackage.eINSTANCE.getView_RootEClass() != feature) {
			return NOT_APPLICABLE;
		}
		boolean allowChange = Boolean.FALSE;
		final String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length; i++) {
			final String arg = commandLineArgs[i];
			if ("-changeableClass".equalsIgnoreCase(arg)) { //$NON-NLS-1$
				allowChange = Boolean.TRUE;
			}
		}
		if (allowChange) {
			return 4d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ControlRootEClassControl2SWTRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return ControlRootEClassControlChangeableSWTRenderer.class;
	}

}
