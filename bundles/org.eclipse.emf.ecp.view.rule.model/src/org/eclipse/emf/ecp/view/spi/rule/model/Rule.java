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

import org.eclipse.emf.ecp.view.spi.model.VAttachment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.Rule#getCondition <em>Condition</em>}</li>
 *        </ul>
 *        </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getRule()
 * @model abstract="true"
 * @generated
 */
public interface Rule extends VAttachment {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Condition)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getRule_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.Rule#getCondition <em>Condition</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Condition value);

} // Rule
