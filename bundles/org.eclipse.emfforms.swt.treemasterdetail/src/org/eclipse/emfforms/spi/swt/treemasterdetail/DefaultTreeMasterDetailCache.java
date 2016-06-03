/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;

/**
 * A default implementation of the TreeMasterDetailCache which uses the EClass as the key.
 *
 * @author Eugen Neufeld
 * @since 1.9
 *
 */
public class DefaultTreeMasterDetailCache implements TreeMasterDetailCache {

	private final Map<EClass, ECPSWTView> cache;

	/**
	 * Creates a cache with maximal 5 entries.
	 */
	public DefaultTreeMasterDetailCache() {
		this(5);
	}

	/**
	 * Creates a cache with a custom number of maximal entries.
	 *
	 * @param maxEntries The number of maximal entries to cache
	 */
	public DefaultTreeMasterDetailCache(final int maxEntries) {
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailCache#isChached(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean isChached(EObject selection) {
		return cache.containsKey(selection.eClass());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailCache#getCachedView(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ECPSWTView getCachedView(EObject selection) {
		return cache.get(selection.eClass());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailCache#cache(org.eclipse.emf.ecp.ui.view.swt.ECPSWTView)
	 */
	@Override
	public void cache(ECPSWTView ecpView) {
		cache.put(ecpView.getViewModelContext().getDomainModel().eClass(), ecpView);
	}

}
