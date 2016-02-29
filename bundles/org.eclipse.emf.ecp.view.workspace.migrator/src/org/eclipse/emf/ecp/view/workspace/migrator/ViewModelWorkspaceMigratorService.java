/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.workspace.migrator;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
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
		final ArrayList<URI> uris = new ArrayList<URI>();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.getRoot().accept(new IResourceVisitor() {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource.getFileExtension() != null && resource.getFileExtension().equals("view")) { //$NON-NLS-1$
					try {
						uris.add(URI.createURI(resource.getLocationURI().toURL().toExternalForm()));
					} catch (final MalformedURLException ex) {
						return false;
					}
				}
				if (resource.getType() == IResource.FILE) {
					return false;
				}
				return true;
			}
		});
		return uris;
	}
}
