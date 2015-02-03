/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.b;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.EdaptTestA;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.EdaptTestC;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestB#getA <em>A</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestB#getC <em>C</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestBPackage#getB()
 * @model
 * @generated
 */
public interface EdaptTestB extends EObject {
	/**
	 * Returns the value of the '<em><b>A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>A</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>A</em>' reference.
	 * @see #setA(EdaptTestA)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestBPackage#getB_A()
	 * @model
	 * @generated
	 */
	EdaptTestA getA();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestB#getA <em>A</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>A</em>' reference.
	 * @see #getA()
	 * @generated
	 */
	void setA(EdaptTestA value);

	/**
	 * Returns the value of the '<em><b>C</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>C</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>C</em>' reference.
	 * @see #setC(EdaptTestC)
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestBPackage#getB_C()
	 * @model
	 * @generated
	 */
	EdaptTestC getC();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestB#getC <em>C</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>C</em>' reference.
	 * @see #getC()
	 * @generated
	 */
	void setC(EdaptTestC value);

} // EdaptTestB
