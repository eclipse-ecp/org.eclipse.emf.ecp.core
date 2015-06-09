/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentChangedObserver;
import org.eclipse.emf.ecp.edit.internal.swt.ImageDescriptorToImage;
import org.eclipse.emf.ecp.view.model.provider.xmi.ViewModelFileExtensionsManager;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.editor.controls"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ServiceReference<ReportService> reportServiceReference;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// TODO check if necessary
	private final Map<String, ImageDescriptorToImage> imageRegistry = new LinkedHashMap<String, ImageDescriptorToImage>(
		20, .8F, true) {
		private static final long serialVersionUID = 1L;

		// This method is called just after a new entry has been added
		@Override
		public boolean removeEldestEntry(Map.Entry<String, ImageDescriptorToImage> eldest) {
			return size() > 20;
		}

		@Override
		public ImageDescriptorToImage remove(Object arg0) {
			final ImageDescriptorToImage image = super.remove(arg0);
			image.getImage().dispose();
			return image;
		}

	};

	private final Map<Action, ImageDescriptorToImage> imageRegistryByAction = new LinkedHashMap<Action, ImageDescriptorToImage>();

	/**
	 * Loads an image based on the provided path form this bundle.
	 *
	 * @param path the bundle specific path to the image
	 * @return the {@link Image}
	 */
	public static Image getImage(String path) {
		if (!getDefault().imageRegistry.containsKey(path)) {
			getDefault().imageRegistry.put(path,
				new ImageDescriptorToImage(ImageDescriptor.createFromURL(getDefault().getBundle().getResource(path))));
		}
		return getDefault().imageRegistry.get(path).getImage();

	}

	/**
	 * Loads an image for the given Action.
	 *
	 * @param action the action
	 * @return the {@link Image}
	 */
	public static Image getImage(Action action) {
		final String path = action.toString();
		if (!getDefault().imageRegistry.containsKey(path)) {
			getDefault().imageRegistry.put(path,
				new ImageDescriptorToImage(action.getImageDescriptor()));
		}
		return getDefault().imageRegistry.get(path).getImage();

	}

	/**
	 * Loads an image based on the provided {@link URL} form this bundle. The url may be null, then an empty image is
	 * returned.
	 *
	 * @param url the {@link URL} to load the {@link Image} from
	 * @return the {@link Image}
	 */
	public static Image getImage(URL url) {
		if (!getDefault().imageRegistry.containsKey(url == null ? "NULL" : url.toExternalForm())) { //$NON-NLS-1$

			final ImageDescriptor createFromURL = ImageDescriptor.createFromURL(url);
			// final ImageData imageData = createFromURL.getImageData();
			getDefault().imageRegistry.put(url == null ? "NULL" : url.toExternalForm(), new ImageDescriptorToImage( //$NON-NLS-1$
				createFromURL));
		}
		return getDefault().imageRegistry.get(url == null ? "NULL" : url.toExternalForm()).getImage(); //$NON-NLS-1$

	}

	/**
	 * Loads an {@link ImageDescriptor} based on the provided path form this bundle.
	 *
	 * @param path the bundle specific path to the {@link ImageDescriptor}
	 * @return the {@link ImageDescriptor}
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		if (!getDefault().imageRegistry.containsKey(path)) {
			getDefault().imageRegistry.put(path,
				new ImageDescriptorToImage(ImageDescriptor.createFromURL(getDefault().getBundle().getResource(path))));
		}
		return getDefault().imageRegistry.get(path).getImageDescriptor();

	}

	/**
	 * Loads an {@link ImageData} based on the provided {@link URL}.
	 *
	 * @param url the {@link URL} to the {@link ImageData}
	 * @return the {@link ImageData}
	 */
	public static ImageData getImageData(URL url) {
		return ImageDescriptor.createFromURL(url).getImageData();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		/**
		 * Listen to changes in file projects. If a view is affected, reload the view models provided over file
		 * extension point.
		 */
		ECPUtil.getECPObserverBus().register(new ECPProjectContentChangedObserver() {

			@Override
			public Collection<Object> objectsChanged(ECPProject project, Collection<Object> objects) {
				if (project.getProvider().getName().equals(WorkspaceProvider.NAME)) {
					final EList<Object> contents = project.getContents();
					for (final Object object : contents) {
						if (object instanceof VView) {
							ViewModelFileExtensionsManager.dispose();
						}
					}
				}
				return new ArrayList<Object>();

			}

		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		for (final ImageDescriptorToImage descriptorToImage : imageRegistry.values()) {
			descriptorToImage.getImage().dispose();
		}
		for (final ImageDescriptorToImage descriptorToImage : imageRegistryByAction.values()) {
			descriptorToImage.getImage().dispose();
		}
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportServiceReference == null) {
			reportServiceReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ReportService.class);
		}
		return plugin.getBundle().getBundleContext().getService(reportServiceReference);
	}

	/**
	 * Returns the {@link EMFFormsDatabinding} service.
	 *
	 * @return The {@link EMFFormsDatabinding}
	 */
	public EMFFormsDatabinding getEMFFormsDatabinding() {
		final ServiceReference<EMFFormsDatabinding> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsDatabinding.class);

		final EMFFormsDatabinding service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}

	/**
	 * Returns the {@link EMFFormsLabelProvider} service.
	 *
	 * @return The {@link EMFFormsLabelProvider}
	 */
	public EMFFormsLabelProvider getEMFFormsLabelProvider() {
		final ServiceReference<EMFFormsLabelProvider> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsLabelProvider.class);

		final EMFFormsLabelProvider service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}
}
