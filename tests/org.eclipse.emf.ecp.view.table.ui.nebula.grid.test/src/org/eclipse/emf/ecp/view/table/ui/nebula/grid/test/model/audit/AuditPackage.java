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
 */
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit;

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
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditFactory
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
	String eNS_URI = "http://eclipse/org/emf/ecp/view/table/ui/nebula/grid/test/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	AuditPackage eINSTANCE = org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.OrganizationImpl
	 * <em>Organization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.OrganizationImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getOrganization()
	 * @generated
	 */
	int ORGANIZATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__MEMBERS = 1;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__GROUPS = 2;

	/**
	 * The number of structural features of the '<em>Organization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Organization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
	 * <em>Member</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getMember()
	 * @generated
	 */
	int MEMBER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER__JOIN_DATE = 1;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER__IS_ACTIVE = 2;

	/**
	 * The number of structural features of the '<em>Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.BotImpl
	 * <em>Bot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.BotImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getBot()
	 * @generated
	 */
	int BOT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT__NAME = MEMBER__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT__JOIN_DATE = MEMBER__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT__IS_ACTIVE = MEMBER__IS_ACTIVE;

	/**
	 * The feature id for the '<em><b>Execution Interval Seconds</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT__EXECUTION_INTERVAL_SECONDS = MEMBER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT_FEATURE_COUNT = MEMBER_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BOT_OPERATION_COUNT = MEMBER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl
	 * <em>Human</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getHuman()
	 * @generated
	 */
	int HUMAN = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HUMAN__NAME = MEMBER__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HUMAN__JOIN_DATE = MEMBER__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HUMAN__IS_ACTIVE = MEMBER__IS_ACTIVE;

	/**
	 * The number of structural features of the '<em>Human</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HUMAN_FEATURE_COUNT = MEMBER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Human</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HUMAN_OPERATION_COUNT = MEMBER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.GuestUserImpl <em>Guest User</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.GuestUserImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getGuestUser()
	 * @generated
	 */
	int GUEST_USER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__NAME = HUMAN__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__JOIN_DATE = HUMAN__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUEST_USER__IS_ACTIVE = HUMAN__IS_ACTIVE;

	/**
	 * The number of structural features of the '<em>Guest User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUEST_USER_FEATURE_COUNT = HUMAN_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Guest User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GUEST_USER_OPERATION_COUNT = HUMAN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.RegisteredUserImpl <em>Registered
	 * User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.RegisteredUserImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getRegisteredUser()
	 * @generated
	 */
	int REGISTERED_USER = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__NAME = HUMAN__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__JOIN_DATE = HUMAN__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__IS_ACTIVE = HUMAN__IS_ACTIVE;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__LOGIN = HUMAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__PASSWORD = HUMAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegates</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER__DELEGATES = HUMAN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Registered User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER_FEATURE_COUNT = HUMAN_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Registered User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REGISTERED_USER_OPERATION_COUNT = HUMAN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser
	 * <em>Privileged User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getPrivilegedUser()
	 * @generated
	 */
	int PRIVILEGED_USER = 6;

	/**
	 * The number of structural features of the '<em>Privileged User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Privileged User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AdminUserImpl <em>Admin User</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AdminUserImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getAdminUser()
	 * @generated
	 */
	int ADMIN_USER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__NAME = REGISTERED_USER__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__JOIN_DATE = REGISTERED_USER__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__IS_ACTIVE = REGISTERED_USER__IS_ACTIVE;

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
	 * The feature id for the '<em><b>Created By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER__CREATED_BY = REGISTERED_USER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Admin User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADMIN_USER_FEATURE_COUNT = REGISTERED_USER_FEATURE_COUNT + 1;

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
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.PrivilegedBotImpl <em>Privileged
	 * Bot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.PrivilegedBotImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getPrivilegedBot()
	 * @generated
	 */
	int PRIVILEGED_BOT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT__NAME = BOT__NAME;

	/**
	 * The feature id for the '<em><b>Join Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT__JOIN_DATE = BOT__JOIN_DATE;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT__IS_ACTIVE = BOT__IS_ACTIVE;

	/**
	 * The feature id for the '<em><b>Execution Interval Seconds</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT__EXECUTION_INTERVAL_SECONDS = BOT__EXECUTION_INTERVAL_SECONDS;

	/**
	 * The number of structural features of the '<em>Privileged Bot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT_FEATURE_COUNT = BOT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Privileged Bot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PRIVILEGED_BOT_OPERATION_COUNT = BOT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl <em>Member
	 * Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getMemberGroup()
	 * @generated
	 */
	int MEMBER_GROUP = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Members</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP__MEMBERS = 1;

	/**
	 * The feature id for the '<em><b>Admins</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP__ADMINS = 2;

	/**
	 * The feature id for the '<em><b>Bots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP__BOTS = 3;

	/**
	 * The number of structural features of the '<em>Member Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Member Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEMBER_GROUP_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Organization</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization
	 * @generated
	 */
	EClass getOrganization();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getName()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Name();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getMembers
	 * <em>Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Members</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getMembers()
	 * @see #getOrganization()
	 * @generated
	 */
	EReference getOrganization_Members();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getGroups()
	 * @see #getOrganization()
	 * @generated
	 */
	EReference getOrganization_Groups();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
	 * <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Member</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
	 * @generated
	 */
	EClass getMember();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#getName()
	 * @see #getMember()
	 * @generated
	 */
	EAttribute getMember_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#getJoinDate <em>Join Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Join Date</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#getJoinDate()
	 * @see #getMember()
	 * @generated
	 */
	EAttribute getMember_JoinDate();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#isIsActive <em>Is Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is Active</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member#isIsActive()
	 * @see #getMember()
	 * @generated
	 */
	EAttribute getMember_IsActive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot
	 * <em>Bot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Bot</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot
	 * @generated
	 */
	EClass getBot();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot#getExecutionIntervalSeconds
	 * <em>Execution Interval Seconds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Execution Interval Seconds</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot#getExecutionIntervalSeconds()
	 * @see #getBot()
	 * @generated
	 */
	EAttribute getBot_ExecutionIntervalSeconds();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human
	 * <em>Human</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Human</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human
	 * @generated
	 */
	EClass getHuman();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.GuestUser <em>Guest User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Guest User</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.GuestUser
	 * @generated
	 */
	EClass getGuestUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser <em>Registered User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Registered User</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser
	 * @generated
	 */
	EClass getRegisteredUser();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Login</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getLogin()
	 * @see #getRegisteredUser()
	 * @generated
	 */
	EAttribute getRegisteredUser_Login();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getPassword
	 * <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getPassword()
	 * @see #getRegisteredUser()
	 * @generated
	 */
	EAttribute getRegisteredUser_Password();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getDelegates
	 * <em>Delegates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Delegates</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser#getDelegates()
	 * @see #getRegisteredUser()
	 * @generated
	 */
	EReference getRegisteredUser_Delegates();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser <em>Privileged User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Privileged User</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser
	 * @generated
	 */
	EClass getPrivilegedUser();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser <em>Admin User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Admin User</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser
	 * @generated
	 */
	EClass getAdminUser();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser#getCreatedBy <em>Created
	 * By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Created By</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser#getCreatedBy()
	 * @see #getAdminUser()
	 * @generated
	 */
	EReference getAdminUser_CreatedBy();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedBot <em>Privileged Bot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Privileged Bot</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedBot
	 * @generated
	 */
	EClass getPrivilegedBot();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup <em>Member Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Member Group</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup
	 * @generated
	 */
	EClass getMemberGroup();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getName()
	 * @see #getMemberGroup()
	 * @generated
	 */
	EAttribute getMemberGroup_Name();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getMembers <em>Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Members</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getMembers()
	 * @see #getMemberGroup()
	 * @generated
	 */
	EReference getMemberGroup_Members();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getAdmins <em>Admins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Admins</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getAdmins()
	 * @see #getMemberGroup()
	 * @generated
	 */
	EReference getMemberGroup_Admins();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getBots <em>Bots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Bots</em>'.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getBots()
	 * @see #getMemberGroup()
	 * @generated
	 */
	EReference getMemberGroup_Bots();

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
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.OrganizationImpl
		 * <em>Organization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.OrganizationImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getOrganization()
		 * @generated
		 */
		EClass ORGANIZATION = eINSTANCE.getOrganization();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ORGANIZATION__NAME = eINSTANCE.getOrganization_Name();

		/**
		 * The meta object literal for the '<em><b>Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ORGANIZATION__MEMBERS = eINSTANCE.getOrganization_Members();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ORGANIZATION__GROUPS = eINSTANCE.getOrganization_Groups();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
		 * <em>Member</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getMember()
		 * @generated
		 */
		EClass MEMBER = eINSTANCE.getMember();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MEMBER__NAME = eINSTANCE.getMember_Name();

		/**
		 * The meta object literal for the '<em><b>Join Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MEMBER__JOIN_DATE = eINSTANCE.getMember_JoinDate();

		/**
		 * The meta object literal for the '<em><b>Is Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MEMBER__IS_ACTIVE = eINSTANCE.getMember_IsActive();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.BotImpl <em>Bot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.BotImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getBot()
		 * @generated
		 */
		EClass BOT = eINSTANCE.getBot();

		/**
		 * The meta object literal for the '<em><b>Execution Interval Seconds</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute BOT__EXECUTION_INTERVAL_SECONDS = eINSTANCE.getBot_ExecutionIntervalSeconds();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl <em>Human</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getHuman()
		 * @generated
		 */
		EClass HUMAN = eINSTANCE.getHuman();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.GuestUserImpl <em>Guest
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.GuestUserImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getGuestUser()
		 * @generated
		 */
		EClass GUEST_USER = eINSTANCE.getGuestUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.RegisteredUserImpl <em>Registered
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.RegisteredUserImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getRegisteredUser()
		 * @generated
		 */
		EClass REGISTERED_USER = eINSTANCE.getRegisteredUser();

		/**
		 * The meta object literal for the '<em><b>Login</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REGISTERED_USER__LOGIN = eINSTANCE.getRegisteredUser_Login();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REGISTERED_USER__PASSWORD = eINSTANCE.getRegisteredUser_Password();

		/**
		 * The meta object literal for the '<em><b>Delegates</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference REGISTERED_USER__DELEGATES = eINSTANCE.getRegisteredUser_Delegates();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser <em>Privileged
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getPrivilegedUser()
		 * @generated
		 */
		EClass PRIVILEGED_USER = eINSTANCE.getPrivilegedUser();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AdminUserImpl <em>Admin
		 * User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AdminUserImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getAdminUser()
		 * @generated
		 */
		EClass ADMIN_USER = eINSTANCE.getAdminUser();

		/**
		 * The meta object literal for the '<em><b>Created By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ADMIN_USER__CREATED_BY = eINSTANCE.getAdminUser_CreatedBy();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.PrivilegedBotImpl <em>Privileged
		 * Bot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.PrivilegedBotImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getPrivilegedBot()
		 * @generated
		 */
		EClass PRIVILEGED_BOT = eINSTANCE.getPrivilegedBot();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl <em>Member
		 * Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl
		 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.AuditPackageImpl#getMemberGroup()
		 * @generated
		 */
		EClass MEMBER_GROUP = eINSTANCE.getMemberGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MEMBER_GROUP__NAME = eINSTANCE.getMemberGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Members</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MEMBER_GROUP__MEMBERS = eINSTANCE.getMemberGroup_Members();

		/**
		 * The meta object literal for the '<em><b>Admins</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MEMBER_GROUP__ADMINS = eINSTANCE.getMemberGroup_Admins();

		/**
		 * The meta object literal for the '<em><b>Bots</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MEMBER_GROUP__BOTS = eINSTANCE.getMemberGroup_Bots();

	}

} // AuditPackage
