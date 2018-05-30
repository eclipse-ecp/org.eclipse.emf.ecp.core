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
 * jonas - initial API and implementation
 * Lucas Koehler - RAP adaptions
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.Property;
import org.eclipse.emfforms.common.Property.ChangeListener;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerColumnBuilder;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;

/**
 * Nebula Grid viewer configuration helper class.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class GridViewerColumnBuilder extends AbstractTableViewerColumnBuilder<GridTableViewer, GridViewerColumn> {

	/**
	 * The constructor.
	 *
	 * @param config the {@link ColumnConfiguration}
	 */
	public GridViewerColumnBuilder(ColumnConfiguration config) {
		super(config);
	}

	@Override
	public GridViewerColumn createViewerColumn(GridTableViewer tableViewer) {
		return new GridViewerColumn(tableViewer, getConfig().getStyleBits());
	}

	@Override
	protected void configure(GridTableViewer tableViewer, GridViewerColumn viewerColumn) {
		super.configure(tableViewer, viewerColumn);

		// Nebula Grid supports a few more things
		configureHideShow(tableViewer, viewerColumn);
	}

	@Override
	protected Item getTableColumn(GridViewerColumn viewerColumn) {
		return viewerColumn.getColumn();
	}

	@Override
	protected void configureViewerColumn(GridViewerColumn viewerColumn) {
		final GridColumn column = viewerColumn.getColumn();

		column.setResizeable(getConfig().isResizeable());
		column.setMoveable(getConfig().isMoveable());
		column.setVisible(getConfig().visible().getValue());
		// column.getColumn().setWidth(width);
	}

	@Override
	protected void configureEditingSupport(GridViewerColumn viewerColumn, GridTableViewer tableViewer) {
		final Optional<EditingSupport> editingSupport = getConfig().createEditingSupport(tableViewer);
		if (editingSupport.isPresent()) {
			viewerColumn.setEditingSupport(editingSupport.get());
		}
	}

	/**
	 * Configure hide/show columns toggle.
	 *
	 * @param tableViewer the table viewer
	 * @param viewerColumn the viewer column to configure
	 */
	protected void configureHideShow(final GridTableViewer tableViewer, final GridViewerColumn viewerColumn) {

		getConfig().visible().addChangeListener(new ChangeListener<Boolean>() {
			@Override
			public void valueChanged(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
				getConfig().matchFilter().resetToDefault();

				final GridColumn column = viewerColumn.getColumn();
				final Listener hideShowListener = extractShowListener(viewerColumn.getColumn());
				if (hideShowListener != null) {
					column.removeListener(SWT.Show, hideShowListener);
					column.removeListener(SWT.Hide, hideShowListener);
				}
				column.setVisible(newValue);
				if (hideShowListener != null) {
					column.addListener(SWT.Show, hideShowListener);
					column.addListener(SWT.Hide, hideShowListener);
				}
			}
		});

	}

	private static Listener extractShowListener(final GridColumn column) {
		for (final Listener listener : column.getListeners(SWT.Show)) {
			if (listener.getClass().getEnclosingClass() != null
				&& ControlEditor.class.isAssignableFrom(listener.getClass().getEnclosingClass())) {
				return listener;
			}
		}
		return null;
	}
}
