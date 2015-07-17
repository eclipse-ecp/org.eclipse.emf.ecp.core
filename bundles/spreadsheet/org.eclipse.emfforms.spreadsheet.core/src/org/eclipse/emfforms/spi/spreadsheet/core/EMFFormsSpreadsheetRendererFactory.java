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

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This is the factory which selects the most fitting renderer for the provided {@link VElement} and
 * {@link ViewModelContext}.
 *
 * @author Eugen Neufeld
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface EMFFormsSpreadsheetRendererFactory {

	/**
	 * Adds an {@link EMFFormsSpreadsheetRendererService} to the list of available renderer.
	 * 
	 * @param spreadsheetRendererService The EMFFormsSpreadsheetRendererService to add
	 */
	void addEMFFormsSpreadsheetRendererService(EMFFormsSpreadsheetRendererService<VElement> spreadsheetRendererService);

	/**
	 * Removes an {@link EMFFormsSpreadsheetRendererService} from the list of available renderer.
	 * 
	 * @param spreadsheetRendererService The EMFFormsSpreadsheetRendererService to remove
	 */
	void removeEMFFormsSpreadsheetRendererService(EMFFormsSpreadsheetRendererService<VElement> spreadsheetRendererService);

	/**
	 * Returns the renderer which fits the provided {@link VElement} and {@link ViewModelContext} the most.
	 *
	 * @param vElement the {@link VElement} to find the renderer for
	 * @param viewModelContext the {@link ViewModelContext} to find the renderer for
	 * @param <VELEMENT> The VElement type
	 * @return the renderer
	 * @throws EMFFormsNoRendererException is thrown when no renderer can be found
	 */
	<VELEMENT extends VElement> EMFFormsAbstractSpreadsheetRenderer<VElement> getRendererInstance(VELEMENT vElement,
		ViewModelContext viewModelContext) throws EMFFormsNoRendererException;

}
