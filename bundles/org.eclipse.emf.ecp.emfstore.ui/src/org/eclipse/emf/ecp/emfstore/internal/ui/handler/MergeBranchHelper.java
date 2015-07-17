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
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIMergeController;
import org.eclipse.swt.widgets.Shell;

/**
 * A MergeBranch Handler. This allows to merge a branch.
 *
 * @author Eugen Neufeld
 *
 */
public final class MergeBranchHelper {

	private MergeBranchHelper() {
	}

	/**
	 * Merges a branch into the selected {@link InternalProject}. Delegates to {@link UIMergeController}.
	 *
	 * @param project the {@link InternalProject} to merge into
	 * @param shell the {@link Shell}
	 */
	public static void mergeBranch(InternalProject project, Shell shell) {
		final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		// TODO EMFStore Constructor is missing
		new UIMergeController(shell, localProject).execute();
	}
}
