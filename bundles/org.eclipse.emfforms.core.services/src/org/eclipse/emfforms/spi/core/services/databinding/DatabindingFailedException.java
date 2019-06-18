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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding;

/**
 * A checked {@link Exception} that is thrown by the {@link EMFFormsDatabinding databinding service} when the
 * databinding fails.
 *
 * @author Lucas Koehler
 *
 */
public class DatabindingFailedException extends Exception {

	private static final long serialVersionUID = -6546814649353012826L;

	/**
	 * Creates a new {@link DatabindingFailedException}.
	 *
	 * @param message The message of the Exception
	 */
	public DatabindingFailedException(String message) {
		super(message);
	}
}
