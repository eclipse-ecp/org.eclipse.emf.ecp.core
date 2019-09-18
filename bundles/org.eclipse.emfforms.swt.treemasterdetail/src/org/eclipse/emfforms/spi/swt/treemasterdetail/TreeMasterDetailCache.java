/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.BasicDetailViewCache;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewCache;

/**
 * A cache for the TreeMasterDetail that allows to cache rendered ECPSWTViews and reuse them when switching between
 * elements in the tree.
 *
 * @author Eugen Neufeld
 * @since 1.9
 *
 * @deprecated As of 1.22, use the {@link DetailViewCache} API, instead, or extend the {@link BasicDetailViewCache}
 *             class
 */
@Deprecated
public interface TreeMasterDetailCache extends DetailViewCache {

	/**
	 * Checks whether there is already a cached view available.
	 *
	 * @param selection The new selection of the tree
	 * @return true if there is a cached view for the provided selection, false otherwise
	 */
	boolean isChached(EObject selection);

	@Override
	default boolean isCached(EObject selection) {
		return isChached(selection);
	}

	/**
	 * Returns the previously cached view for the provided selection.
	 *
	 * @param selection The new selection of the tree
	 * @return The cached view
	 */
	@Override
	ECPSWTView getCachedView(EObject selection);

	/**
	 * Caches the provided {@link ECPSWTView} to allow it to be reused later.
	 *
	 * @param ecpView The {@link ECPSWTView} to cache.
	 */
	void cache(ECPSWTView ecpView);

	@Override
	default boolean cacheView(ECPSWTView ecpView) {
		// Don't attempt to cache a view already disposed
		if (ecpView != null && !ecpView.getSWTControl().isDisposed()) {
			cache(ecpView);
		}

		return ecpView != null && !ecpView.getSWTControl().isDisposed();
	}
}
