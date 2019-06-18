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
 * Christian W. Damus - bug 529138
 */
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditFactory
 * @model kind="package"
 * @generated
 */
public interface AuditPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "audit"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://eclipse/org/emfforms/core/services/datatemplate/test/model/audit"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.core.services.datatemplate.test.model.audit"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AuditPackage eINSTANCE = org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl
	 * <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getUser()
	 * @generated
	 */
	int USER = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER__DISPLAY_NAME = 0;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER__LOGIN = 1;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER__PASSWORD = 2;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER__DELEGATES = 3;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER__SUB_USERS = 4;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser <em>Privileged
	 * User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getPrivilegedUser()
	 * @generated
	 */
	int PRIVILEGED_USER = 1;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER__DISPLAY_NAME = USER__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER__LOGIN = USER__LOGIN;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER__PASSWORD = USER__PASSWORD;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER__DELEGATES = USER__DELEGATES;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER__SUB_USERS = USER__SUB_USERS;

	/**
	 * The number of structural features of the '<em>Privileged User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER_FEATURE_COUNT = USER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Privileged User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER_OPERATION_COUNT = USER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.GuestUserImpl <em>Guest User</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.GuestUserImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getGuestUser()
	 * @generated
	 */
	int GUEST_USER = 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__DISPLAY_NAME = USER__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__LOGIN = USER__LOGIN;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__PASSWORD = USER__PASSWORD;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__DELEGATES = USER__DELEGATES;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__SUB_USERS = USER__SUB_USERS;

	/**
	 * The number of structural features of the '<em>Guest User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER_FEATURE_COUNT = USER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Guest User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GUEST_USER_OPERATION_COUNT = USER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.RegisteredUserImpl <em>Registered
	 * User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.RegisteredUserImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getRegisteredUser()
	 * @generated
	 */
	int REGISTERED_USER = 3;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__DISPLAY_NAME = USER__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__LOGIN = USER__LOGIN;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__PASSWORD = USER__PASSWORD;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__DELEGATES = USER__DELEGATES;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__SUB_USERS = USER__SUB_USERS;

	/**
	 * The number of structural features of the '<em>Registered User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER_FEATURE_COUNT = USER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Registered User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER_OPERATION_COUNT = USER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AdminUserImpl <em>Admin User</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AdminUserImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getAdminUser()
	 * @generated
	 */
	int ADMIN_USER = 4;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__DISPLAY_NAME = REGISTERED_USER__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__LOGIN = REGISTERED_USER__LOGIN;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__PASSWORD = REGISTERED_USER__PASSWORD;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__DELEGATES = REGISTERED_USER__DELEGATES;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__SUB_USERS = REGISTERED_USER__SUB_USERS;

	/**
	 * The number of structural features of the '<em>Admin User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER_FEATURE_COUNT = REGISTERED_USER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Admin User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER_OPERATION_COUNT = REGISTERED_USER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl <em>User Group</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getUserGroup()
	 * @generated
	 */
	int USER_GROUP = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__USERS = 1;

	/**
	 * The feature id for the '<em><b>Admins</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__ADMINS = 2;

	/**
	 * The feature id for the '<em><b>Registered Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__REGISTERED_USERS = 3;

	/**
	 * The feature id for the '<em><b>Guests</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__GUESTS = 4;

	/**
	 * The number of structural features of the '<em>User Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>User Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USER_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AbstractSubUserImpl <em>Abstract
	 * Sub User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AbstractSubUserImpl
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getAbstractSubUser()
	 * @generated
	 */
	int ABSTRACT_SUB_USER = 6;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER__DISPLAY_NAME = USER__DISPLAY_NAME;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER__LOGIN = USER__LOGIN;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER__PASSWORD = USER__PASSWORD;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER__DELEGATES = USER__DELEGATES;

	/**
	 * The feature id for the '<em><b>Sub Users</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER__SUB_USERS = USER__SUB_USERS;

	/**
	 * The number of structural features of the '<em>Abstract Sub User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER_FEATURE_COUNT = USER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Sub User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SUB_USER_OPERATION_COUNT = USER_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User
	 * <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDisplayName <em>Display
	 * Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDisplayName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_DisplayName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Login</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getLogin()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Login();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getPassword()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Password();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDelegates <em>Delegates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Delegates</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getDelegates()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Delegates();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getSubUsers <em>Sub Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Sub Users</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User#getSubUsers()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_SubUsers();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser <em>Privileged
	 * User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Privileged User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser
	 * @generated
	 */
	EClass getPrivilegedUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser <em>Guest User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Guest User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser
	 * @generated
	 */
	EClass getGuestUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser <em>Registered
	 * User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Registered User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser
	 * @generated
	 */
	EClass getRegisteredUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser <em>Admin User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Admin User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser
	 * @generated
	 */
	EClass getAdminUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup <em>User Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>User Group</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup
	 * @generated
	 */
	EClass getUserGroup();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName()
	 * @see #getUserGroup()
	 * @generated
	 */
	EAttribute getUserGroup_Name();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getUsers <em>Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Users</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getUsers()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_Users();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getAdmins <em>Admins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Admins</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getAdmins()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_Admins();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getRegisteredUsers
	 * <em>Registered Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Registered Users</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getRegisteredUsers()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_RegisteredUsers();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getGuests <em>Guests</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Guests</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getGuests()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_Guests();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser <em>Abstract Sub
	 * User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Abstract Sub User</em>'.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser
	 * @generated
	 */
	EClass getAbstractSubUser();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AuditFactory getAuditFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USER__DISPLAY_NAME = eINSTANCE.getUser_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Login</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USER__LOGIN = eINSTANCE.getUser_Login();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USER__PASSWORD = eINSTANCE.getUser_Password();

		/**
		 * The meta object literal for the '<em><b>Delegates</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER__DELEGATES = eINSTANCE.getUser_Delegates();

		/**
		 * The meta object literal for the '<em><b>Sub Users</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER__SUB_USERS = eINSTANCE.getUser_SubUsers();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser <em>Privileged
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getPrivilegedUser()
		 * @generated
		 */
		EClass PRIVILEGED_USER = eINSTANCE.getPrivilegedUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.GuestUserImpl <em>Guest
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.GuestUserImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getGuestUser()
		 * @generated
		 */
		EClass GUEST_USER = eINSTANCE.getGuestUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.RegisteredUserImpl
		 * <em>Registered User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.RegisteredUserImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getRegisteredUser()
		 * @generated
		 */
		EClass REGISTERED_USER = eINSTANCE.getRegisteredUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AdminUserImpl <em>Admin
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AdminUserImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getAdminUser()
		 * @generated
		 */
		EClass ADMIN_USER = eINSTANCE.getAdminUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl <em>User
		 * Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getUserGroup()
		 * @generated
		 */
		EClass USER_GROUP = eINSTANCE.getUserGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute USER_GROUP__NAME = eINSTANCE.getUserGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Users</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER_GROUP__USERS = eINSTANCE.getUserGroup_Users();

		/**
		 * The meta object literal for the '<em><b>Admins</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER_GROUP__ADMINS = eINSTANCE.getUserGroup_Admins();

		/**
		 * The meta object literal for the '<em><b>Registered Users</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER_GROUP__REGISTERED_USERS = eINSTANCE.getUserGroup_RegisteredUsers();

		/**
		 * The meta object literal for the '<em><b>Guests</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference USER_GROUP__GUESTS = eINSTANCE.getUserGroup_Guests();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AbstractSubUserImpl
		 * <em>Abstract Sub User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AbstractSubUserImpl
		 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.AuditPackageImpl#getAbstractSubUser()
		 * @generated
		 */
		EClass ABSTRACT_SUB_USER = eINSTANCE.getAbstractSubUser();

	}

} // AuditPackage
