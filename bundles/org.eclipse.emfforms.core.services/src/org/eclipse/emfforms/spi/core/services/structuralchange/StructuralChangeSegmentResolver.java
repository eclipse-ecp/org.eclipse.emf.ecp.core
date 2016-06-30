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
package org.eclipse.emfforms.spi.core.services.structuralchange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface StructuralChangeSegmentResolver {

	/**
	 * The value that expresses that a {@link StructuralChangeSegmentResolver} is not applicable for a
	 * {@link VDomainModelReference}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Returns a double that expresses if and how suitable this tester is for the given
	 * {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment}
	 * @return The value indicating how suitable this tester is, negative infinity if it cannot work with the given
	 *         {@link VDomainModelReferenceSegment}.
	 */
	double isApplicable(VDomainModelReferenceSegment segment);

	/**
	 * Resolves the given {@link VDomainModelReferenceSegment segment} from the given {@link EObject domain object} and
	 * returns the resulting {@link Setting}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} to resolve
	 * @param domainObject The segment's root for which the segment is resolved
	 * @return The resolved {@link Setting} or <strong>null</strong> if no {@link Setting} could be resolved
	 */
	Setting resolveSegment(VDomainModelReferenceSegment segment, EObject domainObject);
}
