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
 * A representation of the model object '<em><b>Show Rule</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule#isHide <em>Hide</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getShowRule()
 * @model
 * @generated
 */
public interface ShowRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Hide</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hide</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Hide</em>' attribute.
	 * @see #setHide(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getShowRule_Hide()
	 * @model
	 * @generated
	 */
	boolean isHide();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule#isHide <em>Hide</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Hide</em>' attribute.
	 * @see #isHide()
	 * @generated
	 */
	void setHide(boolean value);

} // ShowRule
