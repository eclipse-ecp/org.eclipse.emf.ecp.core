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
package org.eclipse.emfforms.spi.spreadsheet.core.transfer;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.internal.spreadsheet.core.transfer.EMFFormsSpreadsheetImporterImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;

/**
 * Entry point for triggering the import from an Spreadsheet document.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsSpreadsheetImporter {
	/**
	 * Singleton to get access to the importer.
	 */
	EMFFormsSpreadsheetImporter INSTANCE = new EMFFormsSpreadsheetImporterImpl();

	/**
	 * Starts the import from an Spreadsheet document.
	 *
	 * @param workbook The Workbook to read from.
	 * @param eClass The {@link EClass} of the stored objects
	 * @return The result containing the collection of all read objects and the collected errors.
	 */
	SpreadsheetImportResult importSpreadsheet(Workbook workbook, EClass eClass);

}
