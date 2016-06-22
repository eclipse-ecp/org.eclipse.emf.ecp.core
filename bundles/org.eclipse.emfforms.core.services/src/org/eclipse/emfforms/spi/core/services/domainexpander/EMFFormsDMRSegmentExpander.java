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
package org.eclipse.emfforms.spi.core.services.domainexpander;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * This service offers the method {@link #prepareDomainObject(VDomainModelReferenceSegment, EObject)} that allows to
 * expand a given {@link EObject domain object} for a {@link VDomainModelReferenceSegment}. The method
 * {@link #isApplicable(VDomainModelReferenceSegment)} is used to determine how suitable this service is for a certain
 * {@link VDomainModelReferenceSegment}.
 * <p>
 * <strong>Note:</strong> This interface is not intended for public use but defines the services which are internally
 * used in the {@link EMFFormsDMRSegmentExpander}.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface EMFFormsDMRSegmentExpander {
	/**
	 * This value is returned by {@link #isApplicable(VDomainModelReferenceSegment)} if the
	 * {@link EMFFormsDMRSegmentExpander} is not applicable for the given {@link VDomainModelReferenceSegment}.
	 */
	Double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Prepares a {@link EObject domain object} for the given {@link VDomainModelReferenceSegment}. Thereby, the feature
	 * defined by the segment is analyzed and if it is a reference, the missing target object will be created. Thereby,
	 * the segment is not changed. The target of the segment's feature is returned. This is the created {@link EObject}
	 * if it was created by this method or the already existing target of the segment's feature.
	 * <p>
	 * Example:<br/>
	 * DMR: A -a-> B<br/>
	 * The domain model is instance of A but does not reference an instance of B. The segment contains the feature 'a'.
	 * <br/>
	 * => An instance of B is created and referenced by the domain model. B is returned by this expander.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} for which the {@link EObject domain object} should be
	 *            prepared.
	 * @param domainObject The {@link EObject domain object} to prepare.
	 * @return The new target of the segment's feature. If an {@link EObject} was created, it is returned,
	 *         otherwise the already existing target is returned. Does not return <code>null</code>.
	 * @throws EMFFormsExpandingFailedException if the domain expansion fails.
	 */
	EObject prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException;

	/**
	 * Returns how suitable this {@link EMFFormsDMRSegmentExpander} is for the given
	 * {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} for which an {@link EObject domain object} should
	 *            be prepared.
	 * @return a value indicating how suitable this {@link EMFFormsDMRSegmentExpander} is to expand a {@link EObject
	 *         domain object} for the given {@link VDomainModelReferenceSegment}. Returns NOT_APPLICABLE if it's not
	 *         applicable.
	 */
	double isApplicable(VDomainModelReferenceSegment segment);
}
