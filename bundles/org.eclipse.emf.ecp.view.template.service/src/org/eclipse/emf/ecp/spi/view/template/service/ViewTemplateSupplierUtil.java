/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.template.service;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;

/**
 * Utility class for common functionality useful for implementing {@link ViewTemplateSupplier ViewTemplateSuppliers}.
 *
 * @author Lucas Koehler
 * @since 1.18
 */
public final class ViewTemplateSupplierUtil {

	// Do not instantiate utility class
	private ViewTemplateSupplierUtil() {
	}

	/**
	 * Loads a {@link VTViewTemplate} from the resource at the given URI. Returns <code>null</code> if the resource does
	 * not exist, cannot be loaded, or does not contain exactly one {@link VTViewTemplate} on the root level.
	 *
	 * @param uri The URI to the resource containing the {@link VTViewTemplate} to load
	 * @return The loaded {@link VTViewTemplate}
	 */
	public static VTViewTemplate loadViewTemplate(URI uri) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VTTemplatePackage.eNS_URI,
			VTTemplatePackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(uri);
		if (resource == null) {
			return null;
		}

		try {
			resource.load(null);
		} catch (final IOException exception) {
			return null;
		}

		int rsSize = resourceSet.getResources().size();
		EcoreUtil.resolveAll(resourceSet);
		while (rsSize != resourceSet.getResources().size()) {
			EcoreUtil.resolveAll(resourceSet);
			rsSize = resourceSet.getResources().size();
		}

		if (resource.getContents().size() != 1) {
			return null;
		}

		final EObject eObject = resource.getContents().get(0);
		return eObject instanceof VTViewTemplate ? (VTViewTemplate) eObject : null;
	}
}
