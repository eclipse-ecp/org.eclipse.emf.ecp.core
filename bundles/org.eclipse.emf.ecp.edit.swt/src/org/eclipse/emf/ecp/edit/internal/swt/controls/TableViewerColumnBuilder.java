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

	public static TableViewerColumnBuilder create() {
		return new TableViewerColumnBuilder();
	}

	public TableViewerColumnBuilder setText(String text) {
		this.text = text;
		return this;
	}

	public TableViewerColumnBuilder setToolTipText(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public TableViewerColumnBuilder setResizable(boolean isResizable) {
		this.isResizable = isResizable;
		return this;
	}

	public TableViewerColumnBuilder setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
		return this;
	}

	public TableViewerColumnBuilder setData(String key, Object value) {
		data.put(key, value);
		return this;
	}

	public TableViewerColumnBuilder setStyle(int style) {
		this.style = style;
		return this;
	}

	public TableViewerColumnBuilder setWidth(int width) {
		this.width = width;
		return this;
	}

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
