/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enable Rule</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule#isDisable <em>Disable</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getEnableRule()
 * @model
 * @generated
 */
public interface EnableRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Disable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disable</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Disable</em>' attribute.
	 * @see #setDisable(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getEnableRule_Disable()
	 * @model
	 * @generated
	 */
	boolean isDisable();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule#isDisable <em>Disable</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Disable</em>' attribute.
	 * @see #isDisable()
	 * @generated
	 */
	void setDisable(boolean value);

} // EnableRule
