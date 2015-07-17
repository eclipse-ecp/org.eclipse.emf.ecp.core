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
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import org.eclipse.emf.ecp.emfstore.internal.ui.Activator;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Helper class to start a local EMFStore Server.
 *
 * @author Eugen
 *
 */
public final class StartLocalServerHelper {
	private StartLocalServerHelper() {
	}

	/**
	 * Starts a local EMFStore Server.
	 */
	public static void startLocalServer() {
		try {
			EMFStoreController.runAsNewThread();
		} catch (final FatalESException ex) {
			Activator.log(ex);
		}
	}
}
