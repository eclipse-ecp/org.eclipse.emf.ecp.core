/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.domainexpander;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * This service offers the method {@link #prepareDomainObject(VDomainModelReference, EObject)} that allows to expand a
 * given {@link EObject domain object} for a {@link VDomainModelReference}. The method
 * {@link #isApplicable(VDomainModelReference)} is used to determine how suitable this service is for a certain
 * {@link VDomainModelReference}.
 * <p>
 * <strong>Note:</strong> This interface is not intended for public use but defines the services which are internally
 * used in the {@link EMFFormsDomainExpander}.
 *
 * @author Lucas Koehler
 * @since 1.7
 *
 */
public interface EMFFormsDMRExpander {

	/**
	 * This value is returned by {@link #isApplicable(VDomainModelReference)} if the {@link EMFFormsDMRExpander} is not
	 * applicable for the given {@link VDomainModelReference} and {@link EObject
	 * domain object}.
	 */
	Double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Prepares a {@link EObject domain object} for the given {@link VDomainModelReference}. Thereby, the path defined
	 * by the {@link VDomainModelReference} is analyzed and missing objects in the domain model are created. Thereby,
	 * the {@link VDomainModelReference} is not changed.
	 * <p>
	 * Example:<br/>
	 * DMR: A -> B -> x<br/>
	 * domain model is instance of A but does not reference an instance of B<br/>
	 * => An instance of B is created and referenced by the domain model.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} for which the {@link EObject domain object} should
	 *            be prepared.
	 * @param domainObject The {@link EObject domain object} to prepare.
	 * @throws EMFFormsExpandingFailedException if the domain expansion fails.
	 */
	void prepareDomainObject(VDomainModelReference domainModelReference, EObject domainObject)
		throws EMFFormsExpandingFailedException;

	/**
	 * Returns how suitable this {@link EMFFormsDMRExpander} is for the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} for which a {@link EObject domain object} should
	 *            be prepared.
	 * @return a value indicating how suitable this {@link EMFFormsDMRExpander} is to expand a {@link EObject domain
	 *         object} for the given {@link VDomainModelReference}. Returns NOT_APPLICABLE if it's not applicable.
	 */
	double isApplicable(VDomainModelReference domainModelReference);
}
