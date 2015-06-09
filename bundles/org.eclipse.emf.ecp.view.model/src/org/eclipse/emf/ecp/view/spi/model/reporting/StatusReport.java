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
package org.eclipse.emf.ecp.view.spi.model.reporting;

import org.eclipse.core.runtime.Status;
import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * A {@link Status} that may be passed to the report service.
 *
 * @author emueller
 * @since 1.5
 */
public class StatusReport extends AbstractReport {

	private final Status status;

	/**
	 * Constructor.
	 *
	 * @param status
	 *            the status to be wrapped
	 */
	public StatusReport(Status status) {
		this.status = status;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getMessage()
	 */
	@Override
	public String getMessage() {
		return status.getMessage();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#hasException()
	 */
	@Override
	public boolean hasException() {
		return status.getException() != null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getException()
	 */
	@Override
	public Throwable getException() {
		return status.getException();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getSeverity()
	 */
	@Override
	public int getSeverity() {
		return status.getSeverity();
	}

}
