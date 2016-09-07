/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link IndexDomainModelReferenceSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class IndexDomainModelReferenceSegmentConverter_Test {

	private IndexDomainModelReferenceSegmentConverter converter;
	private DefaultRealm realm;

	/**
	 */
	@Before
	public void setUp() {
		converter = new IndexDomainModelReferenceSegmentConverter();
		realm = new DefaultRealm();
	}

	/**
	 */
	@After
	public void tearDown() {
		realm.dispose();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		assertEquals(10d, converter.isApplicable(segment), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegment() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, converter.isApplicable(segment), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToValuePropertyIndexOne() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);

		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setIndex(1);
		segment.setDomainModelFeature("cList"); //$NON-NLS-1$
		final IEMFValueProperty property = converter.convertToValueProperty(segment, b.eClass(), getEditingDomain(b));

		assertEquals("B.cList<C> index 1", property.toString()); //$NON-NLS-1$
		assertEquals(c1, property.getValue(b));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToValuePropertyIndexZero() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);

		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setIndex(0);
		segment.setDomainModelFeature("cList"); //$NON-NLS-1$
		final IEMFValueProperty property = converter.convertToValueProperty(segment, b.eClass(), getEditingDomain(b));

		assertEquals("B.cList<C> index 0", property.toString()); //$NON-NLS-1$
		assertEquals(c0, property.getValue(b));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testConvertToListProperty() throws DatabindingFailedException {
		converter.convertToListProperty(mock(VIndexDomainModelReferenceSegment.class), mock(EClass.class),
			mock(EditingDomain.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetSetting() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C c2 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);
		b.getCList().add(c2);

		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setIndex(1);
		segment.setDomainModelFeature("cList"); //$NON-NLS-1$

		final Setting setting = converter.getSetting(segment, b);
		assertEquals(b, setting.getEObject());
		assertEquals(c1, setting.get(true));
		assertTrue(setting instanceof IndexedSetting);
		assertEquals(1, ((IndexedSetting) setting).getIndex());
		assertEquals(TestPackage.eINSTANCE.getB_CList(), setting.getEStructuralFeature());
	}

	private EditingDomain getEditingDomain(EObject object) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}
}
