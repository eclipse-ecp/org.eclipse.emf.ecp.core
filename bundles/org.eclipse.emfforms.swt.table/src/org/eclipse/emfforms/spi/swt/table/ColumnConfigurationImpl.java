/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ConfigurationCallback;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of the {@link ColumnConfiguration}.
 *
 * @author Johannes Faltermeier
 * @author Mat Hansen
 *
 */
public class ColumnConfigurationImpl implements ColumnConfiguration {

	private final boolean resizeable;
	private final boolean moveable;
	private final int styleBits;
	private final int weight;
	private final int minWidth;
	private final IObservableValue columnText;
	private final IObservableValue tooltipText;
	private final CellLabelProviderFactory labelProviderFactory;
	private final Optional<EditingSupportCreator> editingSupportCreator;
	private final Optional<Image> image;
	private final Map<String, Object> data;
	private final List<ConfigurationCallback<AbstractTableViewer, ViewerColumn>> configurationCallbacks;

	// BEGIN COMPLEX CODE
	/**
	 * Constructs a new {@link ColumnConfiguration}.
	 *
	 * @param resizeable resizeable
	 * @param moveable moveable
	 * @param styleBits styleBits
	 * @param weight weight
	 * @param minWidth minWidth
	 * @param columnText columnText
	 * @param tooltipText tooltipText
	 * @param labelProviderFactory labelProvider
	 * @param editingSupport editingSupport. May be <code>null</code> to indicate that there is no editing support
	 */
	public ColumnConfigurationImpl(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProviderFactory labelProviderFactory,
		EditingSupportCreator editingSupport,
		Image image,
		Map<String, Object> data,
		List<ConfigurationCallback<AbstractTableViewer, ViewerColumn>> configurationCallbacks) {
		// END COMPLEX CODE
		this.resizeable = resizeable;
		this.moveable = moveable;
		this.styleBits = styleBits;
		this.weight = weight;
		this.minWidth = minWidth;
		this.columnText = columnText;
		this.tooltipText = tooltipText;
		this.labelProviderFactory = labelProviderFactory;
		editingSupportCreator = Optional.ofNullable(editingSupport);
		this.image = Optional.ofNullable(image);
		if (data == null) {
			throw new IllegalArgumentException("Data map cannot be null"); //$NON-NLS-1$
		}
		this.data = data;
		if (configurationCallbacks == null || configurationCallbacks.isEmpty()) {
			this.configurationCallbacks = Collections.emptyList();
		} else {
			this.configurationCallbacks = Collections.unmodifiableList(configurationCallbacks);
		}

	}

	@Override
	public boolean isResizeable() {
		return resizeable;
	}

	@Override
	public boolean isMoveable() {
		return moveable;
	}

	@Override
	public int getStyleBits() {
		return styleBits;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public int getMinWidth() {
		return minWidth;
	}

	@Override
	public IObservableValue getColumnText() {
		return columnText;
	}

	@Override
	public IObservableValue getColumnTooltip() {
		return tooltipText;
	}

	@Override
	public CellLabelProvider createLabelProvider(AbstractTableViewer columnViewer) {
		return labelProviderFactory.createCellLabelProvider(columnViewer);
	}

	/**
	 * Returns the cell label provider factory.
	 *
	 * @return the cell label provider factory
	 */
	CellLabelProviderFactory getLabelProviderFactory() {
		return labelProviderFactory;
	}

	@Override
	public Optional<EditingSupport> createEditingSupport(AbstractTableViewer columnViewer) {
		if (editingSupportCreator.isPresent()) {
			return Optional.of(editingSupportCreator.get().createEditingSupport(columnViewer));
		}
		return Optional.empty();
	}

	/**
	 * Returns the editing support creator.
	 *
	 * @return the editing support creator
	 */
	Optional<EditingSupportCreator> getEditingSupportCreator() {
		return editingSupportCreator;
	}

	@Override
	public Optional<Image> getColumnImage() {
		return image;
	}

	@Override
	public void setData(Map<String, Object> data) {
		this.data.putAll(data);
	}

	@Override
	public Object getData(String key) {
		return data.get(key);
	}

	@Override
	public Map<String, Object> getData() {
		return data;
	}

	@Override
	public List<ConfigurationCallback<AbstractTableViewer, ViewerColumn>> getConfigurationCallbacks() {
		return configurationCallbacks;
	}

}