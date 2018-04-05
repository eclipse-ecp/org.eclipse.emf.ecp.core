/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfforms.bazaar.BazaarContext;

/**
 * Default implementation of the bazaar context.
 *
 * @author Christian W. Damus
 */
public class BazaarContextImpl implements BazaarContext {
	private final Map<String, Object> contextMap;

	/**
	 * Initializes me with my contents.
	 *
	 * @param contextMap my contents.
	 */
	public BazaarContextImpl(Map<String, Object> contextMap) {
		super();

		this.contextMap = unmodifiableMap(new HashMap<String, Object>(contextMap));
	}

	@Override
	public Map<String, Object> getContextMap() {
		return contextMap;
	}

}
