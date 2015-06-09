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
package org.eclipse.emfforms.spi.localization;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Helper class for retrieving translated strings.
 *
 * @author Eugen Neufeld
 */
public final class LocalizationServiceHelper {

	private static LocalizationServiceHelper instance;

	private static LocalizationServiceHelper getInstance() {
		if (instance == null) {
			instance = new LocalizationServiceHelper();
		}
		return instance;
	}

	private final BundleContext bundleContext;

	private LocalizationServiceHelper() {
		bundleContext = FrameworkUtil.getBundle(LocalizationServiceHelper.class)
			.getBundleContext();
	}

	private String innerGetString(Bundle bundle, String key) {
		final ServiceReference<EMFFormsLocalizationService> serviceReference = bundleContext
			.getServiceReference(EMFFormsLocalizationService.class);
		if (serviceReference == null) {
			return null;
		}
		final EMFFormsLocalizationService localeProvider = bundleContext.getService(serviceReference);
		final String result = localeProvider.getString(bundle, key);
		bundleContext.ungetService(serviceReference);
		return result;
	}

	private String innerGetString(Class<?> clazz, String key) {
		final ServiceReference<EMFFormsLocalizationService> serviceReference = bundleContext
			.getServiceReference(EMFFormsLocalizationService.class);
		if (serviceReference == null) {
			return null;
		}
		final EMFFormsLocalizationService localeProvider = bundleContext.getService(serviceReference);
		final String result = localeProvider.getString(clazz, key);
		bundleContext.ungetService(serviceReference);
		return result;
	}

	/**
	 * Return the String for the provided key.
	 *
	 * @param bundle The bundle which provides the translated strings
	 * @param key The key of the string
	 * @return The translated key
	 */
	public static String getString(Bundle bundle, String key) {
		return getInstance().innerGetString(bundle, key);
	}

	/**
	 * Return the String for the provided key.
	 *
	 * @param clazz The class which needs a translated string
	 * @param key The key of the string
	 * @return The translated key
	 */
	public static String getString(Class<?> clazz, String key) {
		return getInstance().innerGetString(clazz, key);
	}
}
