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
package org.eclipse.emf.ecp.spi.view.migrator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for the {@link ViewNsMigrationUtil}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewNsMigrationUtil_Test {

	private File outputFile;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		outputFile = new File("testdata/tmp.template");
		if (outputFile.exists()) {
			outputFile.delete();
		}
		EPackage.Registry.INSTANCE.clear();
	}

	@Test
	public void testMigrateViewEcoreNsUri() throws IOException {
		final Registry instance = EPackage.Registry.INSTANCE;
		instance.put("http://org/eclipse/emf/ecp/view/model/1170", mock(EPackage.class));

		final File modelFile = new File("testdata/migrate_140.template");
		final File expectedResultFile = new File("testdata/migrate_expected.template");
		ViewNsMigrationUtil.migrateViewEcoreNsUri(modelFile, outputFile);

		final String output = readAllLines(outputFile);
		final String expected = readAllLines(expectedResultFile);

		assertEquals("The migrated file does not match the expected output.", output.trim(), expected.trim());
	}

	@Test
	public void testMigrateViewEcoreNsUri_noMigrationNecessary() throws IOException {
		final Registry instance = EPackage.Registry.INSTANCE;
		instance.put("http://org/eclipse/emf/ecp/view/model/1170", mock(EPackage.class));

		final File modelFile = new File("testdata/migrate_current.template");
		ViewNsMigrationUtil.migrateViewEcoreNsUri(modelFile, outputFile);

		assertFalse("If no migration is necessary, nothing should be written to the output file.",
			outputFile.exists());
	}

	@Test
	public void testMigrateViewEcoreNsUri_noRegisteredViewEcore() throws IOException {
		final File modelFile = new File("testdata/migrate_140.template");
		ViewNsMigrationUtil.migrateViewEcoreNsUri(modelFile, outputFile);

		assertFalse(
			"If no view ecore is necessary, no migration should be performed and, thus, nothing should be written to the output file.",
			outputFile.exists());
	}

	@Test
	public void checkMigration_migrationNecessary() throws IOException {
		final Registry instance = EPackage.Registry.INSTANCE;
		instance.put("http://org/eclipse/emf/ecp/view/model/1170", mock(EPackage.class));

		final File modelFile = new File("testdata/migrate_140.template");
		final boolean checkMigration = ViewNsMigrationUtil.checkMigration(modelFile);
		assertFalse(checkMigration);
	}

	@Test
	public void checkMigration_noMigrationNecessary() throws IOException {
		final Registry instance = EPackage.Registry.INSTANCE;
		instance.put("http://org/eclipse/emf/ecp/view/model/1170", mock(EPackage.class));

		final File modelFile = new File("testdata/migrate_current.template");
		final boolean checkMigration = ViewNsMigrationUtil.checkMigration(modelFile);
		assertTrue(checkMigration);
	}

	@Test
	public void checkMigration_noRegisteredViewEcore() throws IOException {
		final File modelFile = new File("testdata/migrate_140.template");
		final boolean checkMigration = ViewNsMigrationUtil.checkMigration(modelFile);
		assertTrue(checkMigration);
	}

	@Test
	public void checkMigration_noModelViewEcore() throws IOException {
		final Registry instance = EPackage.Registry.INSTANCE;
		instance.put("http://org/eclipse/emf/ecp/view/model/1170", mock(EPackage.class));

		final File modelFile = new File("testdata/no_view_uri.template");
		final boolean checkMigration = ViewNsMigrationUtil.checkMigration(modelFile);
		assertTrue(checkMigration);
	}

	private String readAllLines(File file) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(file));
		final StringBuilder builder = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
		} finally {
			reader.close();
		}
		return builder.toString();
	}
}
