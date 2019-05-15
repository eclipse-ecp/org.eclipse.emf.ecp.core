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
package org.eclipse.emfforms.spi.spreadsheet.core;

/**
 * The {@link EMFFormsNoRendererException} is used by the {@link EMFFormsSpreadsheetRendererFactory}.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 */
public class EMFFormsNoRendererException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * The EMFFormsNoRendererException throwed by {@link EMFFormsSpreadsheetRendererFactory}.
	 *
	 * @param message The message of the exception
	 */
	public EMFFormsNoRendererException(String message) {
		super(message);
	}
}
