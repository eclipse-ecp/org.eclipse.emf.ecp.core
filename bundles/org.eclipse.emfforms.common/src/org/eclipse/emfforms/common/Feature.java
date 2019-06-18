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
package org.eclipse.emfforms.common;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * ECP feature representation.
 *
 * @author Mat Hansen
 * @since 1.15
 *
 */
public final class Feature {

	/**
	 * Feature strategy enum.
	 *
	 */
	public enum STRATEGY {
		/**
		 * No strategy.
		 */
		NONE,
		/**
		 * Inherit the feature automatically to children.
		 */
		INHERIT,
	}

	private final String id;
	private final String description;
	private final STRATEGY strategy;

	/**
	 * Default constructor (no feature inheritance).
	 *
	 * @param id the feature id
	 * @param description the feature description
	 */
	public Feature(String id, String description) {
		this.id = id;
		this.description = description;
		strategy = STRATEGY.NONE;
	}

	/**
	 * Alternative constructor allowing to customize {@link STRATEGY}.
	 *
	 * @param id the feature id
	 * @param description the feature description
	 * @param strategy the feature {@link STRATEGY}
	 */
	public Feature(String id, String description, STRATEGY strategy) {
		this.id = id;
		this.description = description;
		this.strategy = strategy;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the strategy
	 */
	public STRATEGY getStrategy() {
		return strategy;
	}

	/**
	 * Features are like {@link Enum#equals(Object) enum}s in that they are equal by identity.
	 */
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("Feature(%s, %s)", id, strategy); //$NON-NLS-1$
	}

	/**
	 * Inherit the given {@code features} that are {@linkplain STRATEGY#INHERIT inheritable} and
	 * are supported by the caller.
	 *
	 * @param features a collection of features to inherit
	 * @param isSupported a test of whether the caller supports any particular feature
	 * @return the supported inherited features
	 *
	 * @since 1.21
	 */
	public static Set<Feature> inherit(Collection<Feature> features, Predicate<? super Feature> isSupported) {
		final Set<Feature> result = new LinkedHashSet<>();

		for (final Feature feature : features) {
			if (!STRATEGY.INHERIT.equals(feature.getStrategy())) {
				continue;
			}
			if (!isSupported.test(feature)) {
				continue;
			}
			result.add(feature);
		}

		return result;
	}
}
