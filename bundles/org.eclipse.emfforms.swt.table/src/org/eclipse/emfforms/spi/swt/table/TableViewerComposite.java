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
package org.eclipse.emfforms.spi.swt.table;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;

/**
 * A {@link Composite} containing a {@link TableViewer}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 */
public class TableViewerComposite extends AbstractTableViewerComposite {

	private TableViewer tableViewer;

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
	TableViewerComposite(Composite parent, int style, Object inputObject,
		TableViewerSWTCustomization customization,
		IObservableValue title, IObservableValue tooltip) {
		super(parent, style, inputObject, customization, title, tooltip);
	}

	/**
	 * @return the {@link TableViewer}
	 */
	@Override
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite#createTableViewer(org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected AbstractTableViewer createTableViewer(TableViewerSWTCustomization customization,
		Composite viewerComposite) {
		tableViewer = (TableViewer) customization.createTableViewer(viewerComposite);
		return tableViewer;
	}

	@Override
	protected AbstractColumnLayout createLayout(Composite viewerComposite) {
		final TableColumnLayout layout = new TableColumnLayout();
		viewerComposite.setLayout(layout);
		return layout;
	}

	@Override
	public Widget[] getColumns() {
		return tableViewer.getTable().getColumns();
	}

	@Override
	public void addColumnListener(ControlListener columnlistener) {
		for (int i = 0; i < tableViewer.getTable().getColumns().length; i++) {
			final TableColumn tableColumn = tableViewer.getTable().getColumns()[i];
			tableColumn.addControlListener(columnlistener);
		}
	}

	@Override
	public TableControl getTableControl() {
		return new TableControl() {
			@Override
			public boolean isDisposed() {
				return getTableViewer().getTable().isDisposed();
			}

			@Override
			public int getItemHeight() {
				return getTableViewer().getTable().getItemHeight();
			}

			@Override
			public boolean getHeaderVisible() {
				return getTableViewer().getTable().getHeaderVisible();
			}

			@Override
			public int getHeaderHeight() {
				return getTableViewer().getTable().getHeaderHeight();
			}
		};
	}

	@Override
	// TODO: refactor (ms)
	protected ViewerColumn createColumn(ColumnDescription columnDescription,
		EMFDataBindingContext emfDataBindingContext, AbstractTableViewer tableViewer) {
		final TableViewerColumnBuilder builder = TableViewerColumnBuilder
			.create();

		final TableViewerColumn column = builder
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
		final IObservableValue tooltipText = columnDescription.getColumnTooltip();
		emfDataBindingContext.bindValue(WidgetProperties.tooltipText().observe(column.getColumn()), tooltipText);

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
		for (int i = 0; i < getTableViewer().getTable().getColumns().length; i++) {
			if (!sortableColumns.contains(i)) {
				continue;
			}
			final int j = i;
			final TableColumn tableColumn = getTableViewer().getTable().getColumns()[i];
			final SelectionAdapter selectionAdapter = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					comparator.setColumn(j);
					final int dir = comparator.getDirection();
					tableViewer.getTable().setSortDirection(dir);
					tableViewer.getTable().setSortColumn(tableColumn);
					tableViewer.refresh();
				}
			};
			tableColumn.addSelectionListener(selectionAdapter);
		}

	}

}
