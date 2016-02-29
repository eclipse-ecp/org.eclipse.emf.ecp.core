/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.edit;

import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;

/**
 * The Factory for creating the {@link EMFDeleteServiceImpl}.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
@Component
public class DeleteServiceFactoryImpl implements EMFFormsViewServiceFactory<DeleteService> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getPolicy()
	 */
	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.LAZY;
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
		return 1;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getType()
	 */
	@Override
	public Class<DeleteService> getType() {
		return DeleteService.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#createService(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public DeleteService createService(EMFFormsViewContext emfFormsViewContext) {
		if (ViewModelContext.class.isInstance(emfFormsViewContext)) {
			final EMFDeleteServiceImpl dsi = new EMFDeleteServiceImpl();
			dsi.instantiate(ViewModelContext.class.cast(emfFormsViewContext));
			return dsi;
		}
		throw new IllegalStateException("The provided context is not a ViewModelContext."); //$NON-NLS-1$
	}

}
