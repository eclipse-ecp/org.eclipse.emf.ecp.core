/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.c;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.D;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>C</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.c.C#getD <em>D</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage#getC()
 * @model
 * @generated
 */
public interface C extends EObject {
	/**
	 * Returns the value of the '<em><b>D</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>D</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>D</em>' reference.
	 * @see #setD(D)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage#getC_D()
	 * @model
	 * @generated
	 */
	D getD();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.c.C#getD <em>D</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>D</em>' reference.
	 * @see #getD()
	 * @generated
	 */
	void setD(D value);

} // C
