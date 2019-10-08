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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.internal.reference.table;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;

/**
 * Dummy Domain expander doing nothing because we do not need to expand the extent segments.
 *
 * @author Lucas Koehler
 * @since 1.22
 *
 */
public class DummyDomainExpander implements EMFFormsDMRSegmentExpander {

	private final VDomainModelReferenceSegment segment;

	/**
	 * Default constructor.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} which this expander is applicable for
	 */
	DummyDomainExpander(VDomainModelReferenceSegment segment) {
		this.segment = segment;
	}

	@Override
	public Optional<EObject> prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		// We do not need expansion for the extent segment
		return Optional.empty();
	}

	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		return this.segment == segment ? Double.POSITIVE_INFINITY : NOT_APPLICABLE;
	}

	@Override
	public boolean needsToExpandLastSegment() {
		return false;
	}

}
