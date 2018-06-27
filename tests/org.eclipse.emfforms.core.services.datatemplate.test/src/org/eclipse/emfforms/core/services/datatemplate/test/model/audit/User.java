/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 * Christian W. Damus - bug 529138
 */
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getLogin <em>Login</em>}</li>
 *   <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getPassword <em>Password</em>}</li>
 *   <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDelegates <em>Delegates</em>}</li>
 *   <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getSubUsers <em>Sub Users</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser()
 * @model abstract="true"
 * @generated
 */
public interface User extends EObject {
	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login</em>' attribute.
	 * @see #setLogin(String)
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser_Login()
	 * @model
	 * @generated
	 */
	String getLogin();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getLogin <em>Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login</em>' attribute.
	 * @see #getLogin()
	 * @generated
	 */
	void setLogin(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser_Password()
	 * @model
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Delegates</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delegates</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delegates</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser_Delegates()
	 * @model
	 * @generated
	 */
	EList<RegisteredUser> getDelegates();

	/**
	 * Returns the value of the '<em><b>Sub Users</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Users</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sub Users</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUser_SubUsers()
	 * @model
	 * @generated
	 */
	EList<AbstractSubUser> getSubUsers();

} // User
