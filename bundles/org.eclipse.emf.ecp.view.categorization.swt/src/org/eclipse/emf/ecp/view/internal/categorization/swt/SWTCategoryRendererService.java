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
package org.eclipse.emf.ecp.view.internal.categorization.swt;

import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.categorization.swt.SWTCategoryRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * SWTCategoryRendererService which provides the SWTCategoryRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class SWTCategoryRendererService implements EMFFormsRendererService<VCategory> {

	private EMFFormsRendererFactory rendererFactory;
	private ReportService reportService;
	private ServiceReference<EMFFormsRendererFactory> serviceReference;
	private EMFFormsDatabinding databindingService;

	/**
	 * Activate ViewSWTRendererService.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	protected void activate(BundleContext bundleContext) {
		serviceReference = bundleContext.getServiceReference(EMFFormsRendererFactory.class);
		rendererFactory = bundleContext.getService(serviceReference);
	}

	/**
	 * Deactivate ViewSWTRendererService.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	protected void deactivate(BundleContext bundleContext) {
		bundleContext.ungetService(serviceReference);
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (VCategory.class.isInstance(vElement)) {
			return 1;
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
	public AbstractSWTRenderer<VCategory> getRendererInstance(VCategory vElement,
		ViewModelContext viewModelContext) {
		return new SWTCategoryRenderer(vElement, viewModelContext, reportService, rendererFactory, databindingService);
	}

}
