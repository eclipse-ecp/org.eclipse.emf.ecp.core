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
package org.eclipse.emfforms.internal.core.services.databinding.multi;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;

/**
 * A {@link DomainModelReferenceSegmentConverterEMF} for {@link VMultiDomainModelReferenceSegment
 * VMultiDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiDomainModelReferenceSegmentConverter", service = {
	DomainModelReferenceSegmentConverterEMF.class })
public class MultiDomainModelReferenceSegmentConverter extends FeatureDomainModelReferenceSegmentConverter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (VMultiDomainModelReferenceSegment.class.isInstance(segment)) {
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
		final IEMFValueProperty valueProperty = super.convertToValueProperty(segment, segmentRoot, editingDomain);
		checkForMultiReference(valueProperty.getStructuralFeature());
		return valueProperty;
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
		final IEMFListProperty listProperty = super.convertToListProperty(segment, segmentRoot, editingDomain);
		checkForMultiReference(listProperty.getStructuralFeature());
		return listProperty;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
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
