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
 * Eugen Neufeld - changed interface to EMFFormsDatabindingEMF
 * Lucas Koehler - adjusted to DMR segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFObservable;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;

/**
 * EMF implementation of {@link EMFFormsDatabindingEMF}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDatabindingImpl implements EMFFormsDatabindingEMF {

	private final Set<DomainModelReferenceSegmentConverterEMF> segmentConverters = new LinkedHashSet<DomainModelReferenceSegmentConverterEMF>();

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
		final Realm realm = Realm.getDefault();
		if (realm != null) {
			return valueProperty.observe(object);
		}
		final DefaultRealm dr = new DefaultRealm();
		final IObservableValue observableValue = valueProperty.observe(object);
		dr.dispose();
		return observableValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			throw new DatabindingFailedException(String.format(
				"The reference being resolved does not contain any segments. The DMR is %1$s.", domainModelReference)); //$NON-NLS-1$
		}

		final EditingDomain editingDomain = getEditingDomain(object);

		// Get value property for the (always present) first segment
		final DomainModelReferenceSegmentConverterEMF firstConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(0));
		IEMFValueProperty resultProperty = firstConverter.convertToValueProperty(segments.get(0),
			object.eClass(), editingDomain);

		/*
		 * Iterate over all remaining segments and get the value properties for their corresponding EClasses.
		 * Get the EClass by getting the target EClass of the EReference from the value property of the previously
		 * resolved segment.
		 */
		EStructuralFeature feature = resultProperty.getStructuralFeature();
		for (int i = 1; i < segments.size(); i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final EClass nextEClass = getNextEClass(domainModelReference, object, feature, segment);

			final DomainModelReferenceSegmentConverterEMF bestConverter = getBestDomainModelReferenceSegmentConverter(
				segment);
			final IEMFValueProperty nextProperty = bestConverter.convertToValueProperty(segment, nextEClass,
				editingDomain);
			feature = nextProperty.getStructuralFeature();

			// Chain the properties together
			resultProperty = resultProperty.value(nextProperty);
		}

		return resultProperty;
	}

	/**
	 * Adds the given {@link DomainModelReferenceSegmentConverterEMF} to the Set of segment converters.
	 *
	 * @param converter The {@link DomainModelReferenceSegmentConverterEMF} to add
	 */
	protected void addDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF converter) {
		segmentConverters.add(converter);
	}

	/**
	 * Removes the given {@link DomainModelReferenceSegmentConverterEMF} from the Set of segment converters.
	 *
	 * @param converter The {@link DomainModelReferenceSegmentConverterEMF} to remove
	 */
	protected void removeDomainModelReferenceSegmentConverter(DomainModelReferenceSegmentConverterEMF converter) {
		segmentConverters.remove(converter);
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
		final Realm realm = Realm.getDefault();
		if (realm != null) {
			return listProperty.observe(object);
		}
		final DefaultRealm dr = new DefaultRealm();
		final IObservableList observableList = listProperty.observe(object);
		dr.dispose();
		return observableList;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getListProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IEMFListProperty getListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			throw new DatabindingFailedException(String.format(
				"The reference being resolved does not contain any segments. The DMR is %1$s.", domainModelReference)); //$NON-NLS-1$
		}

		final EditingDomain editingDomain = getEditingDomain(object);

		// If there is only one segment, get its list property. Otherwise, get its value property
		final DomainModelReferenceSegmentConverterEMF firstConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(0));
		if (segments.size() == 1) {
			// If there is only one segment, directly return its list property
			return firstConverter.convertToListProperty(segments.get(0), object.eClass(), editingDomain);
		}

		IEMFValueProperty valueProperty = firstConverter.convertToValueProperty(segments.get(0),
			object.eClass(), editingDomain);

		/*
		 * Iterate over all "middle" segments and get the value properties for their corresponding EClasses.
		 * Get the EClass by getting the target EClass of the EReference from the value property of the previously
		 * resolved segment.
		 */
		EStructuralFeature feature = valueProperty.getStructuralFeature();
		for (int i = 1; i < segments.size() - 1; i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final EClass nextEClass = getNextEClass(domainModelReference, object, feature, segment);

			final DomainModelReferenceSegmentConverterEMF bestConverter = getBestDomainModelReferenceSegmentConverter(
				segment);
			final IEMFValueProperty nextProperty = bestConverter.convertToValueProperty(segment, nextEClass,
				editingDomain);
			feature = nextProperty.getStructuralFeature();

			// Chain the properties together
			valueProperty = valueProperty.value(nextProperty);
		}

		// Get the list property for the last segment
		final int lastIndex = segments.size() - 1;
		final EClass lastEClass = getNextEClass(domainModelReference, object, feature, segments.get(lastIndex));
		final DomainModelReferenceSegmentConverterEMF lastConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(lastIndex));
		final IEMFListProperty listProperty = lastConverter.convertToListProperty(segments.get(lastIndex), lastEClass,
			editingDomain);

		return valueProperty.list(listProperty);
	}

	/**
	 * @param domainModelReference only needed for exception description
	 * @param object only needed for exception description
	 * @param feature The feature to extract the next {@link EClass} from
	 * @param segment only needed for exception description
	 * @return The next EClass if the given {@link EStructuralFeature} is a single {@link EReference}, throws a
	 *         {@link DatabindingFailedException} otherwise.
	 * @throws DatabindingFailedException if the given {@link EStructuralFeature} is not a single {@link EReference}
	 */
	private EClass getNextEClass(VDomainModelReference domainModelReference, EObject object, EStructuralFeature feature,
		final VDomainModelReferenceSegment segment) throws DatabindingFailedException {
		if (!EReference.class.isInstance(feature)) {
			throw new DatabindingFailedException(String.format(
				"The reference being resolved is not compatible with the given root EObject: " //$NON-NLS-1$
					+ "Segment [%1$s] cannot be resolved as the preceding segment did not resolve to an EReference. " //$NON-NLS-1$
					+ "The DMR is %2$s. The root EObject is %3$s.", //$NON-NLS-1$
				segment, domainModelReference, object));
		}
		if (feature.isMany()) {
			throw new DatabindingFailedException(String.format(
				"The path is not fully resolved. The reference being resolved is not a single reference [%1$s]. The DMR is %2$s.", //$NON-NLS-1$
				feature, domainModelReference));
		}
		return ((EReference) feature).getEReferenceType();
	}

	/**
	 * Returns the most suitable {@link DomainModelReferenceSegmentConverterEMF}, that is registered to this
	 * {@link EMFFormsDatabindingImpl}, for the given {@link VDomainModelReferenceSegment}.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} for which a
	 *            {@link DomainModelReferenceSegmentConverterEMF}
	 *            is needed
	 * @return The most suitable {@link DomainModelReferenceSegmentConverterEMF}, does not return <code>null</code>
	 * @throws DatabindingFailedException if no suitable segment converter could be found
	 */
	private DomainModelReferenceSegmentConverterEMF getBestDomainModelReferenceSegmentConverter(
		VDomainModelReferenceSegment segment) throws DatabindingFailedException {

		Assert.create(segment).notNull();

		double highestPriority = DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE;
		DomainModelReferenceSegmentConverterEMF bestConverter = null;
		for (final DomainModelReferenceSegmentConverterEMF converter : segmentConverters) {
			final double priority = converter.isApplicable(segment);
			if (priority > highestPriority) {
				highestPriority = priority;
				bestConverter = converter;
			}
		}

		if (bestConverter == null) {
			throw new DatabindingFailedException(String
				.format("No suitable DomainModelReferenceSegmentConverter could be found for segment %1$s", segment)); //$NON-NLS-1$
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
		Assert.create(domainModelReference).notNull();
		Assert.create(object).notNull();

		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			throw new DatabindingFailedException(String.format(
				"The reference being resolved does not contain any segments. The DMR is %1$s.", domainModelReference)); //$NON-NLS-1$
		}

		// Retrieve the first setting
		final DomainModelReferenceSegmentConverterEMF firstConverter = getBestDomainModelReferenceSegmentConverter(
			segments.get(0));
		Setting setting = firstConverter.getSetting(segments.get(0), object);

		/*
		 * If present, iterate over the remaining segments. For every iteration step, use the resolved EObject of the
		 * previously resolved Setting in order to resolve the next Setting.
		 */
		for (int i = 1; i < segments.size(); i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final Object nextObject = setting.get(true);

			if (!EObject.class.isInstance(nextObject)) {
				throw new DatabindingFailedException(
					String.format(
						"The Setting could not be fully resolved because an intermediate Object was no EObject or was null. " //$NON-NLS-1$
							+ "The DMR was %1$s. The last resolved segment was %2$s. The root EObject was %3$s.", //$NON-NLS-1$
						domainModelReference, segments.get(i - 1), object));
			}

			final EObject nextEObject = (EObject) nextObject;
			final DomainModelReferenceSegmentConverterEMF converter = getBestDomainModelReferenceSegmentConverter(
				segment);
			setting = converter.getSetting(segment, nextEObject);
		}

		return setting;
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}
}
