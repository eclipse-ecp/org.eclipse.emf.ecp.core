/**
 * Copyright (c) 2018 Christian W. Damus and others.
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
 * A representation of the model object '<em><b>Is Proxy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Matches {@link org.eclipse.emf.ecore.EObject EObject}s that are unresolved proxies. Does not make sense for rules on
 * attributes.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition#getDomainModelReference <em>Domain Model
 * Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIsProxyCondition()
 * @model
 * @generated
 */
public interface IsProxyCondition extends Condition {

	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional reference to check for proxies. If set, must indicate an {@link org.eclipse.emf.ecore.EReference
	 * EReference}. The condition evaluates {@code true} if the reference contains a proxy; {@code false}, otherwise. If
	 * omitted, the condition tests whether the domain object itself is a proxy, which is particularly useful in
	 * {@link IterateCondition}s.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage#getIsProxyCondition_DomainModelReference()
	 * @model containment="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition#getDomainModelReference
	 * <em>Domain Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);
} // IsProxyCondition
