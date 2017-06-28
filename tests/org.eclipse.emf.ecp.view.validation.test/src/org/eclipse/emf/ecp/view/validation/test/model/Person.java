/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.validation.test.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getFirstName <em>First Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getGender <em>Gender</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getLastName <em>Last Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getCustom <em>Custom</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getAge <em>Age</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends EObject {

	/**
	 * Returns the value of the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>First Name</em>' attribute.
	 * @see #setFirstName(String)
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson_FirstName()
	 * @model dataType="org.eclipse.emf.ecp.view.validation.test.model.StringWithMaxLength8"
	 * @generated
	 */
	String getFirstName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getFirstName <em>First
	 * Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>First Name</em>' attribute.
	 * @see #getFirstName()
	 * @generated
	 */
	void setFirstName(String value);

	/**
	 * Returns the value of the '<em><b>Gender</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.validation.test.model.Gender}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gender</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Gender
	 * @see #setGender(Gender)
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson_Gender()
	 * @model
	 * @generated
	 */
	Gender getGender();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getGender <em>Gender</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Gender
	 * @see #getGender()
	 * @generated
	 */
	void setGender(Gender value);

	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson_LastName()
	 * @model dataType="org.eclipse.emf.ecp.view.validation.test.model.OnlyCapitals"
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getLastName <em>Last
	 * Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>Custom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Custom</em>' attribute.
	 * @see #setCustom(String)
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson_Custom()
	 * @model dataType="org.eclipse.emf.ecp.view.validation.test.model.CustomDataType"
	 * @generated
	 */
	String getCustom();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getCustom <em>Custom</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Custom</em>' attribute.
	 * @see #getCustom()
	 * @generated
	 */
	void setCustom(String value);

	/**
	 * Returns the value of the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Age</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Age</em>' attribute.
	 * @see #setAge(Integer)
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getPerson_Age()
	 * @model dataType="org.eclipse.emf.ecp.view.validation.test.model.Age"
	 * @generated
	 */
	Integer getAge();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getAge <em>Age</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Age</em>' attribute.
	 * @see #getAge()
	 * @generated
	 */
	void setAge(Integer value);
} // Person
