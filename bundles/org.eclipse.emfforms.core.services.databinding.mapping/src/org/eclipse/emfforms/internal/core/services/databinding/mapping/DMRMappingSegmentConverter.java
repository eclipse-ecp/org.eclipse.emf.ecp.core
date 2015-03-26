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
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment;

/**
 * @author Lucas Koehler
 *
 */
public class DMRMappingSegmentConverter implements DMRSegmentConverter {

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
		if (VDMRMappingSegment.class.isInstance(dmrSegment)) {
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
			throw new IllegalArgumentException("The given EClass must not be null."); //$NON-NLS-1$
		}
		if (!VDMRMappingSegment.class.isInstance(dmrSegment)) {
			throw new IllegalArgumentException("The given DMR segment must be a DMR mapping segment."); //$NON-NLS-1$
		}

		final EStructuralFeature structuralFeature = eClass.getEStructuralFeature(dmrSegment.getPropertyName());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(
				"The property described in the given VDMRMappingSegment is no EStrucutralFeature of the given EClass."); //$NON-NLS-1$
		}

		checkMapType(structuralFeature);

		final VDMRMappingSegment mappingSegment = (VDMRMappingSegment) dmrSegment;
		return new EMFMappingValueProperty(mappingSegment.getMappedClass(), structuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDMRSegment,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IListProperty convertToListProperty(VDMRSegment dmrSegment, EClass eClass) throws DatabindingFailedException {
		throw new UnsupportedOperationException("Cannot convert a VDMRMappingSegment to an IListProperty."); //$NON-NLS-1$
	}

	/**
	 * Checks whether the given structural feature references a proper map to generate a value property.
	 *
	 * @param structuralFeature The feature to check
	 * @throws IllegalMapTypeException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new IllegalMapTypeException("The VDMRMappingSegment must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new IllegalMapTypeException("The VDMRMappingSegment must reference a map."); //$NON-NLS-1$
		}

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature("key"); //$NON-NLS-1$
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature("value"); //$NON-NLS-1$
		if (keyFeature == null || valueFeature == null) {
			throw new IllegalMapTypeException("The VDMRMappingSegment must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(valueFeature)) {
			throw new IllegalMapTypeException(
				"The values of the map referenced by the VDMRMappingSegment must be referenced EObjects."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the VDMRMappingSegment must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the VDMRMappingSegment must be referenced EClasses."); //$NON-NLS-1$
		}
	}
}
