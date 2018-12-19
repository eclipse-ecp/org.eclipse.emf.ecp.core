/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.databinding.edit.IEMFEditListProperty;
import org.eclipse.emf.databinding.edit.IEMFEditValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.core.services.databinding.SegmentConverterListResultImpl;
import org.eclipse.emfforms.internal.core.services.databinding.SegmentConverterValueResultImpl;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.osgi.service.component.annotations.Component;

/**
 * A {@link DomainModelReferenceSegmentConverterEMF} for {@link VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "FeatureSegmentConverter")
@SuppressWarnings("restriction")
public class FeatureSegmentConverter implements DomainModelReferenceSegmentConverterEMF {

	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public SegmentConverterValueResultEMF convertToValueProperty(VDomainModelReferenceSegment segment,
		EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment featureSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The segment's feature could not be resolved for the given EClass. The segment was %1$s. The EClass was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}
		final IEMFEditValueProperty valueProperty = EMFEditProperties.value(editingDomain, structuralFeature);
		if (EReference.class.isInstance(structuralFeature)) {
			return new SegmentConverterValueResultImpl(valueProperty,
				((EReference) structuralFeature).getEReferenceType());
		}
		return new SegmentConverterValueResultImpl(valueProperty, null);
	}

	@Override
	public SegmentConverterListResultEMF convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		final VFeatureDomainModelReferenceSegment featureSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(featureSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The segment's feature could not be resolved for the given EClass. The segment was %1$s. The EClass was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}
		final IEMFEditListProperty listProperty = EMFEditProperties.list(editingDomain, structuralFeature);
		if (EReference.class.isInstance(structuralFeature)) {
			return new SegmentConverterListResultImpl(listProperty,
				((EReference) structuralFeature).getEReferenceType());
		}
		return new SegmentConverterListResultImpl(listProperty, null);
	}

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
