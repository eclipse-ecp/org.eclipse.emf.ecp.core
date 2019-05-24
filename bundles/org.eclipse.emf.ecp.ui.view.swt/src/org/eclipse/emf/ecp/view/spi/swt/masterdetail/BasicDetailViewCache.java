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
package org.eclipse.emf.ecp.view.spi.swt.masterdetail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;

/**
 * A default implementation of the {@link DetailViewCache} that uses the {@link EClass} as the key.
 *
 * @since 1.22
 */
public class BasicDetailViewCache implements DetailViewCache {

	private final Map<EClass, ECPSWTView> cache;

	/**
	 * Creates a cache with maximal 5 entries.
	 */
	public BasicDetailViewCache() {
		this(5);
	}

	/**
	 * Creates a cache with a custom number of maximal entries.
	 *
	 * @param maxEntries The number of maximal entries to cache
	 */
	public BasicDetailViewCache(final int maxEntries) {
		cache = new LinkedHashMap<EClass, ECPSWTView>(maxEntries + 1, .75F, true) {
			private static final long serialVersionUID = 1L;

			// This method is called just after a new entry has been added
			@Override
			public boolean removeEldestEntry(Map.Entry<EClass, ECPSWTView> eldest) {
				final boolean result = size() > maxEntries;
				if (result) {
					eldest.getValue().dispose();
				}
				return result;
			}
		};
	}

	@Override
	public boolean isCached(EObject selection) {
		return selection != null && cache.containsKey(selection.eClass());
	}

	@Override
	public ECPSWTView getCachedView(EObject selection) {
		return selection == null ? null : cache.get(selection.eClass());
	}

	@Override
	public boolean cacheView(ECPSWTView ecpView) {
		// Don't attempt to cache a view already disposed
		if (ecpView == null || ecpView.getSWTControl().isDisposed()) {
			return false;
		}

		final EClass key = ecpView.getViewModelContext().getDomainModel().eClass();
		final ECPSWTView previous = cache.put(key, ecpView);

		if (previous != null && previous != ecpView) {
			previous.dispose();
		}

		return true;
	}

}
