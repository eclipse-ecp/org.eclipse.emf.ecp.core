/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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

import java.util.Optional;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;

/**
 * The result of a
 * {@link DomainModelReferenceSegmentConverterEMF#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}
 * execution containing the {@link IEMFValueProperty} and the resolved {@link EClass} for the next resolvement step. The
 * EClass is null, if the segment referenced an EAttribute as its domain model feature.
 *
 * @author Lucas Koehler
 * @since 1.19
 *
 */
public interface SegmentConverterValueResultEMF {

	/**
	 * The {@link IEMFValueProperty} resulting from the segment conversion. Must not be <code>null</code>.
	 *
	 * @return The {@link IEMFValueProperty}
	 */
	IEMFValueProperty getValueProperty();

	/**
	 * Returns the target EClass of the resolved segment. This EClass is the root EClass for the next segment in the
	 * DMR.
	 *
	 * @return The next EClass or nothing if the segment's domain model feature is an attribute.
	 */
	Optional<EClass> getNextEClass();
}
