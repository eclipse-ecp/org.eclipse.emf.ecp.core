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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.internal.view.model.provider.xmi.Activator;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.spi.model.LocalizationAdapter;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * Manages the view models provided by the file extension point.
 *
 * @author Jonas Helming
 *
 *
 */
public final class ViewModelFileExtensionsManager {

	private static final String FILTER_VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$
	private static final String FILTER_KEY_ATTRIBUTE = "key"; //$NON-NLS-1$
	private static final String FILTER_ELEMENT = "filter"; //$NON-NLS-1$
	private static final String FILE_EXTENSION = "org.eclipse.emf.ecp.view.model.provider.xmi.file"; //$NON-NLS-1$
	private static final String FILEPATH_ATTRIBUTE = "filePath"; //$NON-NLS-1$

	private final Map<EClass, Map<VView, ExtensionDescription>> map = new LinkedHashMap<EClass, Map<VView, ExtensionDescription>>();
	private static File viewModelFolder;

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

	private void init() {
		final Map<URI, List<ExtensionDescription>> extensionURIS = getExtensionURIS();
		for (final URI uri : extensionURIS.keySet()) {
			final Resource resource = loadResource(uri);
			final EObject eObject = resource.getContents().get(0);
			if (!(eObject instanceof VView)) {
				final ReportService reportService = Activator.getReportService();
				if (reportService != null) {
					reportService.report(new AbstractReport(String.format(
						"The registered file '%1$s' doesn't point to a serialized view model.", uri.toString()))); //$NON-NLS-1$
				}
				continue;
			}
			final VView view = (VView) eObject;

			if (view.getRootEClass() == null) {
				final ReportService reportService = Activator.getReportService();
				if (reportService != null) {
					reportService.report(new AbstractReport(String
						.format("The registered view in file '%1$s' doesn't have a set root eclass.", uri.toString()))); //$NON-NLS-1$
				}
				continue;
			}
			for (final ExtensionDescription extensionDescription : extensionURIS.get(uri)) {
				registerView(view, extensionDescription);
			}
		}

	}

	/**
	 * This registers a view.
	 *
	 * @param view The View to register
	 * @param extensionDescription Additional information like the filters used when identifying the correct view for
	 *            the request
	 */
	void registerView(final VView view, final ExtensionDescription extensionDescription) {
		if (!map.containsKey(view.getRootEClass())) {
			map.put(view.getRootEClass(), new LinkedHashMap<VView, ExtensionDescription>());
		}

		map.get(view.getRootEClass()).put(view, extensionDescription);
	}

	/**
	 * Loads a resource containing a view model.
	 *
	 * @param uri a URI containing the path to the file
	 * @return the loaded resource
	 */
	public static Resource loadResource(URI uri) {

		final ViewModelMigrator viewModelMigrator = ViewModelMigratorUtil.getViewModelMigrator();
		if (viewModelMigrator != null) {
			uri = migrateViewModelIfNecesarry(viewModelMigrator, uri);
		}

		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new VViewResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VViewPackage.eNS_URI,
			VViewPackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(uri);

		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();

		try {
			resource.load(loadOptions);
		} catch (final IOException exception) {
			final ReportService reportService = Activator.getReportService();
			if (reportService != null) {
				reportService.report(new AbstractReport(exception,
					"Loading view model failed. Maybe a migration is needed. Please take a look at the migration guide at: http://www.eclipse.org/ecp/emfforms/documentation.html")); //$NON-NLS-1$
			}
		}
		return resource;
	}

	private static URI migrateViewModelIfNecesarry(ViewModelMigrator viewModelMigrator, URI uri) {
		final ReportService reportService = Activator.getReportService();
		try {
			/* copy file from jar to disk */
			final File dest = getFileDestination();
			if (dest == null) {
				return uri;
			}
			final URIConverter uriConverter = new ExtensibleURIConverterImpl();
			final InputStream inputStream = uriConverter.createInputStream(uri);
			copy(inputStream, dest);
			uri = URI.createFileURI(dest.getAbsolutePath());

			/*
			 * parse file and check if migration is needed. Because of limitations in edapt, the passed uri cannot be a
			 * platform plugin uri. I opened a BR.
			 * Otherwise we could delay the file copy process until we know that a migration is actually needed.
			 */
			if (viewModelMigrator.checkMigration(uri)) {
				return uri;
			}
			if (reportService != null) {
				reportService.report(new AbstractReport(
					MessageFormat.format(
						"The view model at {0} needs migration. Please take a look at the migration guide at: http://www.eclipse.org/ecp/emfforms/documentation.html", //$NON-NLS-1$
						uri.toString()),
					IStatus.WARNING));
			}

			viewModelMigrator.performMigration(uri);
			return uri;

		} catch (final IOException ex) {
			if (reportService != null) {
				reportService.report(new AbstractReport(ex,
					MessageFormat.format("The migration of view model at {0} failed.", uri.toString()))); //$NON-NLS-1$
			}
		} catch (final ViewModelMigrationException ex) {
			if (reportService != null) {
				reportService.report(new AbstractReport(ex,
					MessageFormat.format("The migration of view model at {0} failed.", uri.toString()))); //$NON-NLS-1$
			}
		}
		return uri;
	}

	/**
	 * Returns a new file where a view model may be exported to.
	 *
	 * @return the file location
	 */
	private static synchronized File getFileDestination() {
		if (viewModelFolder == null) {
			final File stateLocation = Activator.getInstance().getStateLocation().toFile();
			viewModelFolder = new File(stateLocation, "views"); //$NON-NLS-1$
			if (!viewModelFolder.exists()) {
				viewModelFolder.mkdir();
			}
			for (final File file : viewModelFolder.listFiles()) {
				file.delete();
			}
		}
		final File file = new File(viewModelFolder, System.currentTimeMillis() + ".view"); //$NON-NLS-1$
		file.deleteOnExit();
		return file;
	}

	private static void copy(InputStream in, File file) throws IOException {
		final OutputStream out = new FileOutputStream(file);
		final byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();
		in.close();
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
	public static Map<URI, List<ExtensionDescription>> getExtensionURIS() {
		final Map<URI, List<ExtensionDescription>> ret = new LinkedHashMap<URI, List<ExtensionDescription>>();
		final IConfigurationElement[] files = Platform.getExtensionRegistry().getConfigurationElementsFor(
			FILE_EXTENSION);
		final URIConverter converter = new ResourceSetImpl().getURIConverter();
		for (final IConfigurationElement file : files) {
			final String bundleId = file.getContributor().getName();
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
			if (!converter.exists(uri, null)) {
				uri = URI.createPlatformResourceURI(filePath, false);
				if (!converter.exists(uri, null)) {
					final ReportService reportService = Activator.getReportService();
					if (reportService != null) {
						reportService.report(new AbstractReport(
							String.format("The provided uri '%1$s' doesn't point to an existing file.", uri.toString()), //$NON-NLS-1$
							IStatus.ERROR));
						continue;
					}
				}
			}
			if (!ret.containsKey(uri)) {
				ret.put(uri, new ArrayList<ViewModelFileExtensionsManager.ExtensionDescription>());
			}
			ret.get(uri).add(new ExtensionDescription(keyValuePairs, bundleId));
		}
		return ret;
	}

	/**
	 * @param eObject the object to be rendered
	 * @param properties the {@link VViewModelProperties properties}
	 * @return if there is a xmi file registered containing a view model for the given type
	 */
	public boolean hasViewModelFor(EObject eObject, VViewModelProperties properties) {
		return !findBestFittingViews(eObject, properties).isEmpty();
	}

	/**
	 * @param eObject The {@link EObject} to create a view for
	 * @param properties the {@link VViewModelProperties properties}
	 * @return a view model for the given eObject
	 */
	public VView createView(EObject eObject, VViewModelProperties properties) {
		final List<VView> bestFitting = findBestFittingViews(eObject, properties);

		if (bestFitting.isEmpty()) {
			final ReportService reportService = Activator.getReportService();
			if (reportService != null) {
				reportService.report(new AbstractReport(
					"No view models have been found for the given View Model Loading Properties. This should have not been called!", //$NON-NLS-1$
					IStatus.ERROR));
			}
			return null;
		}

		if (bestFitting.size() != 1) {
			final ReportService reportService = Activator.getReportService();
			if (reportService != null) {
				reportService.report(new AbstractReport(
					"Multiple view models have been found for the given View Model Loading Properties.", //$NON-NLS-1$
					IStatus.WARNING));
			}
		}

		final VView fittingView = bestFitting.get(0);

		final VView copiedView = EcoreUtil.copy(fittingView);
		final String bundleId = map.get(fittingView.getRootEClass()).get(fittingView).getBundleId();
		copiedView.eAdapters().add(new LocalizationAdapter() {

			@Override
			public String localize(String key) {
				return LocalizationServiceHelper.getString(Platform.getBundle(bundleId),
					key);
			}
		});
		copiedView.setLoadingProperties(EcoreUtil.copy(properties));
		return copiedView;
	}

	private static final int FILTER_NOT_MATCHED = Integer.MIN_VALUE;

	private List<VView> findBestFittingViews(EObject eObject, final VViewModelProperties properties) {
		final Map<VView, ExtensionDescription> viewMap = map.get(eObject.eClass());
		if (viewMap == null) {
			return Collections.emptyList();
		}
		final List<VView> bestFitting = new ArrayList<VView>();
		int maxNumberFittingKeyValues = -1;
		VViewModelProperties propertiesToCheck = properties;
		if (propertiesToCheck == null) {
			propertiesToCheck = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		}
		for (final VView view : viewMap.keySet()) {
			final Map<String, String> viewFilter = viewMap.get(view).getKeyValuPairs();
			int currentFittingKeyValues = 0;
			for (final String viewFilterKey : viewFilter.keySet()) {
				if (propertiesToCheck.containsKey(viewFilterKey)) {
					final Object contextValue = propertiesToCheck.get(viewFilterKey);
					final String viewFilterValue = viewFilter.get(viewFilterKey);
					if (contextValue.toString().equalsIgnoreCase(viewFilterValue)) {
						currentFittingKeyValues++;
					} else {
						currentFittingKeyValues = FILTER_NOT_MATCHED;
						break;
					}
				} else {
					currentFittingKeyValues = FILTER_NOT_MATCHED;
					break;
				}
			}
			if (currentFittingKeyValues == FILTER_NOT_MATCHED) {
				continue;
			}
			if (currentFittingKeyValues > maxNumberFittingKeyValues) {
				maxNumberFittingKeyValues = currentFittingKeyValues;
				bestFitting.clear();
				bestFitting.add(view);
			} else if (currentFittingKeyValues == maxNumberFittingKeyValues) {
				bestFitting.add(view);
			}
		}
		return bestFitting;
	}

	/**
	 *
	 * Inner class to hold the relevant data of the extension point.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	static final class ExtensionDescription {
		private final Map<String, String> keyValuPairs;
		private final String bundleId;

		/**
		 * Constructs an ExtensionDescription.
		 *
		 * @param keyValuPairs The Filter
		 * @param bundleId The Bundle that contributed the view model
		 */
		ExtensionDescription(Map<String, String> keyValuPairs, String bundleId) {
			this.keyValuPairs = keyValuPairs;
			this.bundleId = bundleId;
		}

		/**
		 * Return the KeyValuePairs defined in the extension point.
		 *
		 * @return The KeyValuePair Map
		 */
		Map<String, String> getKeyValuPairs() {
			return keyValuPairs;
		}

		/**
		 * The Bundle Id of the bundle contributing the extension point.
		 *
		 * @return The BundleId
		 */
		String getBundleId() {
			return bundleId;
		}
	}

}
