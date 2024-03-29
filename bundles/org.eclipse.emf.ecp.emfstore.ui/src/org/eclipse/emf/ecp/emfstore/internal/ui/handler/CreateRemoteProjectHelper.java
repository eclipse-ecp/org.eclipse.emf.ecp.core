/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import java.util.Collections;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateRemoteProjectController;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore Create Remote Project helper delegating to the EMFStore {@link UICreateRemoteProjectController}.
 *
 * @author Eugen Neufeld
 *
 */
public final class CreateRemoteProjectHelper {

	private CreateRemoteProjectHelper() {
	}

	/**
	 * Create a remote project on an {@link InternalRepository}. Delegates to {@link UICreateRemoteProjectController}.
	 *
	 * @param ecpRepository the {@link InternalRepository}
	 * @param shell the {@link Shell}
	 */
	public static void createRemoteProject(InternalRepository ecpRepository, Shell shell) {
		final ESServer server = EMFStoreProvider.INSTANCE.getServerInfo(ecpRepository);
		// FIXME:
		final InputDialog dialog = new InputDialog(shell, Messages.CreateRemoteProjectHelper_RemoteProjectName,
			Messages.CreateRemoteProjectHelper_EnterName, "", null); //$NON-NLS-1$

		String projectName = null;
		if (dialog.open() == Window.OK) {
			projectName = dialog.getValue();
		}

		if (projectName == null) {
			return;
		}
		// TODO EMFStore Contructor is missing
		new UICreateRemoteProjectController(shell, server.getLastUsersession(), projectName)
			.execute();
		ecpRepository.notifyObjectsChanged(Collections.singleton((Object) ecpRepository));
	}
}
