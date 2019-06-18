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
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Abstract definition of a LocalizationAdapter.
 *
 * @author Eugen Neufeld
 * @since 1.6
 *
 */
public abstract class LocalizationAdapter extends AdapterImpl {

	/**
	 * Implement in order to localize a key.
	 *
	 * @param key The key to localize
	 * @return The localized String
	 */
	public abstract String localize(String key);

}
