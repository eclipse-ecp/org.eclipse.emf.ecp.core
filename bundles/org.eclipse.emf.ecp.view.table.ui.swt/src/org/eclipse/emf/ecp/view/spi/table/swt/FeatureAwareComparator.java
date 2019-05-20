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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Defines a comparator which knows about the feature that the compared values are stored in. This allows to use
 * knowledge of the feature for the comparison.
 *
 * @param <T> The type of the objects comparable with this comparator
 * @author Lucas Koehler
 * @since 1.22
 */
@FunctionalInterface
public interface FeatureAwareComparator<T> {

	/**
	 * Compares two values of the given feature.
	 *
	 * @param feature The {@link EStructuralFeature} containing the values
	 * @param leftValue Left value
	 * @param rightValue Right Value
	 * @return a negative number if the left value is less than the right value;
	 *         the value <code>0</code> if the left value is equal to the right value;
	 *         a positive number if the left value is greater than the right value.
	 *         Thereby, <code>null</code> input values are treated as bigger than any other non-null value
	 * @see java.util.Comparator#compare(Object, Object)
	 */
	int compare(EStructuralFeature feature, T leftValue, T rightValue);
}
