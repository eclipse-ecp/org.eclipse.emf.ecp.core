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
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
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
 * The {@link EMFFormsSpreadsheetRendererService} for {@link VContainer}.
 *
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsSpreadsheetContainerRendererService implements
	EMFFormsSpreadsheetRendererService<VContainer> {

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
		if (serviceReference != null) {
			bundleContext.ungetService(serviceReference);
		}
	}

	/**
	 * Gets and returns the {@link EMFFormsSpreadsheetRendererFactory}.
	 *
	 * @return The {@link EMFFormsSpreadsheetRendererFactory}
	 */
	protected EMFFormsSpreadsheetRendererFactory getSpreadsheetRendererFactory() {
		if (emfformsSpreadsheetRendererFactory == null) {
			serviceReference = bundleContext.getServiceReference(EMFFormsSpreadsheetRendererFactory.class);
			emfformsSpreadsheetRendererFactory = bundleContext.getService(serviceReference);
		}
		return emfformsSpreadsheetRendererFactory;
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
		if (VContainer.class.isInstance(vElement)) {
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
	public EMFFormsAbstractSpreadsheetRenderer<VContainer> getRendererInstance(
		VContainer vElement, ViewModelContext viewModelContext) {
		return new EMFFormsSpreadsheetContainerRenderer(getSpreadsheetRendererFactory(), reportService);
	}

}
