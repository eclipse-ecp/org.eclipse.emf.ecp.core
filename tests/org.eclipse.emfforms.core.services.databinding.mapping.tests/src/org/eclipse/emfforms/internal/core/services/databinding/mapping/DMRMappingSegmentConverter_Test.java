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
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link DMRMappingSegmentConverter}
 *
 * @author Lucas Koehler
 *
 */
public class DMRMappingSegmentConverter_Test {

	private DMRMappingSegmentConverter converter;

	/**
	 * Creates a new converter for every test case.
	 */
	@Before
	public void setUp() {
		converter = new DMRMappingSegmentConverter();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test
	public void testIsApplicable() {
		assertEquals(10d, converter.isApplicable(mock(VDMRMappingSegment.class)), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsApplicableNull() {
		converter.isApplicable(null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final VDMRMappingSegment segment = VMappingsegmentFactory.eINSTANCE.createDMRMappingSegment();
		segment.setPropertyName("eClassToA"); //$NON-NLS-1$
		segment.setMappedClass(TestPackage.eINSTANCE.getD());
		final EClass eClass = TestPackage.eINSTANCE.getC();
		final IValueProperty valueProperty = converter.convertToValueProperty(segment, eClass);

		assertNotNull(valueProperty);
		assertEquals(TestPackage.eINSTANCE.getC_EClassToA(), valueProperty.getValueType());
		assertTrue(EMFMappingValueProperty.class.isInstance(valueProperty));
		final EMFMappingValueProperty mappingValueProperty = (EMFMappingValueProperty) valueProperty;
		assertEquals("C.eClassToA<EClassToAMap> mapping D", mappingValueProperty.toString()); //$NON-NLS-1$
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyFeatureNotInEClass() throws DatabindingFailedException {
		final VDMRMappingSegment segment = VMappingsegmentFactory.eINSTANCE.createDMRMappingSegment();
		segment.setPropertyName("b"); //$NON-NLS-1$
		final EClass mappedEClass = EcoreFactory.eINSTANCE.createEClass();
		segment.setMappedClass(mappedEClass);
		final EClass eClass = TestPackage.eINSTANCE.getB(); // B doesn't have a property b
		converter.convertToValueProperty(segment, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConvertToValuePropertyNullEClass() throws DatabindingFailedException {
		converter.convertToValueProperty(mock(VDMRMappingSegment.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.DMRMappingSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the conversion fails
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testConvertToListProperty() throws DatabindingFailedException {
		final VDMRMappingSegment segment = VMappingsegmentFactory.eINSTANCE.createDMRMappingSegment();
		segment.setPropertyName("eClassToA"); //$NON-NLS-1$
		final EClass mappedEClass = EcoreFactory.eINSTANCE.createEClass();
		segment.setMappedClass(mappedEClass);
		final EClass eClass = TestPackage.eINSTANCE.getC();
		converter.convertToListProperty(segment, eClass);
	}

}
