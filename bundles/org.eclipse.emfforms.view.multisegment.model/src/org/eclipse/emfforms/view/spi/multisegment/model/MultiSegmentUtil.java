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
package org.eclipse.emfforms.view.spi.multisegment.model;

import java.util.Optional;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * Utility methods for dealing with {@link VMultiDomainModelReferenceSegment multi segments}.
 *
 * @author Lucas Koehler
 *
 */
public final class MultiSegmentUtil {

	// This is a utility class that should not be instantiated
	private MultiSegmentUtil() {
	}

	/**
	 * Gets the last segment of the given {@link VDomainModelReference DMR} and casts it to a
	 * {@link VMultiDomainModelReferenceSegment multi segment}.
	 *
	 * @param dmr The {@link VDomainModelReference} to extract the multi segment from
	 * @return The multi segment, or nothing if the dmr's last segment is not a multi segment
	 */
	public static Optional<VMultiDomainModelReferenceSegment> getMultiSegment(VDomainModelReference dmr) {
		if (dmr != null && dmr.getSegments().size() > 0) {
			final VDomainModelReferenceSegment lastSegment = dmr.getSegments().get(dmr.getSegments().size() - 1);
			if (lastSegment instanceof VMultiDomainModelReferenceSegment) {
				return Optional.of((VMultiDomainModelReferenceSegment) lastSegment);
			}
		}
		return Optional.empty();
	}
}
