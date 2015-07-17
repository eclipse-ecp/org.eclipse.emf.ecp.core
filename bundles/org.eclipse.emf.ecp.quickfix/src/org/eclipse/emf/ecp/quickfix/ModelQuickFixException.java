/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 */
package org.eclipse.emf.ecp.quickfix;

/**
 * Exception that occurs when applying a quick fix.
 *
 * @author Alexandra Buzila
 *
 */
public class ModelQuickFixException extends Exception {

	private static final long serialVersionUID = 298295556617269576L;

	/**
	 * @param message - the detail message
	 *
	 * @see java.lang.Exception#Exception(String message)
	 */
	public ModelQuickFixException(String message) {
		super(message);
	}

}
