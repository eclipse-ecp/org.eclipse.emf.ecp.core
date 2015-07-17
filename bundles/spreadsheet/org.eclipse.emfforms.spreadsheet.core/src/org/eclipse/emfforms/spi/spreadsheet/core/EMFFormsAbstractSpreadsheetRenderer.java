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
package org.eclipse.emfforms.spi.spreadsheet.core;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Abstract class which is the base class for all Spreadsheet renderer.
 *
 * @author Eugen Neufeld
 *
 * @param <VELEMENT> The {@link VElement} type this renderer supports
 */
public abstract class EMFFormsAbstractSpreadsheetRenderer<VELEMENT extends VElement> {

	/**
	 * This is called to trigger the rendering.
	 *
	 * @param workbook The {@link Workbook} to write to
	 * @param vElement The {@link VElement} describing the information to write
	 * @param viewModelContext The {@link ViewModelContext} containing the {@link org.eclipse.emf.ecore.EObject EObject}
	 * @param renderTarget The {@link EMFFormsSpreadsheetRenderTarget} containing the information where to render to
	 * @return The number of rendered columns
	 */
	public abstract int render(Workbook workbook, VELEMENT vElement, ViewModelContext viewModelContext,
		EMFFormsSpreadsheetRenderTarget renderTarget);
}
