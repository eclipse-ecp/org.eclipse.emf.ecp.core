/**
 */
package org.eclipse.emf.ecp.view.keyattribute.test.example;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Container#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.keyattribute.test.example.Child}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getContainer_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Child> getChildren();

} // Container
