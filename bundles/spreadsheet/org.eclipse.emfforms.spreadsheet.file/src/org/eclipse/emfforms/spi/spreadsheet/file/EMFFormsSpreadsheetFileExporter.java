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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
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
	 * @param viewEobject the eObject which will be used to determine the view model
	 * @param properties the {@link VViewModelProperties properties} which will be used to determine the view model
	 */
	void render(File file, Collection<EObject> domainObjects, EObject viewEobject, VViewModelProperties properties);
}
