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
package org.eclipse.emf.emfforms.spi.localization;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.osgi.service.localization.BundleLocalization;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

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
	private ServiceReference<LogService> logServiceReference;
	private LogService logService;

	private LocalizationServiceHelper() {
		bundleContext = FrameworkUtil.getBundle(LocalizationServiceHelper.class)
			.getBundleContext();

		logServiceReference = bundleContext.getServiceReference(LogService.class);
		if (logServiceReference == null) {
			return;
		}
		logService = bundleContext.getService(logServiceReference);
	}

	private String getLocale() {
		final ServiceReference<EMFFormsLocaleProvider> serviceReference = bundleContext
			.getServiceReference(EMFFormsLocaleProvider.class);
		if (serviceReference == null) {
			return null;
		}
		final EMFFormsLocaleProvider localeProvider = bundleContext.getService(serviceReference);
		final Locale result = localeProvider.getLocale();
		bundleContext.ungetService(serviceReference);
		return result.getLanguage();
	}

	private String getString(Bundle bundle, String localeLanguage, String key) {
		final ServiceReference<BundleLocalization> serviceReference = bundleContext
			.getServiceReference(BundleLocalization.class);
		final BundleLocalization bundleLocalization = bundleContext.getService(serviceReference);
		final ResourceBundle resourceBundle = bundleLocalization.getLocalization(bundle, localeLanguage);
		if (resourceBundle == null) {
			logService
				.log(
					LogService.LOG_WARNING,
					String
						.format(
							"No ResourceBundle found for Language '%1$s' in Bundle %2$s with Version %3$s.", localeLanguage, bundle.getSymbolicName(), bundle.getVersion().toString())); //$NON-NLS-1$
			return key;
		}
		if (!resourceBundle.containsKey(key)) {
			logService
				.log(
					LogService.LOG_WARNING,
					String
						.format(
							"The ResourceBundle for Language '%1$s' in Bundle %2$s with Version %3$s doesn't contain the key '%4$s'.", localeLanguage, bundle.getSymbolicName(), bundle.getVersion().toString(), key)); //$NON-NLS-1$
			return key;
		}
		final String result = resourceBundle.getString(key);
		bundleContext.ungetService(serviceReference);
		return result;
	}

	private String innerGetString(Bundle bundle, String key) {
		return getString(bundle, getLocale(), key);
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
		return getString(FrameworkUtil.getBundle(clazz), key);
	}
}
