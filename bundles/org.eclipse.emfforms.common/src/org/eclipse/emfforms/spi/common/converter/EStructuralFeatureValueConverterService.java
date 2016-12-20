/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mathias - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.common.converter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Mathias Schaefer <mschaefer@eclipsesource.com>
 * @since 1.11
 *
 */
public interface EStructuralFeatureValueConverterService {

	/**
	 * Register the given value converter.
	 *
	 * @param converter to add to the registry
	 */
	void addValueConverter(EStructuralFeatureValueConverter converter);

	/**
	 * Unregister the given value converter.
	 *
	 * @param converter to remove from the registry
	 */
	void removeValueConverter(EStructuralFeatureValueConverter converter);

	/**
	 * Convert literal to Object.
	 *
	 * @param eObject of the feature (optional, can be null)
	 * @param feature target feature
	 * @param literal to convert
	 * @return converted object (null if conversion failed)
	 */
	Object convertToModelValue(EObject eObject, EStructuralFeature feature, String literal);

	/**
	 * Convert value instance to literal.
	 *
	 * @param eObject (optional, can be null)
	 * @param feature source feature
	 * @param instance to convert
	 * @return converted object (commonly a string literal, null if conversion failed)
	 */
	Object convertToLiteral(EObject eObject, EStructuralFeature feature, Object instance);
}
