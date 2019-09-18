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
package org.eclipse.emf.ecp.view.spi.swt.masterdetail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;

/**
 * The {@linkplain DetailViewCache#EMPTY empty detail view cache}.
 */
class EmptyDetailViewCache implements DetailViewCache {

	/**
	 * Initializes me.
	 */
	EmptyDetailViewCache() {
		super();
	}

	@Override
	public boolean isCached(EObject selection) {
		return false;
	}

	@Override
	public ECPSWTView getCachedView(EObject selection) {
		return null;
	}

	@Override
	public boolean cacheView(ECPSWTView ecpView) {
		if (ecpView != null) {
			// Dispose the view because we cannot cache it, so it would never be reused
			ecpView.dispose();
		}
		return false;
	}

	@Override
	public void clear() {
		// I have nothing to clear
	}

}
