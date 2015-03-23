/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.segment.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link DMRSegmentConverterImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class DMRSegmentConverterImpl_Test {

	private DMRSegmentConverterImpl converter;

	/**
	 * Creates a new converter for every test case.
	 */
	@Before
	public void setUp() {
		converter = new DMRSegmentConverterImpl();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test
	public void testIsApplicable() {
		assertEquals(1d, converter.isApplicable(mock(VDMRSegment.class)), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsApplicableNull() {
		converter.isApplicable(null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("b"); //$NON-NLS-1$
		final EClass eClass = TestPackage.eINSTANCE.getA();
		final IValueProperty valueProperty = converter.convertToValueProperty(segment, eClass);

		assertNotNull(valueProperty);
		assertEquals(TestPackage.eINSTANCE.getA_B(), valueProperty.getValueType());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyFeatureNotInEClass() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("b"); //$NON-NLS-1$
		final EClass eClass = TestPackage.eINSTANCE.getB(); // B doesn't have a property b
		converter.convertToValueProperty(segment, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConvertToValuePropertyNullSegment() throws DatabindingFailedException {
		converter.convertToValueProperty(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConvertToValuePropertyNullEClass() throws DatabindingFailedException {
		converter.convertToValueProperty(mock(VDMRSegment.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test
	public void testConvertToListProperty() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("cList"); //$NON-NLS-1$
		final EClass eClass = TestPackage.eINSTANCE.getB();
		final IListProperty listProperty = converter.convertToListProperty(segment, eClass);

		assertNotNull(listProperty);
		assertEquals(TestPackage.eINSTANCE.getB_CList(), listProperty.getElementType());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertyFeatureNotInEClass() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("cList"); //$NON-NLS-1$
		final EClass eClass = TestPackage.eINSTANCE.getA(); // A doesn't have a property cList
		converter.convertToListProperty(segment, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConvertToListPropertyNullSegment() throws DatabindingFailedException {
		converter.convertToListProperty(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConvertToListPropertyNullEClass() throws DatabindingFailedException {
		converter.convertToListProperty(null, mock(EClass.class));
	}
}
