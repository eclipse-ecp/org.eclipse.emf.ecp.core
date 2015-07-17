/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.internal.locale;

import java.util.Locale;

import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * A {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService ViewModelService} which provides the default locale.
 *
 * @author Eugen Neufeld
 *
 */
public class DefaultLocaleViewService implements ViewLocaleService {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 10;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ViewLocaleService#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

}
