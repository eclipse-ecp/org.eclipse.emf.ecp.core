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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.After;
import org.junit.Test;

/**
 * ECPProvider Tests
 *
 * @author jfaltermeier
 *
 */
public class ECPProvider_PTest extends AbstractTest {

	private ECPRepository repository;

	@After
	public void tearDown() {
		if (repository != null) {
			repository.delete();
		}
	}

	@Test
	public void getLabelTest() {
		// TODO any programmatic way to get label-string of EMFStore provider?
		assertEquals("EMFStore", getProvider().getLabel());
	}

	// @Test
	// public void canAddRepositoriesTest() {
	// boolean canAddRepositories = getProvider().hasCreateRepositorySupport();
	//
	// int countReposBefore = getProvider().getRepositories().size();
	// repository = ECPUtil.getECPRepositoryManager().addRepository(getProvider(), "repository4Name",
	// "repository4Label",
	// "description", getNewProperties());
	// int countReposAfterAdd = getProvider().getRepositories().size();
	//
	// if (countReposAfterAdd - countReposBefore == 1) {
	// assertTrue(canAddRepositories);
	// } else if (countReposAfterAdd - countReposBefore == 0) {
	// assertFalse(canAddRepositories);
	// } else {
	// fail("More than one repository was added or deleted.");
	// }
	//
	// }

	@Test
	public void ifUnsharedProjectSupportTest() {
		final boolean hasUnsharedProjectSupport = getProvider().hasCreateProjectWithoutRepositorySupport();
		if (!hasUnsharedProjectSupport) {
			return;
		}
		// try to create an offline project;
		try {
			final ECPProject project = getProjectManager().createProject(getProvider(), "test");
			assertNotNull(project);
			assertTrue(getProjectManager().getProjects().contains(project));
		} catch (final ECPProjectWithNameExistsException e) {
			fail("Project with name already existing. Fix test setup.");
		}

	}

	@Test(expected = RuntimeException.class)
	public void ifNotUnsharedProjectSupportTest() {
		final boolean hasUnsharedProjectSupport = getProvider().hasCreateProjectWithoutRepositorySupport();
		if (hasUnsharedProjectSupport) {
			throw new RuntimeException();
		}
		try {
			// try to create an offline project;
			getProjectManager().createProject(getProvider(), "test");

		} catch (final ECPProjectWithNameExistsException e) {
			fail("Project with name already existing. Fix test setup.");
		}

	}

	@Test
	public void getUIProviderTest() {
		getProvider();
		// TODO add correct test
		// AdapterProvider uiProvider=provider.getUIProvider();
		// assertNotNull(uiProvider);
	}

	@Test
	public void setUIProviderTest() {
		// TODO add test
		// fail("Not yet implemented");
	}

	@Test
	public void isSlowTest() {
		final InternalProvider provider = (InternalProvider) getProvider();
		provider.isSlow(null);
		// TODO add correct assert
		// fail("what should be passed?");
	}

	@Test
	public void createEditingDomainTest() {
		final InternalProvider provider = (InternalProvider) getProvider();
		InternalProject project = null;
		try {
			project = (InternalProject) getProjectManager().createProject(
				getProvider(), "test");
		} catch (final ECPProjectWithNameExistsException e) {
			fail(e.getMessage());
		}
		final EditingDomain editingDomain = provider.createEditingDomain(project);
		assertNotNull(editingDomain);
	}

	@Test
	public void getOpenProjectsTest() {
		final InternalProvider provider = (InternalProvider) getProvider();
		assertEquals(0, provider.getOpenProjects().size());
		InternalProject project = null;
		try {
			project = (InternalProject) getProjectManager().createProject(
				getProvider(), "test");
		} catch (final ECPProjectWithNameExistsException e) {
			fail(e.getMessage());
		}
		assertEquals(1, provider.getOpenProjects().size());
		project.close();
		assertEquals(0, provider.getOpenProjects().size());
		project.open();
		assertEquals(1, provider.getOpenProjects().size());
	}
}