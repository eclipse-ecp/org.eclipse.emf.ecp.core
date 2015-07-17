/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.util;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Utility methods for helpers which shall not be exposed as API.
 *
 * @author jfaltermeier
 *
 */
public final class HandlerHelperUtil {

	private HandlerHelperUtil() {
		// util
	}

	/**
	 * Loads the specified class from the given bundle.
	 *
	 * @param bundleName the bundle name
	 * @param clazz the class name
	 * @return the loaded class
	 * @throws ClassNotFoundException in case the class could not be loaded
	 *
	 * @param <T> the type of the class to load
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> loadClass(String bundleName, String clazz) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			// TODO externalize strings
			throw new ClassNotFoundException(clazz + " cannot be loaded because bundle " + bundleName //$NON-NLS-1$
				+ " cannot be resolved"); //$NON-NLS-1$
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

}
