/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getCondition()
 * @model abstract="true"
 * @generated
 */
public interface Condition extends EObject {

	/**
	 * Evaluates the given condition.
	 *
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluate();

	/**
	 * Evaluates the given condition.
	 *
	 * @param possibleNewValues
	 *            the new value that should be compared against the expected value of the condition
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluateChangedValues(Map<Setting, Object> possibleNewValues);
} // Condition
