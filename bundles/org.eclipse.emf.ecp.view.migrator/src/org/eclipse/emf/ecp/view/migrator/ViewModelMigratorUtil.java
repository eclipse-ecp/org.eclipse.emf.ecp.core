/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
import org.osgi.framework.Bundle;

/**
 * @author Lucas Koehler
 *
 */
public final class ViewModelMigratorUtil {

	private static final String MIGRATOR_EXTENSION = "org.eclipse.emf.ecp.view.migrator.migrators"; //$NON-NLS-1$
	private static final String MIGRATOR_CLASS = "class"; //$NON-NLS-1$
	private static final String MIGRATOR_PRIORITY = "priority"; //$NON-NLS-1$

	private ViewModelMigratorUtil() {

	}

	/**
	 *
	 * @return the view model migrator with the highest priority, <code>null</code> if no migrator was registered to the
	 *         extension point.
	 */
	public static ViewModelMigrator getViewModelMigrator() {

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
					ex.printStackTrace();
				} catch (final InvalidRegistryObjectException ex) {
					ex.printStackTrace();
				}
			}
		}
		if (topClass != null) {
			try {
				return topClass.newInstance();
			} catch (final InstantiationException ex) {
				ex.printStackTrace();
			} catch (final IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return null;
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
}
