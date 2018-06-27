/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WizardNewDataTemplateCreationPage_Test {

	private WizardNewDataTemplateCreationPage page;

	@BeforeClass
	public static void prepareClass() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
	}

	@Before
	public void setUp() throws Exception {
		page = new WizardNewDataTemplateCreationPage("test", StructuredSelection.EMPTY); //$NON-NLS-1$
	}

	@After
	public void tearDown() throws Exception {
		page.dispose();
	}

	@Test
	public void testGetInitialContents() throws IOException {
		final InputStream is = page.getInitialContents();
		final ResourceSet rs = new ResourceSetImpl();
		final Resource r = rs.createResource(URI.createURI("VIRTUAL")); //$NON-NLS-1$
		r.load(is, null);
		final EObject eObject = r.getContents().get(0);
		assertThat(eObject.eClass(), is(DataTemplatePackage.eINSTANCE.getTemplateCollection()));
		final TemplateCollection collection = (TemplateCollection) eObject;
		assertTrue(collection.getTemplates().isEmpty());
	}

}
