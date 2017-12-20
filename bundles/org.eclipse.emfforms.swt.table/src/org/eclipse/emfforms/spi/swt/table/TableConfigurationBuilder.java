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

import java.util.Arrays;
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
public final class TableConfigurationBuilder extends AbstractFeatureAwareBuilder<TableConfigurationBuilder> {

	private final Set<Feature> features = new LinkedHashSet<Feature>();

	private final Map<String, Object> data = new LinkedHashMap<String, Object>();

	/**
	 * The default constructor.
	 */
	private TableConfigurationBuilder() {
		super();
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
	 * Returns a new {@link TableConfigurationBuilder} initialized using an existing configuration.
	 *
	 * @param tableConfiguration a {@link TableConfiguration} to use
	 * @return self
	 */
	public static TableConfigurationBuilder usingConfiguration(TableConfiguration tableConfiguration) {
		return new TableConfigurationBuilder(tableConfiguration);
	}

	/**
	 * Constructor which allows to inherit an existing configuration.
	 *
	 * @param tableConfiguration the {@link TableConfiguration} to inherit.
	 */
	private TableConfigurationBuilder(TableConfiguration tableConfiguration) {
		features.addAll(tableConfiguration.getEnabledFeatures());
		// skip: data
	}

	@Override
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(Arrays.asList(TableConfiguration.FEATURES));
	}

	@Override
	protected Set<Feature> getEnabledFeatures() {
		return features;
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
