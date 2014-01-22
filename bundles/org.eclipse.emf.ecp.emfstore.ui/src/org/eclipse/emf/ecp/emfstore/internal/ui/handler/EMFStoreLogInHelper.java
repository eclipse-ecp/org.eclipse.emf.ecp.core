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

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UILoginSessionController;
import org.eclipse.swt.widgets.Shell;

/**
 * Login Helper delegating to {@link UILoginSessionController}.
 * 
 * @author Eugen
 * 
 */
public final class EMFStoreLogInHelper {

	private EMFStoreLogInHelper() {
	}

	/**
	 * Logins to the selected {@link InternalRepository}. Delegates to {@link UILoginSessionController}.
	 * 
	 * @param ecpRepository the {@link InternalRepository}
	 * @param shell the {@link Shell}
	 */
	public static void login(InternalRepository ecpRepository, Shell shell) {
		final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo(ecpRepository);
		new UILoginSessionController(shell, serverInfo).execute();

		// ((TreeView)HandlerUtil.getActivePart(event)).getRefreshAction().run();
		ecpRepository.notifyObjectsChanged((Collection) Collections.singleton(ecpRepository));
	}
}
