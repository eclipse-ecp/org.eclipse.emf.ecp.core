/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bugs 548761, 552127
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecp.view.internal.validation.ECPSubstitutionLabelProvider;
import org.eclipse.emf.ecp.view.internal.validation.ViewSubstitutionLabelProviderFactory;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

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
	 * Validate the {@code objects} provided by an iterable.
	 *
	 * @param objects the iterable from which to obtain objects to validate
	 *
	 * @since 1.23
	 */
	default void validate(Iterable<? extends EObject> objects) {
		if (objects instanceof Collection<?>) {
			// This should be safe because a correct implementation must never modify the collection
			@SuppressWarnings("unchecked")
			final Collection<EObject> collection = (Collection<EObject>) objects;
			validate(collection);
		} else {
			// Create a new collection
			final Collection<EObject> collection = StreamSupport.stream(objects.spliterator(), false)
				.collect(Collectors.toList());
			validate(collection);
		}
	}

	/**
	 * Validates all given eObjects.
	 *
	 * @param eObjects the eObjects to validate
	 *
	 * @see {@link #validate(Iterable)} for an alternative that is more convenient when validating
	 *      content trees covered by EMF {@code TreeIterator}s
	 */
	void validate(Collection<EObject> eObjects);

	/**
	 * Obtain a substitution label provider suitable for rendering model elements as they
	 * are presented in diagnostics produced by the {@link ValidationService}.
	 *
	 * @param context the view model context
	 * @param adapterFactory an adapter factory to use to get item providers for model elements
	 *
	 * @return the substitution label provider
	 *
	 * @since 1.22
	 */
	static SubstitutionLabelProvider getSubstitutionLabelProvider(EMFFormsViewContext context,
		AdapterFactory adapterFactory) {

		SubstitutionLabelProvider result = null;

		// FIXME: This should not have to be a ComposedAdapterFactory
		if (adapterFactory instanceof ComposedAdapterFactory
			&& context instanceof ViewModelContext
			&& ((ViewModelContext) context).hasService(ViewSubstitutionLabelProviderFactory.class)) {

			result = context.getService(ViewSubstitutionLabelProviderFactory.class)
				.createSubstitutionLabelProvider((ComposedAdapterFactory) adapterFactory);
		}

		if (result == null) {
			result = new ECPSubstitutionLabelProvider(adapterFactory);
		}

		return result;
	}

}
