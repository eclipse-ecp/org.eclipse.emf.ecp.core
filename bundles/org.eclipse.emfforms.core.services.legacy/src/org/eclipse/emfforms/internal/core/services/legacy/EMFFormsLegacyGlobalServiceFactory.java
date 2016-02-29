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
package org.eclipse.emfforms.internal.core.services.legacy;

import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;

/**
 * An {@link org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory EMFFormsViewServiceFactory} for
 * {@link GlobalViewModelService}.
 *
 * @param <T> The actual type of the {@link GlobalViewModelService}
 * @author Eugen Neufeld
 *
 */
public class EMFFormsLegacyGlobalServiceFactory<T extends GlobalViewModelService>
	extends EMFFormsAbstractLegacyServiceFactory<T> {

	/**
	 * Default constructor used to create an {@link EMFFormsLegacyGlobalServiceFactory}.
	 *
	 * @param type The type of the service to wrap
	 * @param priority The priority of the wrapped service
	 * @param reportService The {@link ReportService} to use for logging
	 */
	public EMFFormsLegacyGlobalServiceFactory(Class<T> type, double priority, ReportService reportService) {
		super(type, priority, reportService);
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
		return EMFFormsViewServiceScope.GLOBAL;
	}

}
