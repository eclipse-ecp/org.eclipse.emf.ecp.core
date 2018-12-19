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
package org.eclipse.emfforms.internal.core.services.segments;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Default implementation of {@link EMFFormsSegmentGenerator} that delegates the generation to specialized
 * {@link DmrSegmentGenerator DmrSegmentGenerators}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "SegmentGeneratorService")
public class SegmentGeneratorService implements EMFFormsSegmentGenerator {

	private static final RankingHelper<DmrSegmentGenerator> RANKING_HELPER = new RankingHelper<>(
		DmrSegmentGenerator.class, DmrSegmentGenerator.NOT_APPLICABLE, DmrSegmentGenerator.NOT_APPLICABLE);

	private final List<DmrSegmentGenerator> segmentGenerators = new LinkedList<>();

	/**
	 * Register a {@link DmrSegmentGenerator}.
	 *
	 * @param segmentGenerator The {@link DmrSegmentGenerator} to register
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	void addDmrSegmentGenerator(DmrSegmentGenerator segmentGenerator) {
		segmentGenerators.add(segmentGenerator);
	}

	/**
	 * Unregister a {@link DmrSegmentGenerator}.
	 *
	 * @param segmentGenerator The {@link DmrSegmentGenerator} to unregister
	 */
	void removeDmrSegmentGenerator(DmrSegmentGenerator segmentGenerator) {
		segmentGenerators.remove(segmentGenerator);
	}

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference) {
		Assert.create(reference).notNull();

		final DmrSegmentGenerator bestGenerator = RANKING_HELPER.getHighestRankingElement(segmentGenerators,
			generator -> generator.isApplicable(reference));
		if (bestGenerator == null) {
			throw new IllegalStateException(
				String.format(
					"There is no applicable DmrSegmentConverter for %s. Please register a suitable DmrSegmentConverter.", //$NON-NLS-1$
					reference));
		}
		return bestGenerator.generateSegments(reference);
	}
}
