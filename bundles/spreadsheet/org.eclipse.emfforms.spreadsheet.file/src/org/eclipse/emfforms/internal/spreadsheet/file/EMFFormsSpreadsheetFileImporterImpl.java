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
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.internal.spreadsheet.file.messages.Messages;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;
import org.eclipse.emfforms.spi.spreadsheet.core.transfer.EMFFormsSpreadsheetImporter;
import org.eclipse.emfforms.spi.spreadsheet.file.EMFFormsSpreadsheetFileImporter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Class for importing data from the provided File.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsSpreadsheetFileImporterImpl implements EMFFormsSpreadsheetFileImporter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.file.EMFFormsSpreadsheetFileImporter#importSpreadsheet(java.io.File,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public SpreadsheetImportResult importSpreadsheet(File file, EClass eClass) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext.getServiceReference(ReportService.class);
		final ReportService reportService = bundleContext.getService(serviceReference);

		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			workbook = new HSSFWorkbook(inputStream);
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, 4));
		} finally {
			try {
				inputStream.close();
			} catch (final IOException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, 4));
			}
		}
		bundleContext.ungetService(serviceReference);
		if (workbook == null) {
			final SpreadsheetImportResult result = ErrorFactory.eINSTANCE.createSpreadsheetImportResult();
			result.reportError(Severity.CANCEL, Messages.EMFFormsSpreadsheetFileImporterImpl_FileNotAWorkbook);
			return result;
		}
		final SpreadsheetImportResult result = EMFFormsSpreadsheetImporter.INSTANCE.importSpreadsheet(workbook, eClass);
		return result;
	}

}
