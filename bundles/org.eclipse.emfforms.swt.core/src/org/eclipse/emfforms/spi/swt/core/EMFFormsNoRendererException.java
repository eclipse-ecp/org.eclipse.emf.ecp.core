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
package org.eclipse.emfforms.spi.swt.core;

/**
 * The {@link EMFFormsNoRendererException} is used by the {@link EMFFormsRendererFactory}.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 */
public class EMFFormsNoRendererException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * The EMFFormsNoRendererException throwed by {@link EMFFormsRendererFactory}.
	 *
	 * @param message The message of the exception
	 */
	public EMFFormsNoRendererException(String message) {
		super(message);
	}

	/**
	 * The EMFFormsNoRendererException throwed by {@link EMFFormsRendererFactory}.
	 *
	 * @param throwable The reason for this exception
	 * @since 1.7
	 */
	public EMFFormsNoRendererException(Throwable throwable) {
		super(throwable);
	}
}
