/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class EcoreHelperRegistryDependencies_PTest {
	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static String gEcorePath = "/TestEcoreHelperProjectResources/G.ecore";
	private static String hEcorePath = "/TestEcoreHelperProjectResources/H.ecore";

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
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// make sure none of the packages exists in the registry
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(gEcorePath);
		EcoreHelper.unregisterEcore(hEcorePath);
	}

	/**
	 * Test method for {@link EcoreHelper#registerEcore(String)}
	 *
	 * @throws IOException
	 */
	@Test
	public void testRegisterPluginEcore() throws IOException {

		// check initial setup
		assertFalse("Package is already in the registry!",
			packageRegistry.containsKey("g.nsuri"));

		// register
		EcoreHelper.registerEcore(gEcorePath);
		assertTrue("Package not in the registry!",
			packageRegistry.containsKey("g.nsuri"));

		final EPackage gPackage = packageRegistry.getEPackage("g.nsuri");
		final EClass eClass = (EClass) gPackage.getEClassifiers().get(0);
		final EClass umlPlayer = eClass.getESuperTypes().get(0);
		assertFalse("Proxy unresolved!", umlPlayer.eIsProxy());

		// unregister
		EcoreHelper.unregisterEcore(gEcorePath);
		assertFalse("Package is still in the registry!",
			packageRegistry.containsKey("g.nsuri"));

	}

	/**
	 * Test method for {@link EcoreHelper#registerEcore(String)}
	 *
	 * @throws IOException
	 */
	@Test
	public void testRegisterResourceEcore() throws IOException {

		// check initial setup
		assertFalse("Package is already in the registry!",
			packageRegistry.containsKey("h.nsuri"));

		// register
		EcoreHelper.registerEcore(hEcorePath);
		assertTrue("Package not in the registry!",
			packageRegistry.containsKey("h.nsuri"));

		final EPackage hPackage = packageRegistry.getEPackage("h.nsuri");
		final EClass eClass = (EClass) hPackage.getEClassifiers().get(0);
		final EClass umlPlayer = eClass.getESuperTypes().get(0);
		assertFalse("Proxy unresolved!", umlPlayer.eIsProxy());

		// unregister
		EcoreHelper.unregisterEcore(hEcorePath);
		assertFalse("Package is still in the registry!",
			packageRegistry.containsKey("h.nsuri"));

	}

}
