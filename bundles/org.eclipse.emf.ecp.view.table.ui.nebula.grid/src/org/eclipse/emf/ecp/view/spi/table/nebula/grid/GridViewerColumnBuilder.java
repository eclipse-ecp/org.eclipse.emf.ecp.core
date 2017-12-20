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
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.Property;
import org.eclipse.emfforms.common.Property.ChangeListener;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerColumnBuilder;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

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
		configureFiltering(tableViewer, viewerColumn);

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
				Listener hideShowListener = extractShowListener(viewerColumn.getColumn());
				if (column.getHeaderControl() == null && hideShowListener != null) {
					column.removeListener(SWT.Show, hideShowListener);
					column.removeListener(SWT.Hide, hideShowListener);
				} else {
					hideShowListener = null;
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

	/**
	 * Configure column filter.
	 *
	 * @param tableViewer the table viewer
	 * @param viewerColumn the viewer column to configure
	 */
	protected void configureFiltering(final GridTableViewer tableViewer, final GridViewerColumn viewerColumn) {
		final GridColumn column = viewerColumn.getColumn();

		getConfig().showFilterControl().addChangeListener(new ChangeListener<Boolean>() {

			private Control filterControl;

			@Override
			public void valueChanged(Property<Boolean> property, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					filterControl = createHeaderFilterControl(column.getParent());
					column.setHeaderControl(filterControl);
				} else {
					column.setHeaderControl(null);
					if (filterControl != null) {
						filterControl.dispose();
					}

					getConfig().matchFilter().resetToDefault();
				}
				// hack: force header height recalculation
				column.setWidth(column.getWidth());
			}

		});

		getConfig().matchFilter().addChangeListener(new ChangeListener<Object>() {
			@Override
			public void valueChanged(Property<Object> property, Object oldValue, Object newValue) {
				tableViewer.refresh();
			}
		});

	}

	/**
	 * Creates a column filter control.
	 *
	 * @param parent the parent composite
	 * @return new filter control instance
	 */
	protected Control createHeaderFilterControl(Composite parent) {

		final Composite filterComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(filterComposite);

		final Text txtFilter = new Text(filterComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(txtFilter);

		txtFilter.addModifyListener(new ModifyListener() {
			private final Runnable runnable = new Runnable() {
				@Override
				public void run() {
					if (!txtFilter.isDisposed()) {
						getConfig().matchFilter().setValue(txtFilter.getText());
					}
				}
			};

			@Override
			public void modifyText(ModifyEvent e) {
				Display.getDefault().timerExec(300, runnable);
			}
		});

		filterComposite.addListener(SWT.Show, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!txtFilter.isDisposed() && getConfig().matchFilter().getValue() != null) {
					txtFilter.setText(String.valueOf(getConfig().matchFilter().getValue()));
				}
			}
		});

		final Button btnClear = new Button(filterComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(btnClear);
		btnClear.setText("x"); //$NON-NLS-1$
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtFilter.setText(""); //$NON-NLS-1$
			}
		});

		return filterComposite;
	}

}
