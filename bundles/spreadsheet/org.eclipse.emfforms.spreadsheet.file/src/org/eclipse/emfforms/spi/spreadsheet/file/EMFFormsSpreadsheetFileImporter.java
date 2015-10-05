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
package org.eclipse.emfforms.spi.spreadsheet.file;

import java.io.File;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.internal.spreadsheet.file.EMFFormsSpreadsheetFileImporterImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;

/**
 * Entry point for triggering the import from an Spreadsheet document.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsSpreadsheetFileImporter {
	/**
	 * Singleton to get access to the importer.
	 */
	EMFFormsSpreadsheetFileImporter INSTANCE = new EMFFormsSpreadsheetFileImporterImpl();

	/**
	 * Starts the import from an Spreadsheet document.
	 *
	 * @param file The File to read from.
	 * @param eClass The {@link EClass} of the stored objects
	 * @return The result containing the collection of all read objects and the collected errors. This may be
	 *         <code>null</code> if no workbook can be created from the file.
	 */
	SpreadsheetImportResult importSpreadsheet(File file, EClass eClass);

}
