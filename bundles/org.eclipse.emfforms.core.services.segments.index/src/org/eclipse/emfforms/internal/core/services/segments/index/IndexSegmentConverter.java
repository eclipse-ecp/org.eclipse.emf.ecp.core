/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.index;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.core.services.databinding.SegmentConverterValueResultImpl;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;

/**
 * Converts {@link VIndexDomainModelReferenceSegment VIndexDomainModelReferenceSegments} to value and list properties,
 * and to {@link Setting settings}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@Component(name = "IndexSegmentConverter")
public class IndexSegmentConverter implements DomainModelReferenceSegmentConverterEMF {

	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		Assert.create(segment).notNull();
		if (segment instanceof VIndexDomainModelReferenceSegment) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public SegmentConverterValueResultEMF convertToValueProperty(VDomainModelReferenceSegment segment,
		EClass segmentRoot, EditingDomain editingDomain) throws DatabindingFailedException {
		final VIndexDomainModelReferenceSegment indexSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = segmentRoot
			.getEStructuralFeature(indexSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The segment's feature could not be resolved for the given EClass. The segment was %1$s. The EClass was %2$s.", //$NON-NLS-1$
				segment, segmentRoot));
		}

		checkListType(structuralFeature);

		final EMFIndexedValueProperty indexProperty = new EMFIndexedValueProperty(editingDomain,
			indexSegment.getIndex(), structuralFeature);
		final IEMFValueProperty resultProperty = new EMFValuePropertyDecorator(indexProperty, structuralFeature);
		if (EReference.class.isInstance(structuralFeature)) {
			return new SegmentConverterValueResultImpl(resultProperty,
				((EReference) structuralFeature).getEReferenceType());
		}
		return new SegmentConverterValueResultImpl(resultProperty, null);
	}

	@Override
	public SegmentConverterListResultEMF convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		throw new UnsupportedOperationException(
			"A VIndexDomainModelReferenceSegment cannot be converted to a list property, only to a value property."); //$NON-NLS-1$
	}

	@Override
	public Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException {
		final VIndexDomainModelReferenceSegment indexSegment = checkAndConvertSegment(segment);

		final EStructuralFeature structuralFeature = eObject.eClass()
			.getEStructuralFeature(indexSegment.getDomainModelFeature());
		if (structuralFeature == null) {
			throw new DatabindingFailedException(String.format(
				"The given EOject does not contain the segment's feature. The segment was %1$s. The EObject was %2$s.", //$NON-NLS-1$
				segment, eObject));
		}
		if (structuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null.", structuralFeature.getName())); //$NON-NLS-1$
		}

		checkListType(structuralFeature);

		return new IndexedSetting(eObject, structuralFeature, indexSegment.getIndex());
	}

	private VIndexDomainModelReferenceSegment checkAndConvertSegment(VDomainModelReferenceSegment segment)
		throws DatabindingFailedException {
		Assert.create(segment).notNull();
		Assert.create(segment).ofClass(VIndexDomainModelReferenceSegment.class);

		final VIndexDomainModelReferenceSegment indexSegment = (VIndexDomainModelReferenceSegment) segment;

		if (indexSegment.getDomainModelFeature() == null) {
			throw new DatabindingFailedException("The segment's domain model feature must not be null."); //$NON-NLS-1$
		}
		if (indexSegment.getDomainModelFeature().isEmpty()) {
			throw new DatabindingFailedException("The segment's domain model feature must not be an empty string."); //$NON-NLS-1$
		}

		return indexSegment;
	}

	/**
	 * Checks whether the given structural feature references a proper list to generate a value or list property.
	 *
	 * @param structuralFeature The feature to check
	 * @throws IllegalListTypeException if the structural feature doesn't reference a proper list.
	 */
	private void checkListType(EStructuralFeature structuralFeature) throws IllegalListTypeException {
		if (!structuralFeature.isMany()) {
			throw new IllegalListTypeException(
				"The VIndexDomainModelReferenceSegment's domainModelFeature must reference a list."); //$NON-NLS-1$
		}
	}
}
