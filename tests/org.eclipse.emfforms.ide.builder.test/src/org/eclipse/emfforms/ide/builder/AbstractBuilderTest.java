/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.Before;

/**
 * Abstract framework for builder tests.
 */
public abstract class AbstractBuilderTest {

	@Before
	public void deleteProjects() throws CoreException {
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (final IProject project : root.getProjects()) {
			project.delete(true, monitor);
		}
	}

	protected static void setAutoBuild(boolean autoBuild) throws CoreException {
		final IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		description.setAutoBuilding(autoBuild);
		ResourcesPlugin.getWorkspace().setDescription(description);
	}

	protected IMarker[] findMarkersOnResource(IResource resource) {
		IMarker[] problems = null;
		final int depth = IResource.DEPTH_INFINITE;
		try {
			problems = resource.findMarkers(IMarker.PROBLEM, true, depth);
		} catch (final CoreException e) {
			// something went wrong
		}
		return problems;
	}

	protected static IProject createProject(String name, IProgressMonitor monitor) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject project = root.getProject(name);
		project.create(monitor);
		project.open(monitor);
		return project;
	}

	protected static IFile importFileIntoProject(IProject project, File file, IProgressMonitor monitor)
		throws CoreException, IOException {
		final IFile targetResource = project.getFile(new Path(file.getName()));
		final InputStream contentStream = new FileInputStream(file);
		targetResource.create(contentStream, false, monitor);
		contentStream.close();
		return targetResource;

	}

	protected static IProject createAndPopulateProject(String projectName, IProgressMonitor monitor)
		throws CoreException, IOException {
		final IProject project = createProject(projectName, monitor);
		// copy content of the resources equivalent folder
		final String folderName = String.format("/resources/%s/", projectName);//$NON-NLS-1$
		final String resourceFolderPath = new File(".").getAbsolutePath() + folderName;//$NON-NLS-1$
		final File resourceFolder = new File(resourceFolderPath);
		for (final File file : resourceFolder.listFiles()) {
			importFileIntoProject(project, file, monitor);
		}
		return project;
	}

	protected static void waitForAuroBuild() {
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (InterruptedException | OperationCanceledException e) {
			e.printStackTrace();
			fail("Test interrupted waiting for workspace build: " + e.getMessage()); //$NON-NLS-1$
		}
	}
}
