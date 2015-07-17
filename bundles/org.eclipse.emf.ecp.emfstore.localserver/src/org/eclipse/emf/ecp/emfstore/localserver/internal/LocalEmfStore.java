/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Tobias Verhoeven - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.localserver.internal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * The LocalEmfStore contains static methods to manually start the EmfStrore.
 *
 * @author Tobias Verhoeven
 */
@SuppressWarnings("restriction")
public final class LocalEmfStore {

	private static final String STARTUPPREFERENCE = "STARTUP"; //$NON-NLS-1$

	/**
	 * Instantiates a new local emf store.
	 */
	private LocalEmfStore() {
	}

	/**
	 * Starts a EMFStore instance.
	 */
	public static void start() {

		writePasswordFile();

		try {
			EMFStoreController.runAsNewThread();
		} catch (final FatalESException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	// creates a user.properties file in the conf folder if none does exist.
	private static void writePasswordFile() {
		PrintWriter printWriter = null;
		final File file = new File(org.eclipse.emf.emfstore.internal.server.ServerConfiguration.getServerHome()
			+ "/conf/user.properties"); //$NON-NLS-1$

		if (!file.exists()) {
			try {
				printWriter = new PrintWriter(file);
				printWriter.println("testUserA=password"); //$NON-NLS-1$
				printWriter.println("testUserB=password"); //$NON-NLS-1$
				printWriter.flush();
			} catch (final IOException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}
		}
	}

	/**
	 * Start an EMFStore instance if the preference value
	 * org.eclipse.emf.ecp.emfstore.localserver/STARTUP is true.
	 */
	public static void startIfNeeded() {
		if (shouldStart()) {
			start();
		}
	}

	/**
	 * Should start.
	 *
	 * @return returns weather a call to startIfNeeded will start the EmfStore by reading
	 *         the preference org.eclipse.emf.ecp.emfstore.localserver/STARTUP
	 */
	public static boolean shouldStart() {
		final IPreferencesService service = Platform.getPreferencesService();
		return service.getBoolean(Activator.PLUGIN_ID, STARTUPPREFERENCE, true, null);
	}
}
