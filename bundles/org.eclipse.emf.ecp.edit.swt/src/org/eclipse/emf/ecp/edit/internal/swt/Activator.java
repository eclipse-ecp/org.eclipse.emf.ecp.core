/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. **/
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.edit.swt"; //$NON-NLS-1$

	/** The shared instance. **/
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (final ImageDescriptorToImage descriptorToImage : imageRegistry.values()) {
			descriptorToImage.getImage().dispose();
		}
		for (final ImageDescriptorToImage descriptorToImage : imageRegistryByAction.values()) {
			descriptorToImage.getImage().dispose();
		}
		if (viewTemplateReference != null) {
			context.ungetService(viewTemplateReference);
		}
		super.stop(context);
		plugin = null;
	}

	// END SUPRESS CATCH EXCEPTION
	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Logs exception.
	 *
	 * @param e the {@link Exception} to log
	 */
	public static void logException(Exception e) {
		getDefault().getLog().log(
			new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
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
			final ImageData imageData = createFromURL.getImageData();
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

	private ServiceReference<ECPControlFactory> controlFactoryReference;

	/**
	 * Returns the currentInstance of the control factory.
	 *
	 * @return the {@link ECPControlFactory}
	 */
	public ECPControlFactory getECPControlFactory() {
		if (controlFactoryReference == null) {
			controlFactoryReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ECPControlFactory.class);
		}
		return plugin.getBundle().getBundleContext().getService(controlFactoryReference);
	}

	/**
	 * Frees the OSGi Service, so that it can be shutdown during runtime.
	 */
	public void ungetECPControlFactory() {
		if (controlFactoryReference == null) {
			return;
		}
		plugin.getBundle().getBundleContext().ungetService(controlFactoryReference);
		controlFactoryReference = null;
	}

	private ServiceReference<VTViewTemplateProvider> viewTemplateReference;

	/**
	 * Returns the currentInstance of the {@link VTViewTemplateProvider}.
	 *
	 * @return the {@link ECPControlFactory}
	 */
	public VTViewTemplateProvider getVTViewTemplateProvider() {
		if (viewTemplateReference == null) {
			viewTemplateReference = plugin.getBundle().getBundleContext()
				.getServiceReference(VTViewTemplateProvider.class);
		}
		if (viewTemplateReference != null) {
			return plugin.getBundle().getBundleContext().getService(viewTemplateReference);
		}
		return null;
	}

}
