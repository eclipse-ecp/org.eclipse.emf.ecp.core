/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.util.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that the {@link EcoreHelper#registerEcore(String)} method throws exceptions with informative messages when an
 * Ecore cannot be loaded from the provided path.
 *
 * @author Lucas Koehler
 *
 */
public class EcoreHelperLoadEcoreExceptions_PTest {

	private static String doesNotExistEcorePath = "/TestEcoreHelperProjectResources/DoesNotExist.ecore";
	private static String syntaxErrorEcorePath = "/TestEcoreHelperProjectResources/A_syntaxError.ecore";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		// BEGIN SUPRESS CATCH EXCEPTION
		try {
			JFaceResources.getImageRegistry();
		} catch (final RuntimeException e) {
			// expected fail, some strange initialization error is happing
		}
		// END SUPRESS CATCH EXCEPTION
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
	public void testEcoreDoesNotExistException() {
		try {
			EcoreHelper.registerEcore(doesNotExistEcorePath);
		} catch (final IOException ex) {
			final String message = ex.getMessage();
			assertTrue("Exception must state that the Ecore at the path does not exist.",
				message.contains(MessageFormat.format("The Ecore at the workspace location \"{0}\" does not exist.",
					doesNotExistEcorePath)));
			assertTrue("Exception must inform that the project should be added to the workspace",
				message.contains("import the corresponding project into your workspace"));
			return;
		}
		// An Exception should be thrown
		fail();
	}

	@Test
	public void testEcoreWithSyntaxErrorException() {
		try {
			EcoreHelper.registerEcore(syntaxErrorEcorePath);
		} catch (final IOException ex) {
			final String message = ex.getMessage();
			assertTrue("Exception must state that the Ecore at the path could not be loaded.",
				message.contains(MessageFormat.format("Ecore at workplace location \"{0}\" could not be loaded",
					syntaxErrorEcorePath)));
			return;
		}
		// An Exception should be thrown
		fail();
	}
}
