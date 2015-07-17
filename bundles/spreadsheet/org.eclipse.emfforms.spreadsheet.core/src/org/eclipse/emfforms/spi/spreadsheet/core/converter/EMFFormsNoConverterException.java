/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core.converter;

/**
 * Exception thrown by the {@link EMFFormsSpreadsheetValueConverterRegistry} to indicate no converter is registered for
 * given arguments.
 *
 * @author Johannes Faltermeier
 *
 */
public class EMFFormsNoConverterException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link EMFFormsNoConverterException}.
	 *
	 * @param message The message of the exception
	 */
	public EMFFormsNoConverterException(String message) {
		super(message);
	}
}
