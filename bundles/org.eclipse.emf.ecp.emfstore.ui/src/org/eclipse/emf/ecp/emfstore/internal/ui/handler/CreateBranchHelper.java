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
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICreateBranchController;
import org.eclipse.swt.widgets.Shell;

/**
 * A CreateBranch Helper. This allows to create a new branch.
 *
 * @author Eugen Neufeld
 *
 */
public final class CreateBranchHelper {

	private CreateBranchHelper() {
	}

	/**
	 * Creates a branch of an {@link InternalProject}. Delegates the call to {@link UICreateBranchController}.
	 *
	 * @param project the {@link InternalProject} to create a branch from
	 * @param shell the {@link Shell}
	 */
	public static void createBranch(InternalProject project, Shell shell) {
		final ESLocalProject projectSpace = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		// TODO EMFStore constructor missing
		new UICreateBranchController(shell, projectSpace).execute();
		project.notifyObjectsChanged(Collections.singleton((Object) project), false);
	}
}
