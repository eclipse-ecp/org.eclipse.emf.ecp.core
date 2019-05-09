/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Exchange;

/**
 * @author jonas
 *
 */
public class BazaarContextFunctionWithCounter implements BazaarContextFunction {

	private final Object transformed;
	private int i;

	/**
	 * @param transformed
	 */
	public BazaarContextFunctionWithCounter(Object transformed) {
		this.transformed = transformed;
	}

	@Exchange
	public Object exchange() {
		i++;
		return transformed;
	}

	/**
	 * @return the i
	 */
	public int getCount() {
		return i;
	}

}
