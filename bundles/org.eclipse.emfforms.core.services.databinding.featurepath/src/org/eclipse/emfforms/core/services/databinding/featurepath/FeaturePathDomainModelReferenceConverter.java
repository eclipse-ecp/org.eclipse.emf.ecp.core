/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 553224
 ******************************************************************************/
package org.eclipse.emfforms.core.services.databinding.featurepath;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
				String.format("DomainModelReference %1$s is not an instance of VFeaturePathDomainModelReference.", //$NON-NLS-1$
					identify(domainModelReference)));
		}

		final VFeaturePathDomainModelReference featurePathReference = (VFeaturePathDomainModelReference) domainModelReference;
		if (featurePathReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VFeaturePathDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (featurePathReference.getDomainModelEFeature().eIsProxy()) {
			throw new DatabindingFailedException(
				String.format("The domainModelEFeature %1$s of the given DMR %2$s is a proxy.", //$NON-NLS-1$
					identify(featurePathReference.getDomainModelEFeature()), identify(featurePathReference)));
		}
		for (final EReference path : featurePathReference.getDomainModelEReferencePath()) {
			if (path.eIsProxy()) {
				throw new DatabindingFailedException(
					String.format("The path reference %1$s of the given DMR %2$s is a proxy.", //$NON-NLS-1$
						identify(path), identify(featurePathReference)));
			}
		}
		return featurePathReference;
	}

	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {

		final EditingDomain editingDomain = getEditingDomain(object);
		return convertToValueProperty(domainModelReference, null, editingDomain);
	}

	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException {

		final VFeaturePathDomainModelReference featurePathReference = checkAndConvertDMR(domainModelReference);
		final List<EReference> referencePath = featurePathReference.getDomainModelEReferencePath();
		if (referencePath.isEmpty()) {
			return EMFEditProperties.value(editingDomain, featurePathReference.getDomainModelEFeature());
		}

		if (referencePath.get(0).isMany()) {
			throw new DatabindingFailedException(String.format(
				"The path is not fully resolved. The reference being resolved is not a single reference: %1$s. The DMR is %2$s.", //$NON-NLS-1$
				identify(referencePath.get(0)), identify(domainModelReference)));
		}
		IEMFValueProperty emfValueProperty = EMFEditProperties.value(editingDomain, referencePath.get(0));
		for (int i = 1; i < referencePath.size(); i++) {
			final EReference eReference = referencePath.get(i);
			if (eReference.isMany()) {
				throw new DatabindingFailedException(String.format(
					"The path is not fully resolved. The reference being resolved is not a single reference: %1$s. The DMR is %2$s.", //$NON-NLS-1$
					identify(eReference), identify(domainModelReference)));
			}
			emfValueProperty = emfValueProperty.value(eReference);
		}
		return emfValueProperty.value(featurePathReference.getDomainModelEFeature());
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

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

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final VFeaturePathDomainModelReference featurePathReference = checkAndConvertDMR(domainModelReference);

		EObject currentObject = object;
		for (final EReference eReference : featurePathReference.getDomainModelEReferencePath()) {
			if (eReference.isMany()) {
				throw new DatabindingFailedException(String.format(
					"The path is not fully resolved. The reference being resolved is not a single reference: %1$s. The DMR is %2$s. Last resolved EObject is %3$s.", //$NON-NLS-1$
					identify(eReference), identify(domainModelReference), identify(currentObject)));
			}
			if (currentObject.eClass().getFeatureID(eReference) == -1) {
				throw new DatabindingFailedException(String.format(
					"Given EClass %1$s has no such feature %2$s. The DMR is %3$s. Last resolved EObject is %4$s.", //$NON-NLS-1$
					identify(currentObject.eClass()), identify(eReference),
					identify(domainModelReference), identify(currentObject)));
			}
			final EObject nextObject = (EObject) currentObject.eGet(eReference);
			if (nextObject == null) {
				throw new DatabindingFailedException(String.format(
					"The path is not fully resolved. The DMR is %1$s. Last resolved EObject is %2$s. Reference being resolved is %3$s.", //$NON-NLS-1$
					identify(domainModelReference), identify(currentObject), identify(eReference)));
			}
			currentObject = nextObject;
		}
		final EStructuralFeature structuralFeature = featurePathReference.getDomainModelEFeature();
		if (structuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null in DMR %2$s.", identify(structuralFeature), //$NON-NLS-1$
					identify(domainModelReference)));
		}
		if (currentObject.eClass().getEAllStructuralFeatures().contains(structuralFeature)) {
			return InternalEObject.class.cast(currentObject).eSetting(structuralFeature);
		}
		throw new DatabindingFailedException(String.format("The resolved EObject %1$s doesn't have the feature %2$s.", //$NON-NLS-1$
			identify(currentObject), identify(structuralFeature)));
	}

	private static String identify(EObject eObject) {
		if (eObject == null) {
			return "<null>"; //$NON-NLS-1$
		}

		final StringBuilder result = new StringBuilder();

		// First, a trimmed variant of the to-string representation (omit Java package)
		result.append(eObject.toString());
		final int hashCodePos = result.indexOf("@"); //$NON-NLS-1$
		if (hashCodePos > 0) {
			final int lastDotPos = result.lastIndexOf(".", hashCodePos); //$NON-NLS-1$
			if (lastDotPos > 0) {
				result.delete(0, lastDotPos + 1);
			}
		}

		final Resource resource = eObject.eResource();
		if (resource != null) {
			result.append('<');
			result.append(EcoreUtil.getURI(eObject));
			result.append('>');
		}

		return result.toString();
	}

	private static String identify(ENamedElement element) {
		if (element == null) {
			return "<null>"; //$NON-NLS-1$
		}

		final StringBuilder result = new StringBuilder();

		if (element.eIsProxy()) {
			// Infer as much as possible
			final URI proxyURI = EcoreUtil.getURI(element);

			// Get the qualified ENamedElement name
			String fragment = proxyURI.fragment();
			while (!fragment.isEmpty() && fragment.charAt(0) == '/') {
				fragment = fragment.substring(1);
			}
			result.append(fragment.replace('/', '.'));

			// Get the package namespace (or Ecore path)
			result.append(" ("); //$NON-NLS-1$
			result.append(proxyURI.trimFragment());
			result.append(')');
		} else {
			result.append(element.getName());

			for (EObject container = element.eContainer(); container instanceof ENamedElement; container = container
				.eContainer()) {

				final ENamedElement parent = (ENamedElement) container;

				if (parent instanceof EPackage) {
					result.append(" ("); //$NON-NLS-1$
					result.append(((EPackage) container).getNsURI());
					result.append(')');
				} else {
					result.insert(0, '.');
					result.insert(0, parent.getName());
				}
			}
		}

		return result.toString();
	}

}
