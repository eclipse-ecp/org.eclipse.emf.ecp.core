/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.core.services.databinding.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * An EMFFormsSegmentResolver resolves a {@link VDomainModelReferenceSegment} from a {@link EObject domain object} to
 * the corresponding {@link Setting}.
 *
 * @author Lucas Koehler
 * @since 1.19
 *
 */
public interface EMFFormsSegmentResolver {

	/**
	 * Resolves the given {@link VDomainModelReferenceSegment segment} from the given {@link EObject domain object} and
	 * returns the resulting {@link Setting}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} to resolve
	 * @param domainObject The segment's root for which the segment is resolved
	 * @return The resolved {@link Setting}
	 * @throws DatabindingFailedException if no {@link Setting} could be resolved
	 */
	Setting resolveSegment(VDomainModelReferenceSegment segment, EObject domainObject)
		throws DatabindingFailedException;
}
