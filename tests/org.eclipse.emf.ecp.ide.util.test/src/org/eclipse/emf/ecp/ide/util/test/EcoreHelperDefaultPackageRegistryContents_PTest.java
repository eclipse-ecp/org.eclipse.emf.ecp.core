/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class EcoreHelperDefaultPackageRegistryContents_PTest {

	private static final String A_ECORE_PATH = "/TestEcoreHelperProjectResources/A.ecore";
	private static final String A_NS_URI = "a.nsuri";
	private static final String VIEW_NS_URI = "http://org/eclipse/emf/ecp/view/model/1180";
	private static final String VIEW_ECORE_PATH = "/TestEcoreHelperProjectResources/view.ecore";

	// BEGIN SUPRESS CATCH EXCEPTION
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			JFaceResources.getImageRegistry();
		} catch (final RuntimeException e) {
			// expected fail, some strange initialization error is happing
		}
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject project = root.getProject("TestEcoreHelperProjectResources");
		// create resources to register and unregister
		if (!project.exists()) {
			installResourcesProject();
		}
	}

	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(A_ECORE_PATH);
		EcoreHelper.unregisterEcore(VIEW_ECORE_PATH);
	}

	private static void installResourcesProject() throws Exception {
		final ProjectInstallerWizard wiz = new ProjectInstallerWizard();
		wiz.installExample(new NullProgressMonitor());
	}
	// END SUPRESS CATCH EXCEPTION

	@Test
	public void testGetDefaultPackageRegistryContentsWithIDEEcore() {
		final Object[] defaultPackageRegistryContents = EcoreHelper.getDefaultPackageRegistryContents();
		boolean found = false;
		for (final Object nsURI : defaultPackageRegistryContents) {
			if (VIEW_NS_URI.equals(nsURI)) {
				found = true;
				break;
			}
		}
		assertTrue("The view model package was not returned.",
			found);
	}

	@Test
	public void testGetDefaultPackageRegistryContentsWithRegisteredIDEEcore() throws IOException {
		EcoreHelper.registerEcore(VIEW_ECORE_PATH);
		final Object[] defaultPackageRegistryContents = EcoreHelper.getDefaultPackageRegistryContents();
		boolean found = false;
		for (final Object nsURI : defaultPackageRegistryContents) {
			if (VIEW_NS_URI.equals(nsURI)) {
				found = true;
				break;
			}
		}
		assertTrue("The view model package was not returned.",
			found);
	}

	@Test
	public void testGetDefaultPackageRegistryContentsWithRegisteredWorkspaceEcore() throws IOException {
		EcoreHelper.registerEcore(A_ECORE_PATH);
		final Object[] defaultPackageRegistryContents = EcoreHelper.getDefaultPackageRegistryContents();
		boolean found = false;
		for (final Object nsURI : defaultPackageRegistryContents) {
			if (A_NS_URI.equals(nsURI)) {
				found = true;
				break;
			}
		}
		assertFalse("Workspace only Ecore was returned.", found);
	}
}
