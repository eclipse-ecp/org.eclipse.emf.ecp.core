/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.locale.defaultlocale;

import java.util.Locale;

import org.eclipse.emfforms.spi.common.locale.AbstractEMFFormsLocaleProvider;

/**
 * EMFFormsLocaleProvider that works on the default {@link Locale}.
 *
 * @author Eugen Neufeld
 *
 */
public class DefaultLocaleProvider extends AbstractEMFFormsLocaleProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	/**
	 * Method to update the locale.
	 * 
	 * @param newLocale The new Locale
	 */
	public void setLocale(Locale newLocale) {
		Locale.setDefault(newLocale);
		notifyListeners();
	}
}
