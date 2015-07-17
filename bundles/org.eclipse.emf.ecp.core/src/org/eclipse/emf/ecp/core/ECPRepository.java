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
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core;

import org.eclipse.emf.ecp.core.util.ECPContainer;

/**
 * This class describes a repository.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPRepository extends ECPContainer {
	/**
	 * The type of this ECPElement.
	 */
	String TYPE = "Repository"; //$NON-NLS-1$

	/**
	 * Returns the label for the {@link ECPRepository}.
	 *
	 * @return the label for this repository
	 */
	String getLabel();

	/**
	 * Returns the description for this {@link ECPRepository}.
	 *
	 * @return the description for this repository
	 */
	String getDescription();

}
