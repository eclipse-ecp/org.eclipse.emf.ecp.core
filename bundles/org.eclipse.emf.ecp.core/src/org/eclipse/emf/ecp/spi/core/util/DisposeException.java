/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.core.util;

/**
 * This is an Exception that is thrown during dispose actions.
 *
 * @author Eugen Neufeld
 * @since 1.1
 *
 */
public class DisposeException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is
	 *            permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DisposeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of (cause==null ? null :
	 * cause.toString())
	 *
	 * @param cause the cause (which is saved for later retrieval by the getCause() method).
	 */
	public DisposeException(Throwable cause) {
		super(cause);
	}

}