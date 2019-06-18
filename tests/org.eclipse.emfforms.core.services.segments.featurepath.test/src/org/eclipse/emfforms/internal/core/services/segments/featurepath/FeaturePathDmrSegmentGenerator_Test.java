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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link FeaturePathDmrSegmentGenerator}.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDmrSegmentGenerator_Test {

	private FeaturePathDmrSegmentGenerator converter;

	@Before
	public void setUp() {
		converter = new FeaturePathDmrSegmentGenerator();
	}

	@Test(expected = IllegalArgumentException.class)
	public void isApplicable_nullDmr() {
		converter.isApplicable(null);
	}

	@Test
	public void isApplicable_featurePathDmr() {
		final double applicable = converter
			.isApplicable(VViewFactory.eINSTANCE.createFeaturePathDomainModelReference());
		assertEquals(1d, applicable, 0d);
	}

	@Test
	public void isApplicable_unrelatedDmr() {
		final double applicable = converter.isApplicable(mock(VDomainModelReference.class));
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test
	public void isApplicable_subtypeOfFeaturePathDmr() {
		final FeatureDmrSubType subtypeDmr = new FeatureDmrSubType();
		final double applicable = converter.isApplicable(subtypeDmr);
		assertEquals(DmrSegmentGenerator.NOT_APPLICABLE, applicable, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_nullDmr() {
		converter.generateSegments(null);
	}

	@Test
	public void generateSegments() {
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.getDomainModelEReferencePath().add(TestPackage.Literals.B__C);
		dmr.getDomainModelEReferencePath().add(TestPackage.Literals.C__D);
		dmr.setDomainModelEFeature(TestPackage.Literals.D__X);

		final List<VDomainModelReferenceSegment> result = converter.generateSegments(dmr);

		assertNotNull(result);
		assertEquals(3, result.size());

		assertEquals(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(0).eClass());
		final VFeatureDomainModelReferenceSegment segment1 = (VFeatureDomainModelReferenceSegment) result.get(0);
		assertEquals(TestPackage.Literals.B__C.getName(), segment1.getDomainModelFeature());

		assertEquals(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(1).eClass());
		final VFeatureDomainModelReferenceSegment segment2 = (VFeatureDomainModelReferenceSegment) result.get(1);
		assertEquals(TestPackage.Literals.C__D.getName(), segment2.getDomainModelFeature());

		assertEquals(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(2).eClass());
		final VFeatureDomainModelReferenceSegment segment3 = (VFeatureDomainModelReferenceSegment) result.get(2);
		assertEquals(TestPackage.Literals.D__X.getName(), segment3.getDomainModelFeature());
	}

	@Test
	public void generateSegments_emptyDmr() {
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final List<VDomainModelReferenceSegment> result = converter.generateSegments(dmr);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_unrelatedDmr() {
		converter.generateSegments(mock(VDomainModelReference.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSegments_subtypeOfFeaturePathDmr() {
		final FeatureDmrSubType subtypeDmr = new FeatureDmrSubType();
		converter.generateSegments(subtypeDmr);
	}

	private static class FeatureDmrSubType extends VFeaturePathDomainModelReferenceImpl {

		@Override
		public EClass eClass() {
			final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			eClass.setName("FeatureDmrSubType"); //$NON-NLS-1$
			eClass.getESuperTypes().add(VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
			return eClass;
		}

	}
}
