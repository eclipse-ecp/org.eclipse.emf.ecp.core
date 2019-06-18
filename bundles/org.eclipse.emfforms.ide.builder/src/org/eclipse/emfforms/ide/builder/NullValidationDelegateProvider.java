/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.StaticBid;

/**
 * Provider of the {@link ValidationDelegate#NULL null validation delegate}.
 */
@StaticBid(bid = Double.NEGATIVE_INFINITY)
class NullValidationDelegateProvider implements ValidationDelegateProvider {

	/**
	 * Initializes me.
	 */
	NullValidationDelegateProvider() {
		super();
	}

	/**
	 * Obtain the null delegate.
	 *
	 * @return the {@linkplain ValidationDelegate#NULL null delegate}
	 */
	@Create
	public ValidationDelegate getNullDelegate() {
		return ValidationDelegate.NULL;
	}

}
