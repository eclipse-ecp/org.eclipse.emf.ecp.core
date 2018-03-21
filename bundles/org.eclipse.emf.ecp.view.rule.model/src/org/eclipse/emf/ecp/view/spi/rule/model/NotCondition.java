/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.rule.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Not Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.NotCondition#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getNotCondition()
 * @model
 * @generated
 */
public interface NotCondition extends Condition {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Condition)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getNotCondition_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.NotCondition#getCondition
	 * <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Condition value);

} // NotCondition
