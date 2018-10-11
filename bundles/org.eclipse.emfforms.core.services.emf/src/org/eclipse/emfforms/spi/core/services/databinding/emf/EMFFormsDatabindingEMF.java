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
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * EMF specific interface of the EMFFormsDatabinding.
 *
 * @author Eugen Neufeld
 * @since 1.7
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
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
	 * Returns an {@link IValueProperty} described by the given {@link VDomainModelReference}. Using this method, value
	 * properties are created without an {@link org.eclipse.emf.edit.domain.EditingDomain EditingDomain}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired value
	 * @param rootEClass The root {@link EClass} of the rendered form
	 * @return The resulting {@link IValueProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 * @since 1.19
	 */
	IEMFValueProperty getValueProperty(VDomainModelReference domainModelReference, EClass rootEClass)
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

	/**
	 * Retrieve the Setting which is described by the provided {@link VDomainModelReference} and the provided
	 * {@link EObject}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} to use to retrieve the setting
	 * @param object The {@link EObject} to use to retrieve the setting
	 * @return The Setting being described by the {@link VDomainModelReference}
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 * @since 1.8
	 */
	Setting getSetting(VDomainModelReference domainModelReference, EObject object) throws DatabindingFailedException;
}
