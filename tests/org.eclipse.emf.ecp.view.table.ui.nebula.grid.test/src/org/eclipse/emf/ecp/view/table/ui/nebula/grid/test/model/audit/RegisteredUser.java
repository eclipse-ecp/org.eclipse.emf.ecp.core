/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Registered User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getLogin
 * <em>Login</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getPassword
 * <em>Password</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getDelegates
 * <em>Delegates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getRegisteredUser()
 * @model
 * @generated
 */
public interface RegisteredUser extends Human {
	/**
	 * Returns the value of the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Login</em>' attribute.
	 * @see #setLogin(String)
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getRegisteredUser_Login()
	 * @model
	 * @generated
	 */
	String getLogin();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getLogin <em>Login</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
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
	 *
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getRegisteredUser_Password()
	 * @model
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getPassword
	 * <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Delegates</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delegates</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Delegates</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getRegisteredUser_Delegates()
	 * @model
	 * @generated
	 */
	EList<RegisteredUser> getDelegates();

} // RegisteredUser
