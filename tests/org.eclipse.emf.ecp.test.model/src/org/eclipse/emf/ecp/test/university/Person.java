/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource - Generated code
 */
package org.eclipse.emf.ecp.test.university;

import org.eclipse.emf.common.util.EList;
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
 * <li>{@link org.eclipse.emf.ecp.test.university.Person#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.test.university.Person#getAddresses <em>Addresses</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.test.university.UniversityPackage#getPerson()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Person extends EObject {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.ecp.test.university.UniversityPackage#getPerson_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.test.university.Person#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Addresses</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.test.university.Address}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Addresses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Addresses</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.test.university.UniversityPackage#getPerson_Addresses()
	 * @model containment="true"
	 * @generated
	 */
	EList<Address> getAddresses();
} // Person
