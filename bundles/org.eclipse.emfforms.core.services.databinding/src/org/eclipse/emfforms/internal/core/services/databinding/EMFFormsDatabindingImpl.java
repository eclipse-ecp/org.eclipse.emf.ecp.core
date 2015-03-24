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
package org.eclipse.emfforms.internal.core.services.databinding;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * EMF implementation of {@link EMFFormsDatabinding}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDatabindingImpl implements EMFFormsDatabinding {

	private final Set<DMRSegmentConverter> segmentConverters = new LinkedHashSet<DMRSegmentConverter>();

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

		final IValueProperty valueProperty = getValueProperty(domainModelReference, object.eClass());
		return valueProperty.observe(object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IValueProperty getValueProperty(VDomainModelReference domainModelReference, EClass rootEClass)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (rootEClass == null) {
			throw new IllegalArgumentException("The given EClass must not be null."); //$NON-NLS-1$
		}

		final EList<VDMRSegment> segments = domainModelReference.getSegments();
		if (segments.size() < 1) {
			throw new DatabindingFailedException("The given VDomainModelReference does not contain any segments."); //$NON-NLS-1$
		}

		IValueProperty resultValueProperty = convertSegmentToValueProperty(segments.get(0), rootEClass);
		for (int i = 1; i < segments.size(); i++) {
			final VDMRSegment currentSegment = segments.get(i);
			final EClass currentEClass = getNextEClass(resultValueProperty);
			final IValueProperty currentValueProperty = convertSegmentToValueProperty(currentSegment, currentEClass);
			resultValueProperty = resultValueProperty.value(currentValueProperty);
		}

		return resultValueProperty;

	}

	/**
	 * Adds the given {@link DMRSegmentConverter} to the Set of segment converters.
	 *
	 * @param converter The {@link DMRSegmentConverter} to add
	 */
	protected void addDMRSegmentConverter(DMRSegmentConverter converter) {
		segmentConverters.add(converter);
	}

	/**
	 * Removes the given {@link DMRSegmentConverter} from the Set of segment converters.
	 *
	 * @param converter The {@link DMRSegmentConverter} to remove
	 */
	protected void removeDMRSegmentConverter(DMRSegmentConverter converter) {
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

		final IListProperty listProperty = getListProperty(domainModelReference, object.eClass());
		return listProperty.observe(object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public IListProperty getListProperty(VDomainModelReference domainModelReference, EClass rootEClass)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (rootEClass == null) {
			throw new IllegalArgumentException("The given EClass must not be null."); //$NON-NLS-1$
		}

		final EList<VDMRSegment> segments = domainModelReference.getSegments();
		if (segments.size() < 1) {
			throw new DatabindingFailedException("The given VDomainModelReference does not contain any segments."); //$NON-NLS-1$
		}

		if (segments.size() == 1) {
			return convertSegmentToListProperty(segments.get(0), rootEClass);
		}

		IValueProperty valueProperty = convertSegmentToValueProperty(segments.get(0), rootEClass);
		for (int i = 1; i < segments.size() - 1; i++) {
			final VDMRSegment currentSegment = segments.get(i);
			final EClass currentEClass = getNextEClass(valueProperty);

			final IValueProperty currentValueProperty = convertSegmentToValueProperty(currentSegment, currentEClass);
			valueProperty = valueProperty.value(currentValueProperty);
		}

		return valueProperty.list(convertSegmentToListProperty(segments.get(segments.size() - 1),
			getNextEClass(valueProperty)));
	}

	/**
	 *
	 * @param valueProperty The preceding value property
	 * @return The EClass needed to get the property for the next segment.
	 * @throws DatabindingFailedException if the value property's type is not {@link EReference}
	 */
	private EClass getNextEClass(IValueProperty valueProperty) throws DatabindingFailedException {
		if (!EReference.class.isInstance(valueProperty.getValueType())) {
			throw new DatabindingFailedException(
				"The domain model reference contains more segments after one has not referenced an EObject."); //$NON-NLS-1$
		}
		final EReference eReference = (EReference) valueProperty.getValueType();
		final EClass currentEClass = eReference.getEReferenceType();
		return currentEClass;
	}

	/**
	 * Returns the most suitable {@link DMRSegmentConverter}, that is registered to this {@link EMFFormsDatabindingImpl}
	 * , for the given {@link VDMRSegment}.
	 *
	 * @param dmrSegment The {@link VDMRSegment} for which a {@link DMRSegmentConverter} is needed
	 * @return The most suitable {@link DMRSegmentConverter}
	 */
	private DMRSegmentConverter getBestDMRSegmentConverter(VDMRSegment dmrSegment) {
		if (dmrSegment == null) {
			throw new IllegalArgumentException("The given VDMRSegment must not be null."); //$NON-NLS-1$
		}
		double highestPriority = DMRSegmentConverter.NOT_APPLICABLE;
		DMRSegmentConverter bestConverter = null;
		for (final DMRSegmentConverter converter : segmentConverters) {
			final double priority = converter.isApplicable(dmrSegment);
			if (priority > highestPriority) {
				highestPriority = priority;
				bestConverter = converter;
			}
		}
		return bestConverter;
	}

	private IValueProperty convertSegmentToValueProperty(VDMRSegment segment, EClass eClass)
		throws DatabindingFailedException {
		final DMRSegmentConverter bestConverter = getBestDMRSegmentConverter(segment);
		if (bestConverter == null) {
			throw new DatabindingFailedException(
				"No suitable DMRSegmentConverter could be found for the VDMRSegment: " + segment.toString()); //$NON-NLS-1$
		}
		return bestConverter.convertToValueProperty(segment, eClass);
	}

	private IListProperty convertSegmentToListProperty(VDMRSegment segment, EClass eClass)
		throws DatabindingFailedException {
		final DMRSegmentConverter bestConverter = getBestDMRSegmentConverter(segment);
		if (bestConverter == null) {
			throw new DatabindingFailedException(
				"No suitable DMRSegmentConverter could be found for the VDMRSegment: " + segment.toString()); //$NON-NLS-1$
		}
		return bestConverter.convertToListProperty(segment, eClass);
	}
}
