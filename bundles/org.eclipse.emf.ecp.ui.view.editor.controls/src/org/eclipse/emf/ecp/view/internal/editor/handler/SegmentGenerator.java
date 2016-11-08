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

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * Generates a path of {@link VDomainModelReferenceSegment VDomainModelReferenceSegments} from a path of
 * {@link EStructuralFeature EStructuralFeatures}.
 * <p>
 * Implemented by clients to define which segments are generated.
 *
 * @author Lucas Koehler
 *
 */
public interface SegmentGenerator {

	/**
	 * Generates a list of {@link VDomainModelReferenceSegment VDomainModelReferenceSegments} for a given list of
	 * {@link EStructuralFeature EStructuralFeatures}.
	 *
	 * @param structuralFeatures The {@link EStructuralFeature EStructuralFeatures} to generate the segments for
	 * @return The list of generated segments which has the same order as the list of corresponding structural features
	 */
	List<VDomainModelReferenceSegment> generateSegments(List<EStructuralFeature> structuralFeatures);
}
