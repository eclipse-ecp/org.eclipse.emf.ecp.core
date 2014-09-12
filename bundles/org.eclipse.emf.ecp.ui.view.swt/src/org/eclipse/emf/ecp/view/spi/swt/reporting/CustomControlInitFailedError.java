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

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;

/**
 * Indicates that the initialization of a custom control failed.
 * 
 * @author emueller
 * 
 */
public class CustomControlInitFailedError implements ReportEntity {

	private final String bundleName;
	private final String customControlClassName;

	/**
	 * Constructor.
	 * 
	 * @param bundleName
	 *            the name of the bundle containing the custom control
	 * @param customControlClassName
	 *            the name of the class for the custom control
	 */
	public CustomControlInitFailedError(String bundleName, String customControlClassName) {
		this.bundleName = bundleName;
		this.customControlClassName = customControlClassName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return String.format("The  %1$s/%2$s cannot be loaded!", //$NON-NLS-1$
			bundleName, customControlClassName);
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
