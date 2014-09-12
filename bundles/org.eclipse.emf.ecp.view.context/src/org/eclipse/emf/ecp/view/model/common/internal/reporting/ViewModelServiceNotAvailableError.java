/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.internal.reporting;

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;

/**
 * Indicates that {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService ViewModelService} is not available.
 * 
 * @author emueller
 * 
 * @param <T> The type of the unavailable service
 */
public class ViewModelServiceNotAvailableError<T> implements ReportEntity {

	private static final String NO_VIEW_SERVICE_OF_TYPE_FOUND = "No view service of type '%1$s' found."; //$NON-NLS-1$
	private final Class<T> serviceType;

	/**
	 * Constructor.
	 * 
	 * @param serviceType
	 *            the type of the unavailable service
	 */
	public ViewModelServiceNotAvailableError(Class<T> serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * Returns the type of the unavailable service.
	 * 
	 * @return the type of the unavailable service
	 */
	public Class<T> getServiceType() {
		return serviceType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return String.format(
			NO_VIEW_SERVICE_OF_TYPE_FOUND, getServiceType().getCanonicalName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#hasException()
	 */
	@Override
	public boolean hasException() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getException()
	 */
	@Override
	public <E extends Exception> E getException() {
		return null;
	}

}
