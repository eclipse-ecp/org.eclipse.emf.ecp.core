/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.common.validation;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Pre-set validation service that checks whether a given value
 * complies with constraints defined by a given feature.
 *
 * @since 1.13
 */
public interface PreSetValidationService extends IFeatureConstraint {

	/**
	 * Validate a given value against constraints defined by the EDataType
	 * of the {@link EStructuralFeature}.
	 *
	 * @param eStructuralFeature the feature that defines any constraints
	 * @param value the value to be validated
	 * @return a {@link Diagnostic} describing any potential errors
	 */
	Diagnostic validate(EStructuralFeature eStructuralFeature, Object value);

	/**
	 * Add a custom {@link IFeatureConstraint}.
	 *
	 * @param element {@link ENamedElement} to add a validation constraint for
	 * @param constraint the behavioral validation constraint
	 */
	void addConstraintValidator(ENamedElement element, IFeatureConstraint constraint);

	/**
	 * Validate a given value against loose constraints defined by the EDataType of the
	 * {@link EStructuralFeature}.
	 *
	 * @param eStructuralFeature the feature that defines any constraints
	 * @param value the value to be validated
	 * @return a {@link Diagnostic} describing any potential errors
	 */
	Diagnostic validateLoose(EStructuralFeature eStructuralFeature, Object value);
}
