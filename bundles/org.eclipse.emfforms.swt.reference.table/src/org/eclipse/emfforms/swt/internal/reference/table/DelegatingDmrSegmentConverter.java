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
 ******************************************************************************/
package org.eclipse.emfforms.swt.internal.reference.table;

import java.util.List;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.DelegatingListProperty;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.DelegatingValueProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFListPropertyDecorator;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.core.services.databinding.SegmentConverterListResultImpl;
import org.eclipse.emfforms.internal.core.services.databinding.SegmentConverterValueResultImpl;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;

/**
 * Converter for a DMR Segment that needs to be delegated to another object than the
 * source on which it is being accessed.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes", "restriction" }) // EMF APIs are provisional
class DelegatingDmrSegmentConverter implements DomainModelReferenceSegmentConverterEMF {

	private final VFeatureDomainModelReferenceSegment segment;
	private final EStructuralFeature segmentFeature;
	private final Function<? super EObject, ?> delegator;

	/**
	 * Default constructor.
	 *
	 * @param segment The {@link VDomainModelReferenceSegment} this segment converter is applicable for
	 * @param segmentFeature The {@link EStructuralFeature} of the segment
	 * @param delegator mapping of source object to the object to which to delegate access
	 *            to the segment
	 */
	DelegatingDmrSegmentConverter(VFeatureDomainModelReferenceSegment segment, EStructuralFeature segmentFeature,
		Function<? super EObject, ?> delegator) {
		this.segment = segment;
		this.segmentFeature = segmentFeature;
		this.delegator = delegator;
	}

	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		return segment == this.segment ? Double.POSITIVE_INFINITY : NOT_APPLICABLE;
	}

	@Override
	public SegmentConverterValueResultEMF convertToValueProperty(VDomainModelReferenceSegment segment,
		EClass segmentRoot, EditingDomain editingDomain) throws DatabindingFailedException {
		final EClass nextEClass = segmentFeature instanceof EReference
			? EReference.class.cast(segmentFeature).getEReferenceType()
			: null;
		return new SegmentConverterValueResultImpl(valueDecorator(), nextEClass);
	}

	private IEMFValueProperty valueDecorator() {
		final EStructuralFeature feature = segmentFeature;
		final IValueProperty property = EMFProperties.value(feature);

		return new EMFValuePropertyDecorator(new DelegatingValueProperty(feature) {
			@Override
			protected IValueProperty doGetDelegate(Object source) {
				return property;
			}

			Object substitute(Object source) {
				return delegator.apply((EObject) source);
			}

			@Override
			protected Object doGetValue(Object source) {
				return super.doGetValue(substitute(source));
			}

			@Override
			protected void doSetValue(Object source, Object value) {
				super.doSetValue(substitute(source), value);
			}

			@Override
			public IObservableValue observe(Object source) {
				return super.observe(substitute(source));
			}

			@Override
			public IObservableValue observe(Realm realm, Object source) {
				return super.observe(realm, substitute(source));
			}
		}, feature);
	}

	@Override
	public SegmentConverterListResultEMF convertToListProperty(VDomainModelReferenceSegment segment, EClass segmentRoot,
		EditingDomain editingDomain) throws DatabindingFailedException {
		final EClass nextEClass = segmentFeature instanceof EReference
			? EReference.class.cast(segmentFeature).getEReferenceType()
			: null;
		return new SegmentConverterListResultImpl(listDecorator(), nextEClass);
	}

	private IEMFListProperty listDecorator() {

		final EStructuralFeature feature = segmentFeature;
		final IListProperty property = EMFProperties.list(feature);

		// BEGIN COMPLEX CODE
		return new EMFListPropertyDecorator(new DelegatingListProperty(feature) {
			@Override
			protected IListProperty doGetDelegate(Object source) {
				return property;
			}

			Object substitute(Object source) {
				return delegator.apply((EObject) source);
			}

			@Override
			protected List doGetList(Object source) {
				return super.doGetList(substitute(source));
			}

			@Override
			protected void doSetList(Object source, List list) {
				super.doSetList(substitute(source), list);
			}

			@Override
			protected void doUpdateList(Object source, ListDiff diff) {
				super.doUpdateList(substitute(source), diff);
			}

			@Override
			public IObservableList observe(Object source) {
				return super.observe(substitute(source));
			}

			@Override
			public IObservableList observe(Realm realm, Object source) {
				return super.observe(realm, substitute(source));
			}
		}, feature);
	}

	@Override
	public Setting getSetting(VDomainModelReferenceSegment segment, EObject eObject) throws DatabindingFailedException {
		return ((InternalEObject) delegator.apply(eObject)).eSetting(segmentFeature);
	}
}
