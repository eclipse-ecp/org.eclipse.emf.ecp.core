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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emfforms.common.Feature;

/**
 * Builder for {@link TableConfiguration}s.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
@SuppressWarnings("deprecation")
public final class TableConfigurationBuilder extends AbstractFeatureAwareBuilder<TableConfigurationBuilder> {

	private final Set<Feature> features;

	private final Map<String, Object> data = new LinkedHashMap<String, Object>();

	/**
	 * The default constructor.
	 */
	private TableConfigurationBuilder() {
		this(new LinkedHashSet<Feature>());
	}

	/**
	 * Initializes me with enabled {@code features}.
	 *
	 * @param features initially enabled features
	 *
	 * @since 1.21
	 */
	private TableConfigurationBuilder(Set<Feature> features) {
		super();

		this.features = features;
	}

	/**
	 * Returns a new {@link TableConfigurationBuilder} initialized using default values.
	 *
	 * @return self
	 */
	public static TableConfigurationBuilder usingDefaults() {
		return new TableConfigurationBuilder();
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
	static TableConfigurationBuilder withFeatures(Collection<Feature> features) {
		return new TableConfigurationBuilder(Feature.inherit(features, TableConfiguration.ALL_FEATURES::contains));
	}

	/**
	 * Returns a new {@link TableConfigurationBuilder} initialized using an existing configuration.
	 *
	 * @param tableConfiguration a {@link TableConfiguration} to use
	 * @return self
	 */
	public static TableConfigurationBuilder usingConfiguration(TableConfiguration tableConfiguration) {
		return new TableConfigurationBuilder(tableConfiguration);
	}

	/**
	 * Returns a new {@link TableConfigurationBuilder} initialized using an existing viewer builder.
	 *
	 * @param viewerBuilder a {@link TableViewerSWTBuilder} to transform to a configuration builder
	 * @return the new configuration builder
	 *
	 * @since 1.21
	 */
	public static TableConfigurationBuilder from(TableViewerSWTBuilder viewerBuilder) {
		return withFeatures(viewerBuilder.getEnabledFeatures());
	}

	/**
	 * Constructor which allows to inherit an existing configuration.
	 *
	 * @param tableConfiguration the {@link TableConfiguration} to inherit.
	 *
	 * @since 1.21
	 */
	private TableConfigurationBuilder(TableConfiguration tableConfiguration) {
		this(tableConfiguration.getEnabledFeatures());
		// skip: data
	}

	/**
	 * @deprecated Since 1.21, use the {@link #showHideColumns(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHideColumns(boolean)
	 * @see #columnSubstringFilter(boolean)
	 * @see #columnRegexFilter(boolean)
	 */
	@Override
	@Deprecated
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(TableConfiguration.ALL_FEATURES);
	}

	/**
	 * @deprecated Since 1.21, use the {@link #showHideColumns(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHideColumns(boolean)
	 * @see #columnSubstringFilter(boolean)
	 * @see #columnRegexFilter(boolean)
	 */
	@Override
	@Deprecated
	protected Set<Feature> getEnabledFeatures() {
		return features;
	}

	/**
	 * Set whether support for users to show and hide columns is installed.
	 *
	 * @param showHideColumns {@code true} to enable showing and hiding of columns; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableConfigurationBuilder showHideColumns(boolean showHideColumns) {
		return showHideColumns ? enableFeature(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW);
	}

	/**
	 * Set whether support for users to show a simple substring-matching filter on columns
	 * is installed.
	 *
	 * @param columnSubstringFilter {@code true} to enable the substring filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableConfigurationBuilder columnSubstringFilter(boolean columnSubstringFilter) {
		return columnSubstringFilter ? enableFeature(TableConfiguration.FEATURE_COLUMN_FILTER)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_FILTER);
	}

	/**
	 * Set whether support for users to show a regular expression filter on columns
	 * is installed.
	 *
	 * @param columnRegexFilter {@code true} to enable the regex filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableConfigurationBuilder columnRegexFilter(boolean columnRegexFilter) {
		return columnRegexFilter ? enableFeature(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER);
	}

	/**
	 * Add a data map entry.
	 *
	 * @param key the data map key
	 * @param value the data map value
	 * @return self
	 */
	public TableConfigurationBuilder dataMapEntry(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * Creates a new {@link TableConfiguration} based on the current builder state.
	 *
	 * @return the {@link TableConfiguration}
	 */
	public TableConfiguration build() {
		final TableConfiguration config = new TableConfigurationImpl(
			features,
			data);

		return config;
	}

}
