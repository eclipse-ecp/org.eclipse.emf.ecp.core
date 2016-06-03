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
package org.eclipse.emfforms.internal.core.services.databinding.keyattribute;

import java.util.List;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * An implementation of {@link DomainModelReferenceConverterEMF} that converts {@link VKeyAttributeDomainModelReference
 * VKeyAttributeDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@Component(name = "KeyAttributeDomainModelReferenceConverter", service = { DomainModelReferenceConverterEMF.class,
	DomainModelReferenceConverter.class })
public class KeyAttributeDomainModelReferenceConverter implements DomainModelReferenceConverterEMF {
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
		unsetEMFFormsDatabinding();
		if (databindingServiceReference != null) {
			bundleContext.ungetService(databindingServiceReference);
		}
	}

	/**
	 * Returns the {@link EMFFormsDatabindingEMF}.
	 *
	 * @return the {@link EMFFormsDatabindingEMF}
	 */
	private EMFFormsDatabindingEMF getDatabinding() {
		if (emfFormsDatabinding == null) {
			databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabindingEMF.class);
			if (databindingServiceReference == null) {
				throw new IllegalStateException(
					"The org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF Service is not available!"); //$NON-NLS-1$
			}
			setEMFFormsDatabinding(bundleContext.getService(databindingServiceReference));
		}
		return emfFormsDatabinding;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();
		Assert.create(domainModelReference).ofClass(VKeyAttributeDomainModelReference.class);

		final VKeyAttributeDomainModelReference keyAttributeDMR = VKeyAttributeDomainModelReference.class
			.cast(domainModelReference);

		if (keyAttributeDMR.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VKeyAttributeDomainModelReference must not be null."); //$NON-NLS-1$
		}

		checkListType(keyAttributeDMR.getDomainModelEFeature());

		final List<EReference> referencePath = keyAttributeDMR.getDomainModelEReferencePath();
		final IEMFValueProperty keyAttributeValueProperty = new EMFValuePropertyDecorator(
			new EMFKeyAttributeValueProperty(getEditingDomain(object), getDatabinding(), keyAttributeDMR.getKeyDMR(),
				keyAttributeDMR.getKeyValue(), keyAttributeDMR.getDomainModelEFeature()),
			keyAttributeDMR.getDomainModelEFeature());

		IEMFValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = keyAttributeValueProperty;
		} else {
			IEMFValueProperty emfValueProperty = EMFEditProperties
				.value(getEditingDomain(object), referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}

			valueProperty = emfValueProperty.value(keyAttributeValueProperty);
		}

		return valueProperty.value(getDatabinding().getValueProperty(keyAttributeDMR.getValueDMR(), object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();
		Assert.create(domainModelReference).ofClass(VKeyAttributeDomainModelReference.class);

		final VKeyAttributeDomainModelReference keyAttributeDMR = VKeyAttributeDomainModelReference.class
			.cast(domainModelReference);

		if (keyAttributeDMR.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VKeyAttributeDomainModelReference must not be null."); //$NON-NLS-1$
		}

		checkListType(keyAttributeDMR.getDomainModelEFeature());

		final List<EReference> referencePath = keyAttributeDMR.getDomainModelEReferencePath();
		final IEMFValueProperty keyAttributeValueProperty = new EMFValuePropertyDecorator(
			new EMFKeyAttributeValueProperty(getEditingDomain(object), getDatabinding(), keyAttributeDMR.getKeyDMR(),
				keyAttributeDMR.getKeyValue(), keyAttributeDMR.getDomainModelEFeature()),
			keyAttributeDMR.getDomainModelEFeature());

		IEMFValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = keyAttributeValueProperty;
		} else {
			IEMFValueProperty emfValueProperty = EMFEditProperties
				.value(getEditingDomain(object), referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}

			valueProperty = emfValueProperty.value(keyAttributeValueProperty);
		}

		return valueProperty.list(getDatabinding().getListProperty(keyAttributeDMR.getValueDMR(), object));
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
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();
		Assert.create(domainModelReference).ofClass(VKeyAttributeDomainModelReference.class);

		final VKeyAttributeDomainModelReference keyAttributeDMR = VKeyAttributeDomainModelReference.class
			.cast(domainModelReference);

		final IEMFValueProperty valueProperty = convertToValueProperty(domainModelReference, object);
		final IObservableValue observableValue = valueProperty.observe(object);
		final EObject eObject = (EObject) IObserving.class.cast(observableValue).getObserved();
		if (eObject == null) {
			throw new DatabindingFailedException(
				String.format("There is no EObject that contains the key '%s' for the root EObject %s.", //$NON-NLS-1$
					keyAttributeDMR.getKeyValue(), object));
		}

		final EStructuralFeature eStructuralFeature = valueProperty.getStructuralFeature();
		if (eStructuralFeature.getEType() == null) {
			throw new DatabindingFailedException(
				String.format("The eType of the feature %1$s is null.", eStructuralFeature.getName())); //$NON-NLS-1$
		}
		observableValue.dispose();
		return InternalEObject.class.cast(eObject).eSetting(eStructuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		Assert.create(domainModelReference).notNull();
		if (domainModelReference instanceof VKeyAttributeDomainModelReference) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

	/**
	 * Checks whether the given structural feature references a list of {@link EObject EObjects}.
	 *
	 * @param structuralFeature The feature to check
	 * @throws DatabindingFailedException if the structural feature doesn't reference a proper list.
	 */
	private void checkListType(EStructuralFeature structuralFeature) throws DatabindingFailedException {
		if (!structuralFeature.isMany()) {
			throw new DatabindingFailedException(
				"The VKeyAttributeDomainModelReference's domainModelEFeature must reference a list."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(structuralFeature)) {
			throw new DatabindingFailedException(
				"The VKeyAttributeModelReference's domainModelEFeature must reference a list of EObjects."); //$NON-NLS-1$
		}
	}
}
