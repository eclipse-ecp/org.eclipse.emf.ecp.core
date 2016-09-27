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
package org.eclipse.emfforms.internal.core.services.mappingprovider.multi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link EMFFormsMappingProviderMulti}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsMappingProviderMulti_Test {

	private EMFFormsMappingProviderMulti provider;
	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	@Before
	public void setUp() {
		provider = new EMFFormsMappingProviderMulti();
		databinding = mock(EMFFormsDatabindingEMF.class);
		provider.setEMFFormsDatabinding(databinding);
		reportService = mock(ReportService.class);
		provider.setReportService(reportService);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingFor() throws DatabindingFailedException {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EReference listEReference = TestPackage.eINSTANCE.getB_CList();
		multiSegment.setDomainModelFeature(listEReference.getName());
		final VDomainModelReference childDMR = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(childDMR);
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(multiSegment);

		final B domain = TestFactory.eINSTANCE.createB();
		final C child1 = TestFactory.eINSTANCE.createC();
		final C child2 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(child1);
		domain.getCList().add(child2);

		final Setting domainSetting = ((InternalEObject) domain).eSetting(listEReference);
		final Setting child1Setting = ((InternalEObject) child1).eSetting(TestPackage.eINSTANCE.getC_D());
		final Setting child2Setting = ((InternalEObject) child2).eSetting(TestPackage.eINSTANCE.getC_D());
		when(databinding.getSetting(dmr, domain)).thenReturn(domainSetting);
		when(databinding.getSetting(childDMR, child1)).thenReturn(child1Setting);
		when(databinding.getSetting(childDMR, child2)).thenReturn(child2Setting);

		final Set<UniqueSetting> mapping = provider.getMappingFor(dmr, domain);

		assertEquals(3, mapping.size());
		assertTrue(mapping.contains(UniqueSetting.createSetting(domainSetting)));
		assertTrue(mapping.contains(UniqueSetting.createSetting(child1Setting)));
		assertTrue(mapping.contains(UniqueSetting.createSetting(child2Setting)));
		verify(databinding).getSetting(dmr, domain);
		verify(databinding).getSetting(childDMR, child1);
		verify(databinding).getSetting(childDMR, child2);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingForFirstChildDatabindingFailed() throws DatabindingFailedException {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EReference listEReference = TestPackage.eINSTANCE.getB_CList();
		multiSegment.setDomainModelFeature(listEReference.getName());
		final VDomainModelReference childDMR = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(childDMR);
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(multiSegment);

		final B domain = TestFactory.eINSTANCE.createB();
		final C child1 = TestFactory.eINSTANCE.createC();
		final C child2 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(child1);
		domain.getCList().add(child2);

		final Setting domainSetting = ((InternalEObject) domain).eSetting(listEReference);
		final Setting child2Setting = ((InternalEObject) child2).eSetting(TestPackage.eINSTANCE.getC_D());
		when(databinding.getSetting(dmr, domain)).thenReturn(domainSetting);
		when(databinding.getSetting(childDMR, child1)).thenThrow(mock(DatabindingFailedException.class));
		when(databinding.getSetting(childDMR, child2)).thenReturn(child2Setting);

		final Set<UniqueSetting> mapping = provider.getMappingFor(dmr, domain);

		assertEquals(2, mapping.size());
		assertTrue(mapping.contains(UniqueSetting.createSetting(domainSetting)));
		assertTrue(mapping.contains(UniqueSetting.createSetting(child2Setting)));
		verify(databinding).getSetting(dmr, domain);
		verify(databinding).getSetting(childDMR, child1);
		verify(databinding).getSetting(childDMR, child2);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingForNoChildDMR() throws DatabindingFailedException {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EReference listEReference = TestPackage.eINSTANCE.getB_CList();
		multiSegment.setDomainModelFeature(listEReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(multiSegment);

		final B domain = TestFactory.eINSTANCE.createB();

		final Setting domainSetting = ((InternalEObject) domain).eSetting(listEReference);
		when(databinding.getSetting(dmr, domain)).thenReturn(domainSetting);

		final Set<UniqueSetting> mapping = provider.getMappingFor(dmr, domain);

		assertEquals(1, mapping.size());
		assertTrue(mapping.contains(UniqueSetting.createSetting(domainSetting)));
		verify(databinding).getSetting(dmr, domain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingForDomainDatabindingFailed() throws DatabindingFailedException {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EReference listEReference = TestPackage.eINSTANCE.getB_CList();
		multiSegment.setDomainModelFeature(listEReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(multiSegment);

		final B domain = TestFactory.eINSTANCE.createB();
		when(databinding.getSetting(dmr, domain)).thenThrow(mock(DatabindingFailedException.class));
		final Set<UniqueSetting> mapping = provider.getMappingFor(dmr, domain);

		assertNotNull(mapping);
		assertEquals(0, mapping.size());
		verify(databinding).getSetting(dmr, domain);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForDMRNull() {
		provider.getMappingFor(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForEObjectNull() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		dmr.getSegments().add(multiSegment);
		provider.getMappingFor(dmr, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForDMRWithoutSegments() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		provider.getMappingFor(dmr, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForWrongLastSegment() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(featureSegment);
		provider.getMappingFor(dmr, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableCorrectCase() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		dmr.getSegments().add(multiSegment);
		final double applicable = provider.isApplicable(dmr, mock(EObject.class));
		assertEquals(5d, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableWrongLastSegment() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(featureSegment);
		final double applicable = provider.isApplicable(dmr, mock(EObject.class));
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableNullDMR() {
		final double applicable = provider.isApplicable(null, mock(EObject.class));
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableNullEObject() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		dmr.getSegments().add(multiSegment);
		final double applicable = provider.isApplicable(dmr, null);
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableBothNull() {
		final double applicable = provider.isApplicable(null, null);
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.multi.EMFFormsMappingProviderMulti#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableNoSegments() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final double applicable = provider.isApplicable(dmr, null);
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}
}
