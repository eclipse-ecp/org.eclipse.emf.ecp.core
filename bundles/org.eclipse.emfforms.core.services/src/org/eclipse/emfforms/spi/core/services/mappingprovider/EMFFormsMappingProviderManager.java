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
package org.eclipse.emfforms.spi.core.services.mappingprovider;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * A Manager for the {@link EMFFormsMappingProvider}.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
public interface EMFFormsMappingProviderManager {

	/**
	 * Retrieve all settings for a given domainModelReference and domain object.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} to retrieve the settings for
	 * @param domainObject The {@link EObject} to use for resolvement
	 * @return the set of {@link UniqueSetting} of this control
	 */
	Set<UniqueSetting> getAllSettingsFor(VDomainModelReference domainModelReference, EObject domainObject);
}
