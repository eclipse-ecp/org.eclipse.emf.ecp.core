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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.handler.AdvancedCreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;

/**
 * A {@link SegmentIdeDescriptor} provides information about one type of {@link VDomainModelReferenceSegment} relevant
 * to the IDE tooling. For instance, this information is needed by the {@link AdvancedCreateDomainModelReferenceWizard}
 * to properly create DMRs with different segment types.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface SegmentIdeDescriptor {
	/**
	 * Returns the {@link EClass} of the segment type that is described by this {@link SegmentIdeDescriptor}.
	 *
	 * @return The segment's {@link EClass}
	 */
	EClass getSegmentType();

	/**
	 * Defines whether the described segment type may be created by users.
	 *
	 * @return <code>true</code> if users may create the type, <code>false</code> otherwise
	 */
	boolean isAvailableInIde();

	/**
	 * Defines whether the described segment may appear in the middle of a reference path or if the segment is only
	 * allowed to be at the end of a path.
	 *
	 * @return <code>true</code> if the segment must only be at the end of a path, <code>false</code> otherwise
	 */
	boolean isLastElementInPath();

	/**
	 * Returns an {@link EStructuralFeatureSelectionValidator} that determines whether an {@link EStructuralFeature} is
	 * a
	 * valid reference path part for the described segment type. E.g. an index segment needs a multi reference or
	 * attribute.
	 *
	 * @return The {@link EStructuralFeatureSelectionValidator}
	 */
	EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator();

	/**
	 * Returns a {@link ReferenceTypeResolver} that resolves the root {@link EClass} for the next reference path segment
	 * from an EReference.
	 *
	 * @return The {@link ReferenceTypeResolver}
	 */
	ReferenceTypeResolver getReferenceTypeResolver();
}
