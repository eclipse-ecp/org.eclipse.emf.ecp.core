/**
 */
package org.eclipse.emf.ecp.view.keyattribute.test.example;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Child</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Child#getIntermediateTarget <em>Intermediate Target</em>}</li>
 *   <li>{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Child#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getChild()
 * @model
 * @generated
 */
public interface Child extends EObject {
	/**
	 * Returns the value of the '<em><b>Intermediate Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intermediate Target</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intermediate Target</em>' containment reference.
	 * @see #setIntermediateTarget(IntermediateTarget)
	 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getChild_IntermediateTarget()
	 * @model containment="true"
	 * @generated
	 */
	IntermediateTarget getIntermediateTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Child#getIntermediateTarget <em>Intermediate Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intermediate Target</em>' containment reference.
	 * @see #getIntermediateTarget()
	 * @generated
	 */
	void setIntermediateTarget(IntermediateTarget value);

	/**
	 * Returns the value of the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' containment reference.
	 * @see #setKey(KeyContainer)
	 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getChild_Key()
	 * @model containment="true"
	 * @generated
	 */
	KeyContainer getKey();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Child#getKey <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' containment reference.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(KeyContainer value);

} // Child
