/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.internal.migration;

/**
 * Exception that might be thrown while migrating a view model file.
 *
 */
public class ViewMigrationException extends Exception {

	private static final long serialVersionUID = -3818815268124260934L;

	/**
	 * Constructor.
	 *
	 * @param message the error message
	 */
	public ViewMigrationException(String message) {
		super(message);
	}

	/**
	 * Constructor that wraps the exception that caused the view migration to fail.
	 *
	 * @param exception the inner exception
	 */
	public ViewMigrationException(Exception exception) {
		super(exception);
	}
}
