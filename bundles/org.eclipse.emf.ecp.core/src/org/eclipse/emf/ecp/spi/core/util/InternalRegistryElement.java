/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Jonas Helming - JavaDoc
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.core.util;

import org.eclipse.emf.ecp.core.util.ECPElement;

/**
 * @author Eike Stepper
 * @since 1.1
 */
public interface InternalRegistryElement extends ECPElement, ECPDisposable {
	/**
	 * Returns the label.
	 *
	 * @return the label
	 */
	String getLabel();

	/**
	 * Return the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Sets the label.
	 *
	 * @param label the Label to set
	 */
	void setLabel(String label);

	/**
	 * Sets the description.
	 *
	 * @param description the Description to set
	 */
	void setDescription(String description);
}
