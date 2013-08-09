/**
 */
package org.eclipse.emf.ecp.view.rule.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Or Condition</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.rule.model.OrCondition#getConditions <em>Conditions</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.rule.model.RulePackage#getOrCondition()
 * @model
 * @generated
 */
public interface OrCondition extends Condition {
	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.rule.model.Condition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.rule.model.RulePackage#getOrCondition_Conditions()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<Condition> getConditions();

} // OrCondition
