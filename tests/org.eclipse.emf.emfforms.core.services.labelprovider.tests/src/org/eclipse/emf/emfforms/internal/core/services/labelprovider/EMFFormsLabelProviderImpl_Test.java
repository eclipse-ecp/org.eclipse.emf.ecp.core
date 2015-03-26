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
package org.eclipse.emf.emfforms.internal.core.services.labelprovider;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.emfforms.spi.core.services.emfspecificservice.EMFSpecificService;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link EMFFormsLabelProviderImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsLabelProviderImpl_Test {

	private EMFFormsLabelProviderImpl labelProvider;
	private EMFSpecificService emfSpecificService;
	private IItemPropertyDescriptor itemPropertyDescriptor;
	private IValueProperty valueProperty;
	private EObjectObservableValue observableValue;
	private EMFFormsDatabinding databindingService;
	private DefaultRealm defaultRealm;

	/**
	 * Set up that is executed before every test case.
	 * Registers a databinding and an emf specific service.
	 * Mocks various objects for the tests.
	 */
	@Before
	public void setUp() {
		defaultRealm = new DefaultRealm();
		labelProvider = new EMFFormsLabelProviderImpl();

		databindingService = mock(EMFFormsDatabinding.class);
		labelProvider.setEMFFormsDatabinding(databindingService);

		valueProperty = mock(IValueProperty.class);
		observableValue = mock(EObjectObservableValue.class);
		when(observableValue.getRealm()).thenReturn(Realm.getDefault());

		emfSpecificService = mock(EMFSpecificService.class);
		labelProvider.setEMFSpecificService(emfSpecificService);

		itemPropertyDescriptor = mock(IItemPropertyDescriptor.class);
		when(emfSpecificService.getIItemPropertyDescriptor(any(EObject.class), any(EStructuralFeature.class)))
			.thenReturn(itemPropertyDescriptor);
	}

	/**
	 * Clean up the realm.
	 */
	@After
	public void tearDown() {
		defaultRealm.dispose();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException should not happen, just needs to be thrown because the databinding service
	 *             defines the throw in its interface.
	 */
	@Test
	public void testGetDisplayNameEClass() throws DatabindingFailedException {
		final String expectedResult = "expected"; //$NON-NLS-1$
		final EStructuralFeature structuralFeature = mock(EStructuralFeature.class);
		final VDomainModelReference domainModelReference = mock(VDomainModelReference.class);
		final EClass rootEClass = mock(EClass.class);

		when(structuralFeature.getEContainingClass()).thenReturn(TestPackage.eINSTANCE.getD());
		when(itemPropertyDescriptor.getDisplayName(any(Object.class))).thenReturn(expectedResult);
		when(valueProperty.getValueType()).thenReturn(structuralFeature);
		when(databindingService.getValueProperty(domainModelReference, rootEClass)).thenReturn(valueProperty);

		final String result = labelProvider.getDisplayName(domainModelReference, rootEClass);

		verify(databindingService).getValueProperty(domainModelReference, rootEClass);
		verify(itemPropertyDescriptor).getDisplayName(any(D.class));
		assertEquals(expectedResult, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDisplayNameEClassDMRNull() {
		labelProvider.getDisplayName(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDisplayNameEClassEClassNull() {
		labelProvider.getDisplayName(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException should not happen, just needs to be thrown because the databinding service
	 *             defines the throw in its interface.
	 */
	@Test
	public void testGetDisplayNameEObject() throws DatabindingFailedException {
		final String expectedResult = "expected"; //$NON-NLS-1$
		final EObject eObject = mock(EObject.class);
		final EObject value = mock(EObject.class);
		final EStructuralFeature structuralFeature = mock(EStructuralFeature.class);
		final VDomainModelReference domainModelReference = mock(VDomainModelReference.class);

		when(itemPropertyDescriptor.getDisplayName(value)).thenReturn(expectedResult);
		when(observableValue.getValueType()).thenReturn(structuralFeature);
		when(observableValue.getObserved()).thenReturn(value);
		when(databindingService.getObservableValue(domainModelReference, eObject)).thenReturn(observableValue);
		final String result = labelProvider.getDisplayName(domainModelReference, eObject);

		verify(databindingService).getObservableValue(domainModelReference, eObject);
		verify(itemPropertyDescriptor).getDisplayName(value);
		assertEquals(expectedResult, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDisplayNameEObjectReferenceNull() {
		labelProvider.getDisplayName(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDisplayNameEObjectObjectNull() {
		labelProvider.getDisplayName(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDisplayNameEObjectBothNull() {
		labelProvider.getDisplayName(null, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 *
	 * @throws DatabindingFailedException should not happen, just needs to be thrown because the databinding service
	 *             defines the throw in its interface.
	 */
	@Test
	public void testGetDescriptionEClass() throws DatabindingFailedException {
		final String expectedResult = "expected"; //$NON-NLS-1$
		final EStructuralFeature structuralFeature = mock(EStructuralFeature.class);
		final VDomainModelReference domainModelReference = mock(VDomainModelReference.class);
		final EClass rootEClass = mock(EClass.class);

		when(structuralFeature.getEContainingClass()).thenReturn(TestPackage.eINSTANCE.getD());
		when(itemPropertyDescriptor.getDescription(any(Object.class))).thenReturn(expectedResult);
		when(valueProperty.getValueType()).thenReturn(structuralFeature);
		when(databindingService.getValueProperty(domainModelReference, rootEClass)).thenReturn(valueProperty);

		final String result = labelProvider.getDescription(domainModelReference, rootEClass);

		verify(databindingService).getValueProperty(domainModelReference, rootEClass);
		verify(itemPropertyDescriptor).getDescription(any(D.class));
		assertEquals(expectedResult, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDescriptionEClassDMRNull() {
		labelProvider.getDescription(null, mock(EClass.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EClass)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDescriptionEClassEClassNull() {
		labelProvider.getDescription(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException should not happen, just needs to be thrown because the databinding service
	 *             defines the throw in its interface.
	 */
	@Test
	public void testGetDescriptionEObject() throws DatabindingFailedException {
		final String expectedResult = "expected"; //$NON-NLS-1$
		final EObject eObject = mock(EObject.class);
		final EObject value = mock(EObject.class);
		final EStructuralFeature structuralFeature = mock(EStructuralFeature.class);
		final VDomainModelReference domainModelReference = mock(VDomainModelReference.class);

		when(itemPropertyDescriptor.getDescription(value)).thenReturn(expectedResult);
		when(observableValue.getValueType()).thenReturn(structuralFeature);
		when(observableValue.getObserved()).thenReturn(value);
		when(databindingService.getObservableValue(domainModelReference, eObject)).thenReturn(observableValue);
		final String result = labelProvider.getDescription(domainModelReference, eObject);

		verify(databindingService).getObservableValue(domainModelReference, eObject);
		verify(itemPropertyDescriptor).getDescription(value);
		assertEquals(expectedResult, result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDescriptionEObjectReferenceNull() {
		labelProvider.getDescription(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDescriptionEObjectObjectNull() {
		labelProvider.getDescription(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.emfforms.internal.core.services.labelprovider.EMFFormsLabelProviderImpl#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDescriptionEObjectBothNull() {
		labelProvider.getDescription(null, null);
	}
}
