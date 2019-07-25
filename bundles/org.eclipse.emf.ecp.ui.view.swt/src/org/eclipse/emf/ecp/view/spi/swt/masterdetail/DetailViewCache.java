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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * A cache for the <em>Master-Detail</em> renderers that caches rendered {@link ECPSWTView}s
 * and reuses them when switching between elements in the master viewer. Whether the cache
 * is size-limited is implementation-dependent.
 *
 * @since 1.22
 */
public interface DetailViewCache {

	/**
	 * Name of an {@link ViewModelContext#putContextValue(String, Object) context value}
	 * specifying the view cache to instantiate in the editor, for master-detail
	 * renderers that support caching. May be a positive {@link Integer} value to specify
	 * the cache size, or {@code Boolean} to enable (or not) a cache of the default size.
	 */
	String DETAIL_VIEW_CACHE_SIZE = "detailViewCacheSize"; //$NON-NLS-1$

	/**
	 * A cache implementation that does not cache anything. It is always empty.
	 */
	DetailViewCache EMPTY = new EmptyDetailViewCache();

	/**
	 * Checks whether there is already a cached view available.
	 *
	 * @param selection The new master object selection
	 * @return {@code true} if there is a cached view for the provided {@code selection}; {@code false} otherwise
	 */
	boolean isCached(EObject selection);

	/**
	 * Returns the previously cached view for the provided selection.
	 *
	 * @param selection The new master object selection
	 * @return the cached view, or {@code null} if none is cached
	 *
	 * @see #isCached(EObject)
	 */
	ECPSWTView getCachedView(EObject selection);

	/**
	 * <p>
	 * Caches the provided {@link ECPSWTView} to allow it to be reused later, if there is
	 * room for it the cache.
	 * </p>
	 * <p>
	 * <strong>Note</strong> that a view is cached when it is no longer required, usually
	 * because another detail view is taking its place in the editor. Consequently, if the cache
	 * is size-limited and cannot actually cache the view, it must
	 * {@linkplain ECPSWTView#dispose() dispose that view}.
	 * </p>
	 *
	 * @param ecpView the {@link ECPSWTView} to cache
	 *
	 * @return {@code true} if the view was added to the cache; {@code false} otherwise (in which
	 *         case it would be disposed)
	 */
	boolean cacheView(ECPSWTView ecpView);

	/**
	 * Dispose all cached views, emptying the cache. The cache must still be in
	 * a state to be used (this is not a "dispose" operation).
	 */
	void clear();

	/**
	 * Create a standard cache as indicated by the {@link #DETAIL_VIEW_CACHE_SIZE} value
	 * in the given {@code context}.
	 *
	 * @param context the master view model context
	 * @return the detail view cache (never {@code null})
	 */
	static DetailViewCache createCache(ViewModelContext context) {
		DetailViewCache result;

		final Object value = context.getContextValue(DETAIL_VIEW_CACHE_SIZE);

		if (value instanceof Boolean) {
			result = (Boolean) value ? new BasicDetailViewCache() : EMPTY;
		} else if (value instanceof Integer) {
			final int intValue = (Integer) value;
			result = intValue > 0 ? new BasicDetailViewCache(intValue) : EMPTY;
		} else {
			result = EMPTY;
		}

		return result;
	}
}
