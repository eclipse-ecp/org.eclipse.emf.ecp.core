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
package org.eclipse.emf.ecp.view.internal.stack.ui.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.ui.swt.SWTStackItemRenderer;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * VerticalLayoutSWTRendererService which provides the VerticalLayoutSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class StackItemSWTRendererService implements EMFFormsRendererService<VStackItem> {

	private EMFFormsDatabinding databindingService;
	private EMFFormsRendererFactory rendererFactory;
	private ReportService reportService;
	private ServiceReference<EMFFormsRendererFactory> serviceReference;

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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VStackItem.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		return 1d;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public AbstractSWTRenderer<VStackItem> getRendererInstance(VStackItem vElement,
		ViewModelContext viewModelContext) {
		return new SWTStackItemRenderer(vElement, viewModelContext, reportService, rendererFactory,
			databindingService);
	}

}
