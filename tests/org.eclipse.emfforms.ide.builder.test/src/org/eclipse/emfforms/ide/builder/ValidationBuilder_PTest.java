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
 * Christian W. Damus - bugs 544499, 545418
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Named;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.ide.internal.builder.ProjectNature;
import org.eclipse.emfforms.ide.internal.builder.ValidationBuilder;
import org.eclipse.emfforms.ide.internal.builder.ValidationNature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * Test for the {@link ValidationBuilder} class.
 */
public class ValidationBuilder_PTest extends AbstractBuilderTest {

	private final List<ServiceRegistration<?>> registrations = new ArrayList<>();

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
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// valid Files => No marker
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);
	}

	@Test
	public void notAModelValidationProject() throws CoreException, IOException {
		final String projectName = "NotAViewModel"; //$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// no view files (wrong XMI, not XML file, etc.) => Mark them with an error.
		Assert.assertEquals(2, markers.length);
	}

	/**
	 * Test that problem markers are correctly created for resources that have validation errors.
	 */
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
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
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

	/**
	 * Test that problem markers are correctly <em>not</em> created for resources
	 * even that have validation errors, when auto-build is not enabled.
	 */
	@Test
	public void noAutoBuild() throws CoreException, IOException {
		final String projectName = "ValidationErrors"; //$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is off,
		// which means that it won't do validation
		setAutoBuild(false);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);
		// valid Files => No marker
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);
	}

	@Test
	public void delegateProviders() throws CoreException, IOException {
		registerDataTemplateDelegate();

		final String projectName = "DataTemplateErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);

		// 4 errors:
		// 2 unresolved DMR and one missing DMR as ECP pure validation errros
		// an annotation with a missing key as a simple EMF error
		for (final IMarker marker : markers) {
			System.err.println(marker);
		}
		Assert.assertThat(markers.length, greaterThanOrEqualTo(1));
	}

	@Test
	public void markerHelperProviders() throws CoreException, IOException {
		registerMarkerHelper();

		final String projectName = "ValidationErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);
		IMarker[] markers = findMarkersOnResource(project);
		// No build yet => no markers
		Assert.assertArrayEquals(Collections.<IMarker> emptyList().toArray(), markers);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		// final state
		markers = findMarkersOnResource(project);

		// 4 errors:
		// 2 unresolved DMR and one missing DMR as ECP pure validation errros
		// an annotation with a missing key as a simple EMF error
		for (final IMarker marker : markers) {
			System.err.println(marker);

			// These look like they were created by our marker helper
			Assert.assertThat(marker.getAttribute(IMarker.MESSAGE, ""), startsWith("*TEST* "));
		}
		Assert.assertThat(markers.length, greaterThanOrEqualTo(1));
	}

	@Test
	public void injections() throws CoreException, IOException {
		class Canary implements ValidationDelegateProvider {
			// BEGIN COMPLEX CODE - This is just for testing
			String fileName;
			IContentType contentType;
			String contentTypeID;
			// END COMPLEX CODE

			@Bid
			public Double bid(IFile file, IContentType contentType,
				@Named(BuilderConstants.CONTENT_TYPE) String contentTypeID) {
				fileName = file.getName();
				this.contentType = contentType;
				this.contentTypeID = contentTypeID;
				return null;
			}

			void dump() {
				System.err.printf("File name: %s%n", fileName);
				System.err.printf("Content-type ID: %s%n", contentTypeID);
				System.err.printf("Content-type: %s%n", contentType);
			}
		}

		final Canary canary = new Canary();
		register(ValidationDelegateProvider.class, canary);

		final String projectName = "DataTemplateErrors";//$NON-NLS-1$
		final IProgressMonitor monitor = new NullProgressMonitor();
		final IProject project = createAndPopulateProject(projectName, monitor);

		// trigger builder by adding nature to the project and auto-build is on
		setAutoBuild(true);
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
		waitForAutoBuild();

		canary.dump();
		Assert.assertThat("No file", canary.fileName, notNullValue());
		Assert.assertThat("No content-type ID", canary.contentTypeID, notNullValue());
		Assert.assertThat("No content-type", canary.contentType, notNullValue());
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
		ProjectNature.toggleNature(project, ValidationNature.NATURE_ID);
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

	//
	// Test framework
	//

	@After
	public void deregisterServices() {
		for (final ServiceRegistration<?> next : registrations) {
			next.unregister();
		}
	}

	BundleContext getBundleContext() {
		return FrameworkUtil.getBundle(getClass()).getBundleContext();
	}

	<S> ServiceRegistration<S> register(Class<S> serviceType, S service) {
		final ServiceRegistration<S> result = getBundleContext().registerService(
			serviceType, service, new Hashtable<>());
		registrations.add(result);
		return result;
	}

	ServiceRegistration<ValidationDelegateProvider> registerDataTemplateDelegate() {
		final ValidationDelegateProvider provider = new ValidationDelegateProvider() {
			private final Double bid = Double.MAX_VALUE;

			@Bid
			public Double bid(IFile file) {
				return "datatemplate".equals(file.getFullPath().getFileExtension())
					? bid
					: null;
			}

			@Create
			public ValidationDelegate createDelegate() {
				return new ValidationServiceDelegate();
			}
		};

		return register(ValidationDelegateProvider.class, provider);
	}

	ServiceRegistration<MarkerHelperProvider> registerMarkerHelper() {
		final IFile[] fileHolder = { null };
		final MarkerHelper markerHelper = new DefaultMarkerHelper() {
			@Override
			protected void adjustMarker(IMarker marker, Diagnostic diagnostic, Diagnostic parentDiagnostic)
				throws CoreException {

				super.adjustMarker(marker, diagnostic, parentDiagnostic);

				String message = marker.getAttribute(IMarker.MESSAGE, "");
				message = "*TEST* " + message;
				marker.setAttribute(IMarker.MESSAGE, message);
			}

			@Override
			protected IFile getFile(Diagnostic diagnostic) {
				return fileHolder[0];
			}
		};

		final MarkerHelperProvider provider = new MarkerHelperProvider() {
			private final Double bid = Double.MAX_VALUE;

			@Bid
			public Double bid(IFile file) {
				return "view".equals(file.getFullPath().getFileExtension())
					? bid
					: null;
			}

			@Create
			public MarkerHelper createDelegate(IFile file) {
				fileHolder[0] = file;
				return markerHelper;
			}
		};

		return register(MarkerHelperProvider.class, provider);
	}

}
