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
package org.eclipse.emfforms.internal.core.services.controlmapper;

import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation of {@link EMFFormsViewServiceFactory} as an OSGI service.
 *
 * @author Lucas Koehler
 *
 */
@Component
public class SettingToControlMapFactoryImpl implements EMFFormsViewServiceFactory<EMFFormsSettingToControlMapper> {

	private EMFFormsMappingProviderManager mappingManager;

	/**
	 * Sets the {@link EMFFormsMappingProviderManager}.
	 *
	 * @param mappingManager The {@link EMFFormsMappingProviderManager}
	 */
	@Reference
	protected void setEMFFormsMappingProviderManager(EMFFormsMappingProviderManager mappingManager) {
		this.mappingManager = mappingManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getPolicy()
	 */
	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.IMMEDIATE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getScope()
	 */
	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getPriority()
	 */
	@Override
	public double getPriority() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getType()
	 */
	@Override
	public Class<EMFFormsSettingToControlMapper> getType() {
		return EMFFormsSettingToControlMapper.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#createService(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public EMFFormsSettingToControlMapper createService(EMFFormsViewContext emfFormsViewContext) {
		final EMFFormsSettingToControlMapper mapper = new SettingToControlMapperImpl(mappingManager, emfFormsViewContext);
		return mapper;
	}

}
