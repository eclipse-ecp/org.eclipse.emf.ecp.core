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
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

/**
 * Protocol for asynchronous non-redundant refresh of a viewer.
 * This is {@linkplain Viewer#setData(String, Object) associated with the table viewer}
 * under the {@link #REFRESH_MANAGER} key.
 *
 * @since 1.21
 */
@FunctionalInterface
public interface ViewerRefreshManager {

	/** Viewer data key for the refresh manager. */
	String REFRESH_MANAGER = "refreshManager"; //$NON-NLS-1$

	/**
	 * Post an asynchronous request to refresh the table viewer.
	 */
	void postRefresh();

	/**
	 * Obtain the refresh manager instance for the given {@code viewer}.
	 *
	 * @param viewer a viewer
	 * @return its refresh manager or a simple default implementaiton; never {@code null}
	 */
	static ViewerRefreshManager getInstance(Viewer viewer) {
		final Object result = viewer.getData(REFRESH_MANAGER);

		if (result instanceof ViewerRefreshManager) {
			return (ViewerRefreshManager) result;
		}

		final Runnable refresher = getRefreshRunnable(viewer);
		return () -> viewer.getControl().getDisplay().asyncExec(refresher);
	}

	/**
	 * Obtain a runnable that {@linkplain Viewer#refresh() refreshes} a {@code viewer}.
	 *
	 * @param viewer a viewer to refresh
	 * @return the refresh manager
	 *
	 * @see Viewer#refresh()
	 */
	static Runnable getRefreshRunnable(Viewer viewer) {
		final Control control = viewer.getControl();

		return () -> {
			if (!control.isDisposed()) {
				viewer.refresh();
			}
		};
	}

}
