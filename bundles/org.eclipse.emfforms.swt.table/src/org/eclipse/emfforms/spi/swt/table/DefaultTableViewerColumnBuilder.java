/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;

/**
 * The default table viewer configuration helper class.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class DefaultTableViewerColumnBuilder
	extends AbstractTableViewerColumnBuilder<TableViewer, TableViewerColumn> {

	/**
	 * The constructor.
	 *
	 * @param config the {@link ColumnConfiguration}
	 */
	public DefaultTableViewerColumnBuilder(ColumnConfiguration config) {
		super(config);
	}

	@Override
	public TableViewerColumn createViewerColumn(TableViewer tableViewer) {
		return new TableViewerColumn(tableViewer, getConfig().getStyleBits());
	}

	@Override
	protected Item getTableColumn(TableViewerColumn viewerColumn) {
		return viewerColumn.getColumn();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configureDatabinding(Widget column) {
		super.configureDatabinding(column);
		bindValue(column, WidgetProperties.tooltipText(), getConfig().getColumnTooltip());
	}

	@Override
	protected void configureViewerColumn(TableViewerColumn viewerColumn) {
		final TableColumn column = viewerColumn.getColumn();

		column.setResizable(getConfig().isResizeable());
		column.setMoveable(getConfig().isMoveable());
		// column.setWidth(width);
	}

	@Override
	protected void configureEditingSupport(TableViewerColumn viewerColumn, TableViewer tableViewer) {
		final Optional<EditingSupport> editingSupport = getConfig().createEditingSupport(tableViewer);
		if (editingSupport.isPresent()) {
			viewerColumn.setEditingSupport(editingSupport.get());
		}
	}

}
