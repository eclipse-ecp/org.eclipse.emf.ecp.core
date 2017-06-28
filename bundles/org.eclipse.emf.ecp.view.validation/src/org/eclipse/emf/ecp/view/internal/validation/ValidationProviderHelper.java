/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mat - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;

/**
 * Helper class for fetching ECP validators.
 * See ValidationService#addValidator(org.eclipse.emfforms.common.spi.validation.Validator)
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public final class ValidationProviderHelper {

	private ValidationProviderHelper() {
	}

	/**
	 * Fetch all known ECP validators using the ECP validationProvider extension point.
	 *
	 * @return the validators found
	 */
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

}
