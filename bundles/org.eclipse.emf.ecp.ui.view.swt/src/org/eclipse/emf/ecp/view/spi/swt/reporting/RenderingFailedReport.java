/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import java.text.MessageFormat;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that rendering is not possible due to an exception.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class RenderingFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            the underlying exception
	 */
	public RenderingFailedReport(Throwable exception) {
		super(exception,
			MessageFormat.format("Rendering not possible due to: {0}.", //$NON-NLS-1$
				exception.getMessage()));
	}
}
