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

/**
 * Service for retrieving translated Strings.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsLocalizationService {
	/**
	 * Return the String for the provided key.
	 *
	 * @param bundle The bundle which provides the translated strings
	 * @param key The key of the string
	 * @return The translated key
	 */
	String getString(Bundle bundle, String key);

	/**
	 * Return the String for the provided key.
	 *
	 * @param clazz The class which needs a translated string
	 * @param key The key of the string
	 * @return The translated key
	 */
	String getString(Class<?> clazz, String key);

	/**
	 * Return whether the key in question is available.
	 *
	 * @param bundle The bundle in which to search for the key
	 * @param key The key to search
	 * @return true if the key is available in the bundle, false otherwise
	 */
	boolean hasKey(Bundle bundle, String key);

	/**
	 * Return whether the key in question is available.
	 *
	 * @param clazz The class in whose bundle to search for the key
	 * @param key The key to search
	 * @return true if the key is available in the bundle, false otherwise
	 */
	boolean hasKey(Class<?> clazz, String key);
}
