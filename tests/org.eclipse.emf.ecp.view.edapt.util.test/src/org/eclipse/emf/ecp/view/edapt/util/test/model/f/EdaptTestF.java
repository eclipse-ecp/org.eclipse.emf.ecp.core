/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.f;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestE;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>F</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestF#getE <em>E</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestFPackage#getF()
 * @model
 * @generated
 */
public interface EdaptTestF extends EObject {
	/**
	 * Returns the value of the '<em><b>E</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>E</em>' reference.
	 * @see #setE(EdaptTestE)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestFPackage#getF_E()
	 * @model
	 * @generated
	 */
	EdaptTestE getE();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestF#getE <em>E</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>E</em>' reference.
	 * @see #getE()
	 * @generated
	 */
	void setE(EdaptTestE value);

} // EdaptTestF
