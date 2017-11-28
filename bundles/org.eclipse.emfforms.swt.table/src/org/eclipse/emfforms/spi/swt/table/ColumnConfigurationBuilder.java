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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.internal.swt.table.util.StaticCellLabelProviderFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * Builder for {@link ColumnConfiguration}s.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public final class ColumnConfigurationBuilder extends AbstractFeatureAwareBuilder<ColumnConfigurationBuilder> {

	private final Set<Feature> features = new LinkedHashSet<Feature>();

	private boolean resizeable = true;
	private boolean moveable; // not movable
	private int styleBits = SWT.NONE;
	private int weight = ColumnConfiguration.NO_WEIGHT;
	private int minWidth;

	@SuppressWarnings("rawtypes")
	private IObservableValue textObservable;
	@SuppressWarnings("rawtypes")
	private IObservableValue tooltipObservable;

	private CellLabelProviderFactory labelProviderFactory;
	private EditingSupportCreator editingSupportCreator;

	private Image image;
	private Map<String, Object> data = new LinkedHashMap<String, Object>();
	private List<ConfigurationCallback<AbstractTableViewer, ViewerColumn>> configurationCallbacks;

	/**
	 * The default constructor.
	 */
	private ColumnConfigurationBuilder() {
		super();
	}

	/**
	 * Returns a new {@link ColumnConfigurationBuilder} initialized using default values.
	 *
	 * @return self
	 */
	public static ColumnConfigurationBuilder usingDefaults() {
		return new ColumnConfigurationBuilder();
	}

	/**
	 * Returns a new {@link ColumnConfigurationBuilder} initialized using an existing configuration.
	 *
	 * @param columnConfiguration a {@link ColumnConfiguration} to use
	 * @return self
	 */
	public static ColumnConfigurationBuilder usingConfiguration(ColumnConfiguration columnConfiguration) {
		return new ColumnConfigurationBuilder(columnConfiguration);
	}

	/**
	 * Constructor which allows to inherit an existing configuration.
	 *
	 * @param columnConfiguration the {@link ColumnConfiguration} to inherit.
	 */
	private ColumnConfigurationBuilder(ColumnConfiguration columnConfiguration) {
		super();
		final ColumnConfigurationImpl config = (ColumnConfigurationImpl) columnConfiguration;
		resizable(config.isResizeable());
		moveable(config.isMoveable());
		styleBits(config.getStyleBits());
		weight(config.getWeight());
		minWidth(config.getMinWidth());
		// skip: text, tooltip
		labelProviderFactory(config.getLabelProviderFactory());
		if (config.getEditingSupportCreator().isPresent()) {
			editingSupportCreator(config.getEditingSupportCreator().get());
		}
		// skip: image, data
		configurationCallbacks = config.getConfigurationCallbacks();
	}

	@Override
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(Arrays.asList(ColumnConfiguration.FEATURES));
	}

	@Override
	protected Set<Feature> getEnabledFeatures() {
		return features;
	}

	/**
	 * Makes the column resizable.
	 *
	 * @param resizable true for resizable columns
	 * @return self
	 */
	public ColumnConfigurationBuilder resizable(boolean resizable) {
		resizeable = resizable;
		return this;
	}

	/**
	 * Makes the column moveable.
	 *
	 * @param moveable true for movable columns
	 * @return self
	 */
	public ColumnConfigurationBuilder moveable(boolean moveable) {
		this.moveable = moveable;
		return this;
	}

	/**
	 * Add SWT style bits.
	 *
	 * @param styleBits the SWT style bits
	 * @return self
	 */
	public ColumnConfigurationBuilder styleBits(int styleBits) {
		this.styleBits = styleBits;
		return this;
	}

	/**
	 * Add column weight.
	 *
	 * @param weight the weight
	 * @return self
	 */
	public ColumnConfigurationBuilder weight(int weight) {
		this.weight = weight;
		return this;
	}

	/**
	 * Add a minimal width.
	 *
	 * @param minWidth the minimal width
	 * @return self
	 */
	public ColumnConfigurationBuilder minWidth(int minWidth) {
		this.minWidth = minWidth;
		return this;
	}

	/**
	 * Add a text observable.
	 *
	 * @param textObservable the column text observable
	 * @return self
	 */
	@SuppressWarnings("rawtypes")
	public ColumnConfigurationBuilder text(IObservableValue textObservable) {
		this.textObservable = textObservable;
		return this;
	}

	/**
	 * Add a static text.
	 *
	 * @param text the column text
	 * @return self
	 */
	public ColumnConfigurationBuilder text(String text) {
		return text(Observables.constantObservableValue(text, String.class));
	}

	/**
	 * Add a tooltip observable.
	 *
	 * @param tooltipObservable the tooltip observable
	 * @return self
	 */
	@SuppressWarnings("rawtypes")
	public ColumnConfigurationBuilder tooltip(IObservableValue tooltipObservable) {
		this.tooltipObservable = tooltipObservable;
		return this;
	}

	/**
	 * Add a static tooltip.
	 *
	 * @param tooltip the tooltip
	 * @return self
	 */
	public ColumnConfigurationBuilder tooltip(String tooltip) {
		return tooltip(Observables.constantObservableValue(tooltip, String.class));
	}

	/**
	 * Add a label provider factory.
	 *
	 * @param labelProviderFactory the label provider factory
	 * @return self
	 */
	public ColumnConfigurationBuilder labelProviderFactory(CellLabelProviderFactory labelProviderFactory) {
		this.labelProviderFactory = labelProviderFactory;
		return this;
	}

	/**
	 * Add a label provider.
	 *
	 * @param labelProvider the label provider
	 * @return self
	 */
	public ColumnConfigurationBuilder labelProvider(CellLabelProvider labelProvider) {
		return labelProviderFactory(new StaticCellLabelProviderFactory(labelProvider));
	}

	/**
	 * Add an editing support creator.
	 *
	 * @param editingSupportCreator the editing support creator
	 * @return self
	 */
	public ColumnConfigurationBuilder editingSupportCreator(EditingSupportCreator editingSupportCreator) {
		this.editingSupportCreator = editingSupportCreator;
		return this;
	}

	/**
	 * Add a column image.
	 *
	 * @param image the image
	 * @return self
	 */
	public ColumnConfigurationBuilder image(Image image) {
		this.image = image;
		return this;
	}

	/**
	 * Add a pre-initialized data map.
	 *
	 * @param data the data map
	 * @return self
	 */
	public ColumnConfigurationBuilder dataMap(Map<String, Object> data) {
		if (!this.data.isEmpty()) {
			throw new IllegalArgumentException("Data map values have already been set"); //$NON-NLS-1$
		}
		if (data == null) {
			throw new NullPointerException("Data map cannot be null"); //$NON-NLS-1$
		}
		this.data = data;
		return this;
	}

	/**
	 * Add a data map entry.
	 *
	 * @param key the data map key
	 * @param value the data map value
	 * @return self
	 */
	public ColumnConfigurationBuilder dataMapEntry(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * Add a column configuration callback.
	 *
	 * @param callback the callback
	 * @return self
	 */
	public ColumnConfigurationBuilder callback(ConfigurationCallback<AbstractTableViewer, ViewerColumn> callback) {
		if (configurationCallbacks == null) {
			configurationCallbacks = //
				new ArrayList<ConfigurationCallback<AbstractTableViewer, ViewerColumn>>();
		}
		configurationCallbacks.add(callback);
		return this;
	}

	/**
	 * Create a new {@link ColumnConfiguration} using the current builder state.
	 *
	 * @return the {@link ColumnConfiguration}
	 */
	public ColumnConfiguration build() {
		final ColumnConfiguration config = new ColumnConfigurationImpl(
			features,
			resizeable,
			moveable,
			styleBits,
			minWidth == 0 && weight == ColumnConfiguration.NO_WEIGHT ? 100 : weight,
			minWidth,
			textObservable,
			tooltipObservable,
			labelProviderFactory,
			editingSupportCreator,
			image,
			data,
			configurationCallbacks);

		return config;
	}

}
