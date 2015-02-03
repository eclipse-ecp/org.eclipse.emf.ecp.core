/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.b;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.A;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.C;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.B#getA <em>A</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.B#getC <em>C</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage#getB()
 * @model
 * @generated
 */
public interface B extends EObject {
	/**
	 * Returns the value of the '<em><b>A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>A</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>A</em>' reference.
	 * @see #setA(A)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage#getB_A()
	 * @model
	 * @generated
	 */
	A getA();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.B#getA <em>A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>A</em>' reference.
	 * @see #getA()
	 * @generated
	 */
	void setA(A value);

	/**
	 * Returns the value of the '<em><b>C</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>C</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>C</em>' reference.
	 * @see #setC(C)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage#getB_C()
	 * @model
	 * @generated
	 */
	C getC();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.B#getC <em>C</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>C</em>' reference.
	 * @see #getC()
	 * @generated
	 */
	void setC(C value);

} // B
