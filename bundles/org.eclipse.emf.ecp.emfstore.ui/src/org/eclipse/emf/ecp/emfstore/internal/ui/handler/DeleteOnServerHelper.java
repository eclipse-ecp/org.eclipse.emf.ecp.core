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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProjectWrapper;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIDeleteRemoteProjectController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore DeleteOnServer Helper delegating to the EMFStore {@link UIDeleteRemoteProjectController}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class DeleteOnServerHelper {
	private DeleteOnServerHelper() {
	}

	/**
	 * Deletes an RemoteProject from the server. Delegates to {@link UIDeleteRemoteProjectController}.
	 * 
	 * @param projectWrapper the {@link EMFStoreProjectWrapper}
	 * @param shell the {@link Shell}
	 */
	public static void deleteOnServer(EMFStoreProjectWrapper projectWrapper, Shell shell) {
		final InternalRepository repo = (InternalRepository) projectWrapper.getRepository();
		final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo(repo);
		new UIDeleteRemoteProjectController(shell, serverInfo.getLastUsersession(),
			projectWrapper.getCheckoutData()).execute();
		repo.notifyObjectsChanged((Collection) Collections.singleton(repo));
	}
}
