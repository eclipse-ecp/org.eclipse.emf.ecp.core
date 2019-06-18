/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.datatemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.inject.Named;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.datatemplate.TemplateCollection;

/**
 * Protocol for a data template loader service.
 *
 * @since 1.21
 */
public interface TemplateLoaderService {

	/**
	 * The default template loader service.
	 */
	TemplateLoaderService DEFAULT = uri -> {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI.xmi")); //$NON-NLS-1$
		try (InputStream inputStream = resourceSet.getURIConverter().createInputStream(uri, null)) {
			resource.load(inputStream, null);
			return EcoreUtil.getObjectsByType(resource.getContents(), DataTemplatePackage.Literals.TEMPLATE_COLLECTION);
		}
	};

	/**
	 * Load a template resource from an URI.
	 *
	 * @param uri the URI of the template resource to load
	 * @return the template collection(s) loaded from the resource
	 *
	 * @throws IOException on failure to load the templates
	 */
	Collection<? extends TemplateCollection> loadTemplates(URI uri) throws IOException;

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for {@link TemplateLoaderService} providers.
	 * It is intended that implementations be registered as OSGi services, for
	 * the XMI-based {@link TemplateProvider} to use to load template resources.
	 * The Bazaar context provides the following dependencies for injection:
	 * <ul>
	 * <li>the {@link URI} of the resource to be loaded</li>
	 * <li>the contributor ID of the bundle providing the resource, as a
	 * {@link String} {@linkplain Named named} {@link Provider#CONTRIBUTOR_ID "contributorID"}</li>
	 * </ul>
	 *
	 * @since 1.21
	 */
	// CHECKSTYLE.OFF: Interface - Prefer a distinct type from Vendor for OSGi registration
	public interface Provider extends Vendor<TemplateLoaderService> {
		// CHECKSTYLE.ON: Interface
		/**
		 * Name of the {@link String}-valued context variable for the contributor ID.
		 *
		 * @see Named @Named
		 */
		String CONTRIBUTOR_ID = "contributorID"; //$NON-NLS-1$

		// Nothing to add to the superinterface
	}

}
