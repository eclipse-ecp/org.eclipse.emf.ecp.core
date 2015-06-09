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

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Converts a {@link VDomainModelReference} to a {@link IValueProperty}.
 *
 * @author Lucas Koehler
 *
 */
public interface DomainModelReferenceConverter {

	/**
	 * The constant defining the priority that a {@link DomainModelReferenceConverter} is not for a
	 * {@link VDomainModelReference}.
	 */
	double NOT_APPLICABLE = Double.NEGATIVE_INFINITY;

	/**
	 * Checks whether the given {@link VDomainModelReference} can be converted by this
	 * {@link DomainModelReferenceConverter} to a {@link IValueProperty}. The return value is the priority of this
	 * converter. The higher the priority, the better suits the converter the given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} whose priority is wanted.
	 * @return The priority of the given {@link VDomainModelReference}; negative infinity if this converter is not
	 *         applicable.
	 */
	double isApplicable(VDomainModelReference domainModelReference);

	/**
	 * Converts a {@link VDomainModelReference} to a {@link IValueProperty}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} that will be converted to a {@link IValueProperty}
	 * @param object The root object of the rendered form
	 * @return The created {@link IValueProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if no value property could be created due to an invalid
	 *             {@link VDomainModelReference}.
	 */
	IValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * Converts a {@link VDomainModelReference} to an {@link IListProperty}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} that will be converted to an {@link IListProperty}
	 * @param object The root object of the rendered form
	 * @return The created {@link IListProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if no value property could be created due to an invalid
	 *             {@link VDomainModelReference}.
	 */
	IListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;
}
