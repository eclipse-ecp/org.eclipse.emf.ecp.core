/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.renderer.swt;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages.
 *
 * @author Eugen
 *
 */
public final class Messages {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.diffmerge.internal.renderer.swt.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	/**
	 * retrieve a translated string for a given key.
	 *
	 * @param key the key
	 * @return the translated String
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
