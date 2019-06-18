/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.validation.api;

/**
 * Provides a validation service.
 *
 * @author emueller
 *
 */
public interface IValidationServiceProvider {

	/**
	 * Returns the validation service for the given object key.<br/>
	 * If no such validation service does yet exist, it will be created
	 *
	 * @param key
	 *            the object for which to return a validation service
	 *
	 * @return the validation service instance
	 */
	IValidationService getValidationService(Object key);

	/**
	 * Deletes the validation service for the given object.
	 *
	 * @param key the object for which to delete the validation service
	 */
	void deleteValidationService(Object key);

}
