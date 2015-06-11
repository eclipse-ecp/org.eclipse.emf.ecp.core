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
package org.eclipse.emfforms.spi.spreadsheet.core.transfer;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.spreadsheet.core.transfer.EMFFormsSpreadsheetExporterImpl;

/**
 * Entry point for triggering the rendering to an Spreadsheet document.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsSpreadsheetExporter {

	/**
	 * Singleton to get access to the exporter.
	 */
	EMFFormsSpreadsheetExporter INSTANCE = new EMFFormsSpreadsheetExporterImpl();

	/**
	 * Starts the rendering to the Spreadsheet document.
	 *
	 * @param domainObjects The collection of {@link EObject} containing the data to export
	 * @param viewModel The {@link VView} describing the rendered data
	 * @return The created {@link Workbook}
	 */
	Workbook render(Collection<? extends EObject> domainObjects, VView viewModel);
}
