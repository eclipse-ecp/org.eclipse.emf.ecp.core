/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Rule Entry</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getRule <em>Rule</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getElements <em>Elements</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getMergeType <em>Merge Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleEntry()
 * @model
 * @generated
 */
public interface VRuleEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Rule)
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleEntry_Rule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getRule <em>Rule</em>}'
	 * containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VElement}.
	 * <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Elements</em>' reference list.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleEntry_Elements()
	 * @model
	 * @generated
	 */
	EList<VElement> getElements();

	/**
	 * Returns the value of the '<em><b>Merge Type</b></em>' attribute.
	 * The default value is <code>"Or"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emfforms.spi.rulerepository.model.MergeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Merge Type</em>' attribute.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.MergeType
	 * @see #setMergeType(MergeType)
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleEntry_MergeType()
	 * @model default="Or" required="true"
	 * @generated
	 */
	MergeType getMergeType();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getMergeType <em>Merge
	 * Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Merge Type</em>' attribute.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.MergeType
	 * @see #getMergeType()
	 * @generated
	 */
	void setMergeType(MergeType value);

} // VRuleEntry
