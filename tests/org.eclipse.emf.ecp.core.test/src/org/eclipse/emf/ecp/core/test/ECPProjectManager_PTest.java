/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectsChangedObserver;
import org.junit.Test;

public class ECPProjectManager_PTest extends AbstractTest {

	private final String projectName = "name";

	@Test
	public void createOfflineProjectTest() {
		try {
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName);
			assertTrue(project != null);
			assertEquals(project.getProvider(), getProvider());
			assertEquals(project.getName(), projectName);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test(expected = ECPProjectWithNameExistsException.class)
	public void createOfflineProjectWithExistingNameTest()
		throws ECPProjectWithNameExistsException {

		final ECPProject project = getProjectManager().createProject(getProvider(),
			projectName);
		assertTrue(project != null);
		assertEquals(project.getProvider(), getProvider());
		assertEquals(project.getName(), projectName);

		getProjectManager().createProject(getProvider(), projectName);
	}

	@Test
	public void createOfflineProjectWithPropertiesTest() {
		final ECPProperties properties = getNewProperties();
		try {
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName, properties);
			assertTrue(project != null);
			assertEquals(project.getName(), projectName);
			assertEquals(project.getProvider(), getProvider());
			assertEquals(project.getProperties(), properties);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test(expected = ECPProjectWithNameExistsException.class)
	public void createOfflineProjectWithPropertiesAndExistingNameOffline()
		throws ECPProjectWithNameExistsException {
		final ECPProperties properties = getNewProperties();
		final ECPProject project = getProjectManager().createProject(getProvider(),
			projectName, properties);
		assertTrue(project != null);
		assertEquals(project.getName(), projectName);
		assertEquals(project.getProvider(), getProvider());
		assertEquals(project.getProperties(), properties);

		getProjectManager().createProject(getProvider(), projectName);
	}

	@Test
	public void createSharedProject() {
		final ECPProperties properties = getNewProperties();
		try {
			final ECPProject project = getProjectManager().createProject(
				getRepository(), projectName, properties);
			assertTrue(project != null);
			assertEquals(project.getName(), projectName);
			assertEquals(project.getRepository(), getRepository());
			assertEquals(project.getProperties(), properties);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void createSharedProjectWithoutRepository() {
		final ECPProperties properties = getNewProperties();
		try {
			getProjectManager().createProject(
				(ECPRepository) null, projectName, properties);
			fail("Null Repository not allowed.");
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test(expected = ECPProjectWithNameExistsException.class)
	public void createSharedWithExistingNameProject()
		throws ECPProjectWithNameExistsException {
		final ECPProperties properties = getNewProperties();
		final ECPProject project = getProjectManager().createProject(getRepository(),
			projectName, properties);
		assertTrue(project != null);
		assertEquals(project.getName(), projectName);
		assertEquals(project.getRepository(), getRepository());
		assertEquals(project.getProperties(), properties);

		getProjectManager().createProject(getRepository(), projectName,
			properties);
	}

	@Test
	public void cloneProjectTest() {
		ECPProject project = null;
		try {
			project = getProjectManager().createProject(
				getProvider(), projectName);
		} catch (final ECPProjectWithNameExistsException e) {
			fail(e.getMessage());
		}
		getProjectManager().createProject(project, project.getName() + "Copy");
		// TODO add correct assert
		// assertTrue(project.equals(clonedProject));
	}

	@Test
	public void getProjectWithAdaptableNonECPProjectAwareTest() {

	}

	@Test
	public void getProjectWithAdaptableECPProjectAwareTest() {

	}

	@Test
	public void getProjectByNameTest() {
		try {
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName);
			final ECPProject project2 = getProjectManager().getProject(projectName);
			assertTrue(project == project2);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test
	public void getProjectByItselfTest() {
		try {
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName);
			final ECPProject project2 = getProjectManager().getProject(project);
			assertTrue(project == project2);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test
	public void getProjectByModelElementTest() {
		try {
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName);
			final EObject object = EcoreFactory.eINSTANCE.createEObject();
			project.getContents().add(object);

			final ECPProject project2 = getProjectManager().getProject(object);
			assertTrue(project == project2);
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test
	public void getProjectsTest() {
		try {
			assertEquals(0, getProjectManager().getProjects().size());
			final ECPProject project = getProjectManager().createProject(
				getProvider(), projectName);
			assertEquals(1, getProjectManager().getProjects().size());
			final ECPProject project2 = getProjectManager().createProject(
				getProvider(), projectName + "2");
			assertEquals(2, getProjectManager().getProjects().size());
			assertTrue(getProjectManager().getProjects().contains(project));
			assertTrue(getProjectManager().getProjects().contains(project2));
		} catch (final ECPProjectWithNameExistsException e) {
			fail();
		}
	}

	@Test
	public void getProjectByNameNonExistingTest() {
		final ECPProject project = getProjectManager().getProject(projectName);
		assertTrue(null == project);
	}

	private boolean projectChangeObserverNotified;
	private boolean projectOpenCloseObserverNotified;

	@Test
	public void createProjectWithObservers() {
		projectChangeObserverNotified = false;
		projectOpenCloseObserverNotified = false;
		ECPUtil.getECPObserverBus().register(new ECPProjectOpenClosedObserver() {

			@Override
			public void projectChanged(ECPProject project, boolean opened) {
				projectOpenCloseObserverNotified = true;

			}
		});
		ECPUtil.getECPObserverBus().register(new ECPProjectsChangedObserver() {

			@Override
			public void projectsChanged(Collection<ECPProject> oldProjects,
				Collection<ECPProject> newProjects) {
				projectChangeObserverNotified = true;

			}
		});

		try {
			ECPUtil.getECPProjectManager().createProject(getProvider(), "TestProject");
		} catch (final ECPProjectWithNameExistsException e) {
			fail("Project does already exist");
		}

		assertTrue(projectChangeObserverNotified);
		assertFalse("Open Close Observer should not be notified", projectOpenCloseObserverNotified);
	}

}
