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
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICommitProjectController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore Commit Helper delegating to the EMFStore {@link UICommitProjectController}.
 *
 * @author Eugen Neufeld
 *
 */
public final class CommitProjectHelper {

	private CommitProjectHelper() {

	}

	/**
	 * Delegates the call to {@link UICommitProjectController} and triggers an update of the provided
	 * {@link InternalProject}.
	 *
	 * @param project the {@link InternalProject} to commit
	 * @param shell the {@link Shell}
	 */
	public static void commitProject(InternalProject project, Shell shell) {
		final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		// TODO EMFStore how to set usersession?
		// -> why is this necessary? The project is already checked out
		// if (localProject.getUsersession() == null) {
		// ESServerImpl server = (ESServerImpl) EMFStoreProvider.INSTANCE.getServerInfo(project.getRepository());
		// ServerInfo serverInfo = server.getInternalAPIImpl();
		// RunESCommand
		// ((ESLocalProjectImpl) localProject).getInternalAPIImpl().setUsersession(serverInfo.getLastUsersession());
		// }
		// ESUIControllerFactory.INSTANCE.commitProject(HandlerUtil.getActiveShell(event), projectSpace);
		new UICommitProjectController(shell, localProject).execute();
		// is structural because of possible merge
		project.notifyObjectsChanged(Collections.singleton((Object) project.getRepository()), true);
		project.getRepository().notifyObjectsChanged(Collections.singleton((Object) project.getRepository()));
	}
}
