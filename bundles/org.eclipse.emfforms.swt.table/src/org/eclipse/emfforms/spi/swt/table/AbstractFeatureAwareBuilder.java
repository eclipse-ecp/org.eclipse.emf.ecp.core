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
 * Christian W. Damus - bug 530314
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emfforms.common.Feature;

/**
 * Abstract helper class for feature support.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.14
 *
 * @param <B> the builder type
 *
 * @deprecated as of 1.21, {@link Feature}s are used only to communicate configuration data
 *             to the UI controls that interrogate the configurations. The builder API for
 *             users is a fluent API, not abstracted in terms of features
 */
@Deprecated
public abstract class AbstractFeatureAwareBuilder<B> {

	/**
	 * Returns the list of supported features.
	 *
	 * @return array of supported features
	 */
	protected abstract Set<Feature> getSupportedFeatures();

	/**
	 * Return the list of enabled features.
	 *
	 * @return list of enabled features
	 */
	protected abstract Set<Feature> getEnabledFeatures();

	/**
	 * Enable a feature.
	 *
	 * @param featureToEnable the feature to enable
	 * @return self
	 */
	public B enableFeature(Feature featureToEnable) {
		if (!isFeatureSupported(featureToEnable)) {
			throw new IllegalArgumentException("Unsupported feature"); //$NON-NLS-1$
		}
		getEnabledFeatures().add(featureToEnable);
		return getBuilder();
	}

	/**
	 * Disable a feature.
	 *
	 * @param featureToDisable the feature to disable
	 * @return self
	 */
	public B disableFeature(Feature featureToDisable) {
		getEnabledFeatures().remove(featureToDisable);
		return getBuilder();
	}

	/**
	 * Check whether the given feature is enabled.
	 *
	 * @param feature the feature to check
	 * @return true if enabled
	 */
	public boolean isFeatureEnabled(Feature feature) {
		return getEnabledFeatures().contains(feature);
	}

	/**
	 * Check whether the given feature is supported.
	 *
	 * @param feature the feature to check
	 * @return true if supported
	 */
	public boolean isFeatureSupported(Feature feature) {
		return getSupportedFeatures().contains(feature);
	}

	/**
	 * Inherit the features as long as they are supported.
	 *
	 * @param features list of features to inherit.
	 * @return self
	 */
	public B inheritFeatures(Collection<Feature> features) {
		getEnabledFeatures().addAll(Feature.inherit(features, this::isFeatureSupported));
		return getBuilder();
	}

	/**
	 * Returns the builder instance.
	 *
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	protected B getBuilder() {
		return (B) this;
	}

}
