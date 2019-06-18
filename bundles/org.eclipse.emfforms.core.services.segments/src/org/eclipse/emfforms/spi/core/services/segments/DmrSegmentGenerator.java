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
package org.eclipse.emfforms.spi.core.services.segments;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * Implement this to provide {@link VDomainModelReferenceSegment segment} generation for one or more types of
 * {@link VDomainModelReference domain model references}.
 *
 * @author Lucas Koehler
 *
 */
public interface DmrSegmentGenerator {

	/**
	 * The value that expresses that a {@link DmrSegmentGenerator} is not applicable for a
	 * {@link VDomainModelReference}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Returns a double that expresses if and how suitable this tester is for the given {@link VDomainModelReference}.
	 *
	 * @param reference The {@link VDomainModelReference}
	 * @return The value indicating how suitable this tester is, {@link #NOT_APPLICABLE} if it can't work with the given
	 *         {@link VDomainModelReference}.
	 */
	double isApplicable(VDomainModelReference reference);

	/**
	 * Takes a {@link VDomainModelReference} and generates the list of equivalent {@link VDomainModelReferenceSegment
	 * DMR Segments}. Equivalent means that a DMR using the generated segments resolves exactly the same as the given
	 * DMR.
	 *
	 * @param reference The {@link VDomainModelReference} to generate the {@link VDomainModelReferenceSegment
	 *            segments} for
	 * @return The list of generated {@link VDomainModelReferenceSegment segments}; might return an empty list if the
	 *         given DMR does not specify any path but never <code>null</code>
	 */
	List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference);
}
