/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.spi.model.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;

/**
 * This utility class provides methods to resolve {@link VDomainModelReferenceSegment VDomainModelReferenceSegments}
 * against an {@link EClass}.
 *
 * @author Lucas Koehler
 * @since 1.20
 */
public final class SegmentResolvementUtil {

	// Hide constructor for utility class
	private SegmentResolvementUtil() {
	}

	/**
	 * Resolves a path of {@link VDomainModelReferenceSegment VDomainModelReferenceSegments} starting at the given
	 * {@link EClass}. For every {@link EStructuralFeature} which was resolved, the callback is called.
	 *
	 * @param segments The path to resolve
	 * @param rootEClass The root {@link EClass} of the path
	 * @param callback The callback to handle the resolved {@link EStructuralFeature EStructuralFeatures}
	 */
	public static void resolveSegments(List<VDomainModelReferenceSegment> segments, EClass rootEClass,
		Consumer<EStructuralFeature> callback) {
		EClass currentRoot = rootEClass;
		// Resolve path
		for (int i = 0; i < segments.size() - 1; i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			if (!VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
				return;
			}
			final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
			final EStructuralFeature feature = currentRoot
				.getEStructuralFeature(featureSegment.getDomainModelFeature());
			if (feature == null || !EReference.class.isInstance(feature)) {
				return;
			}
			final EReference reference = (EReference) feature;
			currentRoot = reference.getEReferenceType();
			callback.accept(reference);
		}

		// Resolve last segment
		final VDomainModelReferenceSegment segment = segments.get(segments.size() - 1);
		if (!VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return;
		}
		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
		final EStructuralFeature feature = currentRoot.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (feature == null) {
			return;
		}
		callback.accept(feature);
	}

	/**
	 * Resolves a path of {@link VDomainModelReferenceSegment VDomainModelReferenceSegments} starting at the given
	 * {@link EClass}. Returns a list of all resolved {@link EStructuralFeature EStructuralFeatures} with the same order
	 * as the given list of segments.
	 *
	 * @param segments The path to resolve
	 * @param rootEClass The root {@link EClass} of the path
	 * @return The resolved {@link EStructuralFeature EStructuralFeatures}
	 */
	public static List<EStructuralFeature> resolveSegmentsToFeatureList(List<VDomainModelReferenceSegment> segments,
		EClass rootEClass) {
		final List<EStructuralFeature> result = new LinkedList<>();
		resolveSegments(segments, rootEClass, result::add);
		return result;
	}
}
