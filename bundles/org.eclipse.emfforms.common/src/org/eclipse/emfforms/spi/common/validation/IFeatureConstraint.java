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

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Interface that checks whether a given value
 * complies with constraints defined by a given feature.
 *
 * @since 1.13
 */
public interface IFeatureConstraint {

	/**
	 * Context map key to retrieve the the root EObject (may be null).
	 */
	String E_ROOT_OBJECT = "rootEObject"; //$NON-NLS-1$

	/**
	 * Validate a given value against constraints defined by the {@link org.eclipse.emf.ecore.EDataType EDataType}
	 * of the {@link EStructuralFeature}.
	 *
	 * @param eStructuralFeature the feature that defines any constraints
	 * @param value the value to be validated
	 * @param context the validation context, may be <code>null</code>
	 * @return a {@link Diagnostic} describing any potential errors
	 */
	Diagnostic validate(EStructuralFeature eStructuralFeature, Object value, Map<Object, Object> context);
}
