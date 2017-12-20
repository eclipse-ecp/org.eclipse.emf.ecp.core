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

import java.util.Collection;
import java.util.Set;

import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.common.Feature.STRATEGY;

/**
 * Abstract helper class for feature support.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.14
 *
 * @param <B> the builder type
 */
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
		for (final Feature feature : features) {
			if (!STRATEGY.INHERIT.equals(feature.getStrategy())) {
				continue;
			}
			if (!this.isFeatureSupported(feature)) {
				continue;
			}
			this.enableFeature(feature);
		}
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
