/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
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
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.observer.ECPPropertiesObserver;
import org.eclipse.emf.ecp.internal.core.util.Properties;

/**
 * @author Eike Stepper
 */
public class PropertiesContentProvider extends StructuredContentProvider<Properties> implements ECPPropertiesObserver {
	/**
	 * Constructor.
	 */
	public PropertiesContentProvider() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getInput().getElements().toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void propertiesChanged(ECPProperties properties, Collection<Entry<String, String>> oldProperties,
		Collection<Entry<String, String>> newProperties) {
		if (properties.equals(getInput())) {
			refreshViewer();
		}
	}

	@Override
	protected void connectInput(Properties input) {
		super.connectInput(input);
		input.addObserver(this);
	}

	@Override
	protected void disconnectInput(Properties input) {
		input.removeObserver(this);
		super.disconnectInput(input);
	}
}
