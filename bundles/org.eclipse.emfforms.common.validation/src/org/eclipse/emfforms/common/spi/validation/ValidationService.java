/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.spi.validation;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emfforms.common.spi.validation.exception.ValidationCanceledException;
import org.eclipse.emfforms.common.spi.validation.filter.ValidationFilter;

/**
 * Generic EMF validation service which allows validation of a collection of {@link EObject}s
 * honoring defined EValidators. Additionally custom {@link Validator}s can be registered
 * i.e. if the feature set of the EValidators is insufficient.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface ValidationService {

	/**
	 * Adds a validation provider to the list of known validators.
	 *
	 * @param validator the {@link Validator} to add
	 */
	void addValidator(Validator validator);

	/**
	 * Removes a validation provider from the list of known validators.
	 *
	 * @param validator the {@link Validator} to remove
	 */
	void removeValidator(Validator validator);

	/**
	 * Registers a validation filter.
	 *
	 * @param <Filter> an {@link ValidationFilter} implementation
	 * @param filter the {@link ValidationFilter} to register
	 */
	<Filter extends ValidationFilter> void registerValidationFilter(Filter filter);

	/**
	 * Unregisters a validation filter.
	 *
	 * @param <Filter> an {@link ValidationFilter} implementation
	 * @param filter the {@link ValidationFilter} to unregister
	 */
	<Filter extends ValidationFilter> void unregisterValidationFilter(Filter filter);

	/**
	 * Registers a {@link ValidationResultListener}.
	 *
	 * @param listener the {@link ValidationResultListener} to register
	 */
	void registerValidationResultListener(ValidationResultListener listener);

	/**
	 * Unregisters a {@link ValidationResultListener}.
	 *
	 * @param listener the {@link ValidationResultListener} to unregister
	 */
	void unregisterValidationResultListener(ValidationResultListener listener);

	/**
	 * Set a {@link SubstitutionLabelProvider} to be used for substituting labels in {@link Diagnostic}s.
	 *
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider
	 *
	 * @param substitutionLabelProvider the {@link SubstitutionLabelProvider} to be set for this service
	 */
	void setSubstitutionLabelProvider(SubstitutionLabelProvider substitutionLabelProvider);

	/**
	 * Validates the given eObject.
	 *
	 * @param eObject the eObject to validate
	 * @return the resulting {@link Diagnostic}, or null if the eObject is filtered by a {@link ValidationFilter}
	 */
	Diagnostic validate(EObject eObject);

	/**
	 * Uses the given iterator to validate all eObjects in a collection.
	 *
	 * @param eObjects the list to validate
	 * @return the resulting set of {@link Diagnostic}s
	 * @throws ValidationCanceledException in case {@link #cancel()} has been called
	 */
	Set<Diagnostic> validate(Iterator<EObject> eObjects) throws ValidationCanceledException;

	/**
	 * Returns true as long as a validation is in process.
	 * Not applicable for single validation runs with {@link #validate(EObject)}.
	 * See {@link #cancel()} to interrupt a running validation process.
	 *
	 * @return true as long as a validation is running, false otherwise.
	 */
	boolean isBusy();

	/**
	 * Allows to cancel the current validation run (if any).
	 * Not applicable for single validation runs with {@link #validate(EObject)}.
	 */
	void cancel();

}
