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
 * Mat Hansen - initial API and implementation
 * Christian W. Damus - bugs 534829, 530314
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.menu;

import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

/**
 * Helper class for Nebula Grid column-based context menu actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class GridColumnAction extends GridAction {

	private CurrentColumnProvider currentColumnProvider = this::guessCurrentColumn;

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

	/**
	 * Initializes me with my grid composite, label, and style.
	 *
	 * @param gridTableViewerComposite the {@link GridTableViewerComposite}
	 * @param actionLabel the label text for the menu action
	 * @param style my presentation style
	 *
	 */
	public GridColumnAction(GridTableViewerComposite gridTableViewerComposite, String actionLabel, int style) {
		super(gridTableViewerComposite, actionLabel, style);
	}

	@Override
	public boolean isEnabled() {
		if (!super.isEnabled()) {
			return false;
		}
		final Widget column = currentColumnProvider.getCurrentColumn();
		if (column == null) {
			return false;
		}
		final ColumnConfiguration columnConfig = getGridTableViewer().getColumnConfiguration(column);

		return columnConfig != null;
	}

	/**
	 * Set a provider of the current column.
	 *
	 * @param currentColumnProvider the current column provider
	 *
	 * @since 1.21
	 */
	protected void setCurrentColumnProvider(CurrentColumnProvider currentColumnProvider) {
		this.currentColumnProvider = currentColumnProvider;
	}

	/**
	 * Guess the current column based on the current mouse pointer location.
	 *
	 * @return the current column by pointer location
	 */
	private Widget guessCurrentColumn() {
		final Display currentDisplay = getGrid().getDisplay();
		final Point cursorLocation = currentDisplay.getCursorLocation();
		return getGrid().getColumn(getGrid().toControl(cursorLocation));
	}

	//
	// Nested types
	//

	/**
	 * Protocol for a grid control that can provide the column that the user currently appears
	 * to be interacting with (usually because it was selected, or the mouse pointer is on it).
	 *
	 * @since 1.21
	 */
	@FunctionalInterface
	protected interface CurrentColumnProvider {
		/**
		 * Query the current column, if any.
		 *
		 * @return the current column, or {@code null} if none can be determined
		 */
		Widget getCurrentColumn();
	}

}
