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

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
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
	 * @param viewEobject the eObject which will be used to determine the view model
	 * @param properties the {@link VViewModelProperties properties} which will be used to determine the view model
	 * @return The created {@link Workbook}
	 */
	Workbook render(Collection<? extends EObject> domainObjects, EObject viewEobject, VViewModelProperties properties);
}
