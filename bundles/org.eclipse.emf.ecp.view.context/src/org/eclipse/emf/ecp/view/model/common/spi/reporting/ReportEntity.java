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
package org.eclipse.emf.ecp.view.model.common.spi.reporting;

/**
 * Common base type for reports that may be reported to the
 * {@link org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService ReportService}.
 * 
 * @author emueller
 * 
 */
public interface ReportEntity {

	/**
	 * Returns a message.
	 * 
	 * @return the message
	 */
	String getMessage();

	/**
	 * Whether this report is based upon an exception.
	 * 
	 * @return {@code true}, if this report is based upon an exception, {@code false} otherwise
	 */
	boolean hasException();

	/**
	 * Returns the exception this report is based on, if any.
	 * 
	 * @return the exception this report is based on, if any, otherwise {@code null}
	 * 
	 * @param <T> the type of the exception
	 * 
	 * @see #hasException()
	 */
	<T extends Exception> T getException();
}
