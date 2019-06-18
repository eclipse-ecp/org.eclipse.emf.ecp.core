/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class EcoreHelperOneDependency_PTest {
	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static String bEcorePath = "/TestEcoreHelperProjectResources/B.ecore";
	private static String aEcorePath = "/TestEcoreHelperProjectResources/A.ecore";
	private static String xEcorePath = "/TestEcoreHelperProjectResources/X.ecore";

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

	private static void installResourcesProject() throws Exception {
		final ProjectInstallerWizard wiz = new ProjectInstallerWizard();
		wiz.installExample(new NullProgressMonitor());
	}
	// END SUPRESS CATCH EXCEPTION

	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(bEcorePath);
		EcoreHelper.unregisterEcore(aEcorePath);
		EcoreHelper.unregisterEcore(xEcorePath);
	}

	@Test
	public void testBulkRegisterUnregister() throws IOException {

		// check initial state
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// register B references A
		EcoreHelper.registerEcore(bEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// unregister B references A
		EcoreHelper.unregisterEcore(bEcorePath);
		assertFalse("Package A is still in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is still in the registry!",
			packageRegistry.containsKey("b.nsuri"));
	}

	@Test
	public void testSeparateRegisterAndUnregister1() throws IOException {
		// check initial state
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// first package in the registry
		EcoreHelper.registerEcore(aEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is still in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// second package in the registry (B references A)
		EcoreHelper.registerEcore(bEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// unregister B references A
		EcoreHelper.unregisterEcore(bEcorePath);
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));

		// unregister A
		EcoreHelper.unregisterEcore(aEcorePath);
		assertFalse("Package A is still in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is still in the registry!",
			packageRegistry.containsKey("b.nsuri"));

	}

	@Test
	public void testSeparateRegisterAndUnregister2() throws IOException {
		// check initial state
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// first package in the registry
		EcoreHelper.registerEcore(aEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// second package in the registry (B references A)
		EcoreHelper.registerEcore(bEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// unregister A
		EcoreHelper.unregisterEcore(aEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));

		// unregister B references A
		EcoreHelper.unregisterEcore(bEcorePath);
		assertFalse("Package B is still in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package A is still in the registry!",
			packageRegistry.containsKey("a.nsuri"));

	}

	@Test
	public void testEcoreReferencesRegisteredPackage() throws IOException {
		// check initial state
		assertFalse("Package X is already in the registry!",
			packageRegistry.containsKey("x.nsuri"));
		assertTrue("Package http://www.eclipse.org/emf/2002/Ecore is not in the registry!",
			packageRegistry.containsKey("http://www.eclipse.org/emf/2002/Ecore"));

		// register X references Ecore
		EcoreHelper.registerEcore(xEcorePath);
		assertTrue("Package X not in the registry!",
			packageRegistry.containsKey("x.nsuri"));
		assertTrue("Package http://www.eclipse.org/emf/2002/Ecore is not in the registry!",
			packageRegistry.containsKey("http://www.eclipse.org/emf/2002/Ecore"));

		// unregister X references Ecore
		EcoreHelper.unregisterEcore(xEcorePath);
		assertFalse("Package X is still in the registry!",
			packageRegistry.containsKey("x.nsuri"));
		assertTrue("Package http://www.eclipse.org/emf/2002/Ecore is not in the registry!",
			packageRegistry.containsKey("http://www.eclipse.org/emf/2002/Ecore"));

	}
}
