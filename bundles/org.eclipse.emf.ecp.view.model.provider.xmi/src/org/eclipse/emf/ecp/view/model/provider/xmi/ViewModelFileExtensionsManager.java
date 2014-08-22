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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.BasicResourceHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecp.internal.view.model.provider.xmi.Activator;
import org.eclipse.emf.ecp.view.internal.provider.Migrator;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * Manages the view models provided by the file extension point.
 *
 * @author Jonas Helming
 *
 *
 */
@SuppressWarnings("restriction")
public final class ViewModelFileExtensionsManager {

	private static final String FILTER_VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$
	private static final String FILTER_KEY_ATTRIBUTE = "key"; //$NON-NLS-1$
	private static final String FILTER_ELEMENT = "filter"; //$NON-NLS-1$
	private static final String FILE_EXTENSION = "org.eclipse.emf.ecp.view.model.provider.xmi.file"; //$NON-NLS-1$
	private static final String FILEPATH_ATTRIBUTE = "filePath"; //$NON-NLS-1$

	private final Map<EClass, Map<VView, Map<String, String>>> map = new LinkedHashMap<EClass, Map<VView, Map<String, String>>>();

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
		final Map<URI, Map<String, String>> extensionURIS = getExtensionURIS();
		for (final URI uri : extensionURIS.keySet()) {
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
			if (!map.containsKey(view.getRootEClass())) {
				map.put(view.getRootEClass(), new LinkedHashMap<VView, Map<String, String>>());
			}
			map.get(view.getRootEClass()).put(view, extensionURIS.get(uri));
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

		final List<Migrator> migrators = new ArrayList<Migrator>();
		final IConfigurationElement[] migratorExtensions = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.ui.view.manualMigrator"); //$NON-NLS-1$
		for (final IConfigurationElement migratorExtension : migratorExtensions) {
			try {
				final Migrator migrator = (Migrator) migratorExtension.createExecutableExtension("class"); //$NON-NLS-1$
				migrators.add(migrator);
			} catch (final CoreException ex) {
				Activator.log(ex);
			}
		}

		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions
			.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

		loadOptions.put(XMLResource.OPTION_RESOURCE_HANDLER,
			new BasicResourceHandler() {

				@Override
				@SuppressWarnings("rawtypes")
				public void postLoad(XMLResource resource,
					InputStream inputStream, Map options) {
					final Map extMap = resource.getEObjectToExtensionMap();
					for (final Iterator itr = extMap.entrySet().iterator(); itr
						.hasNext();) {
						final Map.Entry entry = (Map.Entry) itr.next();
						final EObject key = (EObject) entry.getKey();
						final AnyType value = (AnyType) entry.getValue();
						final FeatureMap anyAttribute = value.getAnyAttribute();
						final FeatureMap mixed = value.getMixed();
						for (final Migrator migrator : migrators) {
							migrator.migrate(key, anyAttribute, mixed);
						}
					}
				}
			});

		try {
			resource.load(loadOptions);
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
	public static Map<URI, Map<String, String>> getExtensionURIS() {
		final Map<URI, Map<String, String>> ret = new LinkedHashMap<URI, Map<String, String>>();
		final IConfigurationElement[] files = Platform.getExtensionRegistry().getConfigurationElementsFor(
			FILE_EXTENSION);
		final URIConverter converter = new ResourceSetImpl().getURIConverter();
		for (final IConfigurationElement file : files) {
			final String filePath = file.getAttribute(FILEPATH_ATTRIBUTE);

			final IConfigurationElement[] children = file.getChildren(FILTER_ELEMENT);
			final Map<String, String> keyValuePairs = new LinkedHashMap<String, String>();
			for (final IConfigurationElement child : children) {
				final String key = child.getAttribute(FILTER_KEY_ATTRIBUTE);
				final String value = child.getAttribute(FILTER_VALUE_ATTRIBUTE);
				keyValuePairs.put(key, value);
			}

			URI uri;
			final String bundleName = file.getContributor().getName();
			final String path = bundleName + '/' + filePath;
			uri = URI.createPlatformPluginURI(path, false);
			if (converter.exists(uri, null)) {
				ret.put(uri, keyValuePairs);
			} else {
				uri = URI.createPlatformResourceURI(filePath, false);
				if (converter.exists(uri, null)) {
					ret.put(uri, keyValuePairs);
				}
			}

		}
		return ret;
	}

	/**
	 * @param eObject the object to be rendered
	 * @param context a key-value-map from String to Object
	 * @return if there is a xmi file registered containing a view model for the given type
	 */
	public boolean hasViewModelFor(EObject eObject, Map<String, Object> context) {
		return map.containsKey(eObject.eClass());
	}

	/**
	 * @param eObject The {@link EObject} to create a view for
	 * @param context a key-value-map from String to Object
	 * @return a view model for the given eObject
	 */
	public VView createView(EObject eObject, Map<String, Object> context) {
		final Map<VView, Map<String, String>> viewMap = map.get(eObject.eClass());
		if (context == null) {
			return viewMap.keySet().iterator().next();
		}
		VView bestFitting = null;
		int maxNumberFittingKeyValues = -1;
		for (final VView view : viewMap.keySet()) {
			final Map<String, String> viewFilter = viewMap.get(view);
			int currentFittingKeyValues = 0;
			for (final String viewFilterKey : viewFilter.keySet()) {
				if (context.containsKey(viewFilterKey)) {
					final Object contextValue = context.get(viewFilterKey);
					final String viewFilterValue = viewFilter.get(viewFilterKey);
					if (contextValue.toString().equalsIgnoreCase(viewFilterValue)) {
						currentFittingKeyValues++;
					}
					else {
						currentFittingKeyValues = -1;
						break;
					}
				}
				else {
					currentFittingKeyValues = -1;
					break;
				}
			}
			if (currentFittingKeyValues > maxNumberFittingKeyValues) {
				maxNumberFittingKeyValues = currentFittingKeyValues;
				bestFitting = view;
			}
		}

		return EcoreUtil.copy(bestFitting);
	}

}
