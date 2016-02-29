/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.exporter;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.emf2web.controller.GenerationInfo;
import org.eclipse.emf.ecp.emf2web.internal.messages.Messages;

/**
 * Exporter for saving the generated content to file.
 *
 * @author Stefan Dirix
 *
 */
public class FileGenerationExporter implements GenerationExporter {

	private UserInteraction userInteraction;

	private boolean doCancel;

	@Override
	public void export(Collection<? extends GenerationInfo> generationInfos, UserInteraction userInteraction)
		throws IOException {
		this.userInteraction = userInteraction;
		doCancel = false;
		for (final GenerationInfo generationInfo : generationInfos) {
			if (!doCancel) {
				export(generationInfo);
			}
		}
	}

	/**
	 * Export the given {@link GenerationInfo}.
	 *
	 * @param generationInfo
	 *            The {@link GenerationInfo} to export.
	 * @throws IOException
	 *             If something went wrong during export.
	 */
	protected void export(GenerationInfo generationInfo) throws IOException {
		final String exportString = wrapGeneration(generationInfo);
		final URI location = generationInfo.getLocation();
		export(exportString, location);
	}

	/**
	 * Wraps the generated content if needed.
	 *
	 * @param generationInfo
	 *            The {@link GenerationInfo} containing the information.
	 * @return
	 * 		The (maybe wrapped) generated content.
	 */
	protected String wrapGeneration(GenerationInfo generationInfo) {
		if (generationInfo.getWrapper() == null || !generationInfo.isWrap()) {
			return generationInfo.getGeneratedString();
		}
		return generationInfo.getWrapper().wrap(generationInfo.getGeneratedString(), generationInfo.getType());
	}

	/**
	 * Exports the given string to the given {@code location}.
	 *
	 * @param exportString
	 *            The string to export.
	 * @param location
	 *            The {@link URI} depicting the location to export to.
	 * @throws IOException
	 *             If something went wrong during export.
	 */
	protected void export(String exportString, URI location) throws IOException {
		if (location.isPlatform()) {
			try {
				handlePlatform(exportString, location);
			} catch (final CoreException ex) {
				throw new IOException(ex);
			}
		} else if (location.isFile()) {
			handleFileSystem(exportString, location);
		} else {
			throw new IOException(MessageFormat.format(Messages.FileGenerationExporter_URI_Error, location.toString()));
		}
	}

	private void handlePlatform(String exportString, URI location) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IFile file = root.getFile(new Path(location.toPlatformString(true)));

		if (file.exists() && !askOverwriteAllowed(file.getLocationURI().toString())) {
			return;
		}

		if (file.exists()) {
			file.setContents(new ByteArrayInputStream(exportString.getBytes()), IResource.KEEP_HISTORY, null);
		} else {
			prepareStructure(file.getParent());
			file.create(new ByteArrayInputStream(exportString.getBytes()), IResource.NONE, null);
		}
	}

	private void prepareStructure(IContainer container) throws CoreException {
		final IContainer parent = container.getParent();
		if (parent instanceof IFolder) {
			prepareStructure(parent);
		}
		if (IFolder.class.isInstance(container) && !container.exists()) {
			final IFolder folder = IFolder.class.cast(container);
			folder.create(false, true, null);
		}
	}

	private void handleFileSystem(String exportString, URI location) throws IOException {
		final File file = new File(location.toFileString());
		if (file.exists() && !askOverwriteAllowed(file.getAbsolutePath())) {
			return;
		}
		writeToFileSystemFile(exportString, file);
	}

	private void writeToFileSystemFile(String exportString, File file) throws IOException {
		final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8")); //$NON-NLS-1$
		try {
			writer.write(exportString);
		} finally {
			writer.close();
		}
	}

	/**
	 * Ask the user if a file shall be overwritten.
	 *
	 * @param fileName
	 *            The name of the file displayed to the user.
	 * @return
	 *         {@code true} if the file shall be overwritten, {@code false} otherwise.
	 */
	protected boolean askOverwriteAllowed(String fileName) {
		final String title = Messages.FileGenerationExporter_OverwriteWarning;
		final String question = MessageFormat.format(Messages.FileGenerationExporter_OverwriteWarningMessage,
			fileName);
		final String toggleQuestion = Messages.FileGenerationExporter_OverwriteWarningToggle;
		final int result = userInteraction.askQuestion(title, question, toggleQuestion);
		if (result == UserInteraction.CANCEL) {
			doCancel = true;
			return false;
		} else if (result == UserInteraction.NO) {
			return false;
		} else if (result == UserInteraction.YES) {
			return true;
		}

		// should not occur
		return false;
	}
}
