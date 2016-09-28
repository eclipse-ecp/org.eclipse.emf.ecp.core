/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.label;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClassifier;
import org.osgi.framework.Bundle;

/**
 * Implementation of the BundleResolver.
 *
 * @author Eugen Neufeld
 */
public class BundleResolverImpl implements BundleResolver {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.internal.core.services.label.BundleResolver#getEditBundle(org.eclipse.emf.ecore.EClassifier)
	 */
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
