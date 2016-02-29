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
 * Eugen Neufeld - added constructor with Throwable, changed description
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core.converter;

/**
 * Exception thrown when the value conversion fails.
 *
 * @author Johannes Faltermeier
 *
 */
public class EMFFormsConverterException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link EMFFormsConverterException}.
	 *
	 * @param message The message of the exception
	 */
	public EMFFormsConverterException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link EMFFormsConverterException}.
	 *
	 * @param throwable The Throwable that caused this exception
	 */
	public EMFFormsConverterException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructs a new {@link EMFFormsConverterException}.
	 *
	 * @param message The message of the exception
	 * @param throwable The Throwable that caused this exception
	 */
	public EMFFormsConverterException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
