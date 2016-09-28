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

import java.util.StringTokenizer;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * {@link KeyListener} for the paste action on a {@link Grid} control.
 *
 * @author Alexandra Buzila
 * @since 1.10
 *
 */
public class GridPasteKeyListener implements KeyListener {

	private final Clipboard clipboard;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} on which to allocate this command's {@link Clipboard}.
	 */
	public GridPasteKeyListener(Display display) {
		clipboard = new Clipboard(display);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0 && e.keyCode == 'v') {
			final Grid grid = (Grid) e.widget;
			final Object contents = clipboard.getContents(TextTransfer.getInstance());
			if (contents instanceof String) {
				pasteSelection(grid, (String) contents);
			}
		}
	}

	/**
	 * Pastes the given contents in the grid.
	 * 
	 * @param grid the target {@link Grid}
	 * @param contents the contents to paste
	 */
	public void pasteSelection(Grid grid, String contents) {
		// ignore if no selection
		if (grid.getCellSelection().length == 0) {
			return;
		}
		// if multiple items are selected, we take the first one
		final Point startItem = grid.getCellSelection()[0];
		final int startColumn = startItem.x;
		final int startRow = startItem.y;

		int relativeRow = -1;
		int relativeColumn = -1;
		final StringTokenizer rowTokenizer = new StringTokenizer(contents, "\n", true); //$NON-NLS-1$
		while (rowTokenizer.hasMoreTokens()) {
			relativeRow++;
			String columnString = rowTokenizer.nextToken();
			boolean rowUpdate = false;
			while (rowTokenizer.hasMoreTokens() && columnString.equals("\n")) { //$NON-NLS-1$
				relativeRow++;
				columnString = rowTokenizer.nextToken();
				rowUpdate = true;
			}
			if (rowUpdate) {
				relativeRow--;
			}
			final StringTokenizer columnTokenizer = new StringTokenizer(columnString, "\t", true); //$NON-NLS-1$
			relativeColumn = -1;
			while (columnTokenizer.hasMoreTokens()) {
				relativeColumn++;
				String text = columnTokenizer.nextToken();
				while (columnTokenizer.hasMoreTokens() && text.equals("\t")) { //$NON-NLS-1$
					relativeColumn++;
					text = columnTokenizer.nextToken();
				}
				if (!text.equals("\t")) { //$NON-NLS-1$
					final int insertionIndex = startRow + relativeRow;
					if (insertionIndex < grid.getItemCount()) {
						grid.getItem(startRow + relativeRow).setText(startColumn + relativeColumn, text);
					}
				}
			}
		}
	}
}
