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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;

/**
 * A cache for the TreeMasterDetail that allows to cache rendered ECPSWTViews and reuse them when switching between
 * elements in the tree.
 * 
 * @author Eugen Neufeld
 * @since 1.9
 *
 */
public interface TreeMasterDetailCache {

	/**
	 * Checks whether there is already a cached view available.
	 * 
	 * @param selection The new selection of the tree
	 * @return true if there is a cached view for the provided selection, false otherwise
	 */
	boolean isChached(EObject selection);

	/**
	 * Returns the previously cached view for the provided selection.
	 * 
	 * @param selection The new selection of the tree
	 * @return The cached view
	 */
	ECPSWTView getCachedView(EObject selection);

	/**
	 * Caches the provided {@link ECPSWTView} to allow it to be reused later.
	 * 
	 * @param ecpView The {@link ECPSWTView} to cache.
	 */
	void cache(ECPSWTView ecpView);

}
