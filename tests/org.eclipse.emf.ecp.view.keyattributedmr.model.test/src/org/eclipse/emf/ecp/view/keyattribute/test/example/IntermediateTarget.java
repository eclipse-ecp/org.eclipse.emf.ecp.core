/**
 */
package org.eclipse.emf.ecp.view.keyattribute.test.example;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Intermediate Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.keyattribute.test.example.IntermediateTarget#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getIntermediateTarget()
 * @model
 * @generated
 */
public interface IntermediateTarget extends EObject {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Target)
	 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getIntermediateTarget_Target()
	 * @model containment="true"
	 * @generated
	 */
	Target getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.keyattribute.test.example.IntermediateTarget#getTarget
	 * <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Target value);

} // IntermediateTarget
