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

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterate Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getQuantifier <em>Quantifier</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#isIfEmpty <em>If Empty</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemReference <em>Item Reference</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemCondition <em>Item Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIterateCondition()
 * @model
 * @generated
 */
public interface IterateCondition extends Condition {
	/**
	 * Returns the value of the '<em><b>Quantifier</b></em>' attribute.
	 * The default value is <code>"all"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.rule.model.Quantifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Quantifier</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
	 * @see #setQuantifier(Quantifier)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIterateCondition_Quantifier()
	 * @model default="all" required="true"
	 * @generated
	 */
	Quantifier getQuantifier();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getQuantifier
	 * <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Quantifier</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
	 * @see #getQuantifier()
	 * @generated
	 */
	void setQuantifier(Quantifier value);

	/**
	 * Returns the value of the '<em><b>If Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>If Empty</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>If Empty</em>' attribute.
	 * @see #setIfEmpty(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIterateCondition_IfEmpty()
	 * @model required="true"
	 * @generated
	 */
	boolean isIfEmpty();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#isIfEmpty <em>If
	 * Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>If Empty</em>' attribute.
	 * @see #isIfEmpty()
	 * @generated
	 */
	void setIfEmpty(boolean value);

	/**
	 * Returns the value of the '<em><b>Item Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item Reference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Item Reference</em>' containment reference.
	 * @see #setItemReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIterateCondition_ItemReference()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getItemReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemReference <em>Item
	 * Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Item Reference</em>' containment reference.
	 * @see #getItemReference()
	 * @generated
	 */
	void setItemReference(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Item Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Item Condition</em>' containment reference.
	 * @see #setItemCondition(Condition)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIterateCondition_ItemCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getItemCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemCondition <em>Item
	 * Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Item Condition</em>' containment reference.
	 * @see #getItemCondition()
	 * @generated
	 */
	void setItemCondition(Condition value);

} // IterateCondition
