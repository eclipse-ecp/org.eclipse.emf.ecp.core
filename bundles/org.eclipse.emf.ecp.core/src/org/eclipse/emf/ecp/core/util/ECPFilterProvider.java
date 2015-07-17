/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util;

import java.util.Set;

/**
 * Interface to provide a collection of nsUris that should not be available.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPFilterProvider {

	/**
	 * Returns the {@link Set} of nsUris that should be by default not visible in ecp.
	 *
	 * @return the Set of nsUris
	 */
	Set<String> getHiddenPackages();
}
