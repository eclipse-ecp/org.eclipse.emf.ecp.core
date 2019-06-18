/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding.emf;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;

/**
 * EMF specific DomainModelReferenceConverter interface.
 *
 * @see DomainModelReferenceConverter
 * @author Eugen Neufeld
 * @since 1.7
 *
 */
public interface DomainModelReferenceConverterEMF extends DomainModelReferenceConverter {

	@Override
	IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

	/**
	 * Converts a {@link VDomainModelReference} to a {@link IValueProperty} which uses the given {@link EditingDomain}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} that will be converted to a {@link IValueProperty}
	 * @param rootEClass The root {@link EClass} of the given {@link VDomainModelReference}
	 * @param editingDomain The {@link EditingDomain} used by the created {@link IValueProperty value property}. The
	 *            EditingDomain might be <code>null</code> but in this case the value property will not support set
	 *            functionality
	 * @return The created {@link IValueProperty}, does not return <code>null</code>.
	 * @throws DatabindingFailedException if no value property could be created due to an invalid
	 *             {@link VDomainModelReference}.
	 * @since 1.19
	 */
	IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException;

	@Override
	IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException;

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
