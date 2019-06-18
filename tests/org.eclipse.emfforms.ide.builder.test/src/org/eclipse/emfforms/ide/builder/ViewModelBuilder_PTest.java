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
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bugs 544499, 545418
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emfforms.ide.internal.builder.ProjectNature;
import org.eclipse.emfforms.ide.internal.builder.ValidationNature;
import org.eclipse.emfforms.ide.internal.builder.ViewModelBuilder;
import org.eclipse.emfforms.ide.internal.builder.ViewModelNature;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Class {@link ViewModelBuilder}
 */
public class ViewModelBuilder_PTest extends AbstractBuilderTest {

	@Test
	public void validProject() throws CoreException, IOException {
		// initial state
		final String projectName = "ValidModel"; //$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// valid Files => No marker
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);
	}

	@Test
	public void notAViewModelProject() throws CoreException, IOException {
		final String projectName = "NotAViewModel"; //$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// no view files (wrong XMI, not XML file, etc.) => Mark them with an error.
		Assert.assertEquals(2, markers.length);
	}

	@Test
	public void validationErrors() throws CoreException, IOException {
		final String projectName = "ValidationErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);

		// 4 errors:
		// 2 unresolved DMR and one missing DMR as ECP pure validation errros
		// an annotation with a missing key as a simple EMF error
		for (final IMarker marker : markers) {
			System.err.println(marker);
		}
		Assert.assertEquals(4, markers.length);
	}

	@Test
	public void noAutoBuild() throws CoreException, IOException {
		final String projectName = "ValidationErrors"; //$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(false);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// valid Files => No marker
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);
	}

	@Test
	public void noRedundancyWithValidationNature() throws CoreException, IOException {
		final String projectName = "ValidationErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding both natures to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);

		// Only 4 errors, not 8 which would happen if both builders did their work
		Assert.assertEquals(4, markers.length);
	}

	/**
	 * Test that markers are correctly cleared on incremental validation when
	 * problems are fixed.
	 */
	@Test
	public void validationProblemsFixed() throws CoreException, IOException {
		final String projectName = "ValidationErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		assertThat("Should not have error markers, yet", markers, is(new IMarker[0]));

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ViewModelNature.NATURE_ID);
		waitForAutoBuild();

		// Should have some problems, now
		markers = findMarkersOnResource(project);
		assertThat("No problems", markers.length, greaterThan(0));

		// Fix them
		final IFile problemFile = project.getFile("ValidationError.view");
		final IFile goodFile = project.getFile("goodModel.view");
		final InputStream input = goodFile.getContents();
		try {
			problemFile.setContents(input, true, true, null);
		} finally {
			input.close();
		}

		waitForAutoBuild();
		markers = findMarkersOnResource(project);
		// Problems solved
		assertThat("Should not have error markers", markers, is(new IMarker[0]));
	}

}
