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

import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;

/**
 * This class describes an object that can be checked out.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public interface ECPCheckoutSource extends ECPRepositoryAware {
	/**
	 * This return the default name for a project that was checked out.
	 *
	 * @return the default name
	 */
	String getDefaultCheckoutName();

	/**
	 * This method is called in order execute the checkout.
	 *
	 * @param projectName the name of the project to create
	 * @param projectProperties the {@link ECPProperties} to use
	 * @throws ECPProjectWithNameExistsException is thrown when a project with such a name already exists
	 */

	void checkout(String projectName, ECPProperties projectProperties) throws ECPProjectWithNameExistsException;
}
