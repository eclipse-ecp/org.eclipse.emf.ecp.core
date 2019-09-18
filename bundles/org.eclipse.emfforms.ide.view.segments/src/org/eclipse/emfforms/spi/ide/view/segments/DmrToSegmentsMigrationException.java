/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.ide.view.segments;

import java.text.MessageFormat;

/**
 * Thrown when the migration of a resource from legacy to segment based dmrs fails.
 *
 * @author Lucas Koehler
 * @since 1.22
 */
public class DmrToSegmentsMigrationException extends Exception {

	private static final long serialVersionUID = 6563601659356559914L;

	/**
	 * @param messageFormat describes the exception in {@link MessageFormat}
	 * @param messageArgs Arguments for the message format.
	 */
	public DmrToSegmentsMigrationException(String messageFormat, Object... messageArgs) {
		super(MessageFormat.format(messageFormat, messageArgs));
	}

	/**
	 * @param cause the root cause for this exception
	 * @param messageFormat describes the exception in {@link MessageFormat}
	 * @param messageArgs Arguments for the message format.
	 */
	public DmrToSegmentsMigrationException(Throwable cause, String messageFormat, Object... messageArgs) {
		super(MessageFormat.format(messageFormat, messageArgs), cause);
	}
}
