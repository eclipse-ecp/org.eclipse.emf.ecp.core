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

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * An implementation of {@link DomainModelReferenceConverter} that converts {@link VIndexDomainModelReference
 * VIndexDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
public class IndexDomainModelReferenceConverter implements DomainModelReferenceConverter {
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
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#isApplicable(VDomainModelReference)
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
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);

		final IValueProperty valueProperty;

		if (indexReference.getPrefixDMR() != null) {
			final IValueProperty prefixProperty = emfFormsDatabinding.getValueProperty(indexReference.getPrefixDMR(),
				object);
			valueProperty = new EMFIndexedValuePropertyDelegator(getEditingDomain(object),
				indexReference.getIndex(), prefixProperty,
				EStructuralFeature.class.cast(prefixProperty.getValueType()));
		}
		else {
			if (indexReference.getDomainModelEFeature() == null) {
				throw new DatabindingFailedException(
					"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
			}

			checkListType(indexReference.getDomainModelEFeature());

			final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();

			if (referencePath.isEmpty()) {
				valueProperty = new EMFIndexedValueProperty(getEditingDomain(object), indexReference.getIndex(),
					indexReference.getDomainModelEFeature());
			} else {
				IEMFValueProperty emfValueProperty = EMFEditProperties
					.value(getEditingDomain(object), referencePath.get(0));
				for (int i = 1; i < referencePath.size(); i++) {
					emfValueProperty = emfValueProperty.value(referencePath.get(i));
				}
				final EMFIndexedValueProperty indexedValueProperty = new EMFIndexedValueProperty(
					getEditingDomain(object),
					indexReference.getIndex(),
					indexReference.getDomainModelEFeature());
				valueProperty = emfValueProperty.value(indexedValueProperty);
			}
		}
		return valueProperty.value(emfFormsDatabinding.getValueProperty(indexReference.getTargetDMR(), object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);

		IValueProperty valueProperty;

		if (indexReference.getPrefixDMR() != null) {
			final IValueProperty prefixProperty = emfFormsDatabinding.getValueProperty(indexReference.getPrefixDMR(),
				object);
			valueProperty = new EMFIndexedValuePropertyDelegator(getEditingDomain(object), indexReference.getIndex(),
				prefixProperty,
				EStructuralFeature.class.cast(prefixProperty.getValueType()));
		}
		else {
			if (indexReference.getDomainModelEFeature() == null) {
				throw new DatabindingFailedException(
					"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
			}

			checkListType(indexReference.getDomainModelEFeature());

			final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();

			if (referencePath.isEmpty()) {
				valueProperty = new EMFIndexedValueProperty(getEditingDomain(object), indexReference.getIndex(),
					indexReference.getDomainModelEFeature());
			} else {
				IEMFValueProperty emfValueProperty = EMFEditProperties
					.value(getEditingDomain(object), referencePath.get(0));
				for (int i = 1; i < referencePath.size(); i++) {
					emfValueProperty = emfValueProperty.value(referencePath.get(i));
				}
				final EMFIndexedValueProperty indexedValueProperty = new EMFIndexedValueProperty(
					getEditingDomain(object),
					indexReference.getIndex(),
					indexReference.getDomainModelEFeature());
				valueProperty = emfValueProperty.value(indexedValueProperty);
			}
		}

		return valueProperty.list(emfFormsDatabinding.getListProperty(indexReference.getTargetDMR(), object));
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
}
