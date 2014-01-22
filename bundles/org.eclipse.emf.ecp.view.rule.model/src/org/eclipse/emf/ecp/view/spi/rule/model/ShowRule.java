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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Show Rule</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule#isHide <em>Hide</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getShowRule()
 * @model
 * @generated
 * @since 1.2
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
