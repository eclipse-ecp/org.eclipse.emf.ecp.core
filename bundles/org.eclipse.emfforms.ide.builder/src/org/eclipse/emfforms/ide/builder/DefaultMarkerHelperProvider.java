/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.StaticBid;

/**
 * Provider of the default {@link MarkerHelper} implementation.
 */
@StaticBid(bid = Double.NEGATIVE_INFINITY)
class DefaultMarkerHelperProvider implements MarkerHelperProvider {

	/**
	 * Initializes me.
	 */
	DefaultMarkerHelperProvider() {
		super();
	}

	/**
	 * Create the default marker helper.
	 *
	 * @return a {@code DefaultMarkerHelper}
	 */
	@Create
	public MarkerHelper createDefault() {
		return new DefaultMarkerHelper();
	}

}
