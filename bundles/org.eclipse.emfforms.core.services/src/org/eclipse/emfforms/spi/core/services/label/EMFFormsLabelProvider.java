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
package org.eclipse.emfforms.spi.core.services.label;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * The {@link EMFFormsLabelProvider} offers methods to get the display name and the description
 * for a model object referenced by a {@link VDomainModelReference} and its root {@link EClass} as an IObservableValue.
 * The root class can optionally be replaced by an {@link EObject} which is the root object of the
 * {@link VDomainModelReference}. This enables to get the texts for a concrete instance.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsLabelProvider {

	/**
	 * Returns the display name of the referenced domain object.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the domain object
	 * @param rootEClass The root {@link EClass} of the domain model reference
	 * @return The display name as an {@link IObservableValue}
	 * @throws NoLabelFoundException if the display name cannot be retrieved
	 * @since 2.0
	 */
	IObservableValue getDisplayName(VDomainModelReference domainModelReference, EClass rootEClass)
		throws NoLabelFoundException;

	/**
	 * Returns the display name of the referenced domain object resolved for the given root {@link EObject}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the domain object
	 * @param rootObject The root {@link EObject} which is used to resolve the given {@link VDomainModelReference}
	 * @return The display name as an {@link IObservableValue}
	 * @throws NoLabelFoundException if the display name cannot be retrieved
	 */
	IObservableValue getDisplayName(VDomainModelReference domainModelReference, EObject rootObject)
		throws NoLabelFoundException;

	/**
	 * Returns the description of the referenced domain object.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the model object
	 * @param rootEClass The root {@link EClass} of the domain model reference
	 * @return The description as an {@link IObservableValue}
	 * @throws NoLabelFoundException if the description cannot be retrieved
	 * @since 2.0
	 */
	IObservableValue getDescription(VDomainModelReference domainModelReference, EClass rootEClass)
		throws NoLabelFoundException;

	/**
	 * Returns the description of the referenced domain object resolved for the given root {@link EObject}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the model object
	 * @param rootObject The root {@link EObject} which is used to resolve the given {@link VDomainModelReference}
	 * @return The description as an {@link IObservableValue}
	 * @throws NoLabelFoundException if the description cannot be retrieved
	 */
	IObservableValue getDescription(VDomainModelReference domainModelReference, EObject rootObject)
		throws NoLabelFoundException;
}
