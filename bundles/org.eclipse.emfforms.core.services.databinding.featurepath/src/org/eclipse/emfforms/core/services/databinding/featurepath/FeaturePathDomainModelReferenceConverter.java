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
package org.eclipse.emfforms.core.services.databinding.featurepath;

import java.util.List;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;

/**
 * An implementation of {@link DomainModelReferenceConverter} that converts {@link VFeaturePathDomainModelReference}s.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDomainModelReferenceConverter implements DomainModelReferenceConverter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (domainModelReference instanceof VFeaturePathDomainModelReference) {
			return 0;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VFeaturePathDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VFeaturePathDomainModelReference."); //$NON-NLS-1$
		}

		final VFeaturePathDomainModelReference featurePathReference = (VFeaturePathDomainModelReference) domainModelReference;
		if (featurePathReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VFeaturePathDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (featurePathReference.getDomainModelEFeature().eIsProxy()) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VFeaturePathDomainModelReference must not be a proxy."); //$NON-NLS-1$
		}

		final List<EReference> referencePath = featurePathReference.getDomainModelEReferencePath();
		final EditingDomain editingDomain = getEditingDomain(object);
		if (referencePath.isEmpty()) {
			return EMFEditProperties.value(editingDomain, featurePathReference.getDomainModelEFeature());
		}

		IEMFValueProperty emfValueProperty = EMFEditProperties.value(editingDomain, referencePath.get(0));
		for (int i = 1; i < referencePath.size(); i++) {
			emfValueProperty = emfValueProperty.value(referencePath.get(i));
		}
		return emfValueProperty.value(featurePathReference.getDomainModelEFeature());
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws DatabindingFailedException
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VFeaturePathDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VFeaturePathDomainModelReference."); //$NON-NLS-1$
		}

		final VFeaturePathDomainModelReference featurePathReference = (VFeaturePathDomainModelReference) domainModelReference;
		if (featurePathReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VFeaturePathDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (featurePathReference.getDomainModelEFeature().eIsProxy()) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VFeaturePathDomainModelReference must not be a proxy."); //$NON-NLS-1$
		}

		final List<EReference> referencePath = featurePathReference.getDomainModelEReferencePath();
		final EditingDomain editingDomain = getEditingDomain(object);
		if (referencePath.isEmpty()) {
			return EMFEditProperties.list(editingDomain, featurePathReference.getDomainModelEFeature());
		}

		IEMFValueProperty emfValueProperty = EMFEditProperties.value(editingDomain, referencePath.get(0));
		for (int i = 1; i < referencePath.size(); i++) {
			emfValueProperty = emfValueProperty.value(referencePath.get(i));
		}
		return emfValueProperty.list(featurePathReference.getDomainModelEFeature());
	}

}
