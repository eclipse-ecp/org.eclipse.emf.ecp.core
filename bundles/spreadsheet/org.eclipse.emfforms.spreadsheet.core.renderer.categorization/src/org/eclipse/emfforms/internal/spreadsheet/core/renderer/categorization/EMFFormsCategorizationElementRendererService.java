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
package org.eclipse.emfforms.internal.spreadsheet.core.renderer.categorization;

import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
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
 * The {@link EMFFormsSpreadsheetRendererService} for {@link VCategorizationElement}.
 *
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsCategorizationElementRendererService implements
	EMFFormsSpreadsheetRendererService<VCategorizationElement> {

	private EMFFormsSpreadsheetRendererFactory emfformsSpreadsheetRendererFactory;

	private ReportService reportService;

	/**
	 * The ReportService to use.
	 *
	 * @param reportService the reportService to set
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
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
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (VCategorizationElement.class.isInstance(vElement)) {
			return 1;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService#getRendererInstance(VElement,ViewModelContext)
	 */
	@Override
	public EMFFormsAbstractSpreadsheetRenderer<VCategorizationElement> getRendererInstance(VCategorizationElement vElement,
		ViewModelContext viewModelContext) {
		return new EMFFormsCategorizationElementRenderer(emfformsSpreadsheetRendererFactory, reportService);
	}

}
