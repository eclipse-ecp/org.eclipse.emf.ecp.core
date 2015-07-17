/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ecore.editor.util;

/**
 * Custom exception to indicate an error during the generation of a genmodel.
 *
 * @author emueller
 *
 */
@SuppressWarnings("serial")
public class EcoreGenException extends Exception {

	/**
	 * Constructor.
	 *
	 * @param msg
	 *            exception message
	 */
	public EcoreGenException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with additional cause.
	 *
	 * @param msg
	 *            exception message
	 * @param cause
	 *            the cause of the exception
	 */
	public EcoreGenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
