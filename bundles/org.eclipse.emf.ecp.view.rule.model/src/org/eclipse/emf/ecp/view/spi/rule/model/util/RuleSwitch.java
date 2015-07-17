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
package org.eclipse.emf.ecp.view.spi.rule.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.OrCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each
 * class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage
 * @generated
 */
public class RuleSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static RulePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RuleSwitch() {
		if (modelPackage == null)
		{
			modelPackage = RulePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID)
		{
		case RulePackage.CONDITION: {
			final Condition condition = (Condition) theEObject;
			T result = caseCondition(condition);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.LEAF_CONDITION: {
			final LeafCondition leafCondition = (LeafCondition) theEObject;
			T result = caseLeafCondition(leafCondition);
			if (result == null) {
				result = caseCondition(leafCondition);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.OR_CONDITION: {
			final OrCondition orCondition = (OrCondition) theEObject;
			T result = caseOrCondition(orCondition);
			if (result == null) {
				result = caseCondition(orCondition);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.AND_CONDITION: {
			final AndCondition andCondition = (AndCondition) theEObject;
			T result = caseAndCondition(andCondition);
			if (result == null) {
				result = caseCondition(andCondition);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.RULE: {
			final Rule rule = (Rule) theEObject;
			T result = caseRule(rule);
			if (result == null) {
				result = caseAttachment(rule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.SHOW_RULE: {
			final ShowRule showRule = (ShowRule) theEObject;
			T result = caseShowRule(showRule);
			if (result == null) {
				result = caseRule(showRule);
			}
			if (result == null) {
				result = caseAttachment(showRule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case RulePackage.ENABLE_RULE: {
			final EnableRule enableRule = (EnableRule) theEObject;
			T result = caseEnableRule(enableRule);
			if (result == null) {
				result = caseRule(enableRule);
			}
			if (result == null) {
				result = caseAttachment(enableRule);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leaf Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leaf Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeafCondition(LeafCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrCondition(OrCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAndCondition(AndCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Show Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Show Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShowRule(ShowRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enable Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enable Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnableRule(EnableRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attachment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attachment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttachment(VAttachment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // RuleSwitch
