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
package org.eclipse.emfforms.spi.common.report;

/**
 * Common base type for reports that may be reported.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class AbstractReport {

	private Throwable exception;
	private String message;
	private final int severity;

	/**
	 * <p>
	 * Default constructor.
	 * </p>
	 *
	 * Sets the severity to <code>IStatus.ERROR</code>.
	 */
	public AbstractReport() {
		exception = null;
		severity = 4; // == IStatus.ERROR
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 *
	 * Sets the severity to <code>IStatus.ERROR</code>.
	 *
	 * @param exception
	 *            the exception this report is based on
	 */
	public AbstractReport(Throwable exception) {
		this.exception = exception;
		severity = 4; // == IStatus.ERROR
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 *
	 * Sets the severity to <code>IStatus.ERROR</code>.
	 *
	 * @param message
	 *            the report message
	 */
	public AbstractReport(String message) {
		this.message = message;
		severity = 4; // == IStatus.ERROR
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 *
	 * Sets the severity to <code>IStatus.ERROR</code>.
	 *
	 * @param message
	 *            the report message
	 * @param severity
	 *            the severity of the report
	 */
	public AbstractReport(String message, int severity) {
		this.message = message;
		this.severity = severity;
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 *
	 * Sets the severity to <code>IStatus.ERROR</code>.
	 *
	 * @param exception
	 *            the exception this report is based on
	 * @param message
	 *            the report message
	 */
	public AbstractReport(Throwable exception, String message) {
		this.exception = exception;
		this.message = message;
		severity = 4; // == IStatus.ERROR
	}

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            the exception this report is based on
	 * @param severity
	 *            the severity of the report
	 */
	public AbstractReport(Throwable exception, int severity) {
		this.exception = exception;
		this.severity = severity;
	}

	/**
	 * Returns the report message.
	 *
	 * @return the report message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Whether this report is based upon an exception.
	 *
	 * @return {@code true}, if this report is based upon an exception, {@code false} otherwise
	 */
	public boolean hasException() {
		return getException() != null;
	}

	/**
	 * Returns the exception this report is based on, if any.
	 *
	 * @return the exception this report is based on, if any, otherwise {@code null}
	 *
	 *
	 * @see #hasException()
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * Returns the status of this report.
	 *
	 * @return the status of this report
	 *
	 */
	public int getSeverity() {
		return severity;
	}

	/**
	 * Sets the exception this report is based on.
	 *
	 * @param exception
	 *            the exception this report is based on
	 *
	 * @see #hasException()
	 */
	protected void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * Sets the report' message.
	 *
	 * @param message
	 *            the report message to set
	 */
	protected void setMessage(String message) {
		this.message = message;
	}
}
