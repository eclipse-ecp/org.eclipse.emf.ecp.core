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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
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
		Assert.create(structuralFeatures).notNull();

		final List<VDomainModelReferenceSegment> result = new LinkedList<VDomainModelReferenceSegment>();
		if (structuralFeatures.isEmpty()) {
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
