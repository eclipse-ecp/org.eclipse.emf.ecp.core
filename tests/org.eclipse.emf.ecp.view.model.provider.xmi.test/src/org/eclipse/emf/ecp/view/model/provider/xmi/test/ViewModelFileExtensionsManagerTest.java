/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.provider.xmi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.provider.xmi.ViewModelFileExtensionsManager;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jonas
 * 
 */
public class ViewModelFileExtensionsManagerTest {

	private static final String FILEPATH = "viewmodel.view";

	private static final URI FILE_URI = URI.createPlatformPluginURI("org.eclipse.emf.ecp.view.model.provider.xmi"
		+ "/" + FILEPATH, false);
	private static final String VIEWNAME = "the view name";
	private ViewModelFileExtensionsManager manager;
	private final EClass eClass1 = BowlingPackage.eINSTANCE.getLeague();

	@Before
	public void init() throws IOException {
		ViewModelFileExtensionsManager.dispose();
		manager = ViewModelFileExtensionsManager.getInstance();
	}

	@Test
	public void testGetExtensionURIs() {
		final List<URI> extensionURIS = ViewModelFileExtensionsManager.getExtensionURIS();
		assertEquals(1, extensionURIS.size());
		final URI uri = extensionURIS.get(0);
		assertEquals(FILE_URI, uri);

	}

	@Test
	public void testHasViewModelFor() {
		final EObject eObject = EcoreUtil.create(eClass1);
		assertTrue(manager.hasViewModelFor(eObject));
	}

	@Test
	public void testCreateViewModel() {
		final EObject eObject = EcoreUtil.create(eClass1);
		final VView view = manager.createView(eObject);
		assertEquals(VIEWNAME, view.getName());
	}

	@Test
	public void testIntegrationWithViewProvider() {
		final EObject eObject = EcoreUtil.create(eClass1);
		final VView view = ViewProviderHelper.getView(eObject);
		assertEquals(VIEWNAME, view.getName());
	}
}
