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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.Property;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Image;

/**
 * A ColumnConfiguration is used to configure how a viewer column shall be created
 * and how it behaves during runtime.
 *
 * @author Johannes Faltermeier
 * @author Mat Hansen
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 *
 */
// TODO: migrate all configuration options to Property<T>
public interface ColumnConfiguration {

	/**
	 * Feature toggle for column hide/show support. Can be enabled on a per-column basis.
	 */
	Feature FEATURE_COLUMN_HIDE_SHOW = TableConfiguration.FEATURE_COLUMN_HIDE_SHOW;

	/**
	 * Feature toggle for column filter support.
	 */
	Feature FEATURE_COLUMN_FILTER = TableConfiguration.FEATURE_COLUMN_FILTER;

	/**
	 * All configurable features.
	 */
	Feature[] FEATURES = {
		FEATURE_COLUMN_HIDE_SHOW,
		FEATURE_COLUMN_FILTER
	};

	/**
	 * Column data configuration key.
	 */
	String ID = "emfforms.column.configuration"; //$NON-NLS-1$

	/** Data key for resizable columns. */
	String RESIZABLE = "resizable"; //$NON-NLS-1$

	/** Data key for column weight. */
	String WEIGHT = "weight"; //$NON-NLS-1$

	/** Constant indicating that {@link #getWeight()} has no value. */
	int NO_WEIGHT = -1;

	/** Data key for the minimum width of the column. */
	String MIN_WIDTH = "min_width"; //$NON-NLS-1$

	/** Data key for column id. */
	String COLUMN_ID = "column_id"; //$NON-NLS-1$

	/** Data key for a domain model reference. */
	String DMR = "domain_model_reference"; //$NON-NLS-1$

	/**
	 * Returns a static array of enabled features.
	 *
	 * @return array of enabled features.
	 */
	Set<Feature> getEnabledFeatures();

	/**
	 * <code>true</code> if resizeable, <code>false</code> otherwise.
	 *
	 * @return whether the column is resizeable
	 */
	boolean isResizeable();

	/**
	 * <code>true</code> if moveable, <code>false</code> otherwise.
	 *
	 * @return whether the column is moveable
	 */
	boolean isMoveable();

	/**
	 * The SWT style bits which will be used to create the column.
	 *
	 * @return the SWT style bits for the column
	 */
	int getStyleBits();

	/**
	 * The weight of the column.
	 *
	 * @return the weight of the column
	 */
	int getWeight();

	/**
	 * The minimal width of the column.
	 *
	 * @return the min width of the column in pixels
	 */
	int getMinWidth();

	/**
	 * The header text for the column.
	 *
	 * @return the column header text
	 */
	@SuppressWarnings("rawtypes")
	IObservableValue getColumnText();

	/**
	 * The column header tooltip text.
	 *
	 * @return the column header tooltip
	 */
	@SuppressWarnings("rawtypes")
	IObservableValue getColumnTooltip();

	/**
	 * The cell label provider which will be set on the column.
	 *
	 * @param columnViewer the column viewer
	 * @return the label provider
	 */
	CellLabelProvider createLabelProvider(AbstractTableViewer columnViewer);

	/**
	 * Called to setup the {@link EditingSupport} for the viewer.
	 *
	 * @param columnViewer the {@link AbstractTableViewer}
	 * @return the editing support for the column, if present
	 */
	Optional<EditingSupport> createEditingSupport(AbstractTableViewer columnViewer);

	/**
	 * The image of the column.
	 *
	 * @return the column image, if present
	 */
	Optional<Image> getColumnImage();

	/**
	 * Get an arbitrary element from the data map.
	 *
	 * @param key (see constants)
	 * @return object
	 */
	Object getData(String key);

	/**
	 * Add the contents of the given map to the data map.
	 *
	 * @param data object
	 */
	void setData(Map<String, Object> data);

	/**
	 * Get the underlying data map.
	 *
	 * @return data map object
	 */
	Map<String, Object> getData();

	/**
	 * Get the list of additional ConfigurationCallbacks.
	 *
	 * @return list of ConfigurationCallbacks.
	 */
	List<ConfigurationCallback<AbstractTableViewer, ViewerColumn>> getConfigurationCallbacks();

	/**
	 * Toggle the visible state of the column.
	 *
	 * @return visible property
	 */
	Property<Boolean> visible();

	/**
	 * Toggle the visible state of the filter control.
	 *
	 * @return visible property
	 */
	Property<Boolean> showFilterControl();

	/**
	 * Set a filter on the current column.
	 *
	 * @return visible property
	 */
	Property<Object> matchFilter();

	/**
	 * Dispose this configuration and all its properties.
	 */
	void dispose();
}