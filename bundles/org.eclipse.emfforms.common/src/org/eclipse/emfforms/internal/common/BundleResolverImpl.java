/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emfforms.spi.common.BundleResolver;
import org.osgi.framework.Bundle;

/**
 * Implementation of the BundleResolver.
 *
 * @author Eugen Neufeld
 */
public class BundleResolverImpl implements BundleResolver {

	@Override
	public Bundle getEditBundle(EClassifier eClassifier) throws NoBundleFoundException {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint("org.eclipse.emf.edit.itemProviderAdapterFactories"); //$NON-NLS-1$

		for (final IExtension extension : extensionPoint.getExtensions()) {
			for (final IConfigurationElement configurationElement : extension.getConfigurationElements()) {
				if (configurationElement.getAttribute("uri").equals(eClassifier.getEPackage().getNsURI())) { //$NON-NLS-1$
					return Platform.getBundle(configurationElement.getContributor().getName());
				}
			}
		}
		throw new NoBundleFoundException(eClassifier);
	}

}
