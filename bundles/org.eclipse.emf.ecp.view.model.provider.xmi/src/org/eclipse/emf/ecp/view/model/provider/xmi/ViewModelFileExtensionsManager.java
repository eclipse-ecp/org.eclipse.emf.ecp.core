/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.provider.xmi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.internal.view.model.provider.xmi.Activator;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * @author Jonas
 *         Manages the view models provided by the file extension point.
 * 
 */
public final class ViewModelFileExtensionsManager {

	private static final String FILE_EXTENSION = "org.eclipse.emf.ecp.view.model.provider.xmi.file"; //$NON-NLS-1$
	private static final String FILEPATH_ATTRIBUTE = "filePath"; //$NON-NLS-1$

	private final Map<EClass, VView> map = new HashMap<EClass, VView>();

	private ViewModelFileExtensionsManager() {
	}

	private static ViewModelFileExtensionsManager instance;

	/**
	 * @return the iNSTANCE
	 */
	public static ViewModelFileExtensionsManager getInstance() {
		if (instance == null) {
			instance = new ViewModelFileExtensionsManager();
			instance.init();
		}
		return instance;
	}

	/**
	 * 
	 */
	private void init() {
		final List<URI> extensionURIS = getExtensionURIS();
		for (final URI uri : extensionURIS) {
			final Resource resource = loadResource(uri);
			final EObject eObject = resource.getContents().get(0);
			if (!(eObject instanceof VView)) {
				// TODO:log
				continue;
			}
			final VView view = (VView) eObject;
			if (view.getRootEClass() == null) {
				// TODO:log
				continue;
			}
			map.put(view.getRootEClass(), view);
		}

	}

	/**
	 * Loads a resource containing a view model.
	 * 
	 * @param uri a URI containing the path to the file
	 * @return the loaded resource
	 */
	public static Resource loadResource(URI uri) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VViewPackage.eNS_URI,
			VViewPackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(uri);
		try {
			resource.load(null);
		} catch (final IOException exception) {
			Activator.log(exception);
		}
		return resource;
	}

	/**
	 * Disposed the instance.
	 */
	public static void dispose() {
		instance = null;
	}

	/**
	 * 
	 * @return a list of uris of all xmi files registered
	 */
	public static List<URI> getExtensionURIS() {
		final List<URI> ret = new ArrayList<URI>();
		final IConfigurationElement[] files = Platform.getExtensionRegistry().getConfigurationElementsFor(
			FILE_EXTENSION);
		final URIConverter converter = new ResourceSetImpl().getURIConverter();
		for (final IConfigurationElement file : files) {
			final String filePath = file.getAttribute(FILEPATH_ATTRIBUTE);

			URI uri;
			final String bundleName = file.getContributor().getName();
			final String path = bundleName + '/' + filePath;
			uri = URI.createPlatformPluginURI(path, false);
			if (converter.exists(uri, null)) {
				ret.add(uri);
			} else {
				uri = URI.createPlatformResourceURI(filePath, false);
				if (converter.exists(uri, null)) {
					ret.add(uri);
				}
			}

		}
		return ret;
	}

	/**
	 * @param eObject the object to be rendered
	 * @return if there is a xmi file registered containing a view model for the given type
	 */
	public boolean hasViewModelFor(EObject eObject) {
		return map.containsKey(eObject.eClass());
	}

	/**
	 * @param eObject The {@link EObject} to create a view for
	 * @return a view model for the given eObject
	 */
	public VView createView(EObject eObject) {
		return EcoreUtil.copy(map.get(eObject.eClass()));
	}

}
