/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.transfer.EMFFormsSpreadsheetExporter;
import org.eclipse.emfforms.spi.spreadsheet.file.EMFFormsSpreadsheetFileExporter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Class for exporting data to a workbook based on the provided File.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsSpreadsheetFileExporterImpl implements EMFFormsSpreadsheetFileExporter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.file.EMFFormsSpreadsheetFileExporter#render(File, Collection, VView,
	 *      Map)
	 */
	@Override
	public void render(File file, Collection<EObject> domainObjects, VView viewModel,
		Map<EObject, Map<String, String>> additionalInformation) {
		final Workbook workbook = EMFFormsSpreadsheetExporter.INSTANCE.render(domainObjects, viewModel,
			additionalInformation);

		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext.getServiceReference(ReportService.class);
		final ReportService reportService = bundleContext.getService(serviceReference);

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, 4));
		} finally {
			try {
				outputStream.close();
			} catch (final IOException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, 4));
			}
		}
		bundleContext.ungetService(serviceReference);
	}

}
