/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The preference helper aids storing {key, value} pairs.
 *
 * @author pfeifferc
 *
 */
public final class PreferenceHelper {

	private PreferenceHelper() {
		// nothing to do here
	}

	// TODO: ChainSaw namespace?
	private static final String PREFERENCE_NODE = "ecp"; //$NON-NLS-1$

	/**
	 * Get a preference value for a specific key.
	 *
	 * @param key the
	 * @param defaultValue the
	 * @return the value if it exists, otherwise the defaultValue
	 */
	public static String getPreference(String key, String defaultValue) {
		final String value = Platform.getPreferencesService().getRootNode().node(ConfigurationScope.SCOPE)
			.node(PREFERENCE_NODE).get(key, defaultValue);
		return value;
	}

	/**
	 * Set the preference value for a specific key. Key and value must not equal null.
	 *
	 * @param key the
	 * @param value the
	 */
	public static void setPreference(String key, String value) {
		if (key != null && value != null) {
			Platform.getPreferencesService().getRootNode().node(ConfigurationScope.SCOPE).node(PREFERENCE_NODE)
				.put(key, value);
			try {
				Platform.getPreferencesService().getRootNode().node(ConfigurationScope.SCOPE).node(PREFERENCE_NODE)
					.flush();
			} catch (final BackingStoreException e) {
				Activator.log(
					"Could not persist the preference change: {" + key + ", " + value + "}", e); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
	}

}
