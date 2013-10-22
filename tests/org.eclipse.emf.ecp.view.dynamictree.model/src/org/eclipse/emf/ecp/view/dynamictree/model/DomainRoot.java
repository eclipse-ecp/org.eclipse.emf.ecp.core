/**
 */
package org.eclipse.emf.ecp.view.dynamictree.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.ecp.view.dynamictree.model.DomainRoot#getIntermediate <em>Intermediate</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.dynamictree.model.ModelPackage#getDomainRoot()
 * @model
 * @generated
 */
public interface DomainRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Intermediate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intermediate</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intermediate</em>' reference.
	 * @see #setIntermediate(DomainIntermediate)
	 * @see org.eclipse.emf.ecp.view.dynamictree.model.ModelPackage#getDomainRoot_Intermediate()
	 * @model
	 * @generated
	 */
	DomainIntermediate getIntermediate();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.dynamictree.model.DomainRoot#getIntermediate <em>Intermediate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intermediate</em>' reference.
	 * @see #getIntermediate()
	 * @generated
	 */
	void setIntermediate(DomainIntermediate value);

} // DomainRoot
