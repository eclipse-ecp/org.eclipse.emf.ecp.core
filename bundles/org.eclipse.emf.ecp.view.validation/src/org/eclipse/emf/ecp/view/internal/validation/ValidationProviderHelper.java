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
 * mat - initial API and implementation
 * Christian W. Damus - bug 552715
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.eclipse.emfforms.common.spi.validation.ValidationService;

/**
 * Helper class for fetching ECP validators.
 * See {@link ValidationService#addValidator(org.eclipse.emfforms.common.spi.validation.Validator)}.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public final class ValidationProviderHelper {

	private final Map<ValidationProvider, ValidationProvider> providers = new HashMap<>();
	private final ViewModelContext context;
	private final ValidationServiceImpl validationService;

	/**
	 * Initializes me with the validation service that owns me.
	 *
	 * @param context the view model context
	 * @param validationService my owner
	 */
	ValidationProviderHelper(ViewModelContext context, ValidationServiceImpl validationService) {
		super();

		this.context = context;
		this.validationService = validationService;
	}

	/**
	 * Query the registered validation providers.
	 *
	 * @return the registered validation providers
	 */
	public Set<ValidationProvider> getValidationProviders() {
		return Collections.unmodifiableSet(providers.keySet());
	}

	/**
	 * Initialize my providers.
	 */
	public void initialize() {
		fetchValidationProviders().forEach(p -> validationService.addValidationProvider(p, false));
	}

	/**
	 * Dispose and clear out the registered validation providers.
	 */
	public void dispose() {
		try {
			providers.keySet().forEach(p -> validationService.removeValidationProvider(p, false));
		} finally {
			providers.clear();
		}
	}

	/**
	 * Wrap a validation {@code provider} to inject my view model {@code context} through the core validation service.
	 *
	 * @param provider the provider to wrap
	 *
	 * @return the wrapper
	 */
	ValidationProvider wrap(ValidationProvider provider) {
		return providers.computeIfAbsent(provider, Wrapper::new);
	}

	/**
	 * Unwrap a validation {@code provider}.
	 *
	 * @param provider the provider to unwrap (usually a wrapper)
	 *
	 * @return the unwrapped provider
	 */
	ValidationProvider unwrap(ValidationProvider provider) {
		return provider instanceof Wrapper ? ((Wrapper) provider).delegate : provider;
	}

	/**
	 * Fetch all known ECP validators using the ECP validationProvider extension point.
	 *
	 * @return the validators found
	 *
	 * @deprecated Since 1.23, use instances of this class, instead
	 */
	@Deprecated
	public static Set<ValidationProvider> fetchValidationProviders() {
		final Set<ValidationProvider> providers = new LinkedHashSet<ValidationProvider>();

		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return providers;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.validation.validationProvider"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final ValidationProvider provider = (ValidationProvider) e.createExecutableExtension("class"); //$NON-NLS-1$
				providers.add(provider);
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
		return providers;
	}

	//
	// Nested types
	//

	/**
	 * A wrapper for a validation provider that injects the current view model context into it.
	 */
	private final class Wrapper implements ValidationProvider {

		private final ValidationProvider delegate;

		Wrapper(ValidationProvider delegate) {
			super();

			this.delegate = delegate;
		}

		@Override
		public void setContext(ViewModelContext context) {
			delegate.setContext(context);
		}

		@Override
		public void unsetContext(ViewModelContext context) {
			delegate.unsetContext(context);
		}

		@SuppressWarnings("unchecked") // The core validation service doesn't write to the list
		@Override
		public List<Diagnostic> validate(EObject eObject) {
			final Iterable<? extends Diagnostic> delegated = delegate.validate(context, eObject);
			if (delegated == null) {
				return Collections.emptyList();
			}
			if (delegated instanceof List<?>) {
				return (List<Diagnostic>) delegated;
			}
			if (delegated instanceof Collection<?>) {
				return new ArrayList<>((Collection<Diagnostic>) delegated);
			}
			final List<Diagnostic> result = new ArrayList<Diagnostic>();
			delegated.forEach(result::add);
			return result;
		}

		@Override
		public Iterable<? extends Diagnostic> validate(ViewModelContext userContext, EObject object) {
			return delegate.validate(userContext, object);
		}

	}

}
