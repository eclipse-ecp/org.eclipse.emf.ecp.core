/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Helper class for accessing the bundle which registers a custom migration.
 *
 * @author jfaltermeier
 *
 */
public final class CustomMigrationHelper {

	private static final String MIGRATION = "migration"; //$NON-NLS-1$
	private static final String POINT_ID = "org.eclipse.emf.ecp.view.edapt.customMigrations"; //$NON-NLS-1$

	private static CustomMigrationHelper customMigrationHelper;

	private final Map<String, String> classToBundleMap;

	private CustomMigrationHelper() {
		classToBundleMap = new LinkedHashMap<String, String>();
		loadExtensionPoint();
	}

	/**
	 * Returns the instance.
	 *
	 * @return the instance
	 */
	public static CustomMigrationHelper getInstance() {
		if (customMigrationHelper == null) {
			customMigrationHelper = new CustomMigrationHelper();
		}
		return customMigrationHelper;
	}

	private void loadExtensionPoint() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		final IConfigurationElement[] configurationElements = extensionRegistry
			.getConfigurationElementsFor(POINT_ID);

		for (final IConfigurationElement configurationElement : configurationElements) {
			registerMigration(configurationElement);
		}
	}

	private void registerMigration(IConfigurationElement configurationElement) {
		final String contributor = configurationElement.getContributor().getName();
		final String clazz = configurationElement.getAttribute(MIGRATION);
		classToBundleMap.put(clazz, contributor);
	}

	/**
	 * Returns the name of the bundle which contains the custom migration.
	 *
	 * @param clazz the fully qualified class name of the custom migration.
	 * @return the bundle name
	 */
	public String getBundleNameForClass(String clazz) {
		return classToBundleMap.get(clazz);
	}
}
