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
package org.eclipse.emfforms.internal.core.services.databinding.index;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
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
 * An implementation of {@link DomainModelReferenceConverterEMF} that converts {@link VIndexDomainModelReference
 * VIndexDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class IndexDomainModelReferenceConverter implements DomainModelReferenceConverterEMF {
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
	 * @see DomainModelReferenceConverterEMF#isApplicable(VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (domainModelReference instanceof VIndexDomainModelReference) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see DomainModelReferenceConverterEMF#convertToValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);

		final IEMFValueProperty valueProperty;

		if (indexReference.getPrefixDMR() != null) {
			final IEMFValueProperty prefixProperty = getEMFFormsDatabindingEMF().getValueProperty(
				indexReference.getPrefixDMR(),
				object);
			valueProperty = new EMFValuePropertyDecorator(new EMFIndexedValuePropertyDelegator(getEditingDomain(object),
				indexReference.getIndex(), prefixProperty, prefixProperty.getStructuralFeature()),
				prefixProperty.getStructuralFeature());
		} else {
			if (indexReference.getDomainModelEFeature() == null) {
				throw new DatabindingFailedException(
					"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
			}

			checkListType(indexReference.getDomainModelEFeature());

			final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();
			final IEMFValueProperty indexedValueProperty = new EMFValuePropertyDecorator(new EMFIndexedValueProperty(
				getEditingDomain(object),
				indexReference.getIndex(),
				indexReference.getDomainModelEFeature()), indexReference.getDomainModelEFeature());

			if (referencePath.isEmpty()) {
				valueProperty = indexedValueProperty;
			} else {
				IEMFValueProperty emfValueProperty = EMFEditProperties
					.value(getEditingDomain(object), referencePath.get(0));
				for (int i = 1; i < referencePath.size(); i++) {
					emfValueProperty = emfValueProperty.value(referencePath.get(i));
				}

				valueProperty = emfValueProperty.value(indexedValueProperty);
			}
		}
		return valueProperty.value(getEMFFormsDatabindingEMF().getValueProperty(indexReference.getTargetDMR(), object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see DomainModelReferenceConverterEMF#convertToListProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);

		IEMFValueProperty valueProperty;

		if (indexReference.getPrefixDMR() != null) {
			final IEMFValueProperty prefixProperty = getEMFFormsDatabindingEMF().getValueProperty(
				indexReference.getPrefixDMR(),
				object);
			valueProperty = new EMFValuePropertyDecorator(
				new EMFIndexedValuePropertyDelegator(getEditingDomain(object), indexReference.getIndex(),
					prefixProperty, prefixProperty.getStructuralFeature()),
				prefixProperty.getStructuralFeature());
		} else {
			if (indexReference.getDomainModelEFeature() == null) {
				throw new DatabindingFailedException(
					"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
			}

			checkListType(indexReference.getDomainModelEFeature());

			final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();

			final IEMFValueProperty indexedValueProperty = new EMFValuePropertyDecorator(new EMFIndexedValueProperty(
				getEditingDomain(object),
				indexReference.getIndex(),
				indexReference.getDomainModelEFeature()), indexReference.getDomainModelEFeature());

			if (referencePath.isEmpty()) {
				valueProperty = indexedValueProperty;
			} else {
				IEMFValueProperty emfValueProperty = EMFEditProperties
					.value(getEditingDomain(object), referencePath.get(0));
				for (int i = 1; i < referencePath.size(); i++) {
					emfValueProperty = emfValueProperty.value(referencePath.get(i));
				}

				valueProperty = emfValueProperty.value(indexedValueProperty);
			}
		}

		return valueProperty.list(getEMFFormsDatabindingEMF().getListProperty(indexReference.getTargetDMR(), object));
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
				"The VIndexDomainModelReference's domainModelEFeature must reference a list."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(structuralFeature)) {
			throw new IllegalListTypeException(
				"The VIndexDomainModelReference's domainModelEFeature must reference a list of EObjects."); //$NON-NLS-1$
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
	@SuppressWarnings("unchecked")
	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}
		final VIndexDomainModelReference indexDMR = VIndexDomainModelReference.class
			.cast(domainModelReference);

		EList<EObject> eList;
		if (indexDMR.getPrefixDMR() == null) {
			if (indexDMR.getDomainModelEFeature() == null) {
				throw new DatabindingFailedException(
					"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
			}
			checkListType(indexDMR.getDomainModelEFeature());

			EObject currentObject = object;
			for (final EReference eReference : indexDMR.getDomainModelEReferencePath()) {
				currentObject = (EObject) currentObject.eGet(eReference);
				if (currentObject == null) {
					throw new DatabindingFailedException("The path is not fully resolved."); //$NON-NLS-1$
				}
			}
			eList = (EList<EObject>) currentObject.eGet(indexDMR.getDomainModelEFeature());
		} else {
			final Setting setting = getEMFFormsDatabindingEMF().getSetting(indexDMR.getPrefixDMR(), object);
			eList = (EList<EObject>) setting.get(true);
		}
		if (eList.isEmpty()) {
			throw new DatabindingFailedException(
				"The list used by the index dmr mustr must not be empty."); //$NON-NLS-1$
		}
		final EObject eObject = eList.get(indexDMR.getIndex());
		return getEMFFormsDatabindingEMF().getSetting(indexDMR.getTargetDMR(), eObject);
	}
}
