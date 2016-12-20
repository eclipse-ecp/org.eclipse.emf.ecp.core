/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.TableControl;
import org.eclipse.emfforms.spi.swt.table.TableViewerComparator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.nebula.jface.gridviewer.GridColumnLayout;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * A {@link Composite} containing a {@link GridTableViewer}.
 *
 * @author Jonas Helming
 *
 */
public class GridTableViewerComposite extends AbstractTableViewerComposite {

	private GridTableViewer gridTableViewer;

	/**
	 * Default constructor.
	 *
	 * @param parent the parent {@link Composite}
	 * @param style the style bits
	 * @param inputObject the input object
	 * @param customization the {@link TableViewerSWTCustomization}
	 * @param title the title
	 * @param tooltip the tooltip
	 */
	GridTableViewerComposite(Composite parent, int style, Object inputObject, TableViewerSWTCustomization customization,
		IObservableValue title, IObservableValue tooltip) {
		super(parent, style, inputObject, customization, title, tooltip);
	}

	@Override
	public GridTableViewer getTableViewer() {
		return gridTableViewer;
	}

	@Override
	protected AbstractTableViewer createTableViewer(TableViewerSWTCustomization customization,
		Composite viewerComposite) {
		// TODO: Grid ugly cast
		gridTableViewer = (GridTableViewer) customization.createTableViewer(viewerComposite);
		return gridTableViewer;
	}

	@Override
	protected AbstractColumnLayout createLayout(Composite viewerComposite) {
		final GridColumnLayout layout = new GridColumnLayout();
		viewerComposite.setLayout(layout);
		return layout;
	}

	@Override
	public Widget[] getColumns() {
		return gridTableViewer.getGrid().getColumns();
	}

	@Override
	public void addColumnListener(ControlListener columnlistener) {
		for (int i = 0; i < gridTableViewer.getGrid().getColumns().length; i++) {
			final GridColumn gridColumn = gridTableViewer.getGrid().getColumns()[i];
			gridColumn.addControlListener(columnlistener);
		}

	}

	@Override
	public TableControl getTableControl() {
		return new TableControl() {

			@Override
			public boolean isDisposed() {
				return getTableViewer().getGrid().isDisposed();
			}

			@Override
			public int getItemHeight() {
				return getTableViewer().getGrid().getItemHeight();
			}

			@Override
			public boolean getHeaderVisible() {
				return getTableViewer().getGrid().getHeaderVisible();
			}

			@Override
			public int getHeaderHeight() {
				return getTableViewer().getGrid().getHeaderHeight();
			}
		};
	}

	// TODO: could be refactored to reduce overlap with TableViewerComposite
	// TODO: refactor (ms)
	@Override
	protected ViewerColumn createColumn(ColumnDescription columnDescription,
		EMFDataBindingContext emfDataBindingContext, AbstractTableViewer tableViewer) {
		final GridViewerColumnBuilder builder = GridViewerColumnBuilder
			.create();

		// TODO: set correct colors here
		// builder.setCellRenderer(new CustomSelectionColorCellRenderer(
		// getDisplay().getSystemColor(SWT.COLOR_WHITE),
		// getDisplay().getSystemColor(SWT.COLOR_CYAN)));

		final GridViewerColumn column = builder
			.setData(columnDescription.getData())
			.setData(RESIZABLE, columnDescription.isResizeable())
			.setMoveable(columnDescription.isMoveable())
			.setStyle(columnDescription.getStyleBits())
			.setData(WEIGHT, columnDescription.getWeight())
			.setData(MIN_WIDTH, columnDescription.getMinWidth())
			.build(getTableViewer());

		/* bind text and tooltip */
		final IObservableValue text = columnDescription.getColumnText();
		emfDataBindingContext.bindValue(WidgetProperties.text().observe(column.getColumn()), text);
		// TODO: Grid fix
		// final IObservableValue tooltipText = columnDescription.getColumnTooltip();
		// emfDataBindingContext.bindValue(WidgetProperties.tooltipText().observe(column.getColumn()), tooltipText);

		/* set label provider */
		column.setLabelProvider(columnDescription.createLabelProvider(tableViewer));

		/* set editing support */
		final Optional<EditingSupport> editingSupport = columnDescription.createEditingSupport(tableViewer);
		if (editingSupport.isPresent()) {
			column.setEditingSupport(editingSupport.get());
		}

		if (columnDescription.getColumnImage().isPresent()) {
			column.getColumn().setImage(columnDescription.getColumnImage().get());
		}
		return column;
	}

	@Override
	public void setComparator(final TableViewerComparator comparator, List<Integer> sortableColumns) {
		for (int i = 0; i < getTableViewer().getGrid().getColumns().length; i++) {
			if (!sortableColumns.contains(i)) {
				continue;
			}
			final int j = i;
			final GridColumn tableColumn = getTableViewer().getGrid().getColumns()[i];
			final SelectionAdapter selectionAdapter = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					comparator.setColumn(j);
					tableColumn.setSort(comparator.getDirection());
					gridTableViewer.refresh();
				}
			};
			tableColumn.addSelectionListener(selectionAdapter);
		}

	}

}
