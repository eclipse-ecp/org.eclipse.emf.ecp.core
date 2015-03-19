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
package org.eclipse.emf.ecp.view.spi.model;

/**
 * This class provides helper methods for working with VElements.
 *
 * @author Eugen Neufeld
 *
 */
public final class VElementUtil {

	private VElementUtil() {
		// intentionally left empty
	}

	/**
	 * Return the cleaned Name of the {@link VElement}.
	 *
	 * @param vElement The {@link VElement} to get the cleaned name for
	 * @return the cleaned name
	 */
	public static String getCleanName(VElement vElement) {
		if (vElement.getName() != null && vElement.getName().startsWith("%")) { //$NON-NLS-1$
			return vElement.getName().substring(1);
		}
		return vElement.getName();
	}
}
