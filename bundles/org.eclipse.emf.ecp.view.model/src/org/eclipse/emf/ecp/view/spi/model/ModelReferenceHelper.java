/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - refactoring to segments
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

import java.util.Collection;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Helper class to create {@link VDomainModelReference VDomainModelReferences}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public final class ModelReferenceHelper {

	private ModelReferenceHelper() {
	}

	/**
	 * Create a simple {@link VDomainModelReference} based on a {@link EStructuralFeature}.
	 *
	 * @param feature the feature to use for the {@link VDomainModelReference}
	 * @return the created {@link VDomainModelReference}
	 */
	public static VDomainModelReference createDomainModelReference(EStructuralFeature feature) {
		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		featureSegment.setDomainModelFeature(feature.getName());
		domainModelReference.getSegments().add(featureSegment);
		return domainModelReference;
	}

	/**
	 * Create a simple {@link VDomainModelReference} based on a {@link EStructuralFeature}.
	 *
	 * @param feature the feature to use for the {@link VDomainModelReference}
	 * @param eReferences the collection of {@link EReference EReferences} to use for the {@link VDomainModelReference}
	 * @return the created {@link VDomainModelReference}
	 */

	public static VDomainModelReference createDomainModelReference(EStructuralFeature feature,
		Collection<EReference> eReferences) {
		if (eReferences == null || eReferences.isEmpty()) {
			return createDomainModelReference(feature);
		}

		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();
		for (final EReference reference : eReferences) {
			final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			segment.setDomainModelFeature(reference.getName());
			domainModelReference.getSegments().add(segment);
		}

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		featureSegment.setDomainModelFeature(feature.getName());
		domainModelReference.getSegments().add(featureSegment);

		return domainModelReference;
	}
}
