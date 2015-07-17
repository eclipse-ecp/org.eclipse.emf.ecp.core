/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - contributed inner class
 * Maximilian Koegel - extracted to own class
 ******************************************************************************/
package org.eclipse.emf.ecp.core.exceptions;

/**
 * This exception is thrown when we try to add a project with a name that already exists.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 *
 */
public class ECPProjectWithNameExistsException extends Exception {

	private static final long serialVersionUID = 2896166396540238251L;

	/**
	 * Convenient Constructor for this Exception.
	 *
	 * @param message the message of this exception
	 */

	public ECPProjectWithNameExistsException(String message) {
		super(message);
	}
}