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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link DuplicateRowAction}.
 *
 * @author Eugen Neufeld
 *
 */
public class DuplicateRowAction_Test {

	private DuplicateRowAction action;
	private EClass fooClass;
	private EClass barClass;

	@Before
	public void before() {
		final TableRendererViewerActionContext actionContext = mock(TableRendererViewerActionContext.class);
		action = new DuplicateRowAction(actionContext);
	}

	private void createTestPackage() {
		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("TestPackage");
		ePackage.setNsPrefix("test");
		ePackage.setNsURI("test");
		fooClass = createEClass(ePackage, "Foo");
		barClass = createEClass(ePackage, "Bar");

		// create simple attributes
		createAttribute(fooClass, "name");
		createAttribute(barClass, "name");

		// non containment references
		createReference(fooClass, "refSingleNoOpp", barClass, false, false, false, false);
		createReference(fooClass, "refManyNoOpp", barClass, false, true, false, false);

		createReference(fooClass, "refSingleOppSingle", barClass, false, false, true, false);
		createReference(fooClass, "refManyOppSingle", barClass, false, true, true, false);

		createReference(fooClass, "refSingleOppMany", barClass, false, false, true, true);
		createReference(fooClass, "refManyOppMany", barClass, false, true, true, true);

		// containment
		createReference(fooClass, "containmentSingleNoOpp", barClass, true, false, false, false);
		createReference(fooClass, "containmentManyNoOpp", barClass, true, true, false, false);

		createReference(fooClass, "containmentSingleOppSingle", barClass, true, false, true, false);
		createReference(fooClass, "containmentManyOppSingle", barClass, true, true, true, false);
	}

	private EClass createEClass(EPackage ePackage, String name) {
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		clazz.setName(name);
		ePackage.getEClassifiers().add(clazz);
		return clazz;
	}

	private void createAttribute(EClass clazz, String name) {
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(name);
		attribute.setEType(EcorePackage.eINSTANCE.getEString());
		clazz.getEStructuralFeatures().add(attribute);
	}

	private void createReference(EClass clazz, String name, EClass refClass, boolean containment, boolean many,
		boolean opposite, boolean oppositeMany) {
		final EReference reference = EcoreFactory.eINSTANCE.createEReference();
		reference.setName(name);
		reference.setEType(refClass);
		reference.setContainment(containment);
		reference.setUpperBound(many ? -1 : 1);
		if (opposite) {
			final EReference oppReference = EcoreFactory.eINSTANCE.createEReference();
			oppReference.setName(name + "-opposite");
			oppReference.setEType(clazz);
			oppReference.setUpperBound(oppositeMany ? -1 : 1);
			refClass.getEStructuralFeatures().add(oppReference);
			reference.setEOpposite(oppReference);
			oppReference.setEOpposite(reference);
		}
		clazz.getEStructuralFeatures().add(reference);
	}

	@Test
	public void test_refSingleNoOpp() {
		createTestPackage();

		final String refToTest = "refSingleNoOpp";

		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bar1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bar2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, value1);
		assertSame(bar2, value2);

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, orgValue1);
		assertSame(bar2, orgValue2);
	}

	@Test
	public void test_refManyNoOpp() {
		createTestPackage();
		final String refToTest = "refManyNoOpp";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar1, bar2);
		final List<EObject> bars2 = Arrays.asList(bar1, bar2);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(value1).toArray());
		assertArrayEquals(bars2.toArray(), asList(value2).toArray());

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(orgValue1).toArray());
		assertArrayEquals(bars2.toArray(), asList(orgValue2).toArray());
	}

	@Test
	public void test_refSingleOppSingle() {
		createTestPackage();

		final String refToTest = "refSingleOppSingle";

		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bar1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bar2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertNull(value1);
		assertNull(value2);

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, orgValue1);
		assertSame(bar2, orgValue2);

		final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertSame(foo1, oppValue1);
		assertSame(foo2, oppValue2);

	}

	@Test
	public void test_refManyOppSingle() {
		createTestPackage();
		final String refToTest = "refManyOppSingle";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar1);
		final List<EObject> bars2 = Arrays.asList(bar2);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertEquals(0, asList(value1).size());
		assertEquals(0, asList(value2).size());

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(orgValue1).toArray());
		assertArrayEquals(bars2.toArray(), asList(orgValue2).toArray());

		final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertSame(foo1, oppValue1);
		assertSame(foo2, oppValue2);
	}

	@Test
	public void test_refSingleOppMany() {
		createTestPackage();

		final String refToTest = "refSingleOppMany";

		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bar1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bar2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, value1);
		assertSame(bar2, value2);

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, orgValue1);
		assertSame(bar2, orgValue2);

		// we add the copy also to the many ref of the opposite
		final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertArrayEquals(new Object[] { foo1, foo1Copy }, asList(oppValue1).toArray());
		assertArrayEquals(new Object[] { foo2, foo2Copy }, asList(oppValue2).toArray());
	}

	@Test
	public void test_refManyOppMany() {
		createTestPackage();
		final String refToTest = "refManyOppMany";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar1);
		final List<EObject> bars2 = Arrays.asList(bar2);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(value1).toArray());
		assertArrayEquals(bars2.toArray(), asList(value2).toArray());

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(orgValue1).toArray());
		assertArrayEquals(bars2.toArray(), asList(orgValue2).toArray());

		// we add the copy also to the many ref of the opposite
		final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertArrayEquals(new Object[] { foo1, foo1Copy }, asList(oppValue1).toArray());
		assertArrayEquals(new Object[] { foo2, foo2Copy }, asList(oppValue2).toArray());
	}

	/**
	 * before copy:
	 * foo1 -> bar1
	 * foo2 -> bar2
	 * bar1 -> foo1
	 * bar2 -> foo2
	 *
	 * after copy:
	 * foo1 -> bar1, bar1Copy
	 * foo2 -> bar2, bar2Copy
	 * bar1 -> foo1, foo1Copy
	 * bar2 -> foo2, foo1Copy
	 *
	 * fooCopy1 -> bar1, bar1Copy
	 * fooCopy2 -> bar2, bar2Copy
	 * barCopy1 -> foo1, foo1Copy
	 * barCopy2 -> foo2, foo2Copy
	 */
	@Test
	public void test_refManyOppMany_both() {
		createTestPackage();
		final String refToTest = "refManyOppMany";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar1);
		final List<EObject> bars2 = Arrays.asList(bar2);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy four objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2, bar1, bar2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();
		final EObject bar1Copy = iterator.next();
		final EObject bar2Copy = iterator.next();

		// assert copy
		{
			final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
			final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
			final List<EObject> list1 = asList(value1);
			final List<EObject> list2 = asList(value2);
			assertEquals(2, list1.size());
			assertEquals(2, list2.size());

			assertSame(bar1, list1.get(0));
			assertSame(bar2, list2.get(0));
			assertSame(bar1Copy, list1.get(1));
			assertSame(bar2Copy, list2.get(1));
		}
		{
			final Object value1 = bar1Copy.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
			final Object value2 = bar2Copy.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
			final List<EObject> list1 = asList(value1);
			final List<EObject> list2 = asList(value2);
			assertEquals(2, list1.size());
			assertEquals(2, list2.size());

			assertSame(foo1, list1.get(0));
			assertSame(foo2, list2.get(0));
			assertSame(foo1Copy, list1.get(1));
			assertSame(foo2Copy, list2.get(1));
		}

		// assert old data
		{
			final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
			final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
			final List<EObject> list1 = asList(orgValue1);
			final List<EObject> list2 = asList(orgValue2);
			assertEquals(2, list1.size());
			assertEquals(2, list2.size());

			assertSame(bar1, list1.get(0));
			assertSame(bar2, list2.get(0));
			assertSame(bar1Copy, list1.get(1));
			assertSame(bar2Copy, list2.get(1));
		}
		// we add the copy also to the many ref of the opposite
		{
			final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
			final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));

			final List<EObject> list1 = asList(oppValue1);
			final List<EObject> list2 = asList(oppValue2);
			assertEquals(2, list1.size());
			assertEquals(2, list2.size());

			assertSame(foo1, list1.get(0));
			assertSame(foo1Copy, list1.get(1));
			assertSame(foo2, list2.get(0));
			assertSame(foo2Copy, list2.get(1));
		}
	}

	@Test
	public void test_containmentSingleNoOpp() {
		createTestPackage();

		final String refToTest = "containmentSingleNoOpp";

		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bar1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bar2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertNotSame(bar1, value1);
		assertNotSame(bar2, value2);
		assertTrue(EcoreUtil.equals(bar1, (EObject) value1));
		assertTrue(EcoreUtil.equals(bar2, (EObject) value2));

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, orgValue1);
		assertSame(bar2, orgValue2);
	}

	@Test
	public void test_containmentManyNoOpp() {
		createTestPackage();
		final String refToTest = "containmentManyNoOpp";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar10 = EcoreUtil.create(barClass);
		final EObject bar11 = EcoreUtil.create(barClass);
		final EObject bar20 = EcoreUtil.create(barClass);
		final EObject bar21 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar10, bar11);
		final List<EObject> bars2 = Arrays.asList(bar20, bar21);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar10.eSet(barClass.getEStructuralFeature("name"), "Bar 10");
		bar11.eSet(barClass.getEStructuralFeature("name"), "Bar 11");
		bar20.eSet(barClass.getEStructuralFeature("name"), "Bar 20");
		bar21.eSet(barClass.getEStructuralFeature("name"), "Bar 21");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertTrue(EcoreUtil.equals(bars1, asList(value1)));
		assertTrue(EcoreUtil.equals(bars2, asList(value2)));

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(orgValue1).toArray());
		assertArrayEquals(bars2.toArray(), asList(orgValue2).toArray());
	}

	@Test
	public void test_containmentSingleOppSingle() {
		createTestPackage();

		final String refToTest = "containmentSingleOppSingle";

		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar1 = EcoreUtil.create(barClass);
		final EObject bar2 = EcoreUtil.create(barClass);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar1.eSet(barClass.getEStructuralFeature("name"), "Bar 1");
		bar2.eSet(barClass.getEStructuralFeature("name"), "Bar 2");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bar1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bar2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		assertNotSame(bar1, value1);
		assertNotSame(bar2, value2);
		assertTrue(EcoreUtil.equals(bar1, (EObject) value1));
		assertTrue(EcoreUtil.equals(bar2, (EObject) value2));

		final Object oppCopyValue1 = ((EObject) value1).eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppCopyValue2 = ((EObject) value2).eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertSame(foo1Copy, oppCopyValue1);
		assertSame(foo2Copy, oppCopyValue2);

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertSame(bar1, orgValue1);
		assertSame(bar2, orgValue2);

		final Object oppValue1 = bar1.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue2 = bar2.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertSame(foo1, oppValue1);
		assertSame(foo2, oppValue2);
	}

	@Test
	public void test_containmentManyOppSingle() {
		createTestPackage();
		final String refToTest = "containmentManyOppSingle";
		final EObject foo1 = EcoreUtil.create(fooClass);
		final EObject foo2 = EcoreUtil.create(fooClass);
		final EObject bar10 = EcoreUtil.create(barClass);
		final EObject bar11 = EcoreUtil.create(barClass);
		final EObject bar20 = EcoreUtil.create(barClass);
		final EObject bar21 = EcoreUtil.create(barClass);
		final List<EObject> bars1 = Arrays.asList(bar10, bar11);
		final List<EObject> bars2 = Arrays.asList(bar20, bar21);

		foo1.eSet(fooClass.getEStructuralFeature("name"), "Foo 1");
		foo2.eSet(fooClass.getEStructuralFeature("name"), "Foo 2");
		bar10.eSet(barClass.getEStructuralFeature("name"), "Bar 10");
		bar11.eSet(barClass.getEStructuralFeature("name"), "Bar 11");
		bar20.eSet(barClass.getEStructuralFeature("name"), "Bar 20");
		bar21.eSet(barClass.getEStructuralFeature("name"), "Bar 21");

		// fill reference
		foo1.eSet(fooClass.getEStructuralFeature(refToTest), bars1);
		foo2.eSet(fooClass.getEStructuralFeature(refToTest), bars2);

		// copy two objects
		final Collection<EObject> original = Arrays.asList(foo1, foo2);
		final Collection<EObject> copy = action.copy(original);
		final Iterator<EObject> iterator = copy.iterator();
		final EObject foo1Copy = iterator.next();
		final EObject foo2Copy = iterator.next();

		// assert copy
		final Object value1 = foo1Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object value2 = foo2Copy.eGet(fooClass.getEStructuralFeature(refToTest));
		final List<EObject> copyBars1 = asList(value1);
		final List<EObject> copyBars2 = asList(value2);
		assertTrue(EcoreUtil.equals(bars1, copyBars1));
		assertTrue(EcoreUtil.equals(bars2, copyBars2));

		assertTrue(EcoreUtil.equals(bars1.get(0), copyBars1.get(0)));
		assertTrue(EcoreUtil.equals(bars1.get(1), copyBars1.get(1)));
		assertTrue(EcoreUtil.equals(bars2.get(0), copyBars2.get(0)));
		assertTrue(EcoreUtil.equals(bars2.get(1), copyBars2.get(1)));

		assertSame(foo1Copy, copyBars1.get(0).eGet(barClass.getEStructuralFeature(refToTest + "-opposite")));
		assertSame(foo1Copy, copyBars1.get(1).eGet(barClass.getEStructuralFeature(refToTest + "-opposite")));
		assertSame(foo2Copy, copyBars2.get(0).eGet(barClass.getEStructuralFeature(refToTest + "-opposite")));
		assertSame(foo2Copy, copyBars2.get(1).eGet(barClass.getEStructuralFeature(refToTest + "-opposite")));

		// assert old data
		final Object orgValue1 = foo1.eGet(fooClass.getEStructuralFeature(refToTest));
		final Object orgValue2 = foo2.eGet(fooClass.getEStructuralFeature(refToTest));
		assertArrayEquals(bars1.toArray(), asList(orgValue1).toArray());
		assertArrayEquals(bars2.toArray(), asList(orgValue2).toArray());

		final Object oppValue10 = bar10.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue11 = bar11.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue20 = bar20.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		final Object oppValue21 = bar21.eGet(barClass.getEStructuralFeature(refToTest + "-opposite"));
		assertSame(foo1, oppValue10);
		assertSame(foo1, oppValue11);
		assertSame(foo2, oppValue20);
		assertSame(foo2, oppValue21);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<EObject> asList(Object value) {
		return (List) value;
	}

}
