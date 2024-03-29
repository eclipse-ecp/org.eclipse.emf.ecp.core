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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.databinding;

import java.text.MessageFormat;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * A rendering report indicating that the databinding is not possible due to an exception.
 *
 * @author Lucas Koehler
 *
 */
public class DatabindingFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            the underlying exception
	 */
	public DatabindingFailedReport(Throwable exception) {
		super(exception,
			MessageFormat.format("Databinding not possible due to: {0}.", //$NON-NLS-1$
				exception.getMessage()));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.common.report.AbstractReport#getSeverity()
	 */
	@Override
	public int getSeverity() {
		return 2; // IStatus.Warning
	}

}
