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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.swt.SWT;

/**
 * @author Jonas Helming
 *
 */
public class GridViewerColumnBuilder {
	/**
	 * Creates a new viewer column.
	 *
	 * @param tableViewer the parent table viewer
	 * @return the {@link GridViewerColumn}
	 */
	protected GridViewerColumn buildViewerColumn(GridTableViewer tableViewer) {
		return new GridViewerColumn(tableViewer, style);
	}

	/**
	 *
	 * @return creates an instance of a {@link GridViewerColumnBuilder}
	 */
	public static GridViewerColumnBuilder create() {
		return new GridViewerColumnBuilder();
	}

	/**
	 * Creates and customizes a {@link GridViewerColumn} for the given table viewer.
	 *
	 * @param tableViewer the target {@link GridTableViewer}
	 * @return the {@link GridViewerColumn}
	 * @see #setText(GridViewerColumn)
	 * @see #setToolTipText(GridViewerColumn)
	 * @see #setResizable(boolean)
	 * @see #setMoveable(boolean)
	 * @see #setData(GridViewerColumn)
	 * @see #setWidth(GridViewerColumn)
	 */
	public GridViewerColumn build(GridTableViewer tableViewer) {
		final GridViewerColumn column = buildViewerColumn(tableViewer);
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
	 * Configures the text of the {@link GridViewerColumn}.
	 *
	 * @param text the text to set
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * Configures the tooltip text of the {@link GridViewerColumn}.
	 *
	 * @param tooltip the tooltip text to set
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setToolTipText(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	/**
	 * Configures whether the {@link GridViewerColumn} is resizable.
	 *
	 * @param isResizable the value to set
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setResizable(boolean isResizable) {
		this.isResizable = isResizable;
		return this;
	}

	/**
	 * Configures whether the {@link GridViewerColumn} is moveable.
	 *
	 * @param isMoveable the value to set
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
		return this;
	}

	/**
	 * Configures the application data to be set for the {@link GridViewerColumn}.
	 *
	 * @param key the data key
	 * @param value the value
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setData(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * Configures the style of the {@link GridViewerColumn}.
	 *
	 * @param style the style bits to set
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setStyle(int style) {
		this.style = style;
		return this;
	}

	/**
	 * Configures the width of the {@link GridViewerColumn}.
	 *
	 * @param width the width of the column
	 * @return the GridViewerColumnBuilder instance
	 */
	public GridViewerColumnBuilder setWidth(int width) {
		this.width = width;
		return this;
	}

	private void setText(GridViewerColumn column) {
		if (text != null) {
			column.getColumn().setText(text);
		}
	}

	private void setToolTipText(GridViewerColumn column) {
		if (tooltip != null) {
			// TODO: Grid: correct?
			column.getColumn().setHeaderTooltip(tooltip);
		}
	}

	private void setResizable(GridViewerColumn column) {
		if (isResizable != null) {
			// TODO Grid needed?
			// column.getColumn().setResizable(isResizable);
		}
	}

	private void setMoveable(GridViewerColumn column) {
		if (isMoveable != null) {
			column.getColumn().setMoveable(true);

		}
	}

	private void setData(GridViewerColumn column) {
		for (final Entry<String, Object> entry : data.entrySet()) {
			column.getColumn().setData(entry.getKey(), entry.getValue());
		}
	}

	private void setWidth(GridViewerColumn column) {
		if (width != null) {
			column.getColumn().setWidth(width);
		}
	}

}
