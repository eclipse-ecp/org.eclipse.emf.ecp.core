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
package org.eclipse.emfforms.internal.core.services.databinding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Optional;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit test tests the correct functionality of {@link EMFFormsDatabindingImpl}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings({ "restriction", "rawtypes" })
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final EObject eObject = mock(EObject.class);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final IObservableValue expectedObservableValue = mock(IObservableValue.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.convertToValueProperty(reference, eObject)).thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableValue);

		databindingService.addDomainModelReferenceConverter(converter1);
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
		final VDomainModelReference modelReference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(modelReference.getSegments()).thenReturn(segments);
		final EClass eClass = mock(EClass.class);
		when(eClass.getName()).thenReturn("test"); //$NON-NLS-1$
		when(modelReference.eClass()).thenReturn(eClass);
		databindingService.getValueProperty(modelReference, mock(EObject.class));
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(reference)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(
			mock(IEMFValueProperty.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, valueProperty);
	}

	public void testGetValueProperty_eClass_editingDomain() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final EditingDomain editingDomain = mock(EditingDomain.class);
		final EClass rootEClass = mock(EClass.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.convertToValueProperty(reference, rootEClass, editingDomain))
			.thenReturn(expectedResultProperty);
		when(converter2.isApplicable(reference)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(reference, rootEClass, editingDomain)).thenReturn(
			mock(IEMFValueProperty.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, rootEClass, editingDomain);
		assertEquals(expectedResultProperty, valueProperty);
		verify(converter1).convertToValueProperty(reference, rootEClass, editingDomain);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);

		when(converter1.isApplicable(reference)).thenReturn(5d);
		when(converter1.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(reference)).thenReturn(1d);
		when(converter2.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(
			mock(EMFValuePropertyDecorator.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, valueProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DomainModelReferenceConverterEMF}s, that
	 * are
	 * registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testGetValuePropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);

		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter3 = mock(DomainModelReferenceConverterEMF.class);

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		databindingService.addDomainModelReferenceConverter(converter3);

		databindingService.getValueProperty(reference, mock(EObject.class));

		verify(converter1).isApplicable(reference);
		verify(converter2).isApplicable(reference);
		verify(converter3).isApplicable(reference);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);

		final EObject eObject = mock(EObject.class);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);
		final IObservableList expectedObservableList = mock(IObservableList.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.convertToListProperty(reference, eObject)).thenReturn(expectedResultProperty);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableList);

		databindingService.addDomainModelReferenceConverter(converter1);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.convertToListProperty(same(reference), any(EObject.class))).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(reference)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToListProperty(same(reference), any(EObject.class))).thenReturn(
			mock(IEMFListProperty.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);

		when(converter1.isApplicable(reference)).thenReturn(5d);
		when(converter1.convertToListProperty(same(reference), any(EObject.class))).thenReturn(expectedResultProperty);
		when(converter2.isApplicable(reference)).thenReturn(1d);
		when(converter2.convertToListProperty(same(reference), any(EObject.class))).thenReturn(
			mock(EMFListPropertyDecorator.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
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
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);

		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter3 = mock(DomainModelReferenceConverterEMF.class);

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		databindingService.addDomainModelReferenceConverter(converter3);

		databindingService.getListProperty(reference, mock(EObject.class));

		verify(converter1).isApplicable(reference);
		verify(converter2).isApplicable(reference);
		verify(converter3).isApplicable(reference);
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
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#removeDomainModelReferenceConverter(DomainModelReferenceConverterEMF)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testRemoveDomainModelReferenceConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		when(reference.eClass()).thenReturn(VViewPackage.eINSTANCE.getFeaturePathDomainModelReference());
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);

		when(converter1.isApplicable(reference)).thenReturn(5d);

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.removeDomainModelReferenceConverter(converter1);
		databindingService.getValueProperty(reference, mock(EObject.class));
	}

	@Test(expected = DatabindingFailedException.class)
	public void testGetSettingNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference modelReference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(modelReference.getSegments()).thenReturn(segments);
		final EClass eClass = mock(EClass.class);
		when(eClass.getName()).thenReturn("test"); //$NON-NLS-1$
		when(modelReference.eClass()).thenReturn(eClass);
		databindingService.getSetting(modelReference, mock(EObject.class));
	}

	@Test
	public void testGetSettingPropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final Setting expectedSetting = mock(Setting.class);

		when(converter1.isApplicable(reference)).thenReturn(0d);
		when(converter1.getSetting(same(reference), any(EObject.class))).thenReturn(expectedSetting);
		when(converter2.isApplicable(reference)).thenReturn(DomainModelReferenceConverter.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(
			mock(IEMFValueProperty.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		final Setting setting = databindingService.getSetting(reference, mock(EObject.class));
		assertEquals(expectedSetting, setting);
	}

	@Test
	public void testGetSettingPropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);
		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final Setting expectedSetting = mock(Setting.class);

		when(converter1.isApplicable(reference)).thenReturn(5d);
		when(converter1.getSetting(same(reference), any(EObject.class))).thenReturn(expectedSetting);
		when(converter2.isApplicable(reference)).thenReturn(1d);
		when(converter2.convertToValueProperty(same(reference), any(EObject.class))).thenReturn(
			mock(EMFValuePropertyDecorator.class));

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		final Setting setting = databindingService.getSetting(reference, mock(EObject.class));
		assertEquals(expectedSetting, setting);
	}

	@Test
	public void testGetSettingPropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = mock(VDomainModelReference.class);
		final EList<VDomainModelReferenceSegment> segments = new BasicEList<>();
		when(reference.getSegments()).thenReturn(segments);

		final DomainModelReferenceConverterEMF converter1 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter2 = mock(DomainModelReferenceConverterEMF.class);
		final DomainModelReferenceConverterEMF converter3 = mock(DomainModelReferenceConverterEMF.class);

		databindingService.addDomainModelReferenceConverter(converter1);
		databindingService.addDomainModelReferenceConverter(converter2);
		databindingService.addDomainModelReferenceConverter(converter3);

		databindingService.getSetting(reference, mock(EObject.class));

		verify(converter1).isApplicable(reference);
		verify(converter2).isApplicable(reference);
		verify(converter3).isApplicable(reference);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSettingPropertyNull() throws DatabindingFailedException {
		databindingService.getSetting(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void settings_testGetObservableValue() throws DatabindingFailedException {
		databindingService = spy(new EMFFormsDatabindingImpl());
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final EObject eObject = EcoreFactory.eINSTANCE.createEObject();
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final SegmentConverterValueResultEMF converterResult = mock(SegmentConverterValueResultEMF.class);
		when(converterResult.getValueProperty()).thenReturn(expectedResultProperty);
		when(converterResult.getNextEClass()).thenReturn(Optional.empty());
		final IObservableValue expectedObservableValue = mock(IObservableValue.class);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableValue);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		final IObservableValue resultObservableValue = databindingService.getObservableValue(reference, eObject);

		verify(databindingService).getValueProperty(reference, eObject);
		verify(expectedResultProperty).observe(eObject);
		assertEquals(expectedObservableValue, resultObservableValue);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void settings_testGetValuePropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		databindingService.getValueProperty(reference, TestFactory.eINSTANCE.createA());
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
	public void settings_testGetValuePropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final SegmentConverterValueResultEMF converterResult = mock(SegmentConverterValueResultEMF.class);
		when(converterResult.getValueProperty()).thenReturn(expectedResultProperty);

		when(converter1.isApplicable(segment)).thenReturn(0d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE);
		when(converter2.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(SegmentConverterValueResultEMF.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference,
			TestFactory.eINSTANCE.createA());
		assertEquals(expectedResultProperty, valueProperty);
	}

	@Test
	public void settings_testGetValueProperty_multipleSegments() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment1);
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment2);

		final DomainModelReferenceSegmentConverterEMF converter = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty resultProperty1 = mock(IEMFValueProperty.class, withSettings().name("resultProperty1")); //$NON-NLS-1$
		final IEMFValueProperty resultProperty2 = mock(IEMFValueProperty.class, withSettings().name("resultProperty2")); //$NON-NLS-1$
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class,
			withSettings().name("expectedResultProperty")); //$NON-NLS-1$
		when(resultProperty1.value(resultProperty2)).thenReturn(expectedResultProperty);

		final SegmentConverterValueResultEMF converterResult1 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult1.getValueProperty()).thenReturn(resultProperty1);
		when(converterResult1.getNextEClass()).thenReturn(Optional.of(TestPackage.Literals.D));

		final SegmentConverterValueResultEMF converterResult2 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult2.getValueProperty()).thenReturn(resultProperty2);
		when(converterResult2.getNextEClass()).thenReturn(Optional.empty());

		when(converter.isApplicable(segment1)).thenReturn(1d);
		when(converter.convertToValueProperty(same(segment1), eq(TestPackage.Literals.C), any(EditingDomain.class)))
			.thenReturn(converterResult1);
		when(converter.convertToValueProperty(same(segment2), eq(TestPackage.Literals.D), any(EditingDomain.class)))
			.thenReturn(converterResult2);

		databindingService.addDomainModelReferenceSegmentConverter(converter);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference,
			TestFactory.eINSTANCE.createC());
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
	public void settings_testGetValuePropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class);
		final SegmentConverterValueResultEMF converterResult = mock(SegmentConverterValueResultEMF.class);
		when(converterResult.getValueProperty()).thenReturn(expectedResultProperty);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(converter2.isApplicable(segment)).thenReturn(1d);
		when(converter2.convertToValueProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(SegmentConverterValueResultEMF.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference,
			TestFactory.eINSTANCE.createA());
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
	public void settings_testGetValuePropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
			.thenReturn(mock(SegmentConverterValueResultEMF.class));
		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		databindingService.addDomainModelReferenceSegmentConverter(converter3);

		databindingService.getValueProperty(reference, TestFactory.eINSTANCE.createA());

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
	}

	@Test
	public void settings_testGetValueProperty_eClass() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment1);
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment2);

		final DomainModelReferenceSegmentConverterEMF converter = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty resultProperty1 = mock(IEMFValueProperty.class, withSettings().name("resultProperty1")); //$NON-NLS-1$
		final IEMFValueProperty resultProperty2 = mock(IEMFValueProperty.class, withSettings().name("resultProperty2")); //$NON-NLS-1$
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class,
			withSettings().name("expectedResultProperty")); //$NON-NLS-1$
		when(resultProperty1.value(resultProperty2)).thenReturn(expectedResultProperty);

		final SegmentConverterValueResultEMF converterResult1 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult1.getValueProperty()).thenReturn(resultProperty1);
		when(converterResult1.getNextEClass()).thenReturn(Optional.of(TestPackage.Literals.D));

		final SegmentConverterValueResultEMF converterResult2 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult2.getValueProperty()).thenReturn(resultProperty2);
		when(converterResult2.getNextEClass()).thenReturn(Optional.empty());

		when(converter.isApplicable(segment1)).thenReturn(1d);
		when(converter.convertToValueProperty(same(segment1), eq(TestPackage.Literals.C), any(EditingDomain.class)))
			.thenReturn(converterResult1);
		when(converter.convertToValueProperty(same(segment2), eq(TestPackage.Literals.D), any(EditingDomain.class)))
			.thenReturn(converterResult2);

		databindingService.addDomainModelReferenceSegmentConverter(converter);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference,
			TestPackage.Literals.C);
		assertEquals(expectedResultProperty, valueProperty);
	}

	@Test
	public void settings_testGetValueProperty_eClass_editingDomain() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment1);
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment2);

		final EditingDomain editingDomain = mock(EditingDomain.class);
		final DomainModelReferenceSegmentConverterEMF converter = mock(DomainModelReferenceSegmentConverterEMF.class);
		final IEMFValueProperty resultProperty1 = mock(IEMFValueProperty.class, withSettings().name("resultProperty1")); //$NON-NLS-1$
		final IEMFValueProperty resultProperty2 = mock(IEMFValueProperty.class, withSettings().name("resultProperty2")); //$NON-NLS-1$
		final IEMFValueProperty expectedResultProperty = mock(IEMFValueProperty.class,
			withSettings().name("expectedResultProperty")); //$NON-NLS-1$
		when(resultProperty1.value(resultProperty2)).thenReturn(expectedResultProperty);

		final SegmentConverterValueResultEMF converterResult1 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult1.getValueProperty()).thenReturn(resultProperty1);
		when(converterResult1.getNextEClass()).thenReturn(Optional.of(TestPackage.Literals.D));

		final SegmentConverterValueResultEMF converterResult2 = mock(SegmentConverterValueResultEMF.class);
		when(converterResult2.getValueProperty()).thenReturn(resultProperty2);
		when(converterResult2.getNextEClass()).thenReturn(Optional.empty());

		when(converter.isApplicable(segment1)).thenReturn(1d);
		when(converter.convertToValueProperty(same(segment1), eq(TestPackage.Literals.C), any(EditingDomain.class)))
			.thenReturn(converterResult1);
		when(converter.convertToValueProperty(same(segment2), eq(TestPackage.Literals.D), any(EditingDomain.class)))
			.thenReturn(converterResult2);

		databindingService.addDomainModelReferenceSegmentConverter(converter);
		final IValueProperty valueProperty = databindingService.getValueProperty(reference,
			TestPackage.Literals.C, editingDomain);
		assertEquals(expectedResultProperty, valueProperty);
		verify(converter).convertToValueProperty(segment1, TestPackage.Literals.C, editingDomain);
		verify(converter).convertToValueProperty(segment2, TestPackage.Literals.D, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getObservableList(VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void settings_testGetObservableList() throws DatabindingFailedException {
		databindingService = spy(new EMFFormsDatabindingImpl());
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final EObject eObject = mock(EObject.class);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final SegmentConverterListResultEMF converterResult = mock(SegmentConverterListResultEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);
		when(converterResult.getListProperty()).thenReturn(expectedResultProperty);
		final IObservableList expectedObservableList = mock(IObservableList.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(expectedResultProperty.observe(eObject)).thenReturn(expectedObservableList);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		final IObservableList resultObservableList = databindingService.getObservableList(reference, eObject);

		verify(databindingService).getListProperty(reference, eObject);
		verify(expectedResultProperty).observe(eObject);
		assertEquals(expectedObservableList, resultObservableList);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void settings_testGetListPropertyNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
	public void settings_testGetListPropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final SegmentConverterListResultEMF converterResult = mock(SegmentConverterListResultEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);
		when(converterResult.getListProperty()).thenReturn(expectedResultProperty);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(converter2.isApplicable(segment)).thenReturn(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE);
		when(converter2.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(SegmentConverterListResultEMF.class));

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
	public void settings_testGetListPropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);

		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final DomainModelReferenceSegmentConverterEMF converter2 = mock(DomainModelReferenceSegmentConverterEMF.class);
		final SegmentConverterListResultEMF converterResult = mock(SegmentConverterListResultEMF.class);
		final IEMFListProperty expectedResultProperty = mock(IEMFListProperty.class);
		when(converterResult.getListProperty()).thenReturn(expectedResultProperty);

		when(converter1.isApplicable(segment)).thenReturn(5d);
		when(converter1.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(converterResult);
		when(converter2.isApplicable(segment)).thenReturn(1d);
		when(converter2.convertToListProperty(same(segment), any(EClass.class), any(EditingDomain.class)))
			.thenReturn(mock(SegmentConverterListResultEMF.class));

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.addDomainModelReferenceSegmentConverter(converter2);
		final IListProperty listProperty = databindingService.getListProperty(reference, mock(EObject.class));
		assertEquals(expectedResultProperty, listProperty);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#getValueProperty(VDomainModelReference,EObject)}
	 * .
	 * Tests whether the {@link EMFFormsDatabindingImpl} considers all {@link DomainModelReferenceSegmentConverterEMF}s,
	 * that are registered to it, for its conversions.
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void settings_testGetListPropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
		when(converter2.convertToListProperty(any(VDomainModelReferenceSegment.class), any(EClass.class),
			any(EditingDomain.class))).thenReturn(mock(SegmentConverterListResultEMF.class));
		when(converter3.isApplicable(segment)).thenReturn(2d);

		databindingService.getListProperty(reference, mock(EObject.class));

		verify(converter1).isApplicable(segment);
		verify(converter2).isApplicable(segment);
		verify(converter3).isApplicable(segment);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl#removeDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test(expected = DatabindingFailedException.class)
	public void settings_testRemoveDomainModelReferenceSegmentConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		final DomainModelReferenceSegmentConverterEMF converter1 = mock(DomainModelReferenceSegmentConverterEMF.class);

		when(converter1.isApplicable(segment)).thenReturn(5d);

		databindingService.addDomainModelReferenceSegmentConverter(converter1);
		databindingService.removeDomainModelReferenceSegmentConverter(converter1);
		databindingService.getValueProperty(reference, TestFactory.eINSTANCE.createA());
	}

	@Test(expected = DatabindingFailedException.class)
	public void settings_testGetSettingNoApplicableConverter() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		reference.getSegments().add(segment);
		databindingService.getSetting(reference, mock(EObject.class));
	}

	@Test
	public void settings_testGetSettingPropertyOneApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
	public void settings_testGetSettingPropertyTwoApplicable() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
	public void settings_testGetSettingPropertyAllConsidered() throws DatabindingFailedException {
		final VDomainModelReference reference = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
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
	public void settings_testGetSettingPropertyNull() throws DatabindingFailedException {
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
