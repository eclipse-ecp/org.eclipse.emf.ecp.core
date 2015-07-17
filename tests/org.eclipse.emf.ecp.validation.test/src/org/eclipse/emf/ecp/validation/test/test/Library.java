/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Johannes Faltermeier
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.validation.test.test;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.validation.test.test.Library#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.validation.test.test.Library#getWriters <em>Writers</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.validation.test.test.Library#getBooks <em>Books</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.validation.test.test.TestPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
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
	 * @see org.eclipse.emf.ecp.validation.test.test.TestPackage#getLibrary_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.validation.test.test.Library#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Writers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.validation.test.test.Writer}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.ecp.validation.test.test.Writer#getLibrary
	 * <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Writers</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Writers</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.validation.test.test.TestPackage#getLibrary_Writers()
	 * @see org.eclipse.emf.ecp.validation.test.test.Writer#getLibrary
	 * @model opposite="library" containment="true"
	 * @generated
	 */
	EList<Writer> getWriters();

	/**
	 * Returns the value of the '<em><b>Books</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.validation.test.test.Book}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Books</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Books</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.validation.test.test.TestPackage#getLibrary_Books()
	 * @model containment="true"
	 * @generated
	 */
	EList<Book> getBooks();

} // Library
