/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.spi.util;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

/**
 * Utility class containing common functionality for services using the workspace.
 *
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public final class WorkspaceUtil {

	// Utility class should not be instantiated by clients.
	private WorkspaceUtil() {
	}

	/**
	 * Get the {@link URI URIs} of all files in the workspace that have the given file extension.
	 *
	 * @param fileExtension The file extension of the files to search for in the workspace.
	 * @return The list of {@link URI URIs}
	 * @throws CoreException If something goes wrong while analyzing the workspace
	 */
	public static List<URI> getURIsInWorkspace(final String fileExtension) throws CoreException {
		final ArrayList<URI> uris = new ArrayList<URI>();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.getRoot().accept(new IResourceVisitor() {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource.getFileExtension() != null && resource.getFileExtension().equals(fileExtension)) {
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

	/**
	 * Converts an EMF {@link URI} to a Java {@link File}.
	 *
	 * @param uri The {@link URI} to convert
	 * @return The Java {@link File}
	 */
	public static File uriToFile(URI uri) {
		if (uri.isFile() && !uri.isRelative()) {
			return new Path(uri.toFileString()).toFile();
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.path()))
			.getLocation().toFile();
	}
}
