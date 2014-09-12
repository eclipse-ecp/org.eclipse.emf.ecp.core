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
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import java.text.MessageFormat;

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;

/**
 * Indicates that rendering is not possible due to an exception.
 * 
 * @author emueller
 * 
 * @param <E> the underlying exception type
 */
public class NoRenderingPossibleError<E extends Exception> implements ReportEntity {

	private final E exception;

	/**
	 * Constructor.
	 * 
	 * @param exception
	 *            the underlying exception
	 */
	public NoRenderingPossibleError(E exception) {
		this.exception = exception;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return MessageFormat.format("Rendering not possible due to: {0}.", //$NON-NLS-1$
			exception.getMessage());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#hasException()
	 */
	@Override
	public boolean hasException() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getException()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E getException() {
		return exception;
	}

}
