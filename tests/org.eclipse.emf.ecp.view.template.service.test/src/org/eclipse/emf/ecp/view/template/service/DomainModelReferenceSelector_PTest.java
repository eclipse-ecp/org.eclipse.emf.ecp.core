/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferenceFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

@RunWith(DatabindingClassRunner.class)
public class DomainModelReferenceSelector_PTest {

	private VTDomainModelReferenceSelector domainModelReferenceSelector;
	private ServiceReference<EMFFormsDatabinding> databindingServiceReference;
	private EMFFormsDatabinding databinding;

	@Before
	public void setup() {
		domainModelReferenceSelector = VTDomainmodelreferenceFactory.eINSTANCE
			.createDomainModelReferenceSelector();
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabinding.class);
		databinding = bundleContext.getService(databindingServiceReference);
	}

	@After
	public void tearDown() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		bundleContext.ungetService(databindingServiceReference);
	}

	@Test
	public void testNotAControl() {
		final double specificity = domainModelReferenceSelector.isApplicable(
			mock(VElement.class), mock(ViewModelContext.class));
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	@Test
	public void testControlWithoutDomainModelReference() {
		final VControl vControl = mock(VControl.class);

		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, mock(ViewModelContext.class));
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	@Test
	public void testSelectorDomainModelReferenceNotResolvable() {
		final VFeaturePathDomainModelReference selectorDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		selectorDomainModelReference
			.setDomainModelEFeature(EcorePackage.eINSTANCE
				.getENamedElement_Name());
		domainModelReferenceSelector
			.setDomainModelReference(selectorDomainModelReference);

		final VControl vControl = mock(VControl.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(mock(EObject.class));
		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	@Test
	public void testControlDomainModelReferenceDifferentEFeatures() {
		final VFeaturePathDomainModelReference selectorDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		selectorDomainModelReference
			.setDomainModelEFeature(EcorePackage.eINSTANCE
				.getENamedElement_Name());
		domainModelReferenceSelector
			.setDomainModelReference(selectorDomainModelReference);

		final VControl vControl = mock(VControl.class);
		final VFeaturePathDomainModelReference controlDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		when(vControl.getDomainModelReference()).thenReturn(controlDomainModelReference);
		controlDomainModelReference.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_Abstract());

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(EcoreFactory.eINSTANCE.createEClass());
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	@Ignore
	@Test
	public void testControlDomainModelReferenceLong() {
		final VFeaturePathDomainModelReference selectorDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		selectorDomainModelReference
			.setDomainModelEFeature(EcorePackage.eINSTANCE
				.getENamedElement_Name());
		domainModelReferenceSelector
			.setDomainModelReference(selectorDomainModelReference);

		final VControl vControl = mock(VControl.class);
		final VFeaturePathDomainModelReference controlDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		when(vControl.getDomainModelReference()).thenReturn(controlDomainModelReference);
		final Set<Setting> settings = new LinkedHashSet<Setting>();
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		settings.add(InternalEObject.class.cast(eClass).eSetting(EcorePackage.eINSTANCE
			.getENamedElement_Name()));
		settings.add(InternalEObject.class.cast(eClass).eSetting(EcorePackage.eINSTANCE
			.getEClass_Abstract()));

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eClass);

		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	@Test
	public void testControlDomainModelReferenceCorrect() {
		final VFeaturePathDomainModelReference selectorDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		selectorDomainModelReference
			.setDomainModelEFeature(EcorePackage.eINSTANCE
				.getENamedElement_Name());
		domainModelReferenceSelector
			.setDomainModelReference(selectorDomainModelReference);

		final VControl vControl = mock(VControl.class);
		final VFeaturePathDomainModelReference controlDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		when(vControl.getDomainModelReference()).thenReturn(controlDomainModelReference);
		controlDomainModelReference.setDomainModelEFeature(EcorePackage.eINSTANCE.getENamedElement_Name());

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eClass);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, viewModelContext);
		assertEquals(10d, specificity,
			0d);
	}

	@Ignore
	@Test
	public void testControlDomainModelReferenceDifferentEObjects() {
		final VFeaturePathDomainModelReference selectorDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		selectorDomainModelReference
			.setDomainModelEFeature(EcorePackage.eINSTANCE
				.getENamedElement_Name());
		domainModelReferenceSelector
			.setDomainModelReference(selectorDomainModelReference);

		final VControl vControl = mock(VControl.class);
		final VDomainModelReference controlDomainModelReference = mock(VDomainModelReference.class);
		when(vControl.getDomainModelReference()).thenReturn(controlDomainModelReference);
		final Set<Setting> settings = new LinkedHashSet<Setting>();

		settings.add(InternalEObject.class.cast(EcoreFactory.eINSTANCE.createEClass()).eSetting(EcorePackage.eINSTANCE
			.getENamedElement_Name()));

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(EcoreFactory.eINSTANCE.createEClass());

		final double specificity = domainModelReferenceSelector.isApplicable(
			vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity,
			0d);
	}

	/** selector EClass matches the domain object's EClass. */
	@Test
	public void isApplicable_segments_sameRootEClass_equalDmr() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ABSTRACT.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.ECLASS);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ABSTRACT.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eClass);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(10d, specificity, 0d);
	}

	/** selector EClass matches the domain object's EClass. */
	@Test
	public void isApplicable_segments_sameRootEClass_differentDmr() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ABSTRACT.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.ECLASS);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__INTERFACE.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eClass);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity, 0d);
	}

	/** selector EClass is a super type of the domain object's EClass. */
	@Test
	public void isApplicable_segments_superRootEClass_equalDmr() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.EDATA_TYPE__SERIALIZABLE.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.EDATA_TYPE);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.EDATA_TYPE__SERIALIZABLE.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eEnum);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(10d, specificity, 0d);
	}

	/** selector EClass is a super type of the domain object's EClass. */
	@Test
	public void isApplicable_segments_superRootEClass_differentDmr() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.EDATA_TYPE__SERIALIZABLE.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.EDATA_TYPE);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.EENUM__ELITERALS.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eEnum);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity, 0d);
	}

	/**
	 * If the root EClass of the selector is a sub class of the domain object's EClass, the selector is not applicable.
	 */
	@Test
	public void isApplicable_segments_subRootEClass() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.EENUM);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EDataType eDataType = EcoreFactory.eINSTANCE.createEDataType();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eDataType);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity, 0d);
	}

	@Test
	public void isApplicable_segments_noRootEClass() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(eClass);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity, 0d);
	}

	@Test
	public void isApplicable_segments_unrelatedRootEClass_equalDmr() {
		final VDomainModelReference selectorDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment selectorSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		selectorSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		selectorDmr.getSegments().add(selectorSegment);
		domainModelReferenceSelector.setDomainModelReference(selectorDmr);
		domainModelReferenceSelector.setRootEClass(EcorePackage.Literals.ECLASS);

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference controlDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment controlSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		controlSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
		controlDmr.getSegments().add(controlSegment);
		vControl.setDomainModelReference(controlDmr);

		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		when(viewModelContext.getDomainModel()).thenReturn(ePackage);
		when(viewModelContext.getService(EMFFormsDatabinding.class)).thenReturn(databinding);

		final double specificity = domainModelReferenceSelector.isApplicable(vControl, viewModelContext);
		assertEquals(VTStyleSelector.NOT_APPLICABLE.doubleValue(), specificity, 0d);
	}
}
