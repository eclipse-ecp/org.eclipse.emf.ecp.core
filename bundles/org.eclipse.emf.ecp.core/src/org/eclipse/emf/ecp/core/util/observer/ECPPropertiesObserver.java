/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Edgar Mueller - change to ECPObserver
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.ecp.core.util.ECPProperties;

/**
 * This Observer is called to notify listeners about changes of properties.
 *
 * @author Eike Stepper
 * @author Edgar Mueller
 * @author Eugen Neufeld
 *
 */
public interface ECPPropertiesObserver extends ECPObserver {

	/**
	 * This is called to indicate, that properties changed.
	 *
	 * @param properties the {@link ECPProperties} that are chnaged
	 * @param oldProperties the properties before changing
	 * @param newProperties the properties after changing
	 */
	void propertiesChanged(ECPProperties properties, Collection<Entry<String, String>> oldProperties,
		Collection<Entry<String, String>> newProperties);
}
