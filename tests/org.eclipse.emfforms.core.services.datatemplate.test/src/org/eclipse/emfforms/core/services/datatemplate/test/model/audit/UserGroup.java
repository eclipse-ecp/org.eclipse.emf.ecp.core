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
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getUsers <em>Users</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getAdmins <em>Admins</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getRegisteredUsers
 * <em>Registered Users</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getGuests <em>Guests</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup()
 * @model
 * @generated
 */
public interface UserGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName
	 * <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Users</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Users</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Users()
	 * @model
	 * @generated
	 */
	EList<User> getUsers();

	/**
	 * Returns the value of the '<em><b>Admins</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Admins</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Admins</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Admins()
	 * @model
	 * @generated
	 */
	EList<PrivilegedUser> getAdmins();

	/**
	 * Returns the value of the '<em><b>Registered Users</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registered Users</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Registered Users</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_RegisteredUsers()
	 * @model
	 * @generated
	 */
	EList<RegisteredUser> getRegisteredUsers();

	/**
	 * Returns the value of the '<em><b>Guests</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guests</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guests</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Guests()
	 * @model
	 * @generated
	 */
	EList<GuestUser> getGuests();

} // UserGroup
