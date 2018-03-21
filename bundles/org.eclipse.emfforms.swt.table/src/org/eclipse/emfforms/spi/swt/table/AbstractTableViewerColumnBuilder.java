/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Map.Entry;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;

/**
 * A table viewer configuration helper class.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 * @param <V> the TableViewer implementation to use
 * @param <C> the {@link ViewerColumn} implementation to use
 */
public abstract class AbstractTableViewerColumnBuilder<V extends AbstractTableViewer, C extends ViewerColumn> {

	/**
	 * Column ID counter.
	 */
	private int columnId;

	/**
	 * The {@link ColumnConfiguration}.
	 */
	private final ColumnConfiguration config;

	/**
	 * The {@link EMFDataBindingContext}.
	 */
	private EMFDataBindingContext dataBindingContext;

	/**
	 * The constructor.
	 *
	 * @param config the {@link ColumnConfiguration}
	 */
	public AbstractTableViewerColumnBuilder(ColumnConfiguration config) {
		this.config = config;
	}

	/**
	 * Creates a new viewer column.
	 *
	 * @param tableViewer the parent table viewer
	 * @return the table viewer column
	 */
	public C build(V tableViewer) {
		final C tableViewerColumn = createViewerColumn(tableViewer);

		configure(tableViewer, tableViewerColumn);
		executeCallbacks(tableViewer, tableViewerColumn);

		return tableViewerColumn;
	}

	/**
	 * Creates a new ViewerColumn instance.
	 *
	 * @param tableViewer the TableViewer
	 * @return a ViewerColumn instance
	 */
	public abstract C createViewerColumn(V tableViewer);

	/**
	 * Returns the table column control for the given viewer column.
	 *
	 * @param viewerColumn the viewer column
	 * @return a table column control
	 */
	protected abstract Item getTableColumn(C viewerColumn);

	/**
	 * Configures a viewer column instance.
	 *
	 * @param viewerColumn the viewer column to configure
	 */
	protected abstract void configureViewerColumn(C viewerColumn);

	/**
	 * Configures a viewer column instance.
	 *
	 * @param tableViewer the table viewer the column belongs to
	 * @param viewerColumn the viewer column to configure
	 */
	protected void configure(V tableViewer, C viewerColumn) {

		final Item tableColumn = getTableColumn(viewerColumn);

		configureDatabinding(tableColumn);
		configureLabelProvider(viewerColumn, tableViewer);
		configureEditingSupport(viewerColumn, tableViewer);

		configureContextMap(tableColumn);
		configureViewerColumn(viewerColumn);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void executeCallbacks(V tableViewer, C tableViewerColumn) {
		for (final ConfigurationCallback callback : config.getConfigurationCallbacks()) {
			callback.configure(config, tableViewer, tableViewerColumn);
		}
	}

	/**
	 * Configures the EditingSupport for the given table viewer/column.
	 *
	 * @param viewerColumn the viewer column to configure
	 * @param tableViewer the table viewer the column belongs to
	 */
	protected abstract void configureEditingSupport(C viewerColumn, V tableViewer);

	/**
	 * Adds databinding support.
	 *
	 * @param dataBindingContext the {@link EMFDataBindingContext} to use
	 * @return a new TableViewerColumnBuilder instance
	 */
	public AbstractTableViewerColumnBuilder<V, C> withDatabinding(EMFDataBindingContext dataBindingContext) {
		this.dataBindingContext = dataBindingContext;
		return this;
	}

	/**
	 * Binds a value to a widget value property.
	 *
	 * @param column the widget
	 * @param valueProperty the value property to bind to
	 * @param observable the value to bind to
	 */
	protected void bindValue(Widget column, IWidgetValueProperty valueProperty, IObservableValue observable) {
		if (dataBindingContext == null) {
			return;
		}
		dataBindingContext.bindValue(valueProperty.observe(column), observable);
	}

	/**
	 * Configures data binding for the given column widget.
	 *
	 * @param column the column widget to configure
	 */
	protected void configureDatabinding(Widget column) {
		bindValue(column, WidgetProperties.text(), config.getColumnText());
	}

	/**
	 * Configures the label provider of the given column.
	 *
	 * @param column the column to configure
	 * @param tableViewer the table viewer the column belongs to
	 */
	protected void configureLabelProvider(C column, V tableViewer) {
		column.setLabelProvider(config.createLabelProvider(tableViewer));
	}

	/**
	 * Configures a image for the given column item.
	 *
	 * @param column the column item to configure an image for
	 */
	protected void configureImage(Item column) {
		if (config.getColumnImage().isPresent()) {
			column.setImage(config.getColumnImage().get());
		}
	}

	/**
	 * Configures the context map of the given widget.
	 *
	 * @param column the column widget to configure
	 */
	protected void configureContextMap(Widget column) {

		for (final Entry<String, Object> entry : config.getData().entrySet()) {
			column.setData(entry.getKey(), entry.getValue());
		}

		column.setData(ColumnConfiguration.ID, config);

		column.setData(ColumnConfiguration.COLUMN_ID, columnId++);

		column.setData(ColumnConfiguration.RESIZABLE, config.isResizeable());
		column.setData(ColumnConfiguration.WEIGHT, config.getWeight());
		column.setData(ColumnConfiguration.MIN_WIDTH, config.getMinWidth());
	}

	/**
	 * @return the {@link ColumnConfiguration}
	 */
	public ColumnConfiguration getConfig() {
		return config;
	}

}
