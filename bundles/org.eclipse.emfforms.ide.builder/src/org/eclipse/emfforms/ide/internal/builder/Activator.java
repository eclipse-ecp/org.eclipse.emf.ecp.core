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
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emfforms.ide.builder.MarkerHelperProvider;
import org.eclipse.emfforms.ide.builder.ValidationDelegateProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emfforms.ide.builder"; //$NON-NLS-1$

	/** Shared instance. */
	private static Activator plugin;

	private ServiceTracker<ValidationDelegateProvider, ValidationDelegateProvider> validationDelegateProviders;
	private ServiceTracker<MarkerHelperProvider, MarkerHelperProvider> markerHelperProviders;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		validationDelegateProviders = new ServiceTracker<ValidationDelegateProvider, ValidationDelegateProvider>(
			context, ValidationDelegateProvider.class, null);
		validationDelegateProviders.open();

		markerHelperProviders = new ServiceTracker<MarkerHelperProvider, MarkerHelperProvider>(
			context, MarkerHelperProvider.class, null);
		markerHelperProviders.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		markerHelperProviders.close();
		validationDelegateProviders.close();
		plugin = null;
		super.stop(context);
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Logs an exception for this plugin.
	 *
	 * @param message the specific message to log
	 * @param t throwable causing this log
	 */
	public static void log(String message, Throwable t) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, t));

	}

	/**
	 * Obtain the currently registered validation delegate providers.
	 *
	 * @return the validation delegate providers
	 */
	public Collection<ValidationDelegateProvider> getValidationDelegateProviders() {
		final ValidationDelegateProvider[] result = {};
		return Arrays.asList(validationDelegateProviders.getServices(result));
	}

	/**
	 * Obtain the currently registered marker helper providers.
	 *
	 * @return the marker helper providers
	 */
	public Collection<MarkerHelperProvider> getMarkerHelperProviders() {
		final MarkerHelperProvider[] result = {};
		return Arrays.asList(markerHelperProviders.getServices(result));
	}

}
