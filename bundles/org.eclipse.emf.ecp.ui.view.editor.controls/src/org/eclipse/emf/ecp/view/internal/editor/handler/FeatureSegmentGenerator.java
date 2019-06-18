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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/**
 * Default implementation of {@link SegmentGenerator} that generates a path of
 * {@link VFeatureDomainModelReferenceSegment VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureSegmentGenerator implements SegmentGenerator {

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(List<EStructuralFeature> structuralFeatures) {
		Assert.create(structuralFeatures).notNull();
		return structuralFeatures.stream()
			.map(this::createFeatureSegment)
			.collect(Collectors.toList());
	}

	/**
	 * Creates a {@link VFeatureDomainModelReferenceSegment} for the given {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} that defines the path part represented by the created
	 *            segment
	 * @return The created {@link VFeatureDomainModelReference}
	 */
	protected VFeatureDomainModelReferenceSegment createFeatureSegment(final EStructuralFeature structuralFeature) {
		final VFeatureDomainModelReferenceSegment pathSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		pathSegment.setDomainModelFeature(structuralFeature.getName());
		return pathSegment;
	}
}
