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
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Indicates that no renderer has been found.
 * 
 * @author emueller
 * 
 */
public class NoRendererFoundError implements ReportEntity {

	private final VElement element;

	/**
	 * Constructor.
	 * 
	 * @param element
	 *            the {@link VElement} for which no renderer could be found
	 */
	public NoRendererFoundError(VElement element) {
		this.element = element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return MessageFormat.format("No renderer found for element {0}", //$NON-NLS-1$
			element.getName());
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
	public <T extends Exception> T getException() {
		return null;
	}

}
