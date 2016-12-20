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
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
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
 * @author Mathias Schaefer
 * @since 1.10
 *
 */
public class GridPasteKeyListener implements KeyListener {

	private final Clipboard clipboard;
	private final EMFFormsDatabindingEMF dataBinding;
	private final EStructuralFeatureValueConverterService converterService;
	private final VControl vControl;

	private boolean selectPastedCells = true;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} on which to allocate this command's {@link Clipboard}.
	 * @param vControl the {@link VTableControl}.
	 * @param dataBinding {@link EMFFormsDatabindingEMF}
	 * @param converterService {@link EStructuralFeatureValueConverterService}
	 * @param selectPastedCells whether to select the pasted cells
	 */
	public GridPasteKeyListener(Display display, VControl vControl, EMFFormsDatabindingEMF dataBinding,
		EStructuralFeatureValueConverterService converterService, boolean selectPastedCells) {
		clipboard = new Clipboard(display);
		this.vControl = vControl;
		this.dataBinding = dataBinding;
		this.converterService = converterService;
		this.selectPastedCells = selectPastedCells;
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

		if (grid.getCellSelection().length == 0 || !vControl.isEnabled() || vControl.isReadonly()) {
			return;
		}

		final List<Point> pastedCells = new ArrayList<Point>();
		if (grid.getCellSelection().length > 1
			&& new StringTokenizer(contents, "\n\t", false).countTokens() == 1) { //$NON-NLS-1$

			// fill selection
			for (final Point startItem : grid.getCellSelection()) {
				pastedCells.addAll(pasteContents(startItem, grid, contents));
			}

		} else {

			// expand selection
			final Point startItem = grid.getCellSelection()[0];
			pastedCells.addAll(pasteContents(startItem, grid, contents));
		}

		if (selectPastedCells && !pastedCells.isEmpty() && grid.isCellSelectionEnabled()) {
			grid.setCellSelection(pastedCells.toArray(new Point[] {}));
		}

	}

	/**
	 * Performs the paste operation.
	 *
	 * @param startItem the start uten
	 * @param grid the grid
	 * @param contents the pasted contents
	 * @return the pasted cells
	 */
	@SuppressWarnings("restriction")
	public List<Point> pasteContents(Point startItem, Grid grid, String contents) {
		final int startColumn = startItem.x;
		final int startRow = startItem.y;

		final List<Point> pastedCells = new ArrayList<Point>();
		final List<Object> pastedValues = new ArrayList<Object>();
		int relativeRow = 0;
		final String[] rows = contents.split("\r\n|\n", -1); //$NON-NLS-1$

		for (final String row : rows) {

			int relativeColumn = 0;

			for (final String cellValue : row.split("\t", -1)) { //$NON-NLS-1$

				final int insertionColumnIndex = startColumn + relativeColumn;
				final int insertionRowIndex = startRow + relativeRow;

				if (insertionColumnIndex >= grid.getColumnCount()) {
					relativeColumn++;
					continue;
				}

				final VDomainModelReference dmr = (VDomainModelReference) grid.getColumn(insertionColumnIndex)
					.getData(AbstractTableViewerComposite.DMR);

				if (dmr == null || vControl instanceof VTableControl
					&& org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper
						.isReadOnly((VTableControl) vControl, dmr)) {
					relativeColumn++;
					continue;
				}

				if (insertionRowIndex < grid.getItemCount()) {

					final EObject eObject = (EObject) grid.getItem(insertionRowIndex).getData();

					IObservableValue value = null;
					try {

						value = dataBinding.getObservableValue(dmr, eObject);
						final Object convertedValue = converterService
							.convertToModelValue(eObject, (EStructuralFeature) value.getValueType(), cellValue);
						if (convertedValue != null) {
							value.setValue(convertedValue);
							pastedValues.add(value);
						}

						pastedCells.add(new Point(insertionColumnIndex, insertionRowIndex));

					}
					// BEGIN SUPRESS CATCH EXCEPTION
					catch (final Exception ex) {// END SUPRESS CATCH EXCEPTION
						// silently ignore this
					} finally {
						if (value != null) {
							value.dispose();
						}
					}

				}
				relativeColumn++;
			}
			relativeRow++;
		}
		return pastedCells;
	}

}
