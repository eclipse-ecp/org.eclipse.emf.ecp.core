/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding.emf;

import java.util.Optional;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.ecore.EClass;

/**
 * The result of a
 * {@link DomainModelReferenceSegmentConverterEMF#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
 * execution containing the {@link IEMFListProperty} and the resolved {@link EClass} for the next resolvement step. The
 * EClass is null, if the segment referenced an EAttribute as its domain model feature.
 *
 * @author Lucas Koehler
 * @since 1.19
 *
 */
public interface SegmentConverterListResultEMF {
	/**
	 * The {@link IEMFListProperty} resulting from the segment conversion. Must not be <code>null</code>.
	 *
	 * @return The {@link IEMFListProperty}
	 */
	IEMFListProperty getListProperty();

	/**
	 * Returns the target EClass of the resolved segment. This EClass is the root EClass for the next segment in the
	 * DMR.
	 *
	 * @return The next EClass or nothing if the segment's domain model feature is an attribute.
	 */
	Optional<EClass> getNextEClass();
}
