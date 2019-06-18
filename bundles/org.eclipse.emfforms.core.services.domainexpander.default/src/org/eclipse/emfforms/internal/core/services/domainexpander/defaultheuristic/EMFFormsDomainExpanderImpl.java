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
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Default implementation of {@link EMFFormsDomainExpander}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDomainExpanderImpl")
public class EMFFormsDomainExpanderImpl implements EMFFormsDomainExpander {

	private final Set<EMFFormsDMRExpander> emfFormsDMRExpanders = new CopyOnWriteArraySet<>();
	private final Set<EMFFormsDMRSegmentExpander> emfFormsDMRSegmentExpanders = new CopyOnWriteArraySet<>();

	private static final RankingHelper<EMFFormsDMRExpander> DMR_RANKING_HELPER = //
		new RankingHelper<>(
			EMFFormsDMRExpander.class, EMFFormsDMRExpander.NOT_APPLICABLE, EMFFormsDMRExpander.NOT_APPLICABLE);

	private static final RankingHelper<EMFFormsDMRSegmentExpander> SEGMENTS_RANKING_HELPER = //
		new RankingHelper<>(
			EMFFormsDMRSegmentExpander.class, EMFFormsDMRSegmentExpander.NOT_APPLICABLE,
			EMFFormsDMRSegmentExpander.NOT_APPLICABLE);

	/**
	 * Called by the framework to add an {@link EMFFormsDMRExpander} to the set of DMR expanders.
	 *
	 * @param emfFormsDMRExpander The {@link EMFFormsDMRExpander} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addEMFFormsDMRExpander(EMFFormsDMRExpander emfFormsDMRExpander) {
		emfFormsDMRExpanders.add(emfFormsDMRExpander);
	}

	/**
	 * Called by the framework to remove an {@link EMFFormsDMRExpander} from the set of DMR expanders.
	 *
	 * @param emfFormsDMRExpander The {@link EMFFormsDMRExpander} to remove
	 */
	protected void removeEMFFormsDMRExpander(EMFFormsDMRExpander emfFormsDMRExpander) {
		emfFormsDMRExpanders.remove(emfFormsDMRExpander);
	}

	/**
	 * Called by the framework to add an {@link EMFFormsDMRSegmentExpander} to the set of DMR segment expanders.
	 *
	 * @param emfFormsDMRSegmentExpander The {@link EMFFormsDMRSegmentExpander} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addEMFFormsDMRSegmentExpander(EMFFormsDMRSegmentExpander emfFormsDMRSegmentExpander) {
		emfFormsDMRSegmentExpanders.add(emfFormsDMRSegmentExpander);
	}

	/**
	 * Called by the framework to remove an {@link EMFFormsDMRSegmentExpander} from the set of DMR segment expanders.
	 *
	 * @param emfFormsDMRSegmentExpander The {@link EMFFormsDMRSegmentExpander} to remove
	 */
	protected void removeEMFFormsDMRSegmentExpander(EMFFormsDMRSegmentExpander emfFormsDMRSegmentExpander) {
		emfFormsDMRSegmentExpanders.remove(emfFormsDMRSegmentExpander);
	}

	@Override
	public void prepareDomainObject(final VDomainModelReference domainModelReference, final EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// fall back to DMR based mechanism
			final EMFFormsDMRExpander bestDMRExpander = getBestDmrExpander(domainModelReference);
			bestDMRExpander.prepareDomainObject(domainModelReference, domainObject);
			return;
		}

		Optional<EObject> currentDomainObject = Optional.ofNullable(domainObject);
		for (int i = 0; i < segments.size(); i++) {
			if (!currentDomainObject.isPresent()) {
				throw new EMFFormsExpandingFailedException(
					"Could not finish expansion because a segment could not be expanded."); //$NON-NLS-1$
			}
			final VDomainModelReferenceSegment segment = segments.get(i);
			final EMFFormsDMRSegmentExpander expander = getBestSegmentExpander(segment);

			// Only expand the last segment if it is necessary
			if (i == segments.size() - 1 && !expander.needsToExpandLastSegment()) {
				break;
			}
			currentDomainObject = expander.prepareDomainObject(segment, currentDomainObject.get());
		}
	}

	/**
	 * @param domainModelReference The {@link VDomainModelReference} to get the best expander for
	 * @return the most suitable {@link EMFFormsDMRExpander} for the given segment
	 * @throws EMFFormsExpandingFailedException if no {@link EMFFormsDMRExpander} could be found
	 */
	private EMFFormsDMRExpander getBestDmrExpander(final VDomainModelReference domainModelReference)
		throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander bestDMRExpander = DMR_RANKING_HELPER.getHighestRankingElement(
			emfFormsDMRExpanders, dmrExpander -> dmrExpander.isApplicable(domainModelReference));

		if (bestDMRExpander == null) {
			throw new EMFFormsExpandingFailedException(
				"There is no suitable EMFFormsDMRExpander for the given domain model reference."); //$NON-NLS-1$
		}
		return bestDMRExpander;
	}

	/**
	 * @param segment The {@link VDomainModelReferenceSegment} to get the best expander for
	 * @return the most suitable {@link EMFFormsDMRSegmentExpander} for the given segment
	 * @throws EMFFormsExpandingFailedException if no {@link EMFFormsDMRSegmentExpander} could be found
	 */
	private EMFFormsDMRSegmentExpander getBestSegmentExpander(final VDomainModelReferenceSegment segment)
		throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander bestSegmentExpander = SEGMENTS_RANKING_HELPER
			.getHighestRankingElement(emfFormsDMRSegmentExpanders, expander -> expander.isApplicable(segment));

		if (bestSegmentExpander == null) {
			throw new EMFFormsExpandingFailedException(
				String.format("There is no suitable EMFFormsDMRSegmentExpander for the given segment %1$s.", segment)); //$NON-NLS-1$
		}
		return bestSegmentExpander;
	}
}
