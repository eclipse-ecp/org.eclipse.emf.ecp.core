/**
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
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap.menu;

import org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap.GridTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.Grid;

/**
 * Helper class for Nebula Grid context menu actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
class GridAction extends Action {

	private final GridTableViewerComposite gridTableViewerComposite;

	/**
	 * The constructor.
	 *
	 * @param gridTableViewerComposite the {@link GridTableViewerComposite}
	 * @param actionLabel the label text for the menu action
	 *
	 */
	GridAction(GridTableViewerComposite gridTableViewerComposite, String actionLabel) {
		this.gridTableViewerComposite = gridTableViewerComposite;
		setText(actionLabel);
	}

	/**
	 * Returns the current grid instance.
	 *
	 * @return the {@link Grid}
	 */
	public Grid getGrid() {
		return gridTableViewerComposite.getTableViewer().getGrid();
	}

	/**
	 * Returns the table viewer instance.
	 *
	 * @return the table viewer
	 */
	public AbstractTableViewerComposite<GridTableViewer> getGridTableViewer() {
		return gridTableViewerComposite;
	}

	@Override
	public boolean isEnabled() {
		return getGrid() != null;
	}

}
