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
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.osgi.service.component.annotations.Component;

/**
 * {@link DmrSegmentGenerator} that generates {@link VDomainModelReferenceSegment segments} for a
 * {@link VFeaturePathDomainModelReference}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "FeaturePathDmrSegmentGenerator")
public class FeaturePathDmrSegmentGenerator implements DmrSegmentGenerator {

	@Override
	public double isApplicable(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() == VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() != VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE) {
			throw new IllegalArgumentException(
				String.format("The given DMR was no feature path domain model reference. The DMR was: %s", reference)); //$NON-NLS-1$
		}
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) reference;
		final List<VDomainModelReferenceSegment> result = new LinkedList<>();

		// Create segments for reference path
		for (final EReference eReference : featureDmr.getDomainModelEReferencePath()) {
			final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			segment.setDomainModelFeature(eReference.getName());
			result.add(segment);
		}

		// Create segment for the domain model e feature
		if (featureDmr.getDomainModelEFeature() != null) {
			final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			segment.setDomainModelFeature(featureDmr.getDomainModelEFeature().getName());
			result.add(segment);
		}

		return result;
	}

}
