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

import java.util.List;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of {@link DomainModelReferenceConverter} that converts {@link VMappingDomainModelReference
 * VMappingDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
public class MappingDomainModelReferenceConverter implements DomainModelReferenceConverter {
	private EMFFormsDatabinding emfFormsDatabinding;
	private ServiceReference<EMFFormsDatabinding> databindingServiceReference;

	/**
	 * Sets the {@link EMFFormsDatabinding}.
	 *
	 * @param emfFormsDatabinding the emfFormsDatabinding to set
	 */
	void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Unsets the {@link EMFFormsDatabinding}.
	 */
	void unsetEMFFormsDatabinding() {
		emfFormsDatabinding = null;
	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverter} is activated. It
	 * retrieves the {@link EMFFormsDatabinding EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	protected final void activate(BundleContext bundleContext) {
		databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabinding.class);
		setEMFFormsDatabinding(bundleContext.getService(databindingServiceReference));

	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverter} is deactivated.
	 * It frees the {@link EMFFormsDatabinding EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	protected final void deactivate(BundleContext bundleContext) {
		unsetEMFFormsDatabinding();
		bundleContext.ungetService(databindingServiceReference);
	}

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
		if (VMappingDomainModelReference.class.isInstance(domainModelReference)) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VMappingDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VMappingDomainModelReference."); //$NON-NLS-1$
		}

		final VMappingDomainModelReference mappingReference = VMappingDomainModelReference.class
			.cast(domainModelReference);
		if (mappingReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VMappingDomainModelReference must not be null."); //$NON-NLS-1$
		}

		checkMapType(mappingReference.getDomainModelEFeature());

		final List<EReference> referencePath = mappingReference.getDomainModelEReferencePath();
		IValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = new EMFMappingValueProperty(mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature());
		} else {
			IEMFValueProperty emfValueProperty = EMFProperties.value(referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}
			final EMFMappingValueProperty mappingValueProperty = new EMFMappingValueProperty(
				mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature());
			valueProperty = emfValueProperty.value(mappingValueProperty);
		}

		return valueProperty.value(emfFormsDatabinding.getValueProperty(mappingReference.getDomainModelReference()));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VMappingDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VMappingDomainModelReference."); //$NON-NLS-1$
		}

		final VMappingDomainModelReference mappingReference = VMappingDomainModelReference.class
			.cast(domainModelReference);
		if (mappingReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VMappingDomainModelReference must not be null."); //$NON-NLS-1$
		}

		checkMapType(mappingReference.getDomainModelEFeature());

		final List<EReference> referencePath = mappingReference.getDomainModelEReferencePath();
		IValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = new EMFMappingValueProperty(mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature());
		} else {
			IEMFValueProperty emfValueProperty = EMFProperties.value(referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}
			final EMFMappingValueProperty mappingValueProperty = new EMFMappingValueProperty(
				mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature());
			valueProperty = emfValueProperty.value(mappingValueProperty);
		}

		return valueProperty.list(emfFormsDatabinding.getListProperty(mappingReference.getDomainModelReference()));
	}

	/**
	 * Checks whether the given structural feature references a proper map to generate a value or list property.
	 *
	 * @param structuralFeature The feature to check
	 * @throws IllegalMapTypeException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new IllegalMapTypeException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new IllegalMapTypeException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature("key"); //$NON-NLS-1$
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature("value"); //$NON-NLS-1$
		if (keyFeature == null || valueFeature == null) {
			throw new IllegalMapTypeException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(valueFeature)) {
			throw new IllegalMapTypeException(
				"The values of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EObjects."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EClasses."); //$NON-NLS-1$
		}
	}
}
