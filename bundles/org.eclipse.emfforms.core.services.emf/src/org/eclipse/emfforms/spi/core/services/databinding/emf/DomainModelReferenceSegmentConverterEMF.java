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
package org.eclipse.emfforms.spi.core.services.databinding.emf;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * Converts a {@link VDomainModelReferenceSegment} to an {@link IEMFValueProperty} or an {@link IEMFListProperty}.
 *
 * @author Lucas Koehler
 * @since 2.0
 *
 */
public interface DomainModelReferenceSegmentConverterEMF {

	/**
	 * The constant defining the priority that a {@link DomainModelReferenceSegmentConverterEMF} is not suitable for a
	 * {@link VDomainModelReferenceSegment}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Checks whether the given {@link VDomainModelReferenceSegment} can be converted by this
	 * {@link DomainModelReferenceSegmentConverterEMF} to an {@link IValueProperty} or an {@link IListProperty}. The
	 * return value is the priority of this converter. The higher the priority, the better the converter suits the given
	 * {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} whose priority is wanted.
	 * @return The priority of the given {@link VDomainModelReferenceSegment}; negative infinity if this converter is
	 *         not applicable.
	 */
	double isApplicable(VDomainModelReferenceSegment segment);

	/**
	 * Converts a {@link VDomainModelReferenceSegment} to an {@link IValueProperty}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} that will be converted to an {@link IValueProperty}
	 * @param segmentRoot The root {@link EClass} of the {@link VDomainModelReferenceSegment segment} used to resolve
	 *            the segment to an {@link EStructuralFeature}. This means the {@link EClass} has to contain the feature
	 *            used in the segment
	 * @param editingDomain The {@link EditingDomain} in which the {@link IEMFValueProperty} is created
	 * @return The created {@link IEMFValueProperty}, does not return <code>null</code>
	 * @throws DatabindingFailedException if no value property could be created
	 */
	IEMFValueProperty convertToValueProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException;

	/**
	 * Converts a {@link VDomainModelReferenceSegment} to an {@link IListProperty}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} that will be converted to an {@link IValueProperty}
	 * @param segmentRoot The root {@link EClass} of the {@link VDomainModelReferenceSegment segment} used to resolve
	 *            the segment to an {@link EStructuralFeature}. This means the {@link EClass} has to contain the feature
	 *            used in the segment
	 * @param editingDomain The {@link EditingDomain} in which the {@link IEMFValueProperty} is created
	 * @return The created {@link IEMFListProperty}, does not return <code>null</code>
	 * @throws DatabindingFailedException if no value property could be created
	 */
	IEMFListProperty convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException;

	/**
	 * Retrieve the Setting which is described by the provided {@link VDomainModelReferenceSegment} and the provided
	 * {@link EObject}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} to use to retrieve the setting
	 * @param eObject The {@link EObject} to use to retrieve the setting. This {@link EObject} must have the same
	 *            {@link EClass} as the feature used in the segment
	 * @return The Setting being described by the {@link VDomainModelReferenceSegment segment} and {@link EObject}
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 */
	Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException;
}
