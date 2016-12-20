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
public interface EStructuralFeatureValueConverter {

	/** Conversion direction. */
	enum Direction {
		/**
		 * Conversion from model value to string literal.
		 */
		MODEL_TO_LITERAL,

		/**
		 * Conversion from string literal to model value.
		 */
		LITERAL_TO_MODEL,
	}

	/**
	 * Constant value to mark a converter implementation not applicable
	 * for the given EObject/EStructuralFeature combination.
	 */
	double NOT_APPLICABLE = -1d;

	/**
	 * Check whether this converter instance is applicable for the given feature.
	 *
	 * @param eObject (optional)
	 * @param feature the target feature
	 * @param value the value
	 * @param direction the direction
	 * @return rank of converter (higher value means higher priority)
	 */
	double isApplicable(EObject eObject, EStructuralFeature feature, Object value, Direction direction);

	/**
	 * Convert string literal to model value instance.
	 *
	 * @param eObject (optional, only required for EReferences)
	 * @param feature the target feature
	 * @param literal the string literal
	 * @return converted value
	 */
	Object convertToModelValue(EObject eObject, EStructuralFeature feature, String literal);

	/**
	 * Convert model value instance to string literal.
	 *
	 * @param eObject (optional)
	 * @param feature the source feature
	 * @param instance the source value object
	 * @return converted value (commonly a string literal)
	 */
	Object convertToLiteral(EObject eObject, EStructuralFeature feature, Object instance);

}
