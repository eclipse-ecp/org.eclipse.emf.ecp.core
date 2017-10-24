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
 * Lucas Koehler - adjusted to DMR Segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.common.RankingHelper.RankTester;
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
	private final Set<EMFFormsDMRSegmentExpander> emfFormsDMRSegmentExpanders = new CopyOnWriteArraySet<EMFFormsDMRSegmentExpander>();

	private static final RankingHelper<EMFFormsDMRSegmentExpander> RANKING_HELPER = //
		new RankingHelper<EMFFormsDMRSegmentExpander>(
			EMFFormsDMRSegmentExpander.class, EMFFormsDMRSegmentExpander.NOT_APPLICABLE,
			EMFFormsDMRSegmentExpander.NOT_APPLICABLE);

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void prepareDomainObject(final VDomainModelReference domainModelReference, final EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			// TODO ?throw Exception?
			return;
		}

		EObject currentDomainObject = domainObject;
		for (int i = 0; i < segments.size(); i++) {
			if (currentDomainObject == null) {
				throw new EMFFormsExpandingFailedException(
					"Could not finish expansion because a segment could not be expanded."); //$NON-NLS-1$
			}
			final VDomainModelReferenceSegment segment = segments.get(i);
			final EMFFormsDMRSegmentExpander expander = getBestSegmentExpander(segment);

			// Only expand the last segment if it is necessary
			if (i == segments.size() - 1 && !expander.needsToExpandLastSegment()) {
				break;
			}
			currentDomainObject = expander.prepareDomainObject(segment, currentDomainObject);
		}
	}

	/**
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} to get the best expander for
	 * @return the most suitable {@link EMFFormsDMRSegmentExpander} for the given segment
	 * @throws EMFFormsExpandingFailedException if no {@link EMFFormsDMRSegmentExpander} could be found
	 */
	private EMFFormsDMRSegmentExpander getBestSegmentExpander(final VDomainModelReferenceSegment segment)
		throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander bestSegmentExpander = RANKING_HELPER
			.getHighestRankingElement(emfFormsDMRSegmentExpanders, new RankTester<EMFFormsDMRSegmentExpander>() {

				@Override
				public double getRank(EMFFormsDMRSegmentExpander element) {
					return element.isApplicable(segment);
				}
			});

		if (bestSegmentExpander == null) {
			throw new EMFFormsExpandingFailedException(
				String.format("There is no suitable EMFFormsDMRSegmentExpander for the given segment %1$s.", segment)); //$NON-NLS-1$
		}

		return bestSegmentExpander;
	}
}
