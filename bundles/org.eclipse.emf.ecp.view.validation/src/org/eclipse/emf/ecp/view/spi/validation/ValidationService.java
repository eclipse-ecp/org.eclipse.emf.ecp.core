/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;

/**
 * @author Eugen
 * @since 1.5
 *
 */
public interface ValidationService extends GlobalViewModelService {
	/**
	 * Adds a validation provider to the list of known validation providers. The domain model will be revalidated after
	 * the provider has been added.
	 *
	 * @param validationProvider the {@link ValidationProvider} to add
	 */
	void addValidationProvider(ValidationProvider validationProvider);

	/**
	 * Adds a validation provider to the list of known validation providers.
	 *
	 * @param validationProvider the {@link ValidationProvider} to add
	 * @param revalidate whether to revalidate the domain model after the provider has been added
	 * @since 1.6
	 */
	void addValidationProvider(ValidationProvider validationProvider, boolean revalidate);

	/**
	 * Removes a validation provider from the list of known validation providers.
	 *
	 * @param validationProvider the {@link ValidationProvider} to remove
	 */
	void removeValidationProvider(ValidationProvider validationProvider);

	/**
	 * Removes a validation provider from the list of known validation providers. The domain model will be revalidated
	 * after the provider has been removed
	 *
	 * @param validationProvider the {@link ValidationProvider} to remove
	 * @param revalidate whether to revalidate the domain model after the provider has been removed
	 * @since 1.6
	 */
	void removeValidationProvider(ValidationProvider validationProvider, boolean revalidate);

	/**
	 * Registers a listener that will receive {@link org.eclipse.emf.common.util.Diagnostic Diagnostic}s with severity
	 * higher than {@link org.eclipse.emf.common.util.Diagnostic Diagnostic#OK}. After
	 * registration the listener's {@link ViewValidationListener#onNewValidation(java.util.Set)} will be called with
	 * current
	 * results.
	 *
	 * @param listener the listener to register
	 */
	void registerValidationListener(ViewValidationListener listener);

	/**
	 * Deregisters the given listener.
	 *
	 * @param listener the listener to deregister
	 */
	void deregisterValidationListener(ViewValidationListener listener);

	/**
	 * Validates all given eObjects.
	 *
	 * @param eObjects the eObjects to validate
	 */
	// TODO this should be refactored to use an iterator rather than a collection
	void validate(Collection<EObject> eObjects);
}
