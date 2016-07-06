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
package org.eclipse.emfforms.internal.core.services.databinding.featurepath;

import static org.junit.Assert.assertEquals;
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
import org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
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
 * JUnit test cases for {@link FeatureDomainModelReferenceSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureDomainModelReferenceSegmentConverter_Test {

	private FeatureDomainModelReferenceSegmentConverter converter;

	@Before
	public void setUp() {
		converter = new FeatureDomainModelReferenceSegmentConverter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}
	 * .
	 */
	@Test
	public void testIsApplicable() {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		assertEquals(1d, converter.isApplicable(segment), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}
	 * .
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, converter.isApplicable(segment), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
	 * .
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final A a = TestFactory.eINSTANCE.createA();
		final EReference reference = TestPackage.eINSTANCE.getA_B();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		final IEMFValueProperty property = converter.convertToValueProperty(segment, a.eClass(), getEditingDomain(a));

		assertEquals("A.b<B>", property.toString()); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
	 * .
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyFeatureNotInEClass() throws DatabindingFailedException {
		final C c = TestFactory.eINSTANCE.createC();
		final EReference reference = TestPackage.eINSTANCE.getA_B();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		converter.convertToValueProperty(segment, c.eClass(), getEditingDomain(c));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
	 * .
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testConvertToListProperty() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final EReference reference = TestPackage.eINSTANCE.getB_CList();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		final IEMFListProperty property = converter.convertToListProperty(segment, b.eClass(), getEditingDomain(b));

		assertEquals("B.cList[]<C>", property.toString()); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
	 * .
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertyFeatureNotInEClass() throws DatabindingFailedException {
		final C c = TestFactory.eINSTANCE.createC();
		final EReference reference = TestPackage.eINSTANCE.getB_CList();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(reference.getName());

		converter.convertToListProperty(segment, c.eClass(), getEditingDomain(c));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}
	 * .
	 * 
	 * @throws DatabindingFailedException
	 */
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

	private EditingDomain getEditingDomain(EObject object) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}
}
