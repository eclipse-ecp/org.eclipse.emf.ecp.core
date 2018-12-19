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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TableDmrSegmentGenerator}.
 *
 * @author Lucas Koehler
 *
 */
public class TableDmrSegmentGenerator_Test {

	private TableDmrSegmentGenerator generator;
	private EMFFormsSegmentGenerator emfFormsSegmentGenerator;

	@Before
	public void setUp() {
		generator = new TableDmrSegmentGenerator();
		emfFormsSegmentGenerator = mock(EMFFormsSegmentGenerator.class);
		generator.setEMFFormsSegmentGenerator(emfFormsSegmentGenerator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void isApplicable_nullDmr() {
		generator.isApplicable(null);
	}

	@Test
	public void isApplicable_featurePathDmr() {
		final double applicable = generator
			.isApplicable(VTableFactory.eINSTANCE.createTableDomainModelReference());
		assertEquals(5d, applicable, 0d);
	}

	@Test
	public void isApplicable_unrelatedDmr() {
		final double applicable = generator.isApplicable(mock(VDomainModelReference.class));
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_nullDmr() {
		generator.generateSegments(null);
	}

	@Test
	public void generateSegments_withInnerDmr() {
		final VFeaturePathDomainModelReference innerDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		innerDmr.getDomainModelEReferencePath().add(TestPackage.Literals.A__B);
		innerDmr.setDomainModelEFeature(TestPackage.Literals.B__CLIST);

		// mock EMFFormsSegmentGenerator
		final LinkedList<VDomainModelReferenceSegment> innerSegments = new LinkedList<>();
		final VFeatureDomainModelReferenceSegment innerSegment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		innerSegment1.setDomainModelFeature(TestPackage.Literals.A__B.getName());
		final VFeatureDomainModelReferenceSegment innerSegment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		innerSegment2.setDomainModelFeature(TestPackage.Literals.B__CLIST.getName());
		innerSegments.add(innerSegment1);
		innerSegments.add(innerSegment2);
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class)))
			.thenReturn(innerSegments);
		generator.setEMFFormsSegmentGenerator(emfFormsSegmentGenerator);

		final VFeaturePathDomainModelReference columnDmr1 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		columnDmr1.setDomainModelEFeature(TestPackage.Literals.C__A);
		final VFeaturePathDomainModelReference columnDmr2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		columnDmr2.setDomainModelEFeature(TestPackage.Literals.C__D);

		final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		tableDmr.setDomainModelReference(innerDmr);
		tableDmr.getColumnDomainModelReferences().add(columnDmr1);
		tableDmr.getColumnDomainModelReferences().add(columnDmr2);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(tableDmr);

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(emfFormsSegmentGenerator, times(1)).generateSegments(isA(VFeaturePathDomainModelReference.class));

		assertEquals(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(0).eClass());
		final VFeatureDomainModelReferenceSegment segment1 = (VFeatureDomainModelReferenceSegment) result.get(0);
		assertEquals(TestPackage.Literals.A__B.getName(), segment1.getDomainModelFeature());

		assertEquals(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(1).eClass());
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) result.get(1);
		assertEquals(TestPackage.Literals.B__CLIST.getName(), multiSegment.getDomainModelFeature());
		assertEquals(2, multiSegment.getChildDomainModelReferences().size());
		assertSame(columnDmr1, multiSegment.getChildDomainModelReferences().get(0));
		assertSame(columnDmr2, multiSegment.getChildDomainModelReferences().get(1));
	}

	@Test
	public void generateSegments_noInnerDmr() {
		final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		tableDmr.getDomainModelEReferencePath().add(TestPackage.Literals.A__B);
		tableDmr.setDomainModelEFeature(TestPackage.Literals.B__CLIST);

		// mock EMFFormsSegmentGenerator
		final LinkedList<VDomainModelReferenceSegment> innerSegments = new LinkedList<>();
		final VFeatureDomainModelReferenceSegment innerSegment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		innerSegment1.setDomainModelFeature(TestPackage.Literals.A__B.getName());
		final VFeatureDomainModelReferenceSegment innerSegment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		innerSegment2.setDomainModelFeature(TestPackage.Literals.B__CLIST.getName());
		innerSegments.add(innerSegment1);
		innerSegments.add(innerSegment2);
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class))).thenReturn(innerSegments);
		generator.setEMFFormsSegmentGenerator(emfFormsSegmentGenerator);

		final VFeaturePathDomainModelReference columnDmr1 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		columnDmr1.setDomainModelEFeature(TestPackage.Literals.C__A);
		final VFeaturePathDomainModelReference columnDmr2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		columnDmr2.setDomainModelEFeature(TestPackage.Literals.C__D);

		tableDmr.getColumnDomainModelReferences().add(columnDmr1);
		tableDmr.getColumnDomainModelReferences().add(columnDmr2);

		final List<VDomainModelReferenceSegment> result = generator.generateSegments(tableDmr);

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(emfFormsSegmentGenerator, times(1)).generateSegments(isA(VFeaturePathDomainModelReference.class));

		assertEquals(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(0).eClass());
		final VFeatureDomainModelReferenceSegment segment1 = (VFeatureDomainModelReferenceSegment) result.get(0);
		assertEquals(TestPackage.Literals.A__B.getName(), segment1.getDomainModelFeature());

		assertEquals(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(1).eClass());
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) result.get(1);
		assertEquals(TestPackage.Literals.B__CLIST.getName(), multiSegment.getDomainModelFeature());
		assertEquals(2, multiSegment.getChildDomainModelReferences().size());
		assertSame(columnDmr1, multiSegment.getChildDomainModelReferences().get(0));
		assertSame(columnDmr2, multiSegment.getChildDomainModelReferences().get(1));
	}

	@Test
	public void generateSegments_emptyDmr() {
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class)))
			.thenReturn(Collections.emptyList());
		final VTableDomainModelReference dmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		final List<VDomainModelReferenceSegment> result = generator.generateSegments(dmr);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_unrelatedDmr() {
		generator.generateSegments(mock(VDomainModelReference.class));
	}

}
