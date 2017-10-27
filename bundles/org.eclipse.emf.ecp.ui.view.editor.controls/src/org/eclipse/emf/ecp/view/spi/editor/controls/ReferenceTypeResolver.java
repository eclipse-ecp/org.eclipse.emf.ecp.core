/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.editor.controls;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * Used to resolve the root EClass for the next path segment from an EReference for a certain type of
 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment DMR segment}.
 * <p>
 * Example: The reference type of a map referenced by a mapping segment is
 * M<code>ap$Entry&lt;EClass, ? extends EObject&gt;</code>.
 * However, the root EClass for the next path segment must be the second type parameter of <code>Map$Entry</code>.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface ReferenceTypeResolver {

	/**
	 * Resolves the root EClass for the next reference path segment.
	 * Returns <strong>null</strong> if no valid type can be resolved.
	 *
	 * @param reference The {@link EReference} to resolve the {@link EClass} from
	 * @param segment The segment that contains the {@link EReference}. For some segment types (e.g. mapping segments)
	 *            this allows to return a more precise {@link EClass}. May be <code>null</code>.
	 * @return the resolved {@link EClass} or <strong>null</strong> if no valid type can be resolved
	 */
	EClass resolveNextEClass(EReference reference, VDomainModelReferenceSegment segment);
}
