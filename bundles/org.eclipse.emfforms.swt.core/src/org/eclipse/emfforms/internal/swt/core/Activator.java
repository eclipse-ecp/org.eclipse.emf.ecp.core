/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 *
 *******************************************************************************/
package org.eclipse.emfforms.internal.swt.core;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {
	private static final String NULL = "NULL"; //$NON-NLS-1$

	/** The plug-in ID. **/
	public static final String PLUGIN_ID = "org.eclipse.emfforms.swt.core"; //$NON-NLS-1$

	/** The shared instance. **/
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		imageRegistry.values().forEach(Image::dispose);
		if (viewTemplateReference != null) {
			context.ungetService(viewTemplateReference);
		}
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private final Map<String, Image> imageRegistry = new LinkedHashMap<String, Image>(20, .8F, true) {
		private static final long serialVersionUID = 1L;

		// This method is called just after a new entry has been added
		@Override
		public boolean removeEldestEntry(Map.Entry<String, Image> eldest) {
			return size() > 20;
		}

		@Override
		public Image remove(Object arg0) {
			final Image image = super.remove(arg0);
			image.dispose();
			return image;
		}

	};

	/**
	 * Loads an image based on the provided {@link URL} form this bundle. The url may be null, then an empty image is
	 * returned.
	 *
	 * @param url the {@link URL} to load the {@link Image} from
	 * @return the {@link Image}
	 */
	public static Image getImage(URL url) {
		if (!getDefault().imageRegistry.containsKey(url == null ? NULL : url.toExternalForm())) {
			final ImageDescriptor createFromURL = ImageDescriptor.createFromURL(url);
			getDefault().imageRegistry.put(url == null ? NULL : url.toExternalForm(), createFromURL.createImage());
		}
		return getDefault().imageRegistry.get(url == null ? NULL : url.toExternalForm());

	}

	private ServiceReference<VTViewTemplateProvider> viewTemplateReference;

	/**
	 * Returns the currentInstance of the {@link VTViewTemplateProvider}.
	 *
	 * @return the {@link VTViewTemplateProvider}
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
