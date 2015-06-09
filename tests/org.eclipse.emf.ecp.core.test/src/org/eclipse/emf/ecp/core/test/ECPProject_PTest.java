/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ECPProject-tests
 *
 * @author Tobias Verhoeven
 */
public class ECPProject_PTest extends AbstractTest {

	/** The project. */
	private InternalProject project;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {

		boolean done = false;

		while (!done) {
			try {
				project = (InternalProject) getProjectManager().createProject(getProvider(),
					"Projekt " + UUID.randomUUID());
				done = true;
			} catch (final ECPProjectWithNameExistsException e) {
			}
		}
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {
		project.delete();
	}

	/**
	 * Test get elements.
	 */
	@Test
	public void testGetElements() {
		assertEquals(0, project.getContents().size());

		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		final EObject reference = EcoreFactory.eINSTANCE.createEReference();

		project.getContents().add(clazz);

		assertEquals(1, project.getContents().size());
		assertTrue(project.contains(clazz));
		assertTrue(project.getContents().contains(clazz));

		project.getContents().add(reference);
		assertEquals(2, project.getContents().size());
		assertTrue(project.contains(clazz));
		assertTrue(project.getContents().contains(clazz));
		assertTrue(project.contains(reference));
		assertTrue(project.getContents().contains(reference));

		project.getContents().remove(clazz);
		assertEquals(1, project.getContents().size());
		assertFalse(project.contains(clazz));
		assertFalse(project.getContents().contains(clazz));
		assertTrue(project.contains(reference));
		assertTrue(project.getContents().contains(reference));

		project.getContents().remove(reference);
		assertEquals(0, project.getContents().size());
		assertFalse(project.contains(clazz));
		assertFalse(project.getContents().contains(clazz));
		assertFalse(project.contains(reference));
		assertFalse(project.getContents().contains(reference));
	}

	/**
	 * Test get unsupported e packages.
	 */
	@Test
	public void testGetUnsupportedEPackages() {
		project.getUnsupportedEPackages();
		// TODO add correct assert
		// fail("This method always returns an empty list");
	}

	/**
	 * Test visible packages.
	 */
	@Test
	public void testVisiblePackages() {
		final Set<EPackage> packages = project.getVisiblePackages();
		assertFalse(packages.contains(EcorePackage.eINSTANCE));
		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		final EObject reference = EcoreFactory.eINSTANCE.createEReference();

		project.getContents().add(clazz);
		assertTrue(project.contains(clazz));

		packages.add(EcorePackage.eINSTANCE);
		assertTrue(packages.contains(EcorePackage.eINSTANCE));

		project.getContents().add(reference);
		assertTrue(project.contains(reference));
	}

	/**
	 * Test visible classes.
	 */
	@Test
	public void testVisibleClasses() {
		boolean failed = true;
		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		final EObject reference = EcoreFactory.eINSTANCE.createEReference();

		try {
			project.getVisibleEClasses().add(clazz.eClass());
		} catch (final UnsupportedOperationException upe) {
			failed = false;
		}

		if (failed) {
			fail("Class list is modifiable");
		}

		final Set<EClass> classes = new HashSet<EClass>(project.getVisibleEClasses());
		assertEquals(0, classes.size());
		classes.add(clazz.eClass());
		project.setVisibleEClasses(classes);

		assertEquals(1, project.getVisibleEClasses().size());
		assertTrue(project.getVisibleEClasses().contains(clazz.eClass()));

		classes.add(reference.eClass());

		assertEquals(2, project.getVisibleEClasses().size());
		assertTrue(project.getVisibleEClasses().contains(clazz.eClass()));
		assertTrue(project.getVisibleEClasses().contains(reference.eClass()));
	}

	/**
	 * Test get reference candidates.
	 */
	@Test
	public void testGetReferenceCandidates() {

		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();

		final EObject referenceObjectA = EcoreFactory.eINSTANCE.createEReference();
		referenceObjectA.eSet(referenceObjectA.eClass().getEStructuralFeature("name"), "my little reference");

		final EObject referenceObjectB = EcoreFactory.eINSTANCE.createEReference();
		referenceObjectB.eSet(referenceObjectB.eClass().getEStructuralFeature("name"), "my big reference");

		final EReference reference = (EReference) clazz.eClass().getEStructuralFeature(EcorePackage.EREFERENCE);

		project.getContents().add(clazz);

		assertFalse(project.getReferenceCandidates(clazz, reference).hasNext());

		project.getContents().add(referenceObjectA);

		Iterator<EObject> iterator = project.getReferenceCandidates(clazz, reference);
		final List<EObject> objects = new ArrayList<EObject>(2);
		while (iterator.hasNext()) {
			objects.add(iterator.next());
		}

		assertFalse(iterator.hasNext());
		assertEquals(1, objects.size());
		assertTrue(objects.contains(referenceObjectA));
		objects.clear();

		project.getContents().add(referenceObjectB);
		iterator = project.getReferenceCandidates(clazz, reference);

		while (iterator.hasNext()) {
			objects.add(iterator.next());
		}

		assertFalse(iterator.hasNext());
		assertEquals(2, objects.size());
		assertTrue(objects.contains(referenceObjectA));
		assertTrue(objects.contains(referenceObjectB));
	}

	/**
	 * Test save properties.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testSaveProperties() {
		project.saveProperties();
	}

	/**
	 * Test dirty save model.
	 */
	@Test
	public void testDirtySaveModel() {
		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		assertFalse(project.hasDirtyContents());
		project.getContents().add(clazz);
		assertTrue(project.hasDirtyContents());
		project.saveContents();
		assertFalse(project.hasDirtyContents());
	}

	/**
	 * Test delete elements.
	 */
	@Test
	public void testDeleteElements() {
		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		final EObject reference = EcoreFactory.eINSTANCE.createEReference();
		final EObject attribute = EcoreFactory.eINSTANCE.createEAttribute();

		assertEquals(0, project.getContents().size());
		project.getContents().add(clazz);
		project.getContents().add(reference);
		assertEquals(2, project.getContents().size());

		final Collection<Object> collection = new ArrayList<Object>();
		collection.add(clazz);
		project.deleteElements(collection);
		assertEquals(1, project.getContents().size());
		assertTrue(project.contains(reference));
		assertFalse(project.contains(clazz));

		collection.clear();
		collection.add(reference);
		project.deleteElements(collection);
		assertEquals(0, project.getContents().size());

		project.getContents().add(clazz);
		project.getContents().add(reference);
		project.getContents().add(attribute);

		collection.clear();
		collection.add(clazz);
		collection.add(reference);
		collection.add(attribute);

		assertEquals(3, project.getContents().size());
		project.deleteElements(collection);
		assertEquals(0, project.getContents().size());

		boolean thrown = false;
		try {
			collection.clear();
			collection.add(clazz);
			project.deleteElements(collection);
		} catch (final IllegalArgumentException iae) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	/**
	 * Checks if is modelroot.
	 */
	@Test
	public void testIsModelroot() {
		final EObject clazz = EcoreFactory.eINSTANCE.createEClass();
		final EObject reference = EcoreFactory.eINSTANCE.createEReference();
		final EObject attribute = EcoreFactory.eINSTANCE.createEAttribute();

		assertEquals(0, project.getContents().size());
		project.getContents().add(clazz);
		project.getContents().add(reference);
		project.getContents().add(attribute);
		assertEquals(3, project.getContents().size());

		assertFalse(project.isModelRoot(clazz));
		assertFalse(project.isModelRoot(reference));
		assertFalse(project.isModelRoot(attribute));

		assertTrue(project.isModelRoot(clazz.eContainer()));
	}
}
