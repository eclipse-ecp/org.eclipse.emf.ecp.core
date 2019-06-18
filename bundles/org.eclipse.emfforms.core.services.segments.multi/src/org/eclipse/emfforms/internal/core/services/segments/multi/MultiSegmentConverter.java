/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;

/**
 * A {@link DomainModelReferenceSegmentConverterEMF} for {@link VMultiDomainModelReferenceSegment
 * VMultiDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiSegmentConverter", service = {
	DomainModelReferenceSegmentConverterEMF.class })
public class MultiSegmentConverter extends FeatureSegmentConverter {

	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (VMultiDomainModelReferenceSegment.class.isInstance(segment)) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public SegmentConverterValueResultEMF convertToValueProperty(VDomainModelReferenceSegment segment,
		EClass segmentRoot, EditingDomain editingDomain) throws DatabindingFailedException {
		final SegmentConverterValueResultEMF result = super.convertToValueProperty(segment, segmentRoot,
			editingDomain);
		checkForMultiReference(result.getValueProperty().getStructuralFeature());
		return result;
	}

	@Override
	public SegmentConverterListResultEMF convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		final SegmentConverterListResultEMF result = super.convertToListProperty(segment, segmentRoot, editingDomain);
		checkForMultiReference(result.getListProperty().getStructuralFeature());
		return result;
	}

	@Override
	public Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException {
		final Setting setting = super.getSetting(segment, eObject);
		checkForMultiReference(setting.getEStructuralFeature());
		return setting;
	}

	/**
	 * Throws an {@link DatabindingFailedException} if the given {@link EStructuralFeature} is no multi reference.
	 *
	 * @param feature The {@link EStructuralFeature} to check
	 * @throws DatabindingFailedException if the given feature is no multi reference
	 */
	private void checkForMultiReference(EStructuralFeature feature) throws DatabindingFailedException {
		if (!feature.isMany() || !EReference.class.isInstance(feature)) {
			throw new DatabindingFailedException("The multi segment's domain model feature must be a multi reference."); //$NON-NLS-1$
		}
	}
}
