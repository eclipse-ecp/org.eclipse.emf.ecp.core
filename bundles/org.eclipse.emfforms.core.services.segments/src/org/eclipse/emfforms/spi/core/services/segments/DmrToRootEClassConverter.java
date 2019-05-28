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
package org.eclipse.emfforms.spi.core.services.segments;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Determines the root {@link EClass} for a subset of legacy domain model references. Do not use this directly, use
 * {@link LegacyDmrToRootEClass} instead.
 *
 * @author Lucas Koehler
 * @since 1.22
 */
public interface DmrToRootEClassConverter {

	/**
	 * The value that expresses that a {@link DmrToRootEClassConverter} is not applicable for a
	 * {@link VDomainModelReference}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Returns a double that expresses if and how suitable this DmrToRootEClassConverter is for the given
	 * {@link VDomainModelReference}.
	 *
	 * @param dmr The {@link VDomainModelReference}
	 * @return The value indicating how suitable this tester is, {@link #NOT_APPLICABLE} if it can't work with the given
	 *         {@link VDomainModelReference}.
	 */
	double isApplicable(VDomainModelReference dmr);

	/**
	 * Determines the root {@link EClass} of the given legacy domain model reference. Throws an exception if the root
	 * EClass could not be determined because this means that the given DMR is invalid.
	 *
	 * @param dmr The {@link VDomainModelReference} whose root EClass is calculated
	 * @return The root EClass of the given dmr
	 * @throws IllegalArgumentException if the root EClass cannot be determined because of an invalid dmr
	 */
	EClass getRootEClass(VDomainModelReference dmr) throws IllegalArgumentException;
}
