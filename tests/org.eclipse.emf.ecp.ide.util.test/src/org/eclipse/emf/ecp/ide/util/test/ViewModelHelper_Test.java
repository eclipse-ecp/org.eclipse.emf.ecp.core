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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.junit.Test;

/**
 * Unit tests for the {@link ViewModelHelper}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewModelHelper_Test {

	private static final String PATH_1 = "/test.1/model/model.ecore";
	private static final String PATH_2 = "/test.2/model/model.ecore";

	@Test
	public void getEcorePaths_pre1170() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_pre1170.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 1, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
	}

	@Test
	public void getEcorePaths_1170_singlePath() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_1170_single.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 1, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
	}

	@Test
	public void getEcorePaths_1170_multiplePaths() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_1170_multiple.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 2, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
		assertEquals("Incorrect extracted ecore path.", PATH_2, ecorePaths.get(1));
	}

	private Resource loadResource(String filePath) {
		final ResourceSetImpl rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource resource = rs.createResource(URI.createFileURI(filePath));
		try {
			resource.load(Collections.singletonMap(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE));
			return resource;
		} catch (final IOException ex) {
			fail("Could not load resource at: " + filePath);
		}
		return null; // Never happens
	}
}
