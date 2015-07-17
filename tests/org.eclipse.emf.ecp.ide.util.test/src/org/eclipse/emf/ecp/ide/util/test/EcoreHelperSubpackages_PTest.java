/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
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
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class EcoreHelperSubpackages_PTest {
	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static final String ECORE_1_PATH = "/TestEcoreHelperProjectResources/testSubpackages1.ecore";
	private static final String ECORE_2_PATH = "/TestEcoreHelperProjectResources/testSubpackages2.ecore";
	private static final String ROOT_1_NS_URI = "http://testSubpackages1_root";
	private static final String ROOT_2_NS_URI = "http://testSubpackages2_root";
	private static final String SUBPACKAGE1_1_NS_URI = "http://testSubpackages1_subpackage1";
	private static final String SUBPACKAGE1_2_NS_URI = "http://testSubpackages1_subpackage2";
	private static final String SUBPACKAGE1_3_NS_URI = "http://testSubpackages1_subpackage3";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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

	@Test
	public void testRegisteringRootPackage() throws IOException {
		// check initial state
		assertFalse("Root Package is already in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Subpackage 1 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertFalse("Subpackage 2 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertFalse("Subpackage 3 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.registerEcore(ECORE_1_PATH);

		assertTrue("Root Package is not in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertTrue("Subpackage 1 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertTrue("Subpackage 2 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertTrue("Subpackage 3 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

	}

	@Test
	public void testRegisteringUnregisteringViaReference() throws IOException {
		// check initial state
		assertFalse("Root Package is already in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Root Package is already in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertFalse("Subpackage 1 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertFalse("Subpackage 2 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertFalse("Subpackage 3 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.registerEcore(ECORE_2_PATH);

		assertTrue("Root Package is not in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertTrue("Root Package is not in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertTrue("Subpackage 1 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertTrue("Subpackage 2 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertTrue("Subpackage 3 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.registerEcore(ECORE_1_PATH);

		EcoreHelper.unregisterEcore(ECORE_2_PATH);

		assertTrue("Root Package 1 is not in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Root Package 2 is still in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertTrue("Subpackage 1 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertTrue("Subpackage 2 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertTrue("Subpackage 3 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.unregisterEcore(ECORE_1_PATH);

		assertFalse("Root Package 1 is still in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Root Package 2 is still in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertFalse("Subpackage 1 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertFalse("Subpackage 2 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertFalse("Subpackage 3 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));
	}

	@Test
	public void testRegisteringUnregisteringViaReference_DifferentOrder() throws IOException {
		// check initial state
		assertFalse("Root Package is already in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Root Package is already in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertFalse("Subpackage 1 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertFalse("Subpackage 2 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertFalse("Subpackage 3 is already in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.registerEcore(ECORE_2_PATH);

		assertTrue("Root Package is not in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertTrue("Root Package is not in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertTrue("Subpackage 1 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertTrue("Subpackage 2 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertTrue("Subpackage 3 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.registerEcore(ECORE_1_PATH);

		EcoreHelper.unregisterEcore(ECORE_1_PATH);

		assertTrue("Root Package 1 is not in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertTrue("Root Package 2 is not in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertTrue("Subpackage 1 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertTrue("Subpackage 2 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertTrue("Subpackage 3 is not in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));

		EcoreHelper.unregisterEcore(ECORE_2_PATH);

		assertFalse("Root Package 1 is still in the registry!",
			packageRegistry.containsKey(ROOT_1_NS_URI));
		assertFalse("Root Package 2 is still in the registry!",
			packageRegistry.containsKey(ROOT_2_NS_URI));
		assertFalse("Subpackage 1 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_1_NS_URI));
		assertFalse("Subpackage 2 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_2_NS_URI));
		assertFalse("Subpackage 3 is still in the registry!",
			packageRegistry.containsKey(SUBPACKAGE1_3_NS_URI));
	}

	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(ECORE_1_PATH);
		EcoreHelper.unregisterEcore(ECORE_2_PATH);
	}

}
