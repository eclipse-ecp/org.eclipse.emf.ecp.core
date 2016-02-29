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

import java.util.List;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;

/**
 * An implementation of {@link DomainModelReferenceConverterEMF} that converts {@link VFeaturePathDomainModelReference}
 * s.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDomainModelReferenceConverter implements DomainModelReferenceConverterEMF {

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

	private VFeaturePathDomainModelReference checkAndConvertDMR(VDomainModelReference domainModelReference)
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
		return featurePathReference;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final VFeaturePathDomainModelReference featurePathReference = checkAndConvertDMR(domainModelReference);

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
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final VFeaturePathDomainModelReference featurePathReference = checkAndConvertDMR(domainModelReference);

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final VFeaturePathDomainModelReference featurePathReference = checkAndConvertDMR(domainModelReference);

		EObject currentObject = object;
		for (final EReference eReference : featurePathReference.getDomainModelEReferencePath()) {
			final EObject nextObject = (EObject) currentObject.eGet(eReference);
			if (nextObject == null) {
				throw new DatabindingFailedException(String.format(
					"The path is not fully resolved. The DMR is %1$s. Last resolved EObject is %2$s. Reference beeing resolved is %3$s.", //$NON-NLS-1$
					domainModelReference, currentObject, eReference));
			}
			currentObject = nextObject;
		}
		final EStructuralFeature structuralFeature = featurePathReference.getDomainModelEFeature();
		if (structuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null.", structuralFeature.getName())); //$NON-NLS-1$
		}
		if (currentObject.eClass().getEAllStructuralFeatures().contains(structuralFeature)) {
			return InternalEObject.class.cast(currentObject).eSetting(structuralFeature);
		}
		throw new DatabindingFailedException(String.format("The resolved Object %1$s doesn't contain the feature %2$s.", //$NON-NLS-1$
			currentObject.eClass().getName(), structuralFeature.getName()));
	}

}
