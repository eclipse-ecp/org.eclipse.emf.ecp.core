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
package org.eclipse.emf.emfforms.spi.core.services.labelprovider;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * {@link EMFFormsLabelProvider} provides a label service. It offers methods to get the display name and the description
 * for a model object referenced by a {@link VDomainModelReference}. The reference can optionally be complemented by an
 * {@link EObject} which is the root object of the {@link VDomainModelReference}. This enables to get the texts for a
 * concrete instance.
 *
 * @author Lucas Koehler
 *
 */
public interface EMFFormsLabelProvider {

	/**
	 * Returns the display name of the referenced domain object.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the domain object
	 * @param rootEClass The root EClass of the given domain model reference
	 * @return The display name
	 */
	String getDisplayName(VDomainModelReference domainModelReference, EClass rootEClass);

	/**
	 * Returns the display name of the referenced domain object resolved for the given root {@link EObject}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the domain object
	 * @param rootObject The root {@link EObject} which is used to resolve the given {@link VDomainModelReference}
	 * @return The display name
	 */
	String getDisplayName(VDomainModelReference domainModelReference, EObject rootObject);

	/**
	 * Returns the description of the referenced domain object.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the model object
	 * @param rootEClass The root EClass of the given domain model reference
	 * @return The description
	 */
	String getDescription(VDomainModelReference domainModelReference, EClass rootEClass);

	/**
	 * Returns the description of the referenced domain object resolved for the given root {@link EObject}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} referencing the model object
	 * @param rootObject The root {@link EObject} which is used to resolve the given {@link VDomainModelReference}
	 * @return The description
	 */
	String getDescription(VDomainModelReference domainModelReference, EObject rootObject);
}
