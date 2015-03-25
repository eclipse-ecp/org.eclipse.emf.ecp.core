/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link DMRIndexSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class DMRIndexSegmentConverter_Test {

	private DMRIndexSegmentConverter converter;

	/**
	 * Creates a new converter for every test case.
	 */
	@Before
	public void setUp() {
		converter = new DMRIndexSegmentConverter();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test
	public void testIsApplicable() {
		assertEquals(10d, converter.isApplicable(mock(VDMRIndexSegment.class)), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsApplicableNull() {
		converter.isApplicable(null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final VDMRIndexSegment segment = VIndexsegmentFactory.eINSTANCE.createDMRIndexSegment();
		segment.setPropertyName("cList"); //$NON-NLS-1$
		segment.setIndex(1);
		final EClass eClass = TestPackage.eINSTANCE.getB();
		final IValueProperty valueProperty = converter.convertToValueProperty(segment, eClass);

		assertNotNull(valueProperty);
		assertEquals(TestPackage.eINSTANCE.getB_CList(), valueProperty.getValueType());
		assertTrue(EMFIndexedValueProperty.class.isInstance(valueProperty));
		final EMFIndexedValueProperty indexValueProperty = (EMFIndexedValueProperty) valueProperty;
		assertEquals("B.cList<C> index 1", indexValueProperty.toString()); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyFeatureNotInEClass() throws DatabindingFailedException {
		final VDMRIndexSegment segment = VIndexsegmentFactory.eINSTANCE.createDMRIndexSegment();
		segment.setPropertyName("b"); //$NON-NLS-1$
		segment.setIndex(1);
		final EClass eClass = TestPackage.eINSTANCE.getB(); // B doesn't have a property b
		converter.convertToValueProperty(segment, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.index.DMRIndexSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testConvertToListProperty() throws DatabindingFailedException {
		final VDMRIndexSegment segment = VIndexsegmentFactory.eINSTANCE.createDMRIndexSegment();
		segment.setPropertyName("cList"); //$NON-NLS-1$
		segment.setIndex(1);
		final EClass eClass = TestPackage.eINSTANCE.getB();
		converter.convertToListProperty(segment, eClass);
	}

}
