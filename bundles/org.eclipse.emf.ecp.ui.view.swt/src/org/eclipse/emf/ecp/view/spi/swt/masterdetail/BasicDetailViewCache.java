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
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;

/**
 * A default implementation of the {@link DetailViewCache} that uses the {@link EClass} as the key.
 *
 * @since 1.22
 */
public class BasicDetailViewCache implements DetailViewCache {

	private final Map<EClass, Record> cache;

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
		cache = new LinkedHashMap<EClass, Record>(maxEntries + 1, .75F, true) {
			private static final long serialVersionUID = 1L;

			// This method is called just after a new entry has been added
			@Override
			public boolean removeEldestEntry(Map.Entry<EClass, Record> eldest) {
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
		return getRecord(selection).map(Supplier::get).orElse(null);
	}

	private Optional<Record> getRecord(EObject selection) {
		return Optional.ofNullable(selection).map(EObject::eClass).map(cache::get);
	}

	@Override
	public boolean cacheView(ECPSWTView ecpView) {
		// Don't attempt to cache a view already disposed
		if (ecpView == null || ecpView.getSWTControl().isDisposed()) {
			return false;
		}

		final EClass key = ecpView.getViewModelContext().getDomainModel().eClass();
		final Record existing = cache.get(key);

		if (existing == null) {
			// Easy. Add it
			cache.put(key, new Record(key, ecpView));
		} else if (existing.get() != ecpView) {
			// Not the same view. Dispose the old
			existing.dispose();
			cache.put(key, new Record(key, ecpView));
		} // Otherwise we already have cached exactly this view

		return true;
	}

	//
	// Nested types
	//

	/**
	 * Self-disposing record of the caching of a rendered view for some {@link EClass}.
	 * Disposes itself when the view context is disposed.
	 */
	private final class Record implements ViewModelContextDisposeListener, Supplier<ECPSWTView> {

		private final EClass eClass;
		private final ECPSWTView ecpView;

		Record(EClass eClass, ECPSWTView ecpView) {
			super();

			this.eClass = eClass;
			this.ecpView = ecpView;

			ecpView.getViewModelContext().registerDisposeListener(this);
		}

		void dispose() {
			ecpView.dispose();
		}

		@Override
		public ECPSWTView get() {
			return ecpView;
		}

		@Override
		public void contextDisposed(ViewModelContext viewModelContext) {
			dispose();
			cache.remove(eClass, this);
		}
	}

}
