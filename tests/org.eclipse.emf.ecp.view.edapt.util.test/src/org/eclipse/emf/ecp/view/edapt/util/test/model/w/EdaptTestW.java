/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.w;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.x.EdaptTestX;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>W</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestW#getX <em>X</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestWPackage#getW()
 * @model
 * @generated
 */
public interface EdaptTestW extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>X</em>' reference.
	 * @see #setX(EdaptTestX)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestWPackage#getW_X()
	 * @model
	 * @generated
	 */
	EdaptTestX getX();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestW#getX <em>X</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>X</em>' reference.
	 * @see #getX()
	 * @generated
	 */
	void setX(EdaptTestX value);

} // EdaptTestW
