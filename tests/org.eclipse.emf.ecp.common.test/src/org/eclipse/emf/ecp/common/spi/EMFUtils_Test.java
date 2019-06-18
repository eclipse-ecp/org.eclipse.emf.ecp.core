/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EMFUtils}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFUtils_Test {

	private EClass eClass1;
	private EClass eClass2;
	private EAttribute attribute;
	private EAttribute unsettableAttribute;
	private EPackage ePackage;

	/**
	 * Setup the test model.
	 */
	@Before
	public void setUp() {

		attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName("attribute"); //$NON-NLS-1$
		attribute.setChangeable(true);
		attribute.setEType(EcorePackage.Literals.ESTRING);
		unsettableAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		unsettableAttribute.setName("unsettableAttribute"); //$NON-NLS-1$
		unsettableAttribute.setChangeable(true);
		unsettableAttribute.setUnsettable(true);
		unsettableAttribute.setEType(EcorePackage.Literals.ESTRING);

		eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.setName("eClass1"); //$NON-NLS-1$
		eClass1.getEStructuralFeatures().add(attribute);
		eClass1.getEStructuralFeatures().add(unsettableAttribute);

		eClass2 = EcoreFactory.eINSTANCE.createEClass();
		eClass2.setName("eClass2"); //$NON-NLS-1$

		ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("package"); //$NON-NLS-1$
		ePackage.getEClassifiers().add(eClass1);
		ePackage.getEClassifiers().add(eClass2);
	}

	@Test
	public void filteredEquals_differentEClasses() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		final EObject eObject2 = EcoreUtil.create(eClass2);

		assertFalse(EMFUtils.filteredEquals(eObject1, eObject2));
	}

	public void filteredEquals_param1Null() {
		final EObject eObject2 = EcoreUtil.create(eClass1);
		assertFalse(EMFUtils.filteredEquals(null, eObject2));
	}

	@Test()
	public void filteredEquals_param2Null() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		assertFalse(EMFUtils.filteredEquals(eObject1, null));
	}

	@Test()
	public void filteredEquals_bothNull() {
		assertTrue(EMFUtils.filteredEquals(null, null));
	}

	@Test
	public void filteredEquals_equal() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		final EObject eObject2 = EcoreUtil.create(eClass1);

		eObject1.eSet(attribute, "test"); //$NON-NLS-1$
		eObject2.eSet(attribute, "test"); //$NON-NLS-1$
		eObject1.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$
		eObject2.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$

		assertTrue(EMFUtils.filteredEquals(eObject1, eObject2));
	}

	@Test
	public void filteredEquals_different() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		final EObject eObject2 = EcoreUtil.create(eClass1);

		eObject1.eSet(attribute, "test"); //$NON-NLS-1$
		eObject2.eSet(attribute, "test-different"); //$NON-NLS-1$
		eObject1.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$
		eObject2.eSet(unsettableAttribute, "test2-different"); //$NON-NLS-1$

		assertFalse(EMFUtils.filteredEquals(eObject1, eObject2));
	}

	@Test
	public void filteredEquals_filter() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		final EObject eObject2 = EcoreUtil.create(eClass1);

		eObject1.eSet(attribute, "test"); //$NON-NLS-1$
		eObject2.eSet(attribute, "test"); //$NON-NLS-1$
		eObject1.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$
		eObject2.eSet(unsettableAttribute, "test2-different"); //$NON-NLS-1$

		// unsettableAttribute's values should be ignored => equality check should be true
		assertTrue(EMFUtils.filteredEquals(eObject1, eObject2, unsettableAttribute));
	}

	@Test
	public void filteredEquals_unsettableFeatures() {
		final EObject eObject1 = EcoreUtil.create(eClass1);
		final EObject eObject2 = EcoreUtil.create(eClass1);

		eObject1.eSet(attribute, "test"); //$NON-NLS-1$
		eObject2.eSet(attribute, "test"); //$NON-NLS-1$
		eObject1.eUnset(unsettableAttribute);
		eObject2.eSet(unsettableAttribute, null);
		assertFalse(EMFUtils.filteredEquals(eObject1, eObject2));

		eObject2.eUnset(unsettableAttribute);
		assertTrue(EMFUtils.filteredEquals(eObject1, eObject2));

		eObject1.eSet(unsettableAttribute, null);
		eObject2.eSet(unsettableAttribute, null);
		assertTrue(EMFUtils.filteredEquals(eObject1, eObject2));

		eObject2.eSet(unsettableAttribute, "test2");
		assertFalse(EMFUtils.filteredEquals(eObject1, eObject2));
	}

	/**
	 * Tests that the comparison also checks features inherited from a superclass.
	 */
	@Test
	public void filteredEquals_superClassFeatures() {
		final EClass subClass = EcoreFactory.eINSTANCE.createEClass();
		subClass.setName("SubClass");
		subClass.getESuperTypes().add(eClass1);
		ePackage.getEClassifiers().add(subClass);

		final EObject eObject1 = EcoreUtil.create(subClass);
		final EObject eObject2 = EcoreUtil.create(subClass);
		eObject1.eSet(attribute, "test"); //$NON-NLS-1$
		eObject2.eSet(attribute, "test"); //$NON-NLS-1$
		eObject1.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$
		eObject2.eSet(unsettableAttribute, "test2"); //$NON-NLS-1$
		assertTrue(EMFUtils.filteredEquals(eObject1, eObject2));

		// change value of inherited feature
		eObject2.eSet(attribute, "different"); //$NON-NLS-1$
		assertFalse(EMFUtils.filteredEquals(eObject1, eObject2));
	}
}
