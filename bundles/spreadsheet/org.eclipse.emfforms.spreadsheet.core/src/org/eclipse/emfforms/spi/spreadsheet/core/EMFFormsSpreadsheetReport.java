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
package org.eclipse.emfforms.spi.spreadsheet.core;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * The report to use for the {@link org.eclipse.emfforms.spi.common.report.ReportService ReportService} during the
 * Spreadsheet
 * export or import.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsSpreadsheetReport extends AbstractReport {

	/**
	 * Constant for the INFO severity.
	 */
	public static final int INFO = 1;
	/**
	 * Constant for the WARNING severity.
	 */
	public static final int WARNING = 2;
	/**
	 * Constant for the ERROR severity.
	 */
	public static final int ERROR = 4;

	/**
	 * Default constructor with a {@link Throwable}.
	 *
	 * @param throwable The {@link Throwable} to report
	 * @param severity The severity of the report
	 */
	public EMFFormsSpreadsheetReport(Throwable throwable, int severity) {
		super(throwable, severity);
	}

	/**
	 * Default constructor with a message.
	 *
	 * @param message The message to report
	 * @param severity The severity of the report
	 */
	public EMFFormsSpreadsheetReport(String message, int severity) {
		super(message, severity);
	}
}
