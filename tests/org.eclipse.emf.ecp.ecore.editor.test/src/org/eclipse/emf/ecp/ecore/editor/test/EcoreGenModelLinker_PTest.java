/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ecore.editor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecp.ecore.editor.IEcoreGenModelLinker;
import org.eclipse.emf.ecp.ecore.editor.factory.EcoreGenModelLinkerFactory;
import org.eclipse.emf.ecp.ecore.editor.util.EcoreGenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EcoreGenModelLinker_PTest {

	// JUnit rule needs to be public.
	// REUSED CLASS
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	// END REUSED CLASS

	@Test
	public void test() throws EcoreGenException, IOException {
		final String absPath = folder.newFolder("tmp").getAbsolutePath();
		final IEcoreGenModelLinker linker = EcoreGenModelLinkerFactory.getEcoreGenModelLinker();
		final String ecorePath = new File(".").getAbsolutePath() + "/resources/model/library.ecore";
		final String genModelPath = absPath + "/library.genmodel";
		final String modelProjectPath = absPath + "/result";
		linker.generateGenModel(ecorePath, genModelPath, modelProjectPath);
		final File resultFolder = new File(modelProjectPath);
		final File genModelFile = new File(genModelPath);
		final File projectFile = new File(modelProjectPath + "/.project");
		final File sourceFolder = new File(modelProjectPath + "/src");
		assertTrue(resultFolder.exists());
		assertTrue(genModelFile.exists());
		assertTrue(projectFile.exists());
		assertTrue(sourceFolder.exists());
	}

}
