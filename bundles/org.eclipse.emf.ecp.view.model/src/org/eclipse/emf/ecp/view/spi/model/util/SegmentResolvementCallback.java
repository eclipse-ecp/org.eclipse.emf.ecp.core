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
package org.eclipse.emf.ecp.view.spi.model.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * This callback is called by
 * {@link SegmentResolvementUtil#resolveSegments(java.util.List, org.eclipse.emf.ecore.EClass, SegmentResolvementCallback)}
 * for every {@link EStructuralFeature} that was resolved from
 * a {@link VDomainModelReferenceSegment}.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface SegmentResolvementCallback {

	/**
	 * Processes the resolved {@link EStructuralFeature} (e.g. adds it to a list).
	 *
	 * @param resolvedFeature The resolved {@link EStructuralFeature}.
	 */
	void resolvedFeature(EStructuralFeature resolvedFeature);
}
