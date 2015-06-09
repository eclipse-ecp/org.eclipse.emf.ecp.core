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
package org.eclipse.emf.ecp.view.spi.context;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService ViewModelService} is not available.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class ViewModelServiceNotAvailableReport extends AbstractReport {

	private static final String NO_VIEW_SERVICE_OF_TYPE_FOUND = "No view service of type '%1$s' found."; //$NON-NLS-1$
	private final Class<?> serviceType;

	/**
	 * Constructor.
	 *
	 * @param serviceType
	 *            the type of the unavailable service
	 */
	public ViewModelServiceNotAvailableReport(Class<?> serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * Returns the type of the unavailable service.
	 *
	 * @return the type of the unavailable service
	 */
	public Class<?> getServiceType() {
		return serviceType;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getMessage()
	 */
	@Override
	public String getMessage() {
		return String.format(
			NO_VIEW_SERVICE_OF_TYPE_FOUND, getServiceType().getCanonicalName());
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getSeverity()
	 */
	@Override
	public int getSeverity() {
		return IStatus.ERROR;
	}

}
