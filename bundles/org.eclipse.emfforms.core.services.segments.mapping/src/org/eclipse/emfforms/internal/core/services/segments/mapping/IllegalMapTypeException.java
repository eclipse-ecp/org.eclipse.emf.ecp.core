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
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * A checked {@link Exception} that is thrown during data binding when a mapping segment or mapping dmr do not use a
 * proper map feature.
 *
 * @author Lucas Koehler
 *
 */
public class IllegalMapTypeException extends DatabindingFailedException {

	private static final long serialVersionUID = 4729660645033802593L;

	/**
	 * Creates a new {@link IllegalMapTypeException}.
	 *
	 * @param message The message of the Exception
	 */
	public IllegalMapTypeException(String message) {
		super(message);
	}

}
