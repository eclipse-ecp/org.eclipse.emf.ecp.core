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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.segments;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * This service calculates the root {@link EClass} for a given legacy domain model reference.
 *
 * @author Lucas Koehler
 * @noimplement This interface is not intended to be implemented by clients. Instead, clients should implement
 *              {@link DmrToRootEClassConverter} to support custom {@link VDomainModelReference}
 *              implementations
 * @since 1.22
 */
public interface LegacyDmrToRootEClass {

	/**
	 * Determines the root {@link EClass} of the given legacy domain model reference. Returns nothing, if the DMR is
	 * segment-based, the given DMR is empty, or there is no fitting {@link DmrToRootEClassConverter}.
	 *
	 * @param dmr The {@link VDomainModelReference} whose root EClass is calculated
	 * @return The root EClass of the given DMR or nothing if it could not be determined due to one of the reasons
	 *         listed above.
	 */
	Optional<EClass> getRootEClass(VDomainModelReference dmr);
}
