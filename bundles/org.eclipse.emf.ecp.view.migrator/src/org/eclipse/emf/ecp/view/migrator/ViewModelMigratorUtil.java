/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.migrator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigrator;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Lucas Koehler
 *
 */
public final class ViewModelMigratorUtil {

	private static final String MIGRATOR_EXTENSION = "org.eclipse.emf.ecp.view.migrator.migrators"; //$NON-NLS-1$
	private static final String MIGRATOR_CLASS = "class"; //$NON-NLS-1$
	private static final String MIGRATOR_PRIORITY = "priority"; //$NON-NLS-1$

	private static ViewModelMigrator migrator;

	private ViewModelMigratorUtil() {

	}

	private static void readExtensionPoint() {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint(MIGRATOR_EXTENSION);
		int topPriority = 0;
		Class<ViewModelMigrator> topClass = null;
		for (final IExtension extension : extensionPoint.getExtensions()) {
			for (final IConfigurationElement configurationElement : extension
				.getConfigurationElements()) {
				try {

					final int priority = Integer.parseInt(configurationElement.getAttribute(MIGRATOR_PRIORITY));
					if (priority > topPriority) {
						final Class<ViewModelMigrator> migratorClass = loadClass(configurationElement
							.getContributor().getName(), configurationElement
								.getAttribute(MIGRATOR_CLASS));
						topClass = migratorClass;
						topPriority = priority;
					}
				} catch (final ClassNotFoundException ex) {
					log(ex);
				} catch (final InvalidRegistryObjectException ex) {
					log(ex);
				}
			}
		}
		if (topClass != null) {
			try {
				migrator = topClass.newInstance();
			} catch (final InstantiationException ex) {
				log(ex);
			} catch (final IllegalAccessException ex) {
				log(ex);
			}
		}
	}

	/**
	 * @return the view model migrator with the highest priority, <code>null</code> if no migrator was registered to the
	 *         extension point.
	 * @since 1.8.0
	 */
	public static ViewModelMigrator getViewModelMigrator() {
		if (migrator == null) {
			readExtensionPoint();
		}
		return migrator;
	}

	/**
	 *
	 * @return the view model migrator with the highest priority if it also supports migrating string based view models.
	 * @since 1.8
	 */
	public static StringViewModelMigrator getStringViewModelMigrator() {
		if (!StringViewModelMigrator.class.isInstance(getViewModelMigrator())) {
			return null;
		}
		return StringViewModelMigrator.class.cast(getViewModelMigrator());
	}

	/**
	 * @return the view model workspace migrator
	 * @since 1.8
	 */
	public static ViewModelWorkspaceMigrator getViewModelWorkspaceMigrator() {
		return getService(ViewModelWorkspaceMigrator.class);
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);
	}

	private static void log(Throwable throwable) {
		final ReportService reportService = getService(ReportService.class);
		if (reportService == null) {
			return;
		}
		reportService.report(new AbstractReport(throwable));
	}

	private static <T> T getService(Class<T> serviceClass) {
		final Bundle bundle = FrameworkUtil.getBundle(ViewModelMigratorUtil.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		final ServiceReference<T> serviceReference = bundleContext.getServiceReference(serviceClass);
		if (serviceReference == null) {
			return null;
		}
		final T service = bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);
		return service;
	}
}
