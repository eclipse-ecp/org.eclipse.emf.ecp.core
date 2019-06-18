/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util;

import org.eclipse.emf.ecp.core.ECPProvider;

/**
 * This interface is used to define classes that know the provider they belong to.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public interface ECPProviderAware {
	/**
	 * The Provider this class references in any way.
	 *
	 * @return the known {@link ECPProvider}
	 */
	ECPProvider getProvider();
}
