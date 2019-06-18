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
package org.eclipse.emfforms.spi.core.services.label;

/**
 * Exception used when no label could be found.
 *
 * @author Eugen Neufeld
 *
 */
public class NoLabelFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 *
	 * @param exception The root {@link Exception}.
	 */
	public NoLabelFoundException(Exception exception) {
		super(exception.getMessage(), exception);
	}
}
