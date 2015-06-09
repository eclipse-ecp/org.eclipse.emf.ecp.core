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


/**
 * EMF Forms locale change listener.
 *
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsLocaleChangeListener {

	/**
	 * Notify about a locale change.
	 */
	void notifyLocaleChange();
}
