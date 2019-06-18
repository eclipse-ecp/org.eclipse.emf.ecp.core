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
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link FeatureSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureSegmentConverter_Test {

	private FeatureSegmentConverter converter;

	@Before
	public void setUp() {
		converter = new FeatureSegmentConverter();
	}

	@Test
	public void testIsApplicable() {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		assertEquals(1d, converter.isApplicable(segment), 0d);
	}

	@Test
	public void testIsApplicableWrongSegmentType() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, converter.isApplicable(segment), 0d);
	}

	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final A a = TestFactory.eINSTANCE.createA();
		final EReference reference = TestPackage.eINSTANCE.getA_B();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		final SegmentConverterValueResultEMF conversionResult = converter.convertToValueProperty(segment, a.eClass(),
			getEditingDomain(a));
		final IEMFValueProperty property = conversionResult.getValueProperty();

		assertEquals("A.b<B>", property.toString()); //$NON-NLS-1$
		assertEquals(TestPackage.eINSTANCE.getB(), conversionResult.getNextEClass().get());
	}

	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyFeatureNotInEClass() throws DatabindingFailedException {
		final C c = TestFactory.eINSTANCE.createC();
		final EReference reference = TestPackage.eINSTANCE.getA_B();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		converter.convertToValueProperty(segment, c.eClass(), getEditingDomain(c));
	}

	@Test
	public void testConvertToListProperty() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final EReference reference = TestPackage.eINSTANCE.getB_CList();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		final SegmentConverterListResultEMF conversionResult = converter.convertToListProperty(segment, b.eClass(),
			getEditingDomain(b));
		final IEMFListProperty property = conversionResult.getListProperty();

		assertEquals("B.cList[]<C>", property.toString()); //$NON-NLS-1$
		assertEquals(TestPackage.eINSTANCE.getC(), conversionResult.getNextEClass().get());
	}

	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertyFeatureNotInEClass() throws DatabindingFailedException {
		final C c = TestFactory.eINSTANCE.createC();
		final EReference reference = TestPackage.eINSTANCE.getB_CList();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		converter.convertToListProperty(segment, c.eClass(), getEditingDomain(c));
	}

	@Test
	public void testGetSetting() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final EReference reference = TestPackage.eINSTANCE.getB_CList();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		final Setting result = converter.getSetting(segment, b);
		final Setting expected = ((InternalEObject) b).eSetting(reference);

		assertEquals(expected, result);
	}

	@Test
	public void convertToListProperty_subClassFeature() throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(TestPackage.Literals.A__BLIST.getName());

		final SegmentConverterListResultEMF conversionResult = converter.convertToListProperty(segment,
			TestPackage.Literals.E, null);
		assertSame(TestPackage.Literals.B, conversionResult.getNextEClass().get());
		assertEquals("A.bList[]<B>", conversionResult.getListProperty().toString()); //$NON-NLS-1$
	}

	@Test
	public void convertToValueProperty_subClassFeature() throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(TestPackage.Literals.A__B.getName());

		final SegmentConverterValueResultEMF conversionResult = converter.convertToValueProperty(segment,
			TestPackage.Literals.E, null);
		assertSame(TestPackage.Literals.B, conversionResult.getNextEClass().get());
		assertEquals("A.b<B>", conversionResult.getValueProperty().toString()); //$NON-NLS-1$
	}

	private EditingDomain getEditingDomain(EObject object) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}
}
