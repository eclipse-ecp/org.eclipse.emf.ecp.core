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
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * A concrete structural change tester for a type of {@link VDomainModelReferenceSegment}.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface StructuralChangeSegmentTester {
	/**
	 * The value that expresses that a {@link StructuralChangeSegmentTester} is not applicable for a
	 * {@link VDomainModelReferenceSegment}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Returns a double that expresses if and how suitable this tester is for the given
	 * {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment}
	 * @return The value indicating how suitable this tester is, negative infinity if it can't work with the given
	 *         {@link VDomainModelReferenceSegment}.
	 */
	double isApplicable(VDomainModelReferenceSegment segment);

	/**
	 * Checks whether the domain structure of the given {@link VDomainModelReferenceSegment} has changed for the changes
	 * indicated by the given {@link ModelChangeNotification}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment}
	 * @param domainObject The domain object of the {@link VDomainModelReferenceSegment segment}.
	 * @param notification The {@link ModelChangeNotification}
	 * @return true if the domain structure has changed, false otherwise
	 */
	boolean isStructureChanged(VDomainModelReferenceSegment segment, EObject domainObject,
		ModelChangeNotification notification);

}
