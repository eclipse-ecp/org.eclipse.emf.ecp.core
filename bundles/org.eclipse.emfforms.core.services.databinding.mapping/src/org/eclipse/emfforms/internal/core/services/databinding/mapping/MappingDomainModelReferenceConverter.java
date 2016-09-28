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

import java.util.List;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Implementation of {@link DomainModelReferenceConverterEMF} that converts {@link VMappingDomainModelReference
 * VMappingDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class MappingDomainModelReferenceConverter implements DomainModelReferenceConverterEMF {
	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private ServiceReference<EMFFormsDatabindingEMF> databindingServiceReference;
	private BundleContext bundleContext;

	/**
	 * Sets the {@link EMFFormsDatabindingEMF}.
	 *
	 * @param emfFormsDatabinding the emfFormsDatabinding to set
	 */
	void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Unsets the {@link EMFFormsDatabindingEMF}.
	 */
	void unsetEMFFormsDatabinding() {
		emfFormsDatabinding = null;
	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverterEMF} is activated. It
	 * retrieves the {@link EMFFormsDatabindingEMF EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Activate
	protected final void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverterEMF} is deactivated.
	 * It frees the {@link EMFFormsDatabindingEMF EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Deactivate
	protected final void deactivate(BundleContext bundleContext) {
		if (databindingServiceReference != null) {
			bundleContext.ungetService(databindingServiceReference);
			unsetEMFFormsDatabinding();
		}
	}

	private EMFFormsDatabindingEMF getEMFFormsDatabindingEMF() {
		if (emfFormsDatabinding == null) {
			databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabindingEMF.class);
			if (databindingServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDatabindingEMF available!"); //$NON-NLS-1$
			}
			setEMFFormsDatabinding(bundleContext.getService(databindingServiceReference));
		}
		return emfFormsDatabinding;
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
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,EObject)
	 */
	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
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
		final IEMFValueProperty mappingValueProperty = new EMFValuePropertyDecorator(
			new EMFMappingValueProperty(getEditingDomain(object),
				mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature()),
			mappingReference.getDomainModelEFeature());

		IEMFValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = mappingValueProperty;
		} else {
			IEMFValueProperty emfValueProperty = EMFEditProperties
				.value(getEditingDomain(object), referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}

			valueProperty = emfValueProperty.value(mappingValueProperty);
		}

		return valueProperty
			.value(getEMFFormsDatabindingEMF().getValueProperty(mappingReference.getDomainModelReference(),
				object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,EObject)
	 */
	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
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

		final IEMFValueProperty mappingValueProperty = new EMFValuePropertyDecorator(
			new EMFMappingValueProperty(getEditingDomain(object),
				mappingReference.getMappedClass(),
				mappingReference.getDomainModelEFeature()),
			mappingReference.getDomainModelEFeature());

		IEMFValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = mappingValueProperty;
		} else {
			IEMFValueProperty emfValueProperty = EMFEditProperties
				.value(getEditingDomain(object), referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}

			valueProperty = emfValueProperty.value(mappingValueProperty);
		}

		return valueProperty
			.list(getEMFFormsDatabindingEMF().getListProperty(mappingReference.getDomainModelReference(),
				object));
	}

	/**
	 * Checks whether the given structural feature references a proper map to generate a value or list property.
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

	/**
	 * Checks basic required properties of the given {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to check
	 * @throws IllegalMapTypeException if something's wrong with the feature
	 */
	private void checkStructuralFeature(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		if (structuralFeature.getEType() == null) {
			throw new IllegalMapTypeException(
				"The EType of the VMappingDomainModelReference's domainModelEFeature was null."); //$NON-NLS-1$
		}
		if (structuralFeature.getEType().getInstanceClassName() == null) {
			throw new IllegalMapTypeException(
				"The InstanceClassName of the VMappingDomainModelReference's domainModelEFeature's EType was null."); //$NON-NLS-1$
		}
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new IllegalMapTypeException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new IllegalMapTypeException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
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
		final IEMFValueProperty valueProperty = convertToValueProperty(domainModelReference, object);
		final IObservableValue observableValue = valueProperty.observe(object);
		final EObject eObject = (EObject) IObserving.class.cast(observableValue).getObserved();
		final EStructuralFeature eStructuralFeature = valueProperty.getStructuralFeature();
		if (eStructuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null.", eStructuralFeature.getName())); //$NON-NLS-1$
		}
		return InternalEObject.class.cast(eObject).eSetting(eStructuralFeature);
	}
}
