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
package org.eclipse.emfforms.internal.core.services.databinding.index;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment;

/**
 * Implementation of {@link DMRSegmentConverter} that properly converts {@link VDMRIndexSegment VDMRIndexSegments} to
 * value and list properties.
 *
 * @author Lucas Koehler
 *
 */
public class DMRIndexSegmentConverter implements DMRSegmentConverter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDMRSegment)
	 */
	@Override
	public double isApplicable(VDMRSegment dmrSegment) {
		if (dmrSegment == null) {
			throw new IllegalArgumentException("The VDMRSegment must not be null."); //$NON-NLS-1$
		}
		if (VDMRIndexSegment.class.isInstance(dmrSegment)) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDMRSegment dmrSegment, EClass eClass)
		throws DatabindingFailedException {
		if (dmrSegment == null) {
			throw new IllegalArgumentException("The given domain model reference segment must not be null."); //$NON-NLS-1$
		}
		if (eClass == null) {
			throw new IllegalArgumentException("The given Eclass must not be null."); //$NON-NLS-1$
		}
		if (!VDMRIndexSegment.class.isInstance(dmrSegment)) {
			throw new IllegalArgumentException("The given DMR segment must be a DMR index segment."); //$NON-NLS-1$
		}

		final EStructuralFeature structuralFeature = eClass.getEStructuralFeature(dmrSegment.getPropertyName());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(
				"The property described in the given VDMRIndexSegment is no EStrucutral Feature of the given EClass."); //$NON-NLS-1$
		}
		checkListTypeForValueProperty(structuralFeature);

		final VDMRIndexSegment indexSegment = (VDMRIndexSegment) dmrSegment;
		return new EMFIndexedValueProperty(indexSegment.getIndex(), structuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IListProperty convertToListProperty(VDMRSegment dmrSegment, EClass eClass) throws DatabindingFailedException {
		throw new UnsupportedOperationException("Cannot convert a VDMRIndexSegment to a IListProperty."); //$NON-NLS-1$

		// Not useful, yet.
		// if (dmrSegment == null) {
		//			throw new IllegalArgumentException("The given domain model reference segment must not be null."); //$NON-NLS-1$
		// }
		// if (eClass == null) {
		//			throw new IllegalArgumentException("The given EClass must not be null."); //$NON-NLS-1$
		// }
		// if (!VDMRIndexSegment.class.isInstance(dmrSegment)) {
		//			throw new IllegalArgumentException("The given DMR segment must be a DMR index segment."); //$NON-NLS-1$
		// }
		//
		// final EStructuralFeature structuralFeature = eClass.getEStructuralFeature(dmrSegment.getPropertyName());
		// if (structuralFeature == null) {
		// throw new DatabindingFailedException(
		//				"The property described in the given VDMRIndexSegment is no EStrucutral Feature of the given EClass."); //$NON-NLS-1$
		// }
		//
		// final VDMRIndexSegment indexSegment = (VDMRIndexSegment) dmrSegment;
		// return new EMFIndexedListProperty(indexSegment.getIndex(), structuralFeature);
	}

	private void checkListTypeForValueProperty(EStructuralFeature structuralFeature) throws IllegalListTypeException {
		if (!structuralFeature.isMany()) {
			throw new IllegalListTypeException("The VDMRIndexSegment must reference a list."); //$NON-NLS-1$
		}
	}
}
