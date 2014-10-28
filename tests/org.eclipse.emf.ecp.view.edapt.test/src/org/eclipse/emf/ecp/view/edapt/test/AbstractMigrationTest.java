/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.edapt.EdaptViewModelMigrator;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.osgi.framework.Bundle;

public abstract class AbstractMigrationTest {

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
		copy(openStream, newFile);
		migrator = new EdaptViewModelMigrator();
		resourceURI = URI.createFileURI(newFile.getAbsolutePath());
	}

	@Test
	// BEGIN SUPRESS CATCH EXCEPTION
	public void testMigration() throws Exception {// END SUPRESS CATCH EXCEPTION
		performTest();
	}

	protected static void assertDMRwithEmptyPath(
		VFeaturePathDomainModelReference dmr, EStructuralFeature feature) {
		assertSame(feature, dmr.getDomainModelEFeature());
		assertEquals(0, dmr.getDomainModelEReferencePath().size());
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

	protected VView getMigratedView() throws IOException {
		final Resource resource = new ResourceSetImpl().createResource(resourceURI);
		resource.load(null);
		final VView view = (VView) resource.getContents().get(0);
		return view;
	}

	private void copy(InputStream in, File file) {
		try {
			final OutputStream out = new FileOutputStream(file);
			final byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		catch (final Exception e) {// END SUPRESS CATCH EXCEPTION
			e.printStackTrace();
		}
	}

}
