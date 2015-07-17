/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecp.core.util.ECPElement;

/**
 * @author Eike Stepper
 * @author Jonas
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPProvider extends ECPElement, IAdaptable {
	/**
	 * Type of the ECPElement.
	 */
	String TYPE = "Provider"; //$NON-NLS-1$

	/**
	 * Label of the ECPProvider.
	 *
	 * @return the name of the ECPProvider
	 */
	String getLabel();

	/**
	 * Check whether a new repository can be added to this ECPProvider.
	 *
	 * @return true if new repositories can be added.
	 */
	boolean hasCreateRepositorySupport();

	/**
	 * Whether this ECPProvider can have an offline project.
	 *
	 * @return true if offline projects are allowed, false otherwise.
	 */
	boolean hasCreateProjectWithoutRepositorySupport();

}
