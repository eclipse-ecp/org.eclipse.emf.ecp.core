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
package org.eclipse.emf.emfforms.spi.localization;

import java.util.Locale;

/**
 * EMF Forms provider for {@link Locale Locales}.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsLocaleProvider {

	/**
	 * Returns the current locale service.
	 *
	 * @return the current {@link Locale}
	 */
	Locale getLocale();
}
