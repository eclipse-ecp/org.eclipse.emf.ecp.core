/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import java.util.Map;

import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;
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
	private boolean triggerActive;

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
		setTriggerActive((e.stateMask & SWT.CTRL) != 0 && e.keyCode == 'c');
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isTriggerActive()) {
			final Grid grid = (Grid) e.widget;
			copySelectionToClipboard(grid);
		}
	}

	/**
	 * Copies the table selection of the {@link Grid} as a formatted string (if a selection exists).
	 *
	 * @param grid the {@link Grid} control.
	 */
	public void copySelectionToClipboard(Grid grid) {
		final String selectionText = getSelectionAsText(grid);
		if (selectionText == null || selectionText.isEmpty()) {
			return;
		}
		final String[] data = { selectionText };
		final TextTransfer[] dataTypes = { TextTransfer.getInstance() };

		clipboard.setContents(data, dataTypes);
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
		int minColumn, int minRow) {
		final String[][] tableSelection = new String[columnSize][rowSize];
		for (int i = 0; i < cellSelection.length; i++) {
			final int column = cellSelection[i].x;
			final int row = cellSelection[i].y;
			final String text = getText(grid, column, row);
			tableSelection[column - minColumn][row - minRow] = text;
		}
		return tableSelection;
	}

	private String getText(Grid grid, int column, int row) {
		final GridItem item = grid.getItem(row);
		@SuppressWarnings("unchecked")
		final Map<Integer, String> copyAlternative = (Map<Integer, String>) item
			.getData(ECPCellEditor.COPY_STRING_ALTERNATIVE);
		if (copyAlternative != null && copyAlternative.containsKey(column)) {
			return copyAlternative.get(column);
		}
		final String text = item.getText(column);
		return text;
	}

	/**
	 * @return <code>true</code> if copy was triggered in key pressed
	 */
	protected boolean isTriggerActive() {
		return triggerActive;
	}

	/**
	 * May be called from {@link #keyPressed(KeyEvent)} to indicated whether this triggers the action.
	 * 
	 * @param triggerActive <code>true</code> if key release should perform the action, <code>false</code> otherwise
	 */
	protected void setTriggerActive(boolean triggerActive) {
		this.triggerActive = triggerActive;
	}
}
