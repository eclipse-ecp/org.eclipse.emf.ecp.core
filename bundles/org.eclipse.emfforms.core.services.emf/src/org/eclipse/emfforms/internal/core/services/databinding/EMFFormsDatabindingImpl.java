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
 * Eugen Neufeld - changed interface to EMFFormsDatabindingEMF
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFObservable;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;

/**
 * EMF implementation of {@link EMFFormsDatabindingEMF}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDatabindingImpl implements EMFFormsDatabindingEMF {

	private final Set<DomainModelReferenceConverterEMF> referenceConverters = new LinkedHashSet<DomainModelReferenceConverterEMF>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IObservableValue getObservableValue(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (object == null) {
			throw new IllegalArgumentException("The given EObject must not be null."); //$NON-NLS-1$
		}

		final IEMFValueProperty valueProperty = getValueProperty(domainModelReference, object);
		return valueProperty.observe(object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
			domainModelReference);

		if (bestConverter != null) {
			return bestConverter.convertToValueProperty(domainModelReference, object);
		}

		throw new DatabindingFailedException(
			String
				.format(
					"No applicable DomainModelReferenceConverter could be found for %1$s .", //$NON-NLS-1$
					domainModelReference.eClass().getName()));
	}

	/**
	 * Adds the given {@link DomainModelReferenceConverterEMF} to the Set of reference converters.
	 *
	 * @param converter The {@link DomainModelReferenceConverterEMF} to add
	 */
	protected void addDomainModelReferenceConverter(DomainModelReferenceConverterEMF converter) {
		referenceConverters.add(converter);
	}

	/**
	 * Removes the given {@link DomainModelReferenceConverterEMF} to the Set of reference converters.
	 *
	 * @param converter The {@link DomainModelReferenceConverterEMF} to remove
	 */
	protected void removeDomainModelReferenceConverter(DomainModelReferenceConverterEMF converter) {
		referenceConverters.remove(converter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IObservableList getObservableList(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (object == null) {
			throw new IllegalArgumentException("The given EObject must not be null."); //$NON-NLS-1$
		}

		final IListProperty listProperty = getListProperty(domainModelReference, object);
		return listProperty.observe(object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getListProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFListProperty getListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
			domainModelReference);

		if (bestConverter != null) {
			return bestConverter.convertToListProperty(domainModelReference, object);
		}

		throw new DatabindingFailedException("No applicable DomainModelReferenceConverter could be found."); //$NON-NLS-1$
	}

	/**
	 * Returns the most suitable {@link DomainModelReferenceConverter}, that is registered to this
	 * {@link EMFFormsDatabindingImpl}, for the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} for which a {@link DomainModelReferenceConverter}
	 *            is needed
	 * @return The most suitable {@link DomainModelReferenceConverter}
	 */
	private DomainModelReferenceConverterEMF getBestDomainModelReferenceConverter(
		VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		double highestPriority = DomainModelReferenceConverter.NOT_APPLICABLE;
		DomainModelReferenceConverterEMF bestConverter = null;
		for (final DomainModelReferenceConverterEMF converter : referenceConverters) {
			final double priority = converter.isApplicable(domainModelReference);
			if (priority > highestPriority) {
				highestPriority = priority;
				bestConverter = converter;
			}
		}
		return bestConverter;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF#extractFeature(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	public EStructuralFeature extractFeature(IObservableValue observableValue) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableValue)) {
			return IEMFObservable.class.cast(observableValue).getStructuralFeature();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableValue class %1$s is not supported!", observableValue.getClass().getName())); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF#extractFeature(org.eclipse.core.databinding.observable.list.IObservableList)
	 */
	@Override
	public EStructuralFeature extractFeature(IObservableList observableList) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableList)) {
			return IEMFObservable.class.cast(observableList).getStructuralFeature();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableList class %1$s is not supported!", observableList.getClass().getName())); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF#extractObserved(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	public EObject extractObserved(IObservableValue observableValue) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableValue)) {
			return (EObject) IEMFObservable.class.cast(observableValue).getObserved();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableValue class %1$s is not supported!", observableValue.getClass().getName())); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF#extractObserved(org.eclipse.core.databinding.observable.list.IObservableList)
	 */
	@Override
	public EObject extractObserved(IObservableList observableList) throws DatabindingFailedException {
		if (IEMFObservable.class.isInstance(observableList)) {
			return (EObject) IEMFObservable.class.cast(observableList).getObserved();
		}
		throw new DatabindingFailedException(
			String.format("The IObservableList class %1$s is not supported!", observableList.getClass().getName())); //$NON-NLS-1$
	}

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (object == null) {
			throw new IllegalArgumentException("The given EObject must not be null."); //$NON-NLS-1$
		}
		final DomainModelReferenceConverterEMF bestConverter = getBestDomainModelReferenceConverter(
			domainModelReference);

		if (bestConverter != null) {
			return bestConverter.getSetting(domainModelReference, object);
		}

		throw new DatabindingFailedException(
			String
				.format(
					"No applicable DomainModelReferenceConverter could be found for %1$s .", //$NON-NLS-1$
					domainModelReference.eClass().getName()));
	}
}
