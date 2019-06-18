/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link MultiSegmentGenerator}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSegmentGenerator_Test {

	private MultiSegmentGenerator segmentGenerator;

	@Before
	public void setUp() {
		segmentGenerator = new MultiSegmentGenerator();
	}

	@Test
	public void generateSegments_null() {
		final List<VDomainModelReferenceSegment> result = segmentGenerator.generateSegments(null);
		assertNotNull("MultiSegmentGenerator must never return null", result); //$NON-NLS-1$
		assertTrue(result.isEmpty());
	}

	@Test
	public void generateSegments_emptyList() {
		final List<VDomainModelReferenceSegment> result = segmentGenerator.generateSegments(Collections.emptyList());
		assertNotNull("MultiSegmentGenerator must never return null", result); //$NON-NLS-1$
		assertTrue(result.isEmpty());
	}

	@Test
	public void generateSegments() {
		final List<EStructuralFeature> input = new LinkedList<>();
		input.add(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		input.add(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);

		final List<VDomainModelReferenceSegment> result = segmentGenerator.generateSegments(input);

		assertEquals(2, result.size());
		assertSame(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(0).eClass());
		final VFeatureDomainModelReferenceSegment refTypeSegment = (VFeatureDomainModelReferenceSegment) result.get(0);
		assertEquals(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE.getName(),
			refTypeSegment.getDomainModelFeature());
		assertSame(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT, result.get(1).eClass());
		final VMultiDomainModelReferenceSegment structuralFeaturesSegment = (VMultiDomainModelReferenceSegment) result
			.get(1);
		assertEquals(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES.getName(),
			structuralFeaturesSegment.getDomainModelFeature());
		assertTrue(structuralFeaturesSegment.getChildDomainModelReferences().isEmpty());
	}
}
