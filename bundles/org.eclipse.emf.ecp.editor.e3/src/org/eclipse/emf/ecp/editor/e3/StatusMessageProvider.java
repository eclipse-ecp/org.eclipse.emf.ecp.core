/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.editor.e3;

import org.eclipse.emf.ecore.EObject;

/**
 * Provides a message for the statusbar of the meeditor.
 *
 * @author helming
 */
public interface StatusMessageProvider {
	/**
	 * If a status message provider can return a message for a certain element.
	 *
	 * @param modelelement the modelelement
	 * @return the priority
	 */
	int canRender(EObject modelelement);

	/**
	 * Return the status message for a certain EObject.
	 *
	 * @param modelelement the modelelement
	 * @return the status message
	 */
	String getMessage(EObject modelelement);
}
