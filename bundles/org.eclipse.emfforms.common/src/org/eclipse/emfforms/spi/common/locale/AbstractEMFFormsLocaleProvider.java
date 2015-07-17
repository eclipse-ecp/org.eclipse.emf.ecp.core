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
package org.eclipse.emfforms.spi.common.locale;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Abstract implementation of the EMF Forms provider for {@link java.util.Locale Locales}.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class AbstractEMFFormsLocaleProvider implements EMFFormsLocaleProvider {

	private final Set<EMFFormsLocaleChangeListener> listeners = new LinkedHashSet<EMFFormsLocaleChangeListener>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider#addEMFFormsLocaleChangeListener(org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener)
	 */
	@Override
	public void addEMFFormsLocaleChangeListener(EMFFormsLocaleChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider#removeEMFFormsLocaleChangeListener(org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener)
	 */
	@Override
	public void removeEMFFormsLocaleChangeListener(EMFFormsLocaleChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify listeners about a locale change.
	 */
	protected void notifyListeners() {
		for (final EMFFormsLocaleChangeListener localeChangeListener : listeners) {
			localeChangeListener.notifyLocaleChange();
		}
	}
}
