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
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
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
 * JUnit tests for {@link MappingDomainModelReferenceSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class MappingDomainModelReferenceSegmentConverter_Test {

	private MappingDomainModelReferenceSegmentConverter converter;
	private DefaultRealm realm;

	/**
	 * Set up a new {@link MappingDomainModelReferenceSegmentConverter} before every unit test.
	 */
	@Before
	public void setUp() {
		converter = new MappingDomainModelReferenceSegmentConverter();
		realm = new DefaultRealm();
	}

	/**
	 * Disposes the default realm after every unit test.
	 */
	@After
	public void tearDown() {
		realm.dispose();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final double result = converter.isApplicable(mappingSegment);
		assertEquals(10d, result, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsApplicableNull() {
		converter.isApplicable(null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final double result = converter.isApplicable(segment);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, result, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testConvertToValuePropertyReference() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getC_EClassToA();

		final A value = TestFactory.eINSTANCE.createA();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final C domainObject = TestFactory.eINSTANCE.createC();
		domainObject.getEClassToA().put(key, value);

		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		final IEMFValueProperty valueProperty = converter.convertToValueProperty(mappingSegment, domainObject.eClass(),
			mock(EditingDomain.class));

		final Object returnedValue = valueProperty.getValue(domainObject);
		assertEquals(value, returnedValue);
		assertTrue(valueProperty instanceof EMFValuePropertyDecorator);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testConvertToValuePropertyAttribute() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getC_EClassToString();

		final String value = "TestString"; //$NON-NLS-1$
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final C domainObject = TestFactory.eINSTANCE.createC();
		domainObject.getEClassToString().put(key, value);

		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		final IEMFValueProperty valueProperty = converter.convertToValueProperty(mappingSegment, domainObject.eClass(),
			mock(EditingDomain.class));

		final Object returnedValue = valueProperty.getValue(domainObject);
		assertEquals(value, returnedValue);
		assertTrue(valueProperty instanceof EMFValuePropertyDecorator);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = IllegalMapTypeException.class)
	public void testConvertToValuePropertyNoMap() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getB_CList();

		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		converter.convertToValueProperty(mappingSegment, TestPackage.eINSTANCE.getB(), mock(EditingDomain.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testConvertToListProperty() throws DatabindingFailedException {
		converter.convertToListProperty(mock(VMappingDomainModelReferenceSegment.class), mock(EClass.class),
			mock(EditingDomain.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetSettingValueReference() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getC_EClassToA();

		final A value = TestFactory.eINSTANCE.createA();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final C domainObject = TestFactory.eINSTANCE.createC();

		domainObject.getEClassToA().put(key, value);
		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		final Setting setting = converter.getSetting(mappingSegment, domainObject);

		assertEquals(domainModelEFeature, setting.getEStructuralFeature());
		assertEquals(domainObject, setting.getEObject());
		assertEquals(value, setting.get(true));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetSettingValueAttribute() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getC_EClassToString();

		final String value = "TestValue"; //$NON-NLS-1$
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final C domainObject = TestFactory.eINSTANCE.createC();

		domainObject.getEClassToString().put(key, value);
		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		final Setting setting = converter.getSetting(mappingSegment, domainObject);

		assertEquals(domainModelEFeature, setting.getEStructuralFeature());
		assertEquals(domainObject, setting.getEObject());
		assertEquals(value, setting.get(true));

	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = IllegalMapTypeException.class)
	public void testGetSettingNoMap() throws DatabindingFailedException {
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		final EReference domainModelEFeature = TestPackage.eINSTANCE.getB_CList();

		final B domainObject = TestFactory.eINSTANCE.createB();

		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		mappingSegment.setMappedClass(key);
		mappingSegment.setDomainModelFeature(domainModelEFeature.getName());

		converter.getSetting(mappingSegment, domainObject);
	}
}
