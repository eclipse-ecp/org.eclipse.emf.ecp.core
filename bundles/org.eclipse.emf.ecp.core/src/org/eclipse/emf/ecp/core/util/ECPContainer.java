/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core.util;

/**
 * Common super interface for ECP containers, model elements can belong to. E.g. a
 * {@link org.eclipse.emf.ecp.core.ECPProject ECPProject} or {@link org.eclipse.emf.ecp.core.ECPRepository
 * ECPRepository}.
 *
 *
 *
 * @author Eugen Neufeld
 */
public interface ECPContainer extends ECPElement, ECPPropertiesAware, ECPProviderAware {
	/**
	 * Whether this instance can be deleted or not.
	 *
	 * @return true if this instance can be deleted, false otherwise.
	 */
	boolean canDelete();

	/**
	 * Deletes the current instance.
	 */
	void delete();
}
