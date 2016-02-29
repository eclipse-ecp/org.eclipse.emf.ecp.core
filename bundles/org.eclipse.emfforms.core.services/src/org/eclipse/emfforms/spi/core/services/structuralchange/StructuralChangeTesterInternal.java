/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.structuralchange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * A concrete structural change listener for a type of {@link VDomainModelReference}.
 *
 * @author Lucas Koehler
 * @since 1.8
 *
 */
public interface StructuralChangeTesterInternal {

	/**
	 * The value that expresses that a {@link StructuralChangeTesterInternal} is not applicable for a
	 * {@link VDomainModelReference}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Returns a double that expresses if and how suitable this tester is for the given {@link VDomainModelReference}.
	 *
	 * @param reference The {@link VDomainModelReference}
	 * @return The value indicating how suitable this tester is, negative infinity if it can't work with the given
	 *         {@link VDomainModelReference}.
	 */
	double isApplicable(VDomainModelReference reference);

	/**
	 * Checks whether the domain structure of the given {@link VDomainModelReference} has changed for the changes
	 * indicated by the given {@link ModelChangeNotification}.
	 *
	 * @param reference The {@link VDomainModelReference}
	 * @param domainRootObject The root object of the {@link VDomainModelReference}.
	 * @param notification The {@link ModelChangeNotification}
	 * @return true if the domain structure has changed, false otherwise
	 */
	boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification);
}
