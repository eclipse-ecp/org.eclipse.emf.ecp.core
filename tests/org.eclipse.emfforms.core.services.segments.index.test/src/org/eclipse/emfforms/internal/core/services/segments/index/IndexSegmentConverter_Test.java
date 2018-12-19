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
package org.eclipse.emfforms.internal.core.services.segments.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link IndexSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class IndexSegmentConverter_Test {

	private IndexSegmentConverter converter;
	private DefaultRealm realm;

	/**
	 */
	@Before
	public void setUp() {
		converter = new IndexSegmentConverter();
		realm = new DefaultRealm();
	}

	/**
	 */
	@After
	public void tearDown() {
		realm.dispose();
	}

	@Test
	public void testIsApplicable() {
		final VIndexDomainModelReferenceSegment segment = VIndexsegmentFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		assertEquals(10d, converter.isApplicable(segment), 0d);
	}

	@Test
	public void testIsApplicableWrongSegment() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, converter.isApplicable(segment), 0d);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToValuePropertyIndexOne() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);

		final VIndexDomainModelReferenceSegment segment = VIndexsegmentFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setIndex(1);
		segment.setDomainModelFeature("cList"); //$NON-NLS-1$
		final SegmentConverterValueResultEMF conversionResult = converter.convertToValueProperty(segment, b.eClass(),
			getEditingDomain(b));
		final IEMFValueProperty property = conversionResult.getValueProperty();

		assertEquals("B.cList<C> index 1", property.toString()); //$NON-NLS-1$
		assertEquals(c1, property.getValue(b));
		assertTrue(conversionResult.getNextEClass().isPresent());
		assertEquals(TestPackage.eINSTANCE.getC(), conversionResult.getNextEClass().get());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToValuePropertyIndexZero() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);

		final VIndexDomainModelReferenceSegment segment = VIndexsegmentFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setIndex(0);
		segment.setDomainModelFeature("cList"); //$NON-NLS-1$
		final SegmentConverterValueResultEMF conversionResult = converter.convertToValueProperty(segment, b.eClass(),
			getEditingDomain(b));
		final IEMFValueProperty property = conversionResult.getValueProperty();

		assertEquals("B.cList<C> index 0", property.toString()); //$NON-NLS-1$
		assertEquals(c0, property.getValue(b));
		assertTrue(conversionResult.getNextEClass().isPresent());
		assertEquals(TestPackage.eINSTANCE.getC(), conversionResult.getNextEClass().get());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testConvertToListProperty() throws DatabindingFailedException {
		converter.convertToListProperty(mock(VIndexDomainModelReferenceSegment.class), mock(EClass.class),
			mock(EditingDomain.class));
	}

	@Test
	public void testGetSetting() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C c2 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);
		b.getCList().add(c2);

		final VIndexDomainModelReferenceSegment segment = VIndexsegmentFactory.eINSTANCE
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
