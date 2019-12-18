/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.treemasterdetail.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;
import org.junit.Before;
import org.junit.Test;

/**
 * DiagnosticCache Test.
 *
 * @author Eugen Neufeld
 *
 */
public class DiagnosticCache_Test {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testNull() {
		final EObject object = null;
		final DiagnosticCache cache = new DiagnosticCache(object);
		assertEquals(0, cache.getObjects().size());
	}

	@Test
	public void testExternalReferences() {
		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		final DiagnosticCache cache = new DiagnosticCache(ePackage);
		assertEquals(2, cache.getObjects().size());
		assertTrue(cache.getObjects().contains(ePackage));
		assertTrue(cache.getObjects().contains(ePackage.getEFactoryInstance()));
	}

	@Test
	public void testNoExternalReferences() {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		final DiagnosticCache cache = new DiagnosticCache(eClass);
		assertEquals(1, cache.getObjects().size());
		assertTrue(cache.getObjects().contains(eClass));
	}
}
