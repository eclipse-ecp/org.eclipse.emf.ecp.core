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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

/**
 * @author Jonas Helming
 *
 */
public class TableViewerColumnBuilder {

	/**
	 * Creates a new viewer column.
	 *
	 * @param tableViewer the parent table viewer
	 * @return the {@link TableViewerColumn}
	 */
	protected TableViewerColumn buildViewerColumn(TableViewer tableViewer) {
		return new TableViewerColumn(tableViewer, style);
	}

	/**
	 *
	 * @return creates an instance of a {@link TableViewerColumnBuilder}
	 */
	public static TableViewerColumnBuilder create() {
		return new TableViewerColumnBuilder();
	}

	/**
	 * Creates and customizes a {@link TableViewerColumn} for the given table viewer.
	 *
	 * @param tableViewer the target {@link TableViewerColumn}
	 * @return the {@link TableViewerColumn}
	 * @see #setText(TableViewerColumn)
	 * @see #setToolTipText(TableViewerColumn)
	 * @see #setResizable(boolean)
	 * @see #setMoveable(boolean)
	 * @see #setData(TableViewerColumn)
	 * @see #setWidth(TableViewerColumn)
	 */
	public TableViewerColumn build(TableViewer tableViewer) {
		final TableViewerColumn column = buildViewerColumn(tableViewer);
		setText(column);
		setToolTipText(column);
		setResizable(column);
		setMoveable(column);
		setData(column);
		setWidth(column);
		return column;
	}

	private Boolean isResizable;
	private Boolean isMoveable;
	private String text;
	private String tooltip;
	private final Map<String, Object> data = new LinkedHashMap<String, Object>();
	private Integer width;
	private Integer style = SWT.NONE;

	/**
	 * Configures the text of the {@link TableViewerColumn}.
	 *
	 * @param text the text to set
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * Configures the tooltip text of the {@link TableViewerColumn}.
	 *
	 * @param tooltip the tooltip text to set
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setToolTipText(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	/**
	 * Configures whether the {@link TableViewerColumn} is resizable.
	 *
	 * @param isResizable the value to set
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setResizable(boolean isResizable) {
		this.isResizable = isResizable;
		return this;
	}

	/**
	 * Configures whether the {@link TableViewerColumn} is moveable.
	 *
	 * @param isMoveable the value to set
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
		return this;
	}

	/**
	 * Configures the application data entries to be set for the {@link TableViewerColumn}.
	 *
	 * @param data the data entries to be added
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setData(Map<String, Object> data) {
		data.putAll(data);
		return this;
	}

	/**
	 * Configures the application data to be set for the {@link TableViewerColumn}.
	 *
	 * @param key the data key
	 * @param value the value
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setData(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * Append a map of items to the data map property for the {@link TableViewerColumn}.
	 *
	 * @param map to append to data map
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder addData(Map<String, Object> map) {
		data.putAll(map);
		return this;
	}

	/**
	 * Configures the style of the {@link TableViewerColumn}.
	 *
	 * @param style the style bits to set
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setStyle(int style) {
		this.style = style;
		return this;
	}

	/**
	 * Configures the width of the {@link TableViewerColumn}.
	 *
	 * @param width the width of the column
	 * @return the TableViewerColumnBuilder instance
	 */
	public TableViewerColumnBuilder setWidth(int width) {
		this.width = width;
		return this;
	}

	private void setText(TableViewerColumn column) {
		if (text != null) {
			column.getColumn().setText(text);
		}
	}

	private void setToolTipText(TableViewerColumn column) {
		if (tooltip != null) {
			column.getColumn().setToolTipText(tooltip);
		}
	}

	private void setResizable(TableViewerColumn column) {
		if (isResizable != null) {
		}
	}

	private void setMoveable(TableViewerColumn column) {
		if (isMoveable != null) {
			column.getColumn().setMoveable(true);

		}
	}

	private void setData(TableViewerColumn column) {
		for (final Entry<String, Object> entry : data.entrySet()) {
			column.getColumn().setData(entry.getKey(), entry.getValue());
		}
	}

	private void setWidth(TableViewerColumn column) {
		if (width != null) {
			column.getColumn().setWidth(width);
		}
	}

}
