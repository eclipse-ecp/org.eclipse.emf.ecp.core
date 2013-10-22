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
package org.eclipse.emf.ecp.view.model.edit.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.ViewPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Test;

public class ChildrenDescriptorTest {

	/**
	 * Needs to be adapted after refactoring
	 * These are the counts for the core model only
	 * If a model elements is moved out, the respective test can be removed here
	 */
	private static final int RENDERABLE_CHILD_COUNT = 0;
	private static final int COMPOSITE_CHILD_COUNT = RENDERABLE_CHILD_COUNT;
	private static final int ABSTRACTCATEGORIZATION_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;
	private static final int NUMBER_OF_COMPOSITES = 1;
	private static final int NUMBER_OF_CATEGORIZATIONS = 3;
	private static final int CATEGORIZATION_CHILD_COUNT = ABSTRACTCATEGORIZATION_CHILD_COUNT
		+ NUMBER_OF_CATEGORIZATIONS;
	private static final int COMPOSITECOLLECTION_CHILD_COUNT = COMPOSITE_CHILD_COUNT + NUMBER_OF_COMPOSITES;
	private static final int VIEW_CHILD_COUNT = NUMBER_OF_COMPOSITES + CATEGORIZATION_CHILD_COUNT;

	private static final int CATEGORY_CHILD_COUNT = NUMBER_OF_COMPOSITES + ABSTRACTCATEGORIZATION_CHILD_COUNT;
	private static final int CONTROL_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;

	private static final int ACTION_CHILD_COUNT = 0;

	private final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(
		ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());

	@Test
	public void testViewChildDescriptors() {
		final int size = getChildrenSize(ViewPackage.eINSTANCE.getView());
		assertEquals(VIEW_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAbstractCategorizationChildDescriptors() {
		getChildrenSize(ViewPackage.eINSTANCE.getAbstractCategorization());
	}

	@Test
	public void testCategorizationChildDescriptors() {
		final int size = getChildrenSize(ViewPackage.eINSTANCE.getCategorization());
		assertEquals(CATEGORIZATION_CHILD_COUNT, size);
	}

	@Test
	public void testCategoryChildDescriptors() {
		final int size = getChildrenSize(ViewPackage.eINSTANCE.getCategory());
		assertEquals(CATEGORY_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompositeChildDescriptors() {
		getChildrenSize(ViewPackage.eINSTANCE.getComposite());
	}

	@Test
	public void testControlChildDescriptors() {
		final int size = getChildrenSize(ViewPackage.eINSTANCE.getControl());
		assertEquals(CONTROL_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompositeCollectionDescriptors() {
		getChildrenSize(ViewPackage.eINSTANCE.getCompositeCollection());
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRenderableDescriptors() {
		getChildrenSize(ViewPackage.eINSTANCE.getRenderable());
	}

	@Test
	public void testActionDescriptors() {
		final int size = getChildrenSize(ViewPackage.eINSTANCE.getAction());
		assertEquals(ACTION_CHILD_COUNT, size);
	}

	/**
	 * @param category
	 * @return
	 */
	private int getChildrenSize(EClass eClass) {
		final EObject eObject = EcoreUtil.create(eClass);
		return domain.getNewChildDescriptors(eObject, null).size();
	}

}
