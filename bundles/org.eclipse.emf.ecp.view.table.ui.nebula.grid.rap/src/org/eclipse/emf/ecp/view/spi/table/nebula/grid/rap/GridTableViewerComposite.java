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
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 * Lucas Koehler - adaptions to RAP limitations
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap.menu.GridColumnAction;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap.messages.Messages;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableControl;
import org.eclipse.emfforms.spi.swt.table.TableViewerComparator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.nebula.jface.gridviewer.GridColumnLayout;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;

/**
 * A {@link Composite} containing a {@link GridTableViewer}.
 *
 * @author Jonas Helming
 *
 */
public class GridTableViewerComposite extends AbstractTableViewerComposite<GridTableViewer> {

	private static final long serialVersionUID = 2569328255801045004L;
	private GridTableViewer gridTableViewer;
	private TableViewerComparator comparator;
	private List<Integer> sortableColumns;

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
	@SuppressWarnings("unchecked")
	public GridTableViewerComposite(Composite parent, int style, Object inputObject,
		TableViewerSWTCustomization customization,
		IObservableValue title, IObservableValue tooltip) {
		super(parent, style, inputObject, customization, title, tooltip);
	}

	@Override
	public GridTableViewer getTableViewer() {
		return gridTableViewer;
	}

	@Override
	protected GridTableViewer createTableViewer(TableViewerSWTCustomization<GridTableViewer> customization,
		Composite viewerComposite) {
		gridTableViewer = customization.createTableViewer(viewerComposite);
		gridTableViewer.getGrid().setAutoHeight(true);
		return gridTableViewer;
	}

	@Override
	protected void configureContextMenu(GridTableViewer tableViewer) {
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);

		if (getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)) {
			menuMgr.addMenuListener(new ColumnHideShowMenuListener());
		}

		// TODO usable when the nebula grid RAP implementation supports header controls
		// if (getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_FILTER)) {
		// menuMgr.addMenuListener(new ColumnFilterMenuListener());
		// }

		final Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
	}

	@Override
	protected void configureViewerFilters(GridTableViewer tableViewer) {
		// TODO usable when the nebula grid RAP implementation supports header controls
		// if (getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_FILTER)) {
		// tableViewer.addFilter(new GridColumnFilterViewerFilter(this, tableViewer));
		// }
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

			@Override
			public int getItemCount() {
				return getTableViewer().getGrid().getItemCount();
			}
		};
	}

	@Override
	protected ViewerColumn createColumn(final ColumnConfiguration config,
		EMFDataBindingContext emfDataBindingContext, final GridTableViewer tableViewer) {

		final GridViewerColumn column = new GridViewerColumnBuilder(config)
			.withDatabinding(emfDataBindingContext)
			.build(tableViewer);

		column.getColumn().setWordWrap(true);

		return column;
	}

	@Override
	public void setComparator(final TableViewerComparator comparator, List<Integer> sortableColumns) {
		this.comparator = comparator;
		this.sortableColumns = sortableColumns;
		for (int i = 0; i < getTableViewer().getGrid().getColumns().length; i++) {
			if (!sortableColumns.contains(i)) {
				continue;
			}
			final int j = i;
			final GridColumn tableColumn = getTableViewer().getGrid().getColumns()[i];
			final SelectionAdapter selectionAdapter = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					setCompareColumn(j);
				}
			};
			tableColumn.addSelectionListener(selectionAdapter);
		}

	}

	private ColumnConfiguration getCurrentColumnConfig() {

		final Grid grid = getTableViewer().getGrid();
		final Point cursorLocation = grid.getDisplay().getCursorLocation();
		final GridColumn column = grid.getColumn(grid.toControl(cursorLocation));

		if (column == null) {
			return null;
		}
		return getColumnConfiguration(column);
	}

	/**
	 * Column hide/show menu listener.
	 *
	 * @author Mat Hansen
	 *
	 */
	private class ColumnHideShowMenuListener implements IMenuListener {

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			final ColumnConfiguration columnConfiguration = getCurrentColumnConfig();
			if (columnConfiguration == null) {
				return;
			}
			manager.add(new GridColumnAction(GridTableViewerComposite.this,
				Messages.GridTableViewerComposite_hideColumnAction) {
				@Override
				public void run() {
					columnConfiguration.visible().setValue(Boolean.FALSE);
				}

				@Override
				public boolean isEnabled() {
					if (!super.isEnabled()) {
						return false;
					}
					return columnConfiguration.getEnabledFeatures()
						.contains(ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW);
				}
			});
			manager.add(new GridColumnAction(GridTableViewerComposite.this,
				Messages.GridTableViewerComposite_showAllColumnsAction) {
				@Override
				public void run() {
					for (final Widget widget : getColumns()) {
						getGridTableViewer().getColumnConfiguration(widget).visible().setValue(Boolean.TRUE);
					}
				}

				@Override
				public boolean isEnabled() {
					return getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)
						&& hasHiddenColumns();
				}

				boolean hasHiddenColumns() {
					for (final Widget widget : getColumns()) {
						if (!getGridTableViewer().getColumnConfiguration(widget).visible().getValue()) {
							return true;
						}
					}
					return false;
				}
			});
		}

	}

	@Override
	public void setCompareColumn(int columnIndex) {
		// Reset other columns to avoid left over sort indicators
		for (final int index : sortableColumns) {
			final GridColumn column = getTableViewer().getGrid().getColumns()[index];
			if (index != columnIndex && column.getSort() != SWT.NONE) {
				column.setSort(SWT.NONE);
			}
		}
		comparator.setColumn(columnIndex);
		final GridColumn tableColumn = getTableViewer().getGrid().getColumns()[columnIndex];
		tableColumn.setSort(comparator.getDirection());
		gridTableViewer.refresh();
	}

	// TODO usable when the nebula grid RAP implementation supports header controls
	// /**
	// * Column hide/show menu listener.
	// *
	// * @author Mat Hansen
	// *
	// */
	// private class ColumnFilterMenuListener implements IMenuListener {
	//
	// @Override
	// public void menuAboutToShow(IMenuManager manager) {
	// final ColumnConfiguration columnConfiguration = getCurrentColumnConfig();
	// if (columnConfiguration == null) {
	// return;
	// }
	// manager.add(new GridColumnAction(GridTableViewerComposite.this,
	// Messages.GridTableViewerComposite_toggleFilterControlsAction) {
	// @Override
	// public void run() {
	// for (final Widget widget : getColumns()) {
	// PropertyHelper.toggle(
	// getGridTableViewer().getColumnConfiguration(widget).showFilterControl());
	// }
	//
	// // getGrid().recalculateHeader();
	// }
	//
	// @Override
	// public boolean isEnabled() {
	// if (!super.isEnabled()) {
	// return false;
	// }
	// return columnConfiguration.getEnabledFeatures()
	// .contains(ColumnConfiguration.FEATURE_COLUMN_FILTER);
	// }
	// });
	// }
	//
	// }

	// TODO usable when the nebula grid RAP implementation supports header controls
	// /**
	// * Viewer filter for column filter support.
	// *
	// * @author Mat Hansen
	// *
	// */
	// private class GridColumnFilterViewerFilter extends ViewerFilter {
	//
	// private final GridTableViewerComposite tableViewerComposite;
	// private final GridTableViewer tableViewer;
	// private final Grid grid;
	//
	// /**
	// * The Constructor.
	// *
	// * @param tableViewerComposite the Grid table viewer composite.
	// * @param tableViewer the Grid table viewer.
	// */
	// GridColumnFilterViewerFilter(
	// GridTableViewerComposite tableViewerComposite,
	// GridTableViewer tableViewer) {
	// super();
	// this.tableViewerComposite = tableViewerComposite;
	// this.tableViewer = tableViewer;
	// grid = tableViewer.getGrid();
	// }
	//
	// @Override
	// public boolean select(Viewer viewer, Object parentElement, Object element) {
	//
	// if (grid.getItemCount() == 0) {
	// return true;
	// }
	//
	// grid.setRedraw(false);
	// final GridItem dummyItem = new GridItem(grid, SWT.NONE);
	//
	// try {
	//
	// dummyItem.setData(element);
	// final GridViewerRow viewerRow = (GridViewerRow) ((CustomGridTableViewer) tableViewer)
	// .getViewerRowFromItem(dummyItem);
	//
	// for (final Widget widget : getColumns()) {
	//
	// final ColumnConfiguration config = tableViewerComposite.getColumnConfiguration(widget);
	//
	// final Object filter = config.matchFilter().getValue();
	// if (filter == null || String.valueOf(filter).isEmpty()) {
	// continue;
	// }
	//
	// final GridColumn column = (GridColumn) widget;
	// final int columnIndex = tableViewer.getGrid().indexOf(column);
	//
	// final ViewerCell cell = viewerRow.getCell(columnIndex);
	// final CellLabelProvider labelProvider = tableViewer.getLabelProvider(columnIndex);
	// labelProvider.update(cell);
	//
	// if (!matchesColumnFilter(cell.getText(), filter)) {
	// return false;
	// }
	//
	// }
	//
	// } finally {
	// dummyItem.dispose();
	// grid.setRedraw(true);
	// }
	//
	// return true;
	// }
	//
	// /**
	// * Test whether the given value/filter combination matches.
	// *
	// * @param value the value to test
	// * @param filterValue the filter value
	// * @return true if the value matches the filter value
	// */
	// protected boolean matchesColumnFilter(Object value, Object filterValue) {
	//
	// if (filterValue == null) {
	// return false;
	// }
	//
	// return String.valueOf(value).toLowerCase()
	// .contains(String.valueOf(filterValue).toLowerCase());
	// }
	//
	// }

}
