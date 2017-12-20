/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.internal.migration;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SubMonitor;

/**
 * Utility class for handling view model files.
 *
 */
public final class Resources {

	private static final String VIEW_EXT = "view"; //$NON-NLS-1$

	private Resources() {
		// private ctor
	}

	/**
	 * Find all view model files within the workspace.
	 *
	 * @param monitor a {@link SubMonitor} that allow to report progress
	 *
	 * @return a set of view model files
	 */
	public static Set<IFile> findAllViewFilesInWorkspace(SubMonitor monitor) {
		final Set<IFile> files = new LinkedHashSet<IFile>();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, workspace.getRoot().getProjects().length);
		try {
			workspace.getRoot().accept(new IResourceVisitor() {
				@Override
				public boolean visit(IResource resource) throws CoreException {
					if (resource.getFileExtension() != null && resource.getFileExtension().equals(VIEW_EXT)
						&& resource instanceof IFile) { // $NON-NLS-1$
						files.add((IFile) resource);

					}
					if (resource instanceof IProject) {
						subMonitor.worked(1);
					}
					if (resource.getType() == IResource.FILE) {
						return false;
					}
					return true;
				}
			});
		} catch (final CoreException ex) {
			Activator.log(ex);
		}
		return files;
	}

}
