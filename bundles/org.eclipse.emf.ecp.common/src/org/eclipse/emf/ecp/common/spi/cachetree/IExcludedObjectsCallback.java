/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi.cachetree;

/**
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public interface IExcludedObjectsCallback {

	/**
	 * Checks whether an Object is excluded.
	 *
	 * @param object the obejct to check
	 * @return true if excluded, false otherwise
	 */
	boolean isExcluded(Object object);
}
