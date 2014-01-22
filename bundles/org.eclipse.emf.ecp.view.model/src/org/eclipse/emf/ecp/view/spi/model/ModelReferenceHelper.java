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
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(feature);
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
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(feature);
		domainModelReference.getDomainModelEReferencePath().addAll(eReferences);
		return domainModelReference;
	}
}
