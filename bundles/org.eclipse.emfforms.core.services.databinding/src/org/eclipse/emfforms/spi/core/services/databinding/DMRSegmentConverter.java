/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;

/**
 * @author Lucas Koehler
 *
 */
public interface DMRSegmentConverter {
	/**
	 * The constant defining the priority that a {@link DMRSegmentConverter} is not applicable for a {@link VDMRSegment}
	 * .
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Checks whether the given {@link VDMRSegment} can be converted by this {@link DMRSegmentConverter} to a
	 * {@link IValueProperty}. The return value is the priority of this
	 * converter. The higher the priority, the better suits the converter the given {@link VDMRSegment}.
	 *
	 * @param dmrSegment The {@link VDMRSegment} whose priority is wanted.
	 * @return The priority of the given {@link VDMRSegment}; negative infinity if this converter is not
	 *         applicable.
	 */
	double isApplicable(VDMRSegment dmrSegment);

	/**
	 * Converts a {@link VDMRSegment} with help of an {@link EClass} to an {@link IValueProperty}.
	 *
	 * @param dmrSegment The {@link VDMRSegment} that will be converted to an {@link IListProperty}
	 * @param eClass The {@link EClass} that contains the property described in the {@link VDMRSegment}
	 * @return The created {@link IListProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if no value property could be created due to an invalid {@link VDMRSegment}.
	 */
	IValueProperty convertToValueProperty(VDMRSegment dmrSegment, EClass eClass) throws DatabindingFailedException;

	/**
	 * Converts a {@link VDMRSegment} with help of an {@link EClass} to an {@link IListProperty}.
	 *
	 * @param dmrSegment The {@link VDMRSegment} that will be converted to an {@link IListProperty}
	 * @param eClass The {@link EClass} that contains the property described in the {@link VDMRSegment}
	 * @return The created {@link IListProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if no value property could be created due to an invalid {@link VDMRSegment}.
	 */
	IListProperty convertToListProperty(VDMRSegment dmrSegment, EClass eClass) throws DatabindingFailedException;
}
