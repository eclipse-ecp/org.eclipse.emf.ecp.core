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
package org.eclipse.emfforms.spi.core.services.databinding;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * {@link EMFFormsDatabinding} provides a databinding service. It provides four methods for getting an
 * {@link IValueProperty} or an {@link IListProperty} from a {@link VDomainModelReference} and getting an
 * {@link IObservableValue} or an {@link IObservableList} from a {@link VDomainModelReference} and an {@link EObject}.
 *
 * @author Lucas Koehler
 *
 */
public interface EMFFormsDatabinding {

	/**
	 * Returns an {@link IObservableValue} by observing the value described by the given {@link VDomainModelReference}
	 * of the given {@link EObject}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired value
	 * @param object The object containing the value of the reference
	 * @return The resulting {@link IObservableValue}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 */
	IObservableValue getObservableValue(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * Returns an {@link IObservableList} by observing the list described by the given {@link VDomainModelReference} of
	 * the given {@link EObject}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired list
	 * @param object The object containing the values of the reference
	 * @return The resulting {@link IObservableList}, does not return <code>null</code>
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 */
	IObservableList getObservableList(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * Returns an {@link IValueProperty} described by the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired value
	 * @param rootEClass The root {@link EClass} of the domain model reference
	 * @return The resulting {@link IValueProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 */
	IValueProperty getValueProperty(VDomainModelReference domainModelReference, EClass rootEClass)
		throws DatabindingFailedException;

	/**
	 * Returns an {@link IListProperty} described by the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The domain model reference pointing to the desired list
	 * @param rootEClass The root {@link EClass} of the domain model reference
	 * @return The resulting {@link IListProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if the databinding could not be executed successfully.
	 */
	IListProperty getListProperty(VDomainModelReference domainModelReference, EClass rootEClass)
		throws DatabindingFailedException;
}
