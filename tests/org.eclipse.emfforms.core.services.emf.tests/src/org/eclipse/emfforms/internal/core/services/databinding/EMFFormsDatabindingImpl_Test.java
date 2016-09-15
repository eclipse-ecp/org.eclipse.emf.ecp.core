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
 * Lucas Koehler - adaption to segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFListPropertyDecorator;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test tests the correct functionality of {@link EMFFormsDatabindingImpl}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class EMFFormsDatabindingImpl_Test {

	private EMFFormsDatabindingImpl databindingService;
	private DefaultRealm realm;

	/**
	 * Set up that is executed before every test.
	 */
	@Before
	public void setUp() {
		realm = new DefaultRealm();
		databindingService = new EMFFormsDatabindingImpl();
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
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final EObject eObject = EcoreFactory.eINSTANCE.createEObject();
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final IObservableValue expectedObservableValue = mock(IObservableValue.class);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableValue);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		final IObservableValue resultObservableValue = databindingService.getObservableValue(reference, eObject);

		verify(databindingService).getValueProperty(reference, eObject);
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetValuePropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		databindingService.getValueProperty(reference, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the correct converter is used when one is applicable and one is not.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(IEMFValueProperty.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, valueProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the correct converter is used when there are two applicable ones with different priorities.
	 * Also tests whether the correct result is returned.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(1d);
		when(converter2.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(EMFValuePropertyDecorator.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, valueProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DomainModelReferenceSegmentConverterEMF}s,
	 * that
	 * are
	 * registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter3 = mock(DomainModelReferenceSegmentConverterEMF.class);

		when(converter1.isApplicable(segment)).thenReturn(1d);
		when(converter2.isApplicable(segment)).thenReturn(3d);
		when(converter3.isApplicable(segment)).thenReturn(2d);

		when(converter2.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(IEMFValueProperty.class));
		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		databindingService.addDomainModelReferenceSegmentConverter(converter3);

		databindingService.getValueProperty(reference, mock(EObject.class));

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetValuePropertyNull() throws DatabindingFailedException {
		databindingService.getValueProperty(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetObservableList() throws DatabindingFailedException {
		databindingService = spy(new EMFFormsDatabindingImpl());
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final EObject eObject = mock(EObject.class);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);
		final IObservableList expectedObservableList = mock(IObservableList.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableList);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		final IObservableList resultObservableList = databindingService.getObservableList(reference, eObject);

		verify(databindingService).getListProperty(reference, eObject);
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetListPropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		databindingService.getListProperty(reference, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the correct converter is used when one is applicable and one is not.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE);
		when(converter2.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(EMFListPropertyDecorator.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IListProperty listProperty = databindingService.getListProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, listProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the correct converter is used when there are two applicable ones with different priorities.
	 * Also tests whether the correct result is returned.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(expectedResultProperty);
		when(converter2.isApplicable(segment)).thenReturn(1d);
		when(converter2.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(EMFListPropertyDecorator.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IListProperty listProperty = databindingService.getListProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, listProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DomainModelReferenceConverter}s, that are
	 * registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetListPropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter3 = mock(DomainModelReferenceSegmentConverterEMF.class);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		databindingService.addDomainModelReferenceSegmentConverter(converter3);

		when(converter1.isApplicable(segment)).thenReturn(1d);
		when(converter2.isApplicable(segment)).thenReturn(3d);
		when(converter3.isApplicable(segment)).thenReturn(2d);

		databindingService.getListProperty(reference, mock(EObject.class));

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * <p>
	 * Tests whether the method returns the correct result for a <strong>null</strong> argument.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetListPropertyNull() throws DatabindingFailedException {
		databindingService.getListProperty(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#removeDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testRemoveDomainModelReferenceSegmentConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.removeDomainModelReferenceSegmentConverter(converter1);
		databindingService.getValueProperty(reference, mock(EObject.class));
	}

	@Test(expected = DatabindingFailedException.class)
	public void testGetSettingNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		databindingService.getSetting(reference, mock(EObject.class));
	}

	@Test
	public void testGetSettingPropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final Setting expectedSetting = mock(Setting.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.getSetting(same(segment), any(EObject.class))).thenReturn(expectedSetting);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE);
		when(converter2.getSetting(same(segment), any(EObject.class))).thenReturn(
			mock(Setting.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final Setting setting = databindingService.getSetting(reference, mock(EObject.class));
		assertEquals(expectedSetting, setting);
	}

	@Test
	public void testGetSettingPropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final Setting expectedSetting = mock(Setting.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.getSetting(same(segment), any(EObject.class))).thenReturn(expectedSetting);
		when(converter2.isApplicable(segment)).thenReturn(1d);
		when(converter2.getSetting(same(segment), any(EObject.class))).thenReturn(
			mock(Setting.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final Setting setting = databindingService.getSetting(reference, mock(EObject.class));
		assertEquals(expectedSetting, setting);
	}

	@Test
	public void testGetSettingPropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter3 = mock(DomainModelReferenceSegmentConverterEMF.class);

		when(converter1.isApplicable(segment)).thenReturn(1d);
		when(converter2.isApplicable(segment)).thenReturn(3d);
		when(converter3.isApplicable(segment)).thenReturn(2d);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		databindingService.addDomainModelReferenceSegmentConverter(converter3);

		databindingService.getSetting(reference, mock(EObject.class));

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSettingPropertyNull() throws DatabindingFailedException {
		databindingService.getSetting(null, mock(EObject.class));
	}

	@Test
	public void testResolveSegment() throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EObject eObject = EcoreFactory.eINSTANCE.createEObject();
		final Setting expectedSetting = mock(Setting.class);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter3 = mock(DomainModelReferenceSegmentConverterEMF.class);

		when(converter1.isApplicable(segment)).thenReturn(1d);
		when(converter2.isApplicable(segment)).thenReturn(3d);
		when(converter3.isApplicable(segment)).thenReturn(2d);

		when(converter2.getSetting(segment, eObject)).thenReturn(expectedSetting);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		databindingService.addDomainModelReferenceSegmentConverter(converter3);

		final Setting result = databindingService.resolveSegment(segment, eObject);

		assertEquals(expectedSetting, result);
		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
		verify(converter2).getSetting(segment, eObject);
	}
}
