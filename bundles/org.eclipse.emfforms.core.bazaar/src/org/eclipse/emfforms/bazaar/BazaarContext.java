/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar;

import java.util.Map;

/**
 * Provides the necessary parameters, the {@link Vendor} of a {@link Bazaar} might request. Parameters are provided
 * in a {@link Map} from their key to their value. Those parameters can be adapted to other ones by
 * {@link BazaarContextFunction}s
 *
 */
public interface BazaarContext {

	/**
	 * Returns a map containing the parameters available for {@link Vendor}s on a {@link Bazaar}. The key is either
	 * an id or the full class name. the value is the parameter which will be provided to {@link Vendor}s
	 *
	 * @return A {@link Map} containing the parameters for {@link Vendor} from (key|full class name) to the object
	 *         as value.
	 */
	Map<String, Object> getContextMap();

}
