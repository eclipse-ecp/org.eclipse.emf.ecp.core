/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.z;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.y.EdaptTestY;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Z</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZ#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZPackage#getZ()
 * @model
 * @generated
 */
public interface EdaptTestZ extends EObject {
	/**
	 * Returns the value of the '<em><b>Y</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Y</em>' reference.
	 * @see #setY(EdaptTestY)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZPackage#getZ_Y()
	 * @model
	 * @generated
	 */
	EdaptTestY getY();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZ#getY <em>Y</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Y</em>' reference.
	 * @see #getY()
	 * @generated
	 */
	void setY(EdaptTestY value);

} // EdaptTestZ
