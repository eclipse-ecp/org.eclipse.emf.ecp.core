/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

/**
 *
 *
 * @author emueller
 *
 */
public class TableViewerColumnBuilder {

	private Boolean isResizable;
	private Boolean isMoveable;
	private String text;
	private String tooltip;
	private final Map<String, Object> data = new LinkedHashMap<String, Object>();
	private Integer width;
	private Integer style = SWT.NONE;

	/**
	 * Creates a new {@link TableViewerColumnBuilder} instance.
	 *
	 * @return the new instance
	 */
	public static TableViewerColumnBuilder create() {
		return new TableViewerColumnBuilder();
	}

	/**
	 * @param text the column text
	 * @return self
	 */
	public TableViewerColumnBuilder setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * @param tooltip the column tooltip
	 * @return self
	 */
	public TableViewerColumnBuilder setToolTipText(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	/**
	 * @param isResizable whether the column is resizeable
	 * @return self
	 */
	public TableViewerColumnBuilder setResizable(boolean isResizable) {
		this.isResizable = isResizable;
		return this;
	}

	/**
	 * @param isMoveable whether the column is moveable
	 * @return self
	 */
	public TableViewerColumnBuilder setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
		return this;
	}

	/**
	 * Sets data on the column.
	 *
	 * @param key the key
	 * @param value the value
	 * @return self
	 */
	public TableViewerColumnBuilder setData(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * @param style the swt style bits for the column
	 * @return self
	 */
	public TableViewerColumnBuilder setStyle(int style) {
		this.style = style;
		return this;
	}

	/**
	 * @param width the width of the column
	 * @return self
	 */
	public TableViewerColumnBuilder setWidth(int width) {
		this.width = width;
		return this;
	}

	/**
	 * Creates and returns a {@link TableViewerColumn}.
	 * 
	 * @param tableViewer the parent
	 * @return the column
	 */
	public TableViewerColumn build(TableViewer tableViewer) {
		final TableViewerColumn column = new TableViewerColumn(tableViewer, style);
		setText(column);
		setToolTipText(column);
		setResizable(column);
		setMoveable(column);
		setData(column);
		setWidth(column);
		return column;
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
			column.getColumn().setResizable(isResizable);
		}
	}

	private void setMoveable(TableViewerColumn column) {
		if (isMoveable != null) {
			column.getColumn().setMoveable(isMoveable);
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
