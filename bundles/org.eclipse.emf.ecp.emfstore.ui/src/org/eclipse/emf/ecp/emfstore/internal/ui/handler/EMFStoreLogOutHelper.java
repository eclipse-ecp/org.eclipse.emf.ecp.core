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

import java.util.Collections;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UILogoutSessionController;
import org.eclipse.swt.widgets.Shell;

/**
 * Logout Helper delegating to {@link UILogoutSessionController}.
 *
 * @author Eugen
 *
 */
public final class EMFStoreLogOutHelper {

	private EMFStoreLogOutHelper() {
	}

	/**
	 * Logouts to the selected {@link InternalRepository}. Delegates to {@link UILogoutSessionController}.
	 *
	 * @param ecpRepository the {@link InternalRepository}
	 * @param shell the {@link Shell}
	 */
	public static void logout(InternalRepository ecpRepository, Shell shell) {
		final ESServer server = EMFStoreProvider.INSTANCE.getServerInfo(ecpRepository);
		// TODO EMFStore Constructor is missing
		new UILogoutSessionController(shell, server.getLastUsersession()).execute();

		ecpRepository.notifyObjectsChanged(Collections.singleton((Object) ecpRepository));
	}
}
