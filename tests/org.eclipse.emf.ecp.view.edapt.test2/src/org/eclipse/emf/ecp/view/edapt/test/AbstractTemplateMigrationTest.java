/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.edapt.EdaptViewModelMigrator;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.osgi.framework.Bundle;

/**
 * Abstract basis test for template migration tests.
 *
 * @author Lucas Koehler
 *
 */
public abstract class AbstractTemplateMigrationTest {
	// private static final String ENCODING = StandardCharsets.US_ASCII;

	private static String BUNDLE_PATH_REGEX = "\\$\\{bundlepath\\}/";

	@Rule
	// REUSED CLASS
	public TemporaryFolder testFolder = new TemporaryFolder();// END REUSED CLASS

	private EdaptViewModelMigrator migrator;
	private URI resourceURI;

	@Before
	public void before() throws IOException {
		final File newFile = testFolder.newFile();
		newFile.deleteOnExit();
		final Bundle bundle = Platform.getBundle(Constants.PLUGIN_ID);
		final InputStream openStream = bundle.getEntry(getPath()).openStream();
		copyAndResolvePath(openStream, newFile, bundle.getEntry("/").toString());
		migrator = new EdaptViewModelMigrator();
		resourceURI = URI.createFileURI(newFile.getAbsolutePath());
	}

	@Test
	// BEGIN SUPRESS CATCH EXCEPTION
	public void testMigration() throws Exception {// END SUPRESS CATCH EXCEPTION
		performTest();
	}

	protected static void assertUUIDPresent(EObject object) {
		assertNotNull(XMIResource.class.cast(object.eResource()).getID(object));
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	protected abstract void performTest() throws Exception;// END SUPRESS CATCH EXCEPTION

	protected abstract String getPath();

	protected EdaptViewModelMigrator getMigrator() {
		return migrator;
	}

	protected URI getURI() {
		return resourceURI;
	}

	protected VTViewTemplate getMigratedViewTemplate() throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VTTemplatePackage.eNS_URI,
			VTTemplatePackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(resourceURI);
		resource.load(null);
		final VTViewTemplate viewTemplate = (VTViewTemplate) resource.getContents().get(0);
		return viewTemplate;
	}

	private void copyAndResolvePath(InputStream in, File file, String bundlePath) {
		try {
			final InputStreamReader reader = new InputStreamReader(in);
			final BufferedReader bufferedReader = new BufferedReader(reader);
			final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.replaceAll(BUNDLE_PATH_REGEX, bundlePath);
				bufferedWriter.write(line);
			}
			bufferedWriter.close();
			bufferedReader.close();
			in.close();
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		catch (final Exception e) {// END SUPRESS CATCH EXCEPTION
			e.printStackTrace();
		}
	}

}
