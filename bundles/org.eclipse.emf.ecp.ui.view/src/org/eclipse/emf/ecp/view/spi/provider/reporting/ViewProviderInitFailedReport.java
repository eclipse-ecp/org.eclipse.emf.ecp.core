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
package org.eclipse.emf.ecp.view.spi.provider.reporting;

import java.text.MessageFormat;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that a ViewProvider could not be instantiated.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class ViewProviderInitFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            an exception
	 */
	public ViewProviderInitFailedReport(Throwable exception) {
		super(exception);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getMessage()
	 */
	@Override
	public String getMessage() {
		return MessageFormat.format("ViewProvider could not be initialized due to {0}: ", //$NON-NLS-1$
			getException().getMessage());
	}
}
