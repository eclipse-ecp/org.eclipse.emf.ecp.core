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
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESUsersessionImpl;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUpdateProjectToVersionController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore UpdateProject Helper.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class UpdateProjectHelper {

	private UpdateProjectHelper() {
	}

	/**
	 * Updates an {@link InternalProject} to head. Delegates to {@link UIUpdateProjectController}.
	 * 
	 * @param project the {@link InternalProject}
	 * @param shell the {@link Shell}
	 */
	public static void update(InternalProject project, Shell shell) {
		final ESLocalProject projectSpace = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		// TODO EMFStore how to set user session?
		if (projectSpace.getUsersession() == null) {
			final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo(project.getRepository());
			((ESLocalProjectImpl) projectSpace).toInternalAPI().setUsersession(
				((ESUsersessionImpl) serverInfo.getLastUsersession()).toInternalAPI());
		}
		new UIUpdateProjectController(shell, projectSpace).execute();
		project.notifyObjectsChanged((Collection) Collections.singleton(project), true);
	}

	/**
	 * Updates an {@link InternalProject} to a specific version. Delegates to {@link UIUpdateProjectToVersionController}
	 * .
	 * 
	 * @param project the {@link InternalProject}
	 * @param shell the {@link Shell}
	 */
	public static void updateToVersion(InternalProject project, Shell shell) {
		final ESLocalProject projectSpace = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		// TODO Ugly
		if (projectSpace.getUsersession() == null) {
			final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo(project.getRepository());
			((ESLocalProjectImpl) projectSpace).toInternalAPI().setUsersession(
				((ESUsersessionImpl) serverInfo.getLastUsersession()).toInternalAPI());
		}
		new UIUpdateProjectToVersionController(shell, projectSpace).execute();
		project.notifyObjectsChanged((Collection) Collections.singleton(project), true);
	}
}
