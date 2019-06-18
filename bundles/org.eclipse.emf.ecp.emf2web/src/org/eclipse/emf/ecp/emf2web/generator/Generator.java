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
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.generator;

import org.eclipse.emf.ecore.EObject;

/**
 * A generator for a string representation for the given schema.
 *
 * @author Stefan Dirix
 *
 */
public interface Generator {
	/**
	 * Generate another representation of the given {@link EObject} as a string.
	 *
	 * @param object
	 *            The {@link EObject} which shall serve as a template for the generated string.
	 * @return
	 * 		The string representation of the given {@code object}.
	 */
	String generate(EObject object);
}
