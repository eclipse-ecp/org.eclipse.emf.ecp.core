/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.menu;

import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Helper class for Nebula Grid column-based context menu actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class GridColumnAction extends GridAction {

	/**
	 * The constructor.
	 *
	 * @param gridTableViewerComposite the {@link GridTableViewerComposite}
	 * @param actionLabel the label text for the menu action
	 *
	 */
	public GridColumnAction(GridTableViewerComposite gridTableViewerComposite, String actionLabel) {
		super(gridTableViewerComposite, actionLabel);
	}

	@Override
	public boolean isEnabled() {
		if (!super.isEnabled()) {
			return false;
		}
		final Display currentDisplay = getGrid().getDisplay();
		final Point cursorLocation = currentDisplay.getCursorLocation();
		final GridColumn column = getGrid().getColumn(getGrid().toControl(cursorLocation));
		if (column == null) {
			return false;
		}
		final ColumnConfiguration columnConfig = getGridTableViewer().getColumnConfiguration(column);

		return columnConfig != null;
	}

}