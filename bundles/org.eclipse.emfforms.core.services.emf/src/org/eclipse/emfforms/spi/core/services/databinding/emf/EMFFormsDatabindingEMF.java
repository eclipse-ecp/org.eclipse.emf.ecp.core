/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding.emf;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * EMF specific interface of the EMFFormsDatabinding.
 * 
 * @author Eugen Neufeld
 * @since 1.7
 */
public interface EMFFormsDatabindingEMF extends EMFFormsDatabinding {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getObservableValue(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	IObservableValue getObservableValue(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getObservableList(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	IObservableList getObservableList(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding#getListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	IEMFListProperty getListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * Extracts the {@link EStructuralFeature} from the provided {@link IObservableValue}.
	 *
	 * @param observableValue The {@link IObservableValue} to extract the {@link EStructuralFeature} from
	 * @return The extracted {@link EStructuralFeature}
	 * @throws DatabindingFailedException when the {@link IObservableValue} doesn't implement
	 *             {@link org.eclipse.emf.databinding.IEMFObservable IEMFObservable}
	 */
	EStructuralFeature extractFeature(IObservableValue observableValue) throws DatabindingFailedException;

	/**
	 * Extracts the {@link EStructuralFeature} from the provided {@link IObservableList}.
	 *
	 * @param observableList The {@link IObservableList} to extract the {@link EStructuralFeature} from
	 * @return The extracted {@link EStructuralFeature}
	 * @throws DatabindingFailedException when the {@link IObservableValue} doesn't implement
	 *             {@link org.eclipse.emf.databinding.IEMFObservable IEMFObservable}
	 */
	EStructuralFeature extractFeature(IObservableList observableList) throws DatabindingFailedException;

	/**
	 * Extracts the observed {@link EObject} from the provided {@link IObservableValue}.
	 *
	 * @param observableValue The {@link IObservableValue} to extract the observed {@link EObject} from
	 * @return The extracted {@link EObject}
	 * @throws DatabindingFailedException when the {@link IObservableValue} doesn't implement
	 *             {@link org.eclipse.emf.databinding.IEMFObservable IEMFObservable}
	 */
	EObject extractObserved(IObservableValue observableValue) throws DatabindingFailedException;

	/**
	 * Extracts the observed {@link EObject} from the provided {@link IObservableList}.
	 *
	 * @param observableList The {@link IObservableList} to extract the observed {@link EObject} from
	 * @return The extracted {@link EObject}
	 * @throws DatabindingFailedException when the {@link IObservableValue} doesn't implement
	 *             {@link org.eclipse.emf.databinding.IEMFObservable IEMFObservable}
	 */
	EObject extractObserved(IObservableList observableList) throws DatabindingFailedException;
}
