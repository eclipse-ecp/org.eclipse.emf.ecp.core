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
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class MappingDmrSegmentGenerator_Test {

	private MappingDmrSegmentGenerator generator;
	private EMFFormsSegmentGenerator emfFormsSegmentGenerator;
	private ReportService reportService;

	@Before
	public void setUp() {
		generator = new MappingDmrSegmentGenerator();
		emfFormsSegmentGenerator = mock(EMFFormsSegmentGenerator.class);
		// the interface defines that null is never returned
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class)))
			.thenReturn(Collections.emptyList());
		generator.setEMFFormsSegmentGenerator(emfFormsSegmentGenerator);
		reportService = mock(ReportService.class);
		generator.setReportService(reportService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void isApplicable_nullDmr() {
		generator.isApplicable(null);
	}

	@Test
	public void isApplicable_mappingDmr() {
		final double applicable = generator
			.isApplicable(VMappingdmrFactory.eINSTANCE.createMappingDomainModelReference());
		assertEquals(5d, applicable, 0d);
	}

	@Test
	public void isApplicable_unrelatedDmr() {
		final double applicable = generator.isApplicable(mock(VDomainModelReference.class));
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test
	public void isApplicable_subtypeOfmappingDmr() {
		final MappingDmrSubType subtypeDmr = new MappingDmrSubType();
		final double applicable = generator.isApplicable(subtypeDmr);
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_nullDmr() {
		generator.generateSegments(null);
	}

	@Test
	public void generateSegments() {
		final VMappingDomainModelReference mappingDmr = mockMappingDmr();
		mappingDmr.getDomainModelEReferencePath().add(TestPackage.Literals.B__C);
		when(mappingDmr.getDomainModelEFeature()).thenReturn(TestPackage.Literals.C__ECLASS_TO_E);
		when(mappingDmr.getMappedClass()).thenReturn(TestPackage.Literals.A);

		final VDomainModelReference targetDmr = mock(VDomainModelReference.class);
		when(mappingDmr.getDomainModelReference()).thenReturn(targetDmr);

		final List<VDomainModelReferenceSegment> targetSegments = new LinkedList<>();
		final VDomainModelReferenceSegment targetSegment = mock(VDomainModelReferenceSegment.class);
		targetSegments.add(targetSegment);
		when(emfFormsSegmentGenerator.generateSegments(targetDmr)).thenReturn(targetSegments);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(mappingDmr);
		assertEquals(3, result.size());
		assertFeatureSegment(result.get(0), TestPackage.Literals.B__C.getName());
		assertTrue(result.get(1) instanceof VMappingDomainModelReferenceSegment);
		final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) result.get(1);
		assertEquals(TestPackage.Literals.A, mappingSegment.getMappedClass());
		assertEquals(TestPackage.Literals.C__ECLASS_TO_E.getName(), mappingSegment.getDomainModelFeature());
		assertSame(targetSegment, result.get(2));

		verify(emfFormsSegmentGenerator, times(1)).generateSegments(targetDmr);
	}

	@Test
	public void generateSegments_noDomainModelEFeature() {
		final VMappingDomainModelReference mappingDmr = mockMappingDmr();

		final VDomainModelReference targetDmr = mock(VDomainModelReference.class);
		when(mappingDmr.getDomainModelReference()).thenReturn(targetDmr);

		final List<VDomainModelReferenceSegment> targetSegments = new LinkedList<>();
		final VDomainModelReferenceSegment targetSegment = mock(VDomainModelReferenceSegment.class);
		targetSegments.add(targetSegment);
		when(emfFormsSegmentGenerator.generateSegments(targetDmr)).thenReturn(targetSegments);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(mappingDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	@Test
	public void generateSegments_noTargetDmr() {
		final VMappingDomainModelReference mappingDmr = mockMappingDmr();
		mappingDmr.getDomainModelEReferencePath().add(TestPackage.Literals.B__C);
		when(mappingDmr.getDomainModelEFeature()).thenReturn(TestPackage.Literals.C__ECLASS_TO_E);
		when(mappingDmr.getMappedClass()).thenReturn(TestPackage.Literals.A);

		when(mappingDmr.getDomainModelReference()).thenReturn(null);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(mappingDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	@Test
	public void generateSegments_emptyTargetDmr() {
		final VMappingDomainModelReference mappingDmr = mockMappingDmr();
		mappingDmr.getDomainModelEReferencePath().add(TestPackage.Literals.B__C);
		when(mappingDmr.getDomainModelEFeature()).thenReturn(TestPackage.Literals.C__ECLASS_TO_E);
		when(mappingDmr.getMappedClass()).thenReturn(TestPackage.Literals.A);

		final VDomainModelReference targetDmr = mock(VDomainModelReference.class);
		when(mappingDmr.getDomainModelReference()).thenReturn(targetDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(mappingDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	private VMappingDomainModelReference mockMappingDmr() {
		final VMappingDomainModelReference mappingDmr = mock(VMappingDomainModelReference.class);
		final EList<EReference> path = new BasicEList<EReference>();
		when(mappingDmr.getDomainModelEReferencePath()).thenReturn(path);
		when(mappingDmr.eClass()).thenReturn(VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE);
		return mappingDmr;
	}

	private void assertFeatureSegment(VDomainModelReferenceSegment segment, String expectedDomainModelFeature) {
		assertTrue(segment instanceof VFeatureDomainModelReferenceSegment);
		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
		assertEquals("Segment does not reference the expected feature.", expectedDomainModelFeature, //$NON-NLS-1$
			featureSegment.getDomainModelFeature());
	}

	private static class MappingDmrSubType extends VMappingDomainModelReferenceImpl {
		@Override
		public EClass eClass() {
			final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			eClass.setName("IndexDmrSubType"); //$NON-NLS-1$
			eClass.getESuperTypes().add(VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE);
			return eClass;
		}
	}
}
