/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Locale;

import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * A view service providing the locale.
 *
 * @author Eugen Neufeld
 * @since 1.2
 * @deprecated Please use the {@link org.eclipse.emfforms.spi.core.services.locale.EMFFormsLocaleProvider
 *             EMFFormsLocaleProvider}
 */
@SuppressWarnings("javadoc")
@Deprecated
public interface ViewLocaleService extends ViewModelService {

	/**
	 * The current locale of the view.
	 *
	 * @return the current locale
	 */
	Locale getLocale();
}
