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
package org.eclipse.emf.ecp.view.spi.provider;

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;

/**
 * Indicates that a {@link IViewProvider} has returned {@code null} as
 * the {@link org.eclipse.emf.ecp.view.spi.model.VView VView}.
 * 
 * @author emueller
 * 
 */
public class ViewModelIsNullError implements ReportEntity {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return "ViewProvider has returned null as the view."; //$NON-NLS-1$
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
