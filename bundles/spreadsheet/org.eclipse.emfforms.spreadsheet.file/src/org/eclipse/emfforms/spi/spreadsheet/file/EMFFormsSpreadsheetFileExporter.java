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
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.spreadsheet.file.EMFFormsSpreadsheetFileExporterImpl;

/**
 * Entry point for triggering the rendering to an Spreadsheet document.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsSpreadsheetFileExporter {

	/**
	 * Singleton to get access to the exporter.
	 */
	EMFFormsSpreadsheetFileExporter INSTANCE = new EMFFormsSpreadsheetFileExporterImpl();

	/**
	 * Starts the rendering to the Spreadsheet document.
	 *
	 * @param file The {@link File} to export to
	 * @param domainObjects The collection of {@link EObject} containing the data to export
	 * @param viewModel The {@link VView} describing the rendered data
	 */
	void render(File file, Collection<EObject> domainObjects, VView viewModel,
		Map<EObject, Map<String, String>> additionalInformation);
}
