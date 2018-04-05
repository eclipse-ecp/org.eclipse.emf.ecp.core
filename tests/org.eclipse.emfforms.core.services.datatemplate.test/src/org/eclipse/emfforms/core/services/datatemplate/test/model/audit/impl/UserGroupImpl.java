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
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl#getUsers
 * <em>Users</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl#getAdmins
 * <em>Admins</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl#getRegisteredUsers
 * <em>Registered Users</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserGroupImpl#getGuests
 * <em>Guests</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserGroupImpl extends MinimalEObjectImpl.Container implements UserGroup {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUsers() <em>Users</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUsers()
	 * @generated
	 * @ordered
	 */
	protected EList<User> users;

	/**
	 * The cached value of the '{@link #getAdmins() <em>Admins</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAdmins()
	 * @generated
	 * @ordered
	 */
	protected EList<PrivilegedUser> admins;

	/**
	 * The cached value of the '{@link #getRegisteredUsers() <em>Registered Users</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getRegisteredUsers()
	 * @generated
	 * @ordered
	 */
	protected EList<RegisteredUser> registeredUsers;

	/**
	 * The cached value of the '{@link #getGuests() <em>Guests</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getGuests()
	 * @generated
	 * @ordered
	 */
	protected EList<GuestUser> guests;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected UserGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AuditPackage.Literals.USER_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.USER_GROUP__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<User> getUsers() {
		if (users == null) {
			users = new EObjectResolvingEList<User>(User.class, this, AuditPackage.USER_GROUP__USERS);
		}
		return users;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<PrivilegedUser> getAdmins() {
		if (admins == null) {
			admins = new EObjectResolvingEList<PrivilegedUser>(PrivilegedUser.class, this,
				AuditPackage.USER_GROUP__ADMINS);
		}
		return admins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RegisteredUser> getRegisteredUsers() {
		if (registeredUsers == null) {
			registeredUsers = new EObjectResolvingEList<RegisteredUser>(RegisteredUser.class, this,
				AuditPackage.USER_GROUP__REGISTERED_USERS);
		}
		return registeredUsers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<GuestUser> getGuests() {
		if (guests == null) {
			guests = new EObjectResolvingEList<GuestUser>(GuestUser.class, this, AuditPackage.USER_GROUP__GUESTS);
		}
		return guests;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AuditPackage.USER_GROUP__NAME:
			return getName();
		case AuditPackage.USER_GROUP__USERS:
			return getUsers();
		case AuditPackage.USER_GROUP__ADMINS:
			return getAdmins();
		case AuditPackage.USER_GROUP__REGISTERED_USERS:
			return getRegisteredUsers();
		case AuditPackage.USER_GROUP__GUESTS:
			return getGuests();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AuditPackage.USER_GROUP__NAME:
			setName((String) newValue);
			return;
		case AuditPackage.USER_GROUP__USERS:
			getUsers().clear();
			getUsers().addAll((Collection<? extends User>) newValue);
			return;
		case AuditPackage.USER_GROUP__ADMINS:
			getAdmins().clear();
			getAdmins().addAll((Collection<? extends PrivilegedUser>) newValue);
			return;
		case AuditPackage.USER_GROUP__REGISTERED_USERS:
			getRegisteredUsers().clear();
			getRegisteredUsers().addAll((Collection<? extends RegisteredUser>) newValue);
			return;
		case AuditPackage.USER_GROUP__GUESTS:
			getGuests().clear();
			getGuests().addAll((Collection<? extends GuestUser>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AuditPackage.USER_GROUP__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AuditPackage.USER_GROUP__USERS:
			getUsers().clear();
			return;
		case AuditPackage.USER_GROUP__ADMINS:
			getAdmins().clear();
			return;
		case AuditPackage.USER_GROUP__REGISTERED_USERS:
			getRegisteredUsers().clear();
			return;
		case AuditPackage.USER_GROUP__GUESTS:
			getGuests().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AuditPackage.USER_GROUP__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case AuditPackage.USER_GROUP__USERS:
			return users != null && !users.isEmpty();
		case AuditPackage.USER_GROUP__ADMINS:
			return admins != null && !admins.isEmpty();
		case AuditPackage.USER_GROUP__REGISTERED_USERS:
			return registeredUsers != null && !registeredUsers.isEmpty();
		case AuditPackage.USER_GROUP__GUESTS:
			return guests != null && !guests.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // UserGroupImpl
