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
package org.eclipse.emfforms.internal.core.services.databinding.segment;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * Implementation of {@link DMRSegmentConverter} for 'normal' segments.
 *
 * @author Lucas Koehler
 *
 */
public class DMRSegmentConverterImpl implements DMRSegmentConverter {

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
		return 1d;
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

		final EStructuralFeature structuralFeature = eClass.getEStructuralFeature(dmrSegment.getPropertyName());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(
				"The property described in the given VDMRSegment is no EStrucutral Feature of the given Eclass."); //$NON-NLS-1$
		}

		return EMFProperties.value(structuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IListProperty convertToListProperty(VDMRSegment dmrSegment, EClass eClass) throws DatabindingFailedException {
		if (dmrSegment == null) {
			throw new IllegalArgumentException("The given domain model reference segment must not be null."); //$NON-NLS-1$
		}
		if (eClass == null) {
			throw new IllegalArgumentException("The given Eclass must not be null."); //$NON-NLS-1$
		}

		final EStructuralFeature structuralFeature = eClass.getEStructuralFeature(dmrSegment.getPropertyName());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(
				"The property described in the given VDMRSegment is no EStrucutral Feature of the given Eclass."); //$NON-NLS-1$
		}

		return EMFProperties.list(structuralFeature);
	}

}
