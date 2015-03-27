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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.swt.CompositeCategorySWTTabRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * CompositeCategorySWTTabRendererService which provides the CompositeCategorySWTTabRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class CompositeCategorySWTTabRendererService implements EMFFormsRendererService<VCategorization> {

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
		if (VCategorization.class.isInstance(vElement)) {
			final VCategorization categorization = VCategorization.class.cast(vElement);
			int depth = 0;
			EObject parent = categorization.eContainer();
			while (!VCategorizationElement.class.isInstance(parent)) {
				parent = parent.eContainer();
				depth++;
			}
			if (VCategorizationElement.class.cast(parent).getMainCategoryDepth() > depth + 1) {
				return 1;
			}
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
	public AbstractSWTRenderer<VCategorization> getRendererInstance(VCategorization vElement,
		ViewModelContext viewModelContext) {
		return new CompositeCategorySWTTabRenderer(vElement, viewModelContext, reportService, rendererFactory);
	}

}
