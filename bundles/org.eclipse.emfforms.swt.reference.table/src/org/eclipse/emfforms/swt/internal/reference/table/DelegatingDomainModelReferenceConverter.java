/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;

/**
 * Converter for a DMR that needs to be delegated to another object than the
 * source on which it is being accessed.
 */
@SuppressWarnings({ "unchecked", "rawtypes", "restriction" }) // EMF APIs are provisional
class DelegatingDomainModelReferenceConverter implements DomainModelReferenceConverterEMF {

	private final VFeaturePathDomainModelReference dmr;
	private final Function<? super EObject, ?> delegator;

	/**
	 * Initializes me with the DMR that I convert and a function computing the
	 * source object to delegate the reference access to.
	 *
	 * @param dmr the DMR to convert
	 * @param delegator mapping of source object to the object to which to delegate access
	 *            to the DMR
	 */
	DelegatingDomainModelReferenceConverter(VFeaturePathDomainModelReference dmr,
		Function<? super EObject, ?> delegator) {

		super();

		this.dmr = dmr;
		this.delegator = delegator;
	}

	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		return domainModelReference == dmr ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
	}

	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {

		return valueDecorator();
	}

	private IEMFValueProperty valueDecorator() {
		final EStructuralFeature feature = dmr.getDomainModelEFeature();
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
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException {

		return valueDecorator();
	}

	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {

		final EStructuralFeature feature = dmr.getDomainModelEFeature();
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
		// END COMPLEX CODE
	}

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {

		final EStructuralFeature feature = dmr.getDomainModelEFeature();
		return ((InternalEObject) delegator.apply(object)).eSetting(feature);
	}

}
