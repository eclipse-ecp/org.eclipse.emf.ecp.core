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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;

/**
 * Generates a segment path ending with a {@link VMultiDomainModelReferenceSegment}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSegmentGenerator extends FeatureSegmentGenerator {

	/**
	 * {@inheritDoc}
	 * <p>
	 * The last segment will be a {@link VMultiDomainModelReferenceSegment}.
	 */
	@Override
	public List<VDomainModelReferenceSegment> generateSegments(List<EStructuralFeature> structuralFeatures) {
		final List<VDomainModelReferenceSegment> result = new LinkedList<VDomainModelReferenceSegment>();
		if (structuralFeatures == null || structuralFeatures.isEmpty()) {
			return result;
		}
		for (int i = 0; i < structuralFeatures.size() - 1; i++) {
			result.add(createFeatureSegment(structuralFeatures.get(i)));
		}

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EStructuralFeature lastFeature = structuralFeatures.get(structuralFeatures.size() - 1);
		multiSegment.setDomainModelFeature(lastFeature.getName());
		result.add(multiSegment);

		return result;
	}

}
