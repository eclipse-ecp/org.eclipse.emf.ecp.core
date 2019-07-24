/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Constants;

/**
 * Util class used during the creation and deletion of view models to update other project files accordingly.
 *
 * @author Eugen Neufeld
 *
 */
public final class ContributionUtil {

	private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF"; //$NON-NLS-1$

	private ContributionUtil() {
	}

	/**
	 * Checks whether the project is a plugin project.
	 *
	 * @param project the project to checks
	 * @return true if the project has the plugin nature
	 */
	public static boolean isPluginProject(IProject project) {
		try {
			return project.hasNature("org.eclipse.pde.PluginNature"); //$NON-NLS-1$
		} catch (final CoreException ex) {
			// not interested in handling the exception
			return false;
		}
	}

	/**
	 * Checks whether the project is a fragment project.
	 *
	 * @param project the project to checks
	 * @return true if the Manifest has a fragment host
	 */
	public static boolean isFragmentProject(IProject project) {
		try {
			final IResource manifest = project.findMember(MANIFEST_PATH);
			if (manifest == null || !IFile.class.isInstance(manifest)) {
				/* no osgi project at all */
				return false;
			}
			final InputStream inputStream = IFile.class.cast(manifest).getContents(true);
			final Scanner scanner = new Scanner(inputStream, "UTF-8"); //$NON-NLS-1$
			/* read file as one string */
			final String content = scanner.useDelimiter("\\A").next(); //$NON-NLS-1$
			scanner.close();
			/* Every fragment has a fragment host header in the manifest */
			final int index = content.indexOf(Constants.FRAGMENT_HOST);
			return index != -1;
		} catch (final CoreException ex) {
			return false;
		}
	}

	/**
	 * Util method which allows to parse an IFile.
	 *
	 * @param file the IFile to parse
	 * @return The content of the IFile
	 * @throws UnsupportedEncodingException
	 * @throws IOException Thrown if file is not readable
	 * @throws CoreException Thrown if eclipse cannot read the file
	 */
	public static Optional<String> parseIFile(IFile file) throws IOException, CoreException {
		if (!file.exists()) {
			return Optional.empty();
		}
		try (BufferedReader br = new BufferedReader(
			new InputStreamReader(file.getContents(true), file.getCharset()))) {
			return Optional.ofNullable(br.lines().collect(Collectors.joining(System.lineSeparator())));
		}
	}
}
