/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
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
		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createDomainModelReference();
		final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
		segment.setPropertyName(feature.getName());
		domainModelReference.getSegments().add(segment);
		return domainModelReference;
	}

	/**
	 * Create a simple {@link VDomainModelReference} based on a {@link EStructuralFeature} and a collection of
	 * {@link EReference EReferences}.
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
		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createDomainModelReference();
		for (final EReference reference : eReferences) {
			final VDMRSegment segment = VViewFactory.eINSTANCE.createDMRSegment();
			segment.setPropertyName(reference.getName());
			domainModelReference.getSegments().add(segment);
		}
		final VDMRSegment featureSegment = VViewFactory.eINSTANCE.createDMRSegment();
		featureSegment.setPropertyName(feature.getName());
		domainModelReference.getSegments().add(featureSegment);
		return domainModelReference;
	}
}
