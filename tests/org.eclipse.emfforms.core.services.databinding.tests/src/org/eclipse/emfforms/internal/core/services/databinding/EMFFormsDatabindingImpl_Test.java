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
package org.eclipse.emfforms.internal.core.services.databinding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test tests the correct functionality of {@link EMFFormsDatabindingImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDatabindingImpl_Test {

	private EMFFormsDatabindingImpl databindingService;
	private DefaultRealm realm;

	/**
	 * Set up that is executed before every test.
	 */
	@Before
	public void setUp() {
		databindingService = new EMFFormsDatabindingImpl();
		realm = new DefaultRealm();
	}

	@After
	public void tearDown() {
		realm.dispose();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetObservableValue() throws DatabindingFailedException {
		databindingService = spy(new EMFFormsDatabindingImpl());
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("b"); //$NON-NLS-1$
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);
		final EObject eObject = TestFactory.eINSTANCE.createA();
		final EClass eClass = eObject.eClass();
		final DMRSegmentConverter converter = mock(DMRSegmentConverter.class);
		final IValueProperty expectedResultProperty = mock(IValueProperty.class);
		final IObservableValue expectedObservableValue = mock(IObservableValue.class);

		when(converter.isApplicable(segment)).thenReturn(1d);
		when(converter.convertToValueProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableValue);

		databindingService.addDMRSegmentConverter(converter);
		final IObservableValue resultObservableValue = databindingService.getObservableValue(reference, eObject);

		verify(databindingService).getValueProperty(reference, eClass);
		verify(expectedResultProperty).observe(eObject);
		assertEquals(expectedObservableValue, resultObservableValue);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for both arguments being <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableValueNullBoth() throws DatabindingFailedException {
		databindingService.getObservableValue(null, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for the VDomainModelReference argument being
	 * <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableValueNullDomainModelReference() throws DatabindingFailedException {
		databindingService.getObservableValue(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for the EObject argument being <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableValueNullObject() throws DatabindingFailedException {
		databindingService.getObservableValue(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	public void testGetValueProperty() throws DatabindingFailedException {
		final VDMRSegment segment1 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("b"); //$NON-NLS-1$
		final VDMRSegment segment2 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("c"); //$NON-NLS-1$
		final EClass rootEClass = TestPackage.eINSTANCE.getA();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment1);
		reference.getSegments().add(segment2);

		final IValueProperty valueProperty1 = mock(IValueProperty.class);
		final IValueProperty valueProperty2 = mock(IValueProperty.class);
		final IValueProperty expectedResultProperty = mock(IValueProperty.class);

		when(valueProperty1.value(valueProperty2)).thenReturn(expectedResultProperty);

		final DMRSegmentConverter converter = mock(DMRSegmentConverter.class);
		when(converter.isApplicable(any(VDMRSegment.class))).thenReturn(1d);
		when(converter.convertToValueProperty(segment1, rootEClass)).thenReturn(valueProperty1);
		when(converter.convertToValueProperty(segment2, TestPackage.eINSTANCE.getC())).thenReturn(valueProperty2);

		databindingService.addDMRSegmentConverter(converter);

		final IValueProperty result = databindingService.getValueProperty(reference, rootEClass);
		assertEquals(expectedResultProperty, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetValuePropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		databindingService.getValueProperty(reference, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the correct converter is used when one is applicable and one is not.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyOneApplicable() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);
		final IValueProperty expectedResultProperty = mock(IValueProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToValueProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(segment, eClass)).thenReturn(mock(IValueProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, eClass);
		assertEquals(expectedResultProperty, valueProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the correct converter is used when there are two applicable ones with different priorities.
	 * Also tests whether the correct result is returned.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyTwoApplicable() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);
		final IValueProperty expectedResultProperty = mock(IValueProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(10d);
		when(converter1.convertToValueProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(0d);
		when(converter2.convertToValueProperty(segment, eClass)).thenReturn(mock(IValueProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, eClass);
		assertEquals(expectedResultProperty, valueProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DMRSegmentConverter}s, that are
	 * registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyAllConsidered() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);

		when(converter1.isApplicable(segment)).thenReturn(10d);
		when(converter1.convertToValueProperty(segment, eClass)).thenReturn(mock(IValueProperty.class));
		when(converter2.isApplicable(segment)).thenReturn(0d);
		when(converter2.convertToValueProperty(segment, eClass)).thenReturn(mock(IValueProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);

		databindingService.getValueProperty(reference, eClass);

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetValuePropertyNullReference() throws DatabindingFailedException {
		databindingService.getValueProperty(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetValuePropertyNullEClass() throws DatabindingFailedException {
		databindingService.getValueProperty(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetObservableList() throws DatabindingFailedException {
		databindingService = spy(new EMFFormsDatabindingImpl());
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName("cList"); //$NON-NLS-1$
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);
		final EObject eObject = TestFactory.eINSTANCE.createB();
		final EClass eClass = eObject.eClass();
		final DMRSegmentConverter converter = mock(DMRSegmentConverter.class);
		final IListProperty expectedResultProperty = mock(IListProperty.class);
		final IObservableList expectedObservableList = mock(IObservableList.class);

		when(converter.isApplicable(segment)).thenReturn(1d);
		when(converter.convertToListProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableList);

		databindingService.addDMRSegmentConverter(converter);
		final IObservableList resultObservableList = databindingService.getObservableList(reference, eObject);

		verify(databindingService).getListProperty(reference, eClass);
		verify(expectedResultProperty).observe(eObject);
		assertEquals(expectedObservableList, resultObservableList);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for both arguments being <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableListNullBoth() throws DatabindingFailedException {
		databindingService.getObservableList(null, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for the VDomainModelReference argument being
	 * <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableListNullDomainModelReference() throws DatabindingFailedException {
		databindingService.getObservableList(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for the EObject argument being <strong>null</strong>.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetObservableListNullObject() throws DatabindingFailedException {
		databindingService.getObservableList(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetListProperty() throws DatabindingFailedException {
		final VDMRSegment segment1 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("b"); //$NON-NLS-1$
		final VDMRSegment segment2 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("cList"); //$NON-NLS-1$
		final EClass rootEClass = TestPackage.eINSTANCE.getA();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment1);
		reference.getSegments().add(segment2);

		final IValueProperty valueProperty1 = mock(IValueProperty.class);
		final IListProperty listProperty2 = mock(IListProperty.class);
		final IListProperty expectedResultProperty = mock(IListProperty.class);

		when(valueProperty1.list(listProperty2)).thenReturn(expectedResultProperty);

		final DMRSegmentConverter converter = mock(DMRSegmentConverter.class);
		when(converter.isApplicable(any(VDMRSegment.class))).thenReturn(1d);
		when(converter.convertToValueProperty(segment1, rootEClass)).thenReturn(valueProperty1);
		when(converter.convertToListProperty(segment2, TestPackage.eINSTANCE.getC())).thenReturn(listProperty2);

		databindingService.addDMRSegmentConverter(converter);

		final IListProperty result = databindingService.getListProperty(reference, rootEClass);
		assertEquals(expectedResultProperty, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetListPropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		databindingService.getListProperty(reference, eClass);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the correct converter is used when one is applicable and one is not.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyOneApplicable() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);
		final IListProperty expectedResultProperty = mock(IListProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToListProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToListProperty(segment, eClass)).thenReturn(mock(IListProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);
		final IListProperty listProperty = databindingService.getListProperty(reference, eClass);
		assertEquals(expectedResultProperty, listProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the correct converter is used when there are two applicable ones with different priorities.
	 * Also tests whether the correct result is returned.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyTwoApplicable() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);
		final IListProperty expectedResultProperty = mock(IListProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(10d);
		when(converter1.convertToListProperty(segment, eClass)).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(0d);
		when(converter2.convertToListProperty(segment, eClass)).thenReturn(mock(IListProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);
		final IListProperty listProperty = databindingService.getListProperty(reference, eClass);
		assertEquals(expectedResultProperty, listProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DMRSegmentConverter}s, that are
	 * registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyAllConsidered() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);

		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);
		final DMRSegmentConverter converter2 = mock(DMRSegmentConverter.class);

		when(converter1.isApplicable(segment)).thenReturn(10d);
		when(converter1.convertToListProperty(segment, eClass)).thenReturn(mock(IListProperty.class));
		when(converter2.isApplicable(segment)).thenReturn(0d);
		when(converter2.convertToListProperty(segment, eClass)).thenReturn(mock(IListProperty.class));

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.addDMRSegmentConverter(converter2);

		databindingService.getListProperty(reference, eClass);

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetListPropertyNullReference() throws DatabindingFailedException {
		databindingService.getListProperty(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetListPropertyNullEClass() throws DatabindingFailedException {
		databindingService.getListProperty(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#removeDMRSegmentConverter(org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testRemoveDomainModelReferenceConverter() throws DatabindingFailedException {
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		reference.getSegments().add(segment);
		final DMRSegmentConverter converter1 = mock(DMRSegmentConverter.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);

		databindingService.addDMRSegmentConverter(converter1);
		databindingService.removeDMRSegmentConverter(converter1);
		databindingService.getValueProperty(reference, eClass);
	}
}
