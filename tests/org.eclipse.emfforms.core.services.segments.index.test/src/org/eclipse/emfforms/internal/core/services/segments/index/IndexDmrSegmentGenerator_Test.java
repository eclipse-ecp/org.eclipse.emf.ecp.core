/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexDomainModelReferenceImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link IndexDmrSegmentGenerator}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class IndexDmrSegmentGenerator_Test {

	private IndexDmrSegmentGenerator generator;
	private EMFFormsSegmentGenerator emfFormsSegmentGenerator;
	private ReportService reportService;

	@Before
	public void setUp() {
		generator = new IndexDmrSegmentGenerator();
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
	public void isApplicable_indexDmr() {
		final double applicable = generator.isApplicable(VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference());
		assertEquals(5d, applicable, 0d);
	}

	@Test
	public void isApplicable_unrelatedDmr() {
		final double applicable = generator.isApplicable(mock(VDomainModelReference.class));
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test
	public void isApplicable_subtypeOfIndexDmr() {
		final IndexDmrSubType subtypeDmr = new IndexDmrSubType();
		final double applicable = generator.isApplicable(subtypeDmr);
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_nullDmr() {
		generator.generateSegments(null);
	}

	// In this test case, the index dmr uses a prefix dmr
	@Test
	public void generateSegments_prefixDmr() {
		final int index = 3;
		final VIndexDomainModelReference indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		indexDmr.setIndex(index);
		final VDomainModelReference prefixDmr = mockValidPrefixDmr(indexDmr);
		final VDomainModelReference targetDmr = mockValidTargetDmr(indexDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(indexDmr);

		assertNotNull(result);
		assertEquals("The generated segment list has an incorrect number of segments", 4, result.size()); //$NON-NLS-1$

		assertFeatureSegment(result.get(0), TestPackage.Literals.A__B.getName());

		assertTrue(result.get(1) instanceof VIndexDomainModelReferenceSegment);
		final VIndexDomainModelReferenceSegment seg1 = (VIndexDomainModelReferenceSegment) result.get(1);
		assertEquals(index, seg1.getIndex());
		assertEquals(TestPackage.Literals.B__CLIST.getName(), seg1.getDomainModelFeature());

		assertFeatureSegment(result.get(2), TestPackage.Literals.C__D.getName());
		assertFeatureSegment(result.get(3), TestPackage.Literals.D__X.getName());

		verify(emfFormsSegmentGenerator, times(1)).generateSegments(prefixDmr);
		verify(emfFormsSegmentGenerator, times(1)).generateSegments(targetDmr);
		verify(reportService, never()).report(any(AbstractReport.class));
	}

	// In this test case, the index dmr does not use a prefix dmr but specifies the path to the list itself
	@Test
	public void generateSegments_prefixPath() {
		final int index = 3;
		final VIndexDomainModelReference indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		indexDmr.setIndex(index);
		indexDmr.getDomainModelEReferencePath().add(TestPackage.Literals.A__B);
		indexDmr.setDomainModelEFeature(TestPackage.Literals.B__CLIST);
		final VDomainModelReference targetDmr = mockValidTargetDmr(indexDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(indexDmr);

		assertNotNull(result);
		assertEquals("The generated segment list has an incorrect number of segments", 4, result.size()); //$NON-NLS-1$

		assertFeatureSegment(result.get(0), TestPackage.Literals.A__B.getName());

		assertTrue(result.get(1) instanceof VIndexDomainModelReferenceSegment);
		final VIndexDomainModelReferenceSegment seg1 = (VIndexDomainModelReferenceSegment) result.get(1);
		assertEquals(index, seg1.getIndex());
		assertEquals(TestPackage.Literals.B__CLIST.getName(), seg1.getDomainModelFeature());

		assertFeatureSegment(result.get(2), TestPackage.Literals.C__D.getName());
		assertFeatureSegment(result.get(3), TestPackage.Literals.D__X.getName());

		verify(emfFormsSegmentGenerator, times(1)).generateSegments(targetDmr);
		verify(reportService, never()).report(any(AbstractReport.class));
	}

	@Test
	public void generateSegments_emptyDmr() {
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		final List<VDomainModelReferenceSegment> result = generator.generateSegments(dmr);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	/* noPrefix means that the index dmr neither has a prefix dmr nor a domain model e feature. */
	@Test
	public void generateSegments_noPrefix() {
		final VIndexDomainModelReference indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		mockValidTargetDmr(indexDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(indexDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	@Test
	public void generateSegments_noSegmentsForPrefixDmr() {
		final VIndexDomainModelReference indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();

		final VDomainModelReference prefixDmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		when(emfFormsSegmentGenerator.generateSegments(prefixDmr)).thenReturn(Collections.emptyList());
		indexDmr.setPrefixDMR(prefixDmr);

		mockValidTargetDmr(indexDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(indexDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	private void assertFeatureSegment(VDomainModelReferenceSegment segment, String expectedDomainModelFeature) {
		assertTrue(segment instanceof VFeatureDomainModelReferenceSegment);
		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
		assertEquals("Segment does not reference the expected feature.", expectedDomainModelFeature, //$NON-NLS-1$
			featureSegment.getDomainModelFeature());
	}

	/**
	 * Mocks a target dmr, configures the emfFormsSegmentGenerator, and adds the dmr to the given index dmr
	 *
	 * @param indexDmr The index dmr to add the mocked, valid target dmr to
	 * @return the created target dmr (it already has been added to the given index dmr)
	 */
	private VDomainModelReference mockValidTargetDmr(final VIndexDomainModelReference indexDmr) {
		// Need to create a real instance because otherwise the add to a real index dmr will fail with a class cast
		// exception
		final VDomainModelReference targetDmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		indexDmr.setTargetDMR(targetDmr);
		final List<VDomainModelReferenceSegment> targetSegments = new LinkedList<>();
		final VFeatureDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment1.setDomainModelFeature(TestPackage.Literals.C__D.getName());
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment2.setDomainModelFeature(TestPackage.Literals.D__X.getName());
		targetSegments.add(segment1);
		targetSegments.add(segment2);
		when(emfFormsSegmentGenerator.generateSegments(targetDmr)).thenReturn(targetSegments);
		return targetDmr;
	}

	/**
	 * Mocks a prefix dmr, configures the emfFormsSegmentGenerator, and adds the dmr to the given index dmr
	 *
	 * @param indexDmr The index dmr to add the mocked, valid target dmr to
	 * @return the created prefix dmr (it already has been added to the given index dmr)
	 */
	private VDomainModelReference mockValidPrefixDmr(final VIndexDomainModelReference indexDmr) {
		// Need to create a real instance because otherwise the add to a real index dmr will fail with a class cast
		// exception
		final VDomainModelReference prefixDmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		indexDmr.setPrefixDMR(prefixDmr);
		final List<VDomainModelReferenceSegment> prefixSegments = new LinkedList<>();
		final VFeatureDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		prefixSegments.add(segment1);
		prefixSegments.add(segment2);
		segment1.setDomainModelFeature(TestPackage.Literals.A__B.getName());
		segment2.setDomainModelFeature(TestPackage.Literals.B__CLIST.getName());
		when(emfFormsSegmentGenerator.generateSegments(prefixDmr)).thenReturn(prefixSegments);
		return prefixDmr;
	}

	@Test
	public void generateSegments_noTargetDmr() {
		final VIndexDomainModelReference indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		mockValidPrefixDmr(indexDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(indexDmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	@Test
	public void generateSegments_emptyTargetDmr() {
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
		final VFeaturePathDomainModelReference targetDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		dmr.setTargetDMR(targetDmr);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(dmr);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(reportService, atLeastOnce()).report(any(AbstractReport.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_unrelatedDmr() {
		generator.generateSegments(mock(VDomainModelReference.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_subtypeOfFeaturePathDmr() {
		final IndexDmrSubType subtypeDmr = new IndexDmrSubType();
		generator.generateSegments(subtypeDmr);
	}

	private static class IndexDmrSubType extends VIndexDomainModelReferenceImpl {
		@Override
		public EClass eClass() {
			final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			eClass.setName("IndexDmrSubType"); //$NON-NLS-1$
			eClass.getESuperTypes().add(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE);
			return eClass;
		}
	}
}
