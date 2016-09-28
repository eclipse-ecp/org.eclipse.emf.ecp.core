/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.internal.nebula.grid;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * {@link KeyListener} for the copy action on a {@link Grid} control.
 *
 * @author Alexandra Buzila
 * @since 1.10
 *
 */
public class GridCopyKeyListener implements KeyListener {
	private final Clipboard clipboard;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} on which to allocate this command's {@link Clipboard}.
	 */
	public GridCopyKeyListener(Display display) {
		clipboard = new Clipboard(display);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0 && e.keyCode == 'c') {
			final Grid grid = (Grid) e.widget;

			final String selectionText = getSelectionAsText(grid);
			if (selectionText == null || selectionText.isEmpty()) {
				return;
			}
			final String[] data = { selectionText };
			final TextTransfer[] dataTypes = { TextTransfer.getInstance() };

			clipboard.setContents(data, dataTypes);
		}
	}

	/**
	 * Returns the table selection of the {@link Grid} as a formatted string.
	 *
	 * @param grid the {@link Grid} control
	 * @return the selection
	 */
	public String getSelectionAsText(Grid grid) {
		final Point[] cellSelection = grid.getCellSelection();
		final StringBuilder selection = new StringBuilder();
		int minRow = Integer.MAX_VALUE;
		int minColumn = Integer.MAX_VALUE;
		int maxRow = Integer.MIN_VALUE;
		int maxColumn = Integer.MIN_VALUE;
		for (final Point point : cellSelection) {
			final int row = point.y;
			final int col = point.x;
			if (row < minRow) {
				minRow = row;
			}
			if (row > maxRow) {
				maxRow = row;
			}
			if (col < minColumn) {
				minColumn = col;
			}
			if (col > maxColumn) {
				maxColumn = col;
			}
		}
		final int columnSize = maxColumn - minColumn + 1;
		final int rowSize = maxRow - minRow + 1;
		final String[][] tableSelection = initializeTableSelection(grid, cellSelection, columnSize, rowSize, minColumn,
			minRow);
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {
				final String text = tableSelection[j][i];
				if (j != 0) {
					selection.append('\t');
				}
				if (text != null) {
					selection.append(text);
				}
			}
			if (i != rowSize - 1) {
				selection.append('\n');
			}
		}
		return selection.toString();
	}

	private String[][] initializeTableSelection(Grid grid, Point[] cellSelection, int columnSize, int rowSize,
		int minColumn,
		int minRow) {
		final String[][] tableSelection = new String[columnSize][rowSize];
		for (int i = 0; i < cellSelection.length; i++) {
			final int column = cellSelection[i].x;
			final int row = cellSelection[i].y;
			final String text = grid.getItem(row).getText(column);
			tableSelection[column - minColumn][row - minRow] = text;
		}
		return tableSelection;
	}
}
