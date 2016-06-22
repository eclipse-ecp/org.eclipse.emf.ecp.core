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
package org.eclipse.emfforms.core.services.databinding.featurepath;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.osgi.service.component.annotations.Component;

/**
 * A {@link DomainModelReferenceSegmentConverterEMF} for {@link VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "FeatureDomainModelReferenceSegmentConverter")
public class FeatureDomainModelReferenceSegmentConverter implements DomainModelReferenceSegmentConverterEMF {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return 1d;
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
		final VFeatureDomainModelReferenceSegment featureSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The segment's feature could not be resolved for the given EClass. The segment was %1$s. The EClass was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}
		return EMFEditProperties.value(editingDomain, structuralFeature);
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
		final VFeatureDomainModelReferenceSegment featureSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The segment's feature could not be resolved for the given EClass. The segment was %1$s. The EClass was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}
		return EMFEditProperties.list(editingDomain, structuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment featureSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = eObject.eClass()
			.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The given EOject does not contain the segment's feature. The segment was %1$s. The EObject was %2$s.", //$NON-NLS-1$
				segment, eObject));
		}
		if (structuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null.", structuralFeature.getName())); //$NON-NLS-1$
		}

		return InternalEObject.class.cast(eObject).eSetting(structuralFeature);
	}

	private VFeatureDomainModelReferenceSegment checkAndConvertSegment(VDomainModelReferenceSegment segment)
		throws DatabindingFailedException {
		Assert.create(segment).notNull();
		Assert.create(segment).ofClass(VFeatureDomainModelReferenceSegment.class);

		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;

		if (featureSegment.getDomainModelFeature() == null) {
			throw new DatabindingFailedException("The segment's domain model feature must not be null."); //$NON-NLS-1$
		}
		if (featureSegment.getDomainModelFeature().isEmpty()) {
			throw new DatabindingFailedException("The segment's domain model feature must not be an empty string."); //$NON-NLS-1$
		}

		return featureSegment;
	}
}
