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
 * Christian W. Damus - bug 534829
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emfforms.common.Feature;

/**
 * A TableConfiguration is used to describe how a table viewer should be configured.
 *
 * Currently all configured TableConfiguration keys are passed down to each column as well.
 * This is subject to change.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 *
 */
public interface TableConfiguration {

	/**
	 * Feature toggle for column hide/show support.
	 */
	Feature FEATURE_COLUMN_HIDE_SHOW = //
		new Feature("column_hide_show", "Enable column hide/show support", Feature.STRATEGY.INHERIT); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Feature toggle for column filter support.
	 */
	Feature FEATURE_COLUMN_FILTER = //
		new Feature("column_filter", "Enable column filter support", Feature.STRATEGY.INHERIT); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Feature toggle for column regular expression filter support.
	 *
	 * @since 1.21
	 */
	Feature FEATURE_COLUMN_REGEX_FILTER = //
		new Feature("column_regex_filter", "Enable column regex filter support", Feature.STRATEGY.INHERIT); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * All configurable features.
	 *
	 * @since 1.21
	 */
	Set<Feature> ALL_FEATURES = Collections.unmodifiableSet(new LinkedHashSet<>(
		Arrays.asList(FEATURE_COLUMN_HIDE_SHOW,
			FEATURE_COLUMN_FILTER,
			FEATURE_COLUMN_REGEX_FILTER)));

	/**
	 * All configurable features.
	 *
	 * @deprecated Since 1.21, use the immutable {@link #ALL_FEATURES} set instead of this,
	 *             which can be modified in place.
	 */
	@Deprecated
	Feature[] FEATURES = ALL_FEATURES.toArray(new Feature[0]);

	/**
	 * Table data configuration key.
	 */
	String ID = "emfforms.table.configuration"; //$NON-NLS-1$

	/** Data key for a domain model reference. */
	String DMR = "domain_model_reference"; //$NON-NLS-1$

	/**
	 * Returns a static array of enabled features.
	 *
	 * @return array of enabled features.
	 */
	Set<Feature> getEnabledFeatures();

	/**
	 * Get the underlying data map.
	 *
	 * @return data map object
	 */
	Map<String, Object> getData();

	/**
	 * Dispose this configuration and all its properties.
	 */
	void dispose();

}
