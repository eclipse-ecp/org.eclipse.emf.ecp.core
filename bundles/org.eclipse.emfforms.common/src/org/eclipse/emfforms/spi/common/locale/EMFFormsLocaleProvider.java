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

	/**
	 * Add an EMFFormsLocaleChangeListener that gets notified about locale changes.
	 * 
	 * @param listener The EMFFormsLocaleChangeListener to add
	 */
	void addEMFFormsLocaleChangeListener(EMFFormsLocaleChangeListener listener);

	/**
	 * Remove an EMFFormsLocaleChangeListener.
	 * 
	 * @param listener The EMFFormsLocaleChangeListener to remove
	 */
	void removeEMFFormsLocaleChangeListener(EMFFormsLocaleChangeListener listener);
}
