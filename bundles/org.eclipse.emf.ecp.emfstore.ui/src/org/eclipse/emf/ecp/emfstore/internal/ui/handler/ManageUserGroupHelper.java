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

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIManageOrgUnitsController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore Manage Users/Groups Helper delegating to the EMFStore {@link UIManageOrgUnitsController}.
 *
 * @author Eugen Neufeld
 *
 */
public final class ManageUserGroupHelper {
	private ManageUserGroupHelper() {
	}

	/**
	 * Manages the user and groups of the selected {@link InternalRepository}. Delegates to
	 * {@link UIManageOrgUnitsController}.
	 *
	 * @param ecpRepository the {@link InternalRepository}
	 * @param shell the {@link Shell}
	 */
	public static void mangeUser(InternalRepository ecpRepository, Shell shell) {
		final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo(ecpRepository);
		// TODO EMFStore Constructor is missing
		new UIManageOrgUnitsController(shell, serverInfo.getLastUsersession()).execute();
	}
}
