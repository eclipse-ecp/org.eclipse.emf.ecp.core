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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.emfforms.common.Feature;

/**
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public final class TableConfigurationImpl implements TableConfiguration {

	private final Set<Feature> enabledFeatures;

	private final Map<String, Object> data;

	/**
	 * Constructs a new {@link TableConfiguration}.
	 *
	 * @param enabledFeatures list of enabled features
	 * @param data data map
	 */
	public TableConfigurationImpl(
		Set<Feature> enabledFeatures,
		Map<String, Object> data) {
		super();
		this.enabledFeatures = Collections.unmodifiableSet(enabledFeatures);
		if (data == null) {
			throw new IllegalArgumentException("Data map cannot be null"); //$NON-NLS-1$
		}
		this.data = data;
	}

	@Override
	public Set<Feature> getEnabledFeatures() {
		return enabledFeatures;
	}

	@Override
	public Map<String, Object> getData() {
		return data;
	}

	@Override
	public void dispose() {
		// stub, does nothing at the moment
	}

}
