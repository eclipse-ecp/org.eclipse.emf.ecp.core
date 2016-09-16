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
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.osgi.service.component.annotations.Component;

/**
 * Converts {@link VMappingDomainModelReferenceSegment VIndexDomainModelReferenceSegments} to value and list properties,
 * and to {@link Setting settings}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@Component(name = "MappingDomainModelReferenceSegmentConverter")
public class MappingDomainModelReferenceSegmentConverter implements DomainModelReferenceSegmentConverterEMF {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		Assert.create(segment).notNull();
		if (segment instanceof VMappingDomainModelReferenceSegment) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)
	 */
	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {

		final VMappingDomainModelReferenceSegment mappingSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(mappingSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The given EOject does not contain the segment's feature. The segment was %1$s. The EObject was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}
		checkMapType(structuralFeature);

		final EMFMappingValueProperty mappingProperty = new EMFMappingValueProperty(editingDomain,
			mappingSegment.getMappedClass(), structuralFeature);
		return new EMFValuePropertyDecorator(mappingProperty, structuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)
	 */
	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		throw new UnsupportedOperationException(
			"A VMappingDomainModelReferenceSegment cannot be converted to a list property, only to a value property."); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException {

		final VMappingDomainModelReferenceSegment mappingSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = eObject.eClass()
			.getEStructuralFeature(mappingSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The given EOject does not contain the segment's feature. The segment was %1$s. The EObject was %2$s.", //$NON-NLS-1$
				segment, eObject));
		}
		checkMapType(structuralFeature);

		return new MappedSetting(eObject, structuralFeature, mappingSegment.getMappedClass());
	}

	/**
	 * Checks whether the given segment is a valid {@link VMappingDomainModelReferenceSegment}. If yes, convert and
	 * return it. If no, throw an exception.
	 *
	 * @param segment The segment to check and convert
	 * @return The converted segment
	 * @throws DatabindingFailedException If the given segment is not a valid mapping segment
	 */
	private VMappingDomainModelReferenceSegment checkAndConvertSegment(VDomainModelReferenceSegment segment)
		throws DatabindingFailedException {
		Assert.create(segment).notNull();
		Assert.create(segment).ofClass(VMappingDomainModelReferenceSegment.class);

		final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) segment;

		if (mappingSegment.getDomainModelFeature() == null) {
			throw new DatabindingFailedException("The segment's domain model feature must not be null."); //$NON-NLS-1$
		}
		if (mappingSegment.getDomainModelFeature().isEmpty()) {
			throw new DatabindingFailedException("The segment's domain model feature must not be an empty string."); //$NON-NLS-1$
		}
		if (mappingSegment.getMappedClass() == null) {
			throw new DatabindingFailedException("The mapping segment's mapped class must not be null."); //$NON-NLS-1$
		}

		return mappingSegment;
	}

	/**
	 * Checks whether the given structural feature references a proper map.
	 *
	 * @param structuralFeature The feature to check
	 * @throws IllegalMapTypeException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		checkStructuralFeature(structuralFeature);

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature("key"); //$NON-NLS-1$
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature("value"); //$NON-NLS-1$
		if (keyFeature == null || valueFeature == null) {
			throw new IllegalMapTypeException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the segment's structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the segment's structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
	}

	/**
	 * Checks basic required properties of the given {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to check
	 * @throws IllegalMapTypeException if something's wrong with the feature
	 */
	private void checkStructuralFeature(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		if (structuralFeature.getEType() == null) {
			throw new IllegalMapTypeException(
				"The EType of the segment's structural feature was null."); //$NON-NLS-1$
		}
		if (structuralFeature.getEType().getInstanceClassName() == null) {
			throw new IllegalMapTypeException(
				"The InstanceClassName of the segment's structural feature's EType was null."); //$NON-NLS-1$
		}
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new IllegalMapTypeException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new IllegalMapTypeException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
	}
}
