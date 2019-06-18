/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.workspace.migrator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.ide.spi.util.WorkspaceUtil;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.migrator.ViewModelWorkspaceMigrator;

/**
 * Default implementation of the {@link ViewModelWorkspaceMigrator} interface.
 *
 * @since 1.8
 * @author Alexandra Buzila
 *
 */
public final class ViewModelWorkspaceMigratorService implements ViewModelWorkspaceMigrator {

	private static ViewModelMigrator viewModelMigrator;

	@Override
	public ArrayList<URI> getURIsToMigrate() throws CoreException {
		final ArrayList<URI> urisToMigrate = new ArrayList<URI>();
		if (getViewModelMigrator() == null) {
			return urisToMigrate;
		}
		final List<URI> viewModelURIs = getViewModelURIsInWorkspace();
		for (final URI viewModel : viewModelURIs) {
			if (!viewModelMigrator.checkMigration(viewModel)) {
				urisToMigrate.add(viewModel);
			}
		}
		return urisToMigrate;
	}

	private static ViewModelMigrator getViewModelMigrator() {
		if (viewModelMigrator == null) {
			viewModelMigrator = ViewModelMigratorUtil.getViewModelMigrator();
		}
		return viewModelMigrator;
	}

	private static List<URI> getViewModelURIsInWorkspace() throws CoreException {
		return WorkspaceUtil.getURIsInWorkspace("view"); //$NON-NLS-1$
	}
}
