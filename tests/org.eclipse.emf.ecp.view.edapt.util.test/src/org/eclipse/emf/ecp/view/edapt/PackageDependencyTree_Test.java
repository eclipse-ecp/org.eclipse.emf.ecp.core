/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecp.view.edapt.util.test.model.a.APackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EEPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.FPackage;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jfaltermeier
 *
 */
public class PackageDependencyTree_Test {

	@BeforeClass
	public static void beforeClass() {
		// non osgi -> register epackages
		APackage.eINSTANCE.eClass();
		BPackage.eINSTANCE.eClass();
		CPackage.eINSTANCE.eClass();
		DPackage.eINSTANCE.eClass();
		EEPackage.eINSTANCE.eClass();
		FPackage.eINSTANCE.eClass();
	}

	@Test
	public void testIteratorWithCircles() {
		final PackageDependencyTree packageDependencyTree = new PackageDependencyTree();
		packageDependencyTree.addPackage(FPackage.eNS_URI);
		final Iterator<Set<String>> iterator = packageDependencyTree.getIerator();

		// A
		assertTrue(iterator.hasNext());
		final Set<String> aSet = iterator.next();
		assertEquals(1, aSet.size());
		assertTrue(aSet.contains(APackage.eNS_URI));

		// B C D
		assertTrue(iterator.hasNext());
		final Set<String> bcdSet = iterator.next();
		assertEquals(3, bcdSet.size());
		assertTrue(bcdSet.contains(BPackage.eNS_URI));
		assertTrue(bcdSet.contains(CPackage.eNS_URI));
		assertTrue(bcdSet.contains(DPackage.eNS_URI));

		// E F
		assertTrue(iterator.hasNext());
		final Set<String> efSet = iterator.next();
		assertEquals(2, efSet.size());
		assertTrue(efSet.contains(EEPackage.eNS_URI));
		assertTrue(efSet.contains(FPackage.eNS_URI));
		assertFalse(iterator.hasNext());

	}

}
