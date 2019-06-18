/*******************************************************************************
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
 * Mat Hansen - initial API and implementation
 * Christian W. Damus - bugs 534829, 530314
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.ArrayList;
import java.util.Collection;
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
@SuppressWarnings("deprecation")
public final class ColumnConfigurationBuilder extends AbstractFeatureAwareBuilder<ColumnConfigurationBuilder> {

	private final Set<Feature> features;

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
		this(new LinkedHashSet<Feature>());
	}

	/**
	 * Initializes me with enabled {@code features}.
	 *
	 * @param features initially enabled features
	 */
	private ColumnConfigurationBuilder(Set<Feature> features) {
		super();

		this.features = features;
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
	 * Returns a new {@link TableConfigurationBuilder} initialized using default values
	 * with inherited {@code features}.
	 *
	 * @param features initially enabled features
	 * @return a new builder initialized with the inherited {@code features}
	 *
	 * @since 1.21
	 */
	static ColumnConfigurationBuilder withFeatures(Collection<Feature> features) {
		return new ColumnConfigurationBuilder(Feature.inherit(features, ColumnConfiguration.ALL_FEATURES::contains));
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
	 * Returns a new {@link TableConfigurationBuilder} initialized using an existing viewer builder.
	 *
	 * @param viewerBuilder a {@link TableViewerSWTBuilder} to transform to a configuration builder
	 * @return the new configuration builder
	 *
	 * @since 1.21
	 */
	public static ColumnConfigurationBuilder from(TableViewerSWTBuilder viewerBuilder) {
		return withFeatures(viewerBuilder.getEnabledFeatures());
	}

	/**
	 * Constructor which allows to inherit an existing configuration.
	 *
	 * @param columnConfiguration the {@link ColumnConfiguration} to inherit.
	 */
	private ColumnConfigurationBuilder(ColumnConfiguration columnConfiguration) {
		this();

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

	/**
	 * @deprecated Since 1.21, use the {@link #showHide(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHide(boolean)
	 * @see #substringFilter(boolean)
	 * @see #regexFilter(boolean)
	 */
	@Override
	@Deprecated
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(ColumnConfiguration.ALL_FEATURES);
	}

	/**
	 * @deprecated Since 1.21, use the {@link #showHide(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHide(boolean)
	 * @see #substringFilter(boolean)
	 * @see #regexFilter(boolean)
	 */
	@Override
	@Deprecated
	protected Set<Feature> getEnabledFeatures() {
		return features;
	}

	/**
	 * Set whether support for users to show and hide the column is installed.
	 *
	 * @param showHide {@code true} to enable showing and hiding; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public ColumnConfigurationBuilder showHide(boolean showHide) {
		return showHide ? enableFeature(ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW)
			: disableFeature(ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW);
	}

	/**
	 * Set whether support for users to show a simple substring-matching filter
	 * is installed.
	 *
	 * @param substringFilter {@code true} to enable the substring filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public ColumnConfigurationBuilder substringFilter(boolean substringFilter) {
		return substringFilter ? enableFeature(ColumnConfiguration.FEATURE_COLUMN_FILTER)
			: disableFeature(ColumnConfiguration.FEATURE_COLUMN_FILTER);
	}

	/**
	 * Set whether support for users to show a regular expression filter
	 * is installed.
	 *
	 * @param regexFilter {@code true} to enable the regex filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public ColumnConfigurationBuilder regexFilter(boolean regexFilter) {
		return regexFilter ? enableFeature(ColumnConfiguration.FEATURE_COLUMN_REGEX_FILTER)
			: disableFeature(ColumnConfiguration.FEATURE_COLUMN_REGEX_FILTER);
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
