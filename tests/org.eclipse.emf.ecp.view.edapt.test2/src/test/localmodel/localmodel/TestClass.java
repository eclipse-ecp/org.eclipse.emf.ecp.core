/**
 */
package test.localmodel.localmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link test.localmodel.localmodel.TestClass#getTestAttribute <em>Test Attribute</em>}</li>
 * </ul>
 *
 * @see test.localmodel.localmodel.LocalmodelPackage#getTestClass()
 * @model
 * @generated
 */
public interface TestClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Test Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Test Attribute</em>' attribute.
	 * @see #setTestAttribute(String)
	 * @see test.localmodel.localmodel.LocalmodelPackage#getTestClass_TestAttribute()
	 * @model
	 * @generated
	 */
	String getTestAttribute();

	/**
	 * Sets the value of the '{@link test.localmodel.localmodel.TestClass#getTestAttribute <em>Test Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Test Attribute</em>' attribute.
	 * @see #getTestAttribute()
	 * @generated
	 */
	void setTestAttribute(String value);

} // TestClass
