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
package org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic;

import static org.junit.Assert.assertEquals;
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
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link EMFFormsMappingProviderDefaultHeuristic}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsMappingProviderDefaultHeuristic_Test {

	private EMFFormsMappingProviderDefaultHeuristic mappingProvider;
	private ReportService reportService;
	private EMFFormsDatabindingEMF databinding;

	/**
	 * Sets up a new {@link EMFFormsMappingProviderDefaultHeuristic} for every test case.
	 */
	@Before
	public void setUp() {
		mappingProvider = new EMFFormsMappingProviderDefaultHeuristic();
		databinding = mock(EMFFormsDatabindingEMF.class);
		mappingProvider.setEMFFormsDatabinding(databinding);
		reportService = mock(ReportService.class);
		mappingProvider.setReportService(reportService);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingFor() throws DatabindingFailedException {
		final VDomainModelReference dmr = mock(VDomainModelReference.class);
		final EObject domain = TestFactory.eINSTANCE.createB();
		final EReference eReference = TestPackage.eINSTANCE.getB_C();
		final Setting setting = ((InternalEObject) domain).eSetting(eReference);
		when(databinding.getSetting(dmr, domain)).thenReturn(setting);

		final Set<UniqueSetting> resultSet = mappingProvider.getMappingFor(dmr, domain);

		assertEquals(1, resultSet.size());
		final UniqueSetting uniqueSetting = resultSet.iterator().next();
		assertEquals(domain, uniqueSetting.getEObject());
		assertEquals(eReference, uniqueSetting.getEStructuralFeature());
		verify(databinding).getSetting(dmr, domain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetMappingForDatabindingFailed() throws DatabindingFailedException {
		final VDomainModelReference dmr = mock(VDomainModelReference.class);
		final EObject domain = TestFactory.eINSTANCE.createB();
		when(databinding.getSetting(dmr, domain)).thenThrow(mock(DatabindingFailedException.class));

		final Set<UniqueSetting> resultSet = mappingProvider.getMappingFor(dmr, domain);

		assertEquals(0, resultSet.size());
		verify(databinding).getSetting(dmr, domain);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForDMRNull() {
		mappingProvider.getMappingFor(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForEObjectNull() {
		mappingProvider.getMappingFor(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMappingForBothNull() {
		mappingProvider.getMappingFor(null, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicable() {
		final double applicable = mappingProvider.isApplicable(mock(VDomainModelReference.class), mock(EObject.class));
		assertEquals(1d, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableDMRNull() {
		final double applicable = mappingProvider.isApplicable(null, mock(EObject.class));
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableEObjectNull() {
		final double applicable = mappingProvider.isApplicable(mock(VDomainModelReference.class), null);
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testIsApplicableBothNull() {
		final double applicable = mappingProvider.isApplicable(null, null);
		assertEquals(EMFFormsMappingProvider.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}
}
