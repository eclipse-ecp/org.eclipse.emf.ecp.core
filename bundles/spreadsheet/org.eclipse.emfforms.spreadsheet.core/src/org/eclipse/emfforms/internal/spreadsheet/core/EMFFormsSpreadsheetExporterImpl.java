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
package org.eclipse.emfforms.internal.spreadsheet.core;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetExporter;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of the {@link EMFFormsSpreadsheetExporter}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetExporterImpl implements EMFFormsSpreadsheetExporter {

	private final ReportService reportService;

	/**
	 * Default Constructor.
	 */
	public EMFFormsSpreadsheetExporterImpl() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ReportService> reportServiceReference = bundleContext
			.getServiceReference(ReportService.class);
		reportService = bundleContext.getService(reportServiceReference);
	}

	@Override
	public Workbook render(String pathToFile, EObject domainObject,
		VView viewModel) {
		final Workbook wb = new HSSFWorkbook();
		render(wb, new EMFFormsSpreadsheetViewModelContext(viewModel, domainObject));
		try {
			writeWB(wb, pathToFile);
		} catch (final IOException e) {
			reportService.report(new EMFFormsSpreadsheetReport(e, EMFFormsSpreadsheetReport.ERROR));
		}
		return wb;
	}

	private void render(Workbook workbook, ViewModelContext viewModelContext) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsSpreadsheetRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsSpreadsheetRendererFactory.class);
		final EMFFormsSpreadsheetRendererFactory emfFormsSpreadsheetRendererFactory = bundleContext
			.getService(serviceReference);

		try {
			final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = emfFormsSpreadsheetRendererFactory
				.getRendererInstance(
					viewModelContext.getViewModel(), viewModelContext);
			renderer.render(workbook, viewModelContext.getViewModel(), viewModelContext,
				new EMFFormsSpreadsheetRenderTarget("root", //$NON-NLS-1$
					0, 0));
		} catch (final EMFFormsNoRendererException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}

	}

	private void writeWB(Workbook wb, String pathToFile) throws IOException {
		final FileOutputStream fileOut = new FileOutputStream(pathToFile);
		wb.write(fileOut);
		fileOut.close();
	}
}
