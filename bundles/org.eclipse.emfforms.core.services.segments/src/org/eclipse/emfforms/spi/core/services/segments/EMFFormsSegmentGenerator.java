/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.segments;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * An {@link EMFFormsSegmentGenerator} allows to generate segments based on a given {@link VDomainModelReference}.
 * Thereby, the generated segments resolve equivalent to the given DMR.
 *
 * @author Lucas Koehler
 * @noimplement This interface is not intended to be implemented by clients. Instead, clients should implement
 *              {@link DmrSegmentGenerator} to support custom {@link VDomainModelReference domain model reference}
 *              implementations
 */
public interface EMFFormsSegmentGenerator {

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