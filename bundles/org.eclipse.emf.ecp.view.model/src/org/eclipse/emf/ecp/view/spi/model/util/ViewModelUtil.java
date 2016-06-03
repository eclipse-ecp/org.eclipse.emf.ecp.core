/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - removed #resolveDomainReferences
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model.util;

import org.eclipse.core.runtime.Platform;

/**
 * This Util class provides common methods used often when working with the view model.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public final class ViewModelUtil {

	private static Boolean debugMode;
	private static boolean isDebugInitialized;

	private ViewModelUtil() {

	}

	/**
	 * Whether EMF Forms has been started with the debug flag <code>-debugEMFForms</code>.
	 *
	 * @return <code>true</code> if EMF Forms has been started with the debug flag, <code>false</code> otherwise
	 *
	 * @since 1.5
	 */
	public static boolean isDebugMode() {

		if (!isDebugInitialized) {
			debugMode = Boolean.FALSE;
			final String[] commandLineArgs = Platform.getCommandLineArgs();
			for (int i = 0; i < commandLineArgs.length; i++) {
				final String arg = commandLineArgs[i];
				if ("-debugEMFForms".equalsIgnoreCase(arg)) { //$NON-NLS-1$
					debugMode = Boolean.TRUE;
				}
			}
			isDebugInitialized = true;
		}

		return debugMode;
	}
}
