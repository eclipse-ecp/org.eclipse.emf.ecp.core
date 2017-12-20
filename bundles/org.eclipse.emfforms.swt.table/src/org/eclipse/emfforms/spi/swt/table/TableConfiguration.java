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
	 * All configurable features.
	 */
	Feature[] FEATURES = {
		FEATURE_COLUMN_HIDE_SHOW,
		FEATURE_COLUMN_FILTER
	};

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