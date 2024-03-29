/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link EMFFormsSpreadsheetRendererService} for {@link VView}.
 *
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsSpreadsheetViewRendererService implements
	EMFFormsSpreadsheetRendererService<VView> {

	private EMFFormsSpreadsheetRendererFactory emfformsSpreadsheetRendererFactory;
	private ReportService reportService;

	/**
	 * The ReportService to use.
	 *
	 * @param reportService the reportService to set
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	private ServiceReference<EMFFormsSpreadsheetRendererFactory> serviceReference;
	private BundleContext bundleContext;

	/**
	 * The activate method.
	 *
	 * @param bundleContext The BundleContext
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
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
		if (VView.class.isInstance(vElement)) {
			return 1;
		}
		return NOT_APPLICABLE;
	}

	private EMFFormsSpreadsheetRendererFactory getFactory() {
		if (emfformsSpreadsheetRendererFactory == null) {
			serviceReference = bundleContext
				.getServiceReference(EMFFormsSpreadsheetRendererFactory.class);
			emfformsSpreadsheetRendererFactory = bundleContext
				.getService(serviceReference);
		}
		return emfformsSpreadsheetRendererFactory;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public EMFFormsAbstractSpreadsheetRenderer<VView> getRendererInstance(
		VView vElement, ViewModelContext viewModelContext) {
		return new EMFFormsSpreadsheetViewRenderer(getFactory(), reportService);
	}

}
