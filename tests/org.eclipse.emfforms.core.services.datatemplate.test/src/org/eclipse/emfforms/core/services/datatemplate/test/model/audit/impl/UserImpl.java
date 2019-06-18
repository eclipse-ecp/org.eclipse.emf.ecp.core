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
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl#getDisplayName <em>Display
 * Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl#getLogin
 * <em>Login</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl#getPassword
 * <em>Password</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl#getDelegates
 * <em>Delegates</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl.UserImpl#getSubUsers <em>Sub
 * Users</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UserImpl extends MinimalEObjectImpl.Container implements User {
	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLogin() <em>Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLogin()
	 * @generated
	 * @ordered
	 */
	protected static final String LOGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLogin() <em>Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLogin()
	 * @generated
	 * @ordered
	 */
	protected String login = LOGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDelegates() <em>Delegates</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDelegates()
	 * @generated
	 * @ordered
	 */
	protected EList<RegisteredUser> delegates;

	/**
	 * The cached value of the '{@link #getSubUsers() <em>Sub Users</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSubUsers()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractSubUser> subUsers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected UserImpl() {
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
		return AuditPackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName) {
		final String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.USER__DISPLAY_NAME, oldDisplayName,
				displayName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLogin() {
		return login;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLogin(String newLogin) {
		final String oldLogin = login;
		login = newLogin;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.USER__LOGIN, oldLogin, login));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPassword(String newPassword) {
		final String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.USER__PASSWORD, oldPassword, password));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<RegisteredUser> getDelegates() {
		if (delegates == null) {
			delegates = new EObjectResolvingEList<RegisteredUser>(RegisteredUser.class, this,
				AuditPackage.USER__DELEGATES);
		}
		return delegates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<AbstractSubUser> getSubUsers() {
		if (subUsers == null) {
			subUsers = new EObjectResolvingEList<AbstractSubUser>(AbstractSubUser.class, this,
				AuditPackage.USER__SUB_USERS);
		}
		return subUsers;
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
		case AuditPackage.USER__DISPLAY_NAME:
			return getDisplayName();
		case AuditPackage.USER__LOGIN:
			return getLogin();
		case AuditPackage.USER__PASSWORD:
			return getPassword();
		case AuditPackage.USER__DELEGATES:
			return getDelegates();
		case AuditPackage.USER__SUB_USERS:
			return getSubUsers();
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
		case AuditPackage.USER__DISPLAY_NAME:
			setDisplayName((String) newValue);
			return;
		case AuditPackage.USER__LOGIN:
			setLogin((String) newValue);
			return;
		case AuditPackage.USER__PASSWORD:
			setPassword((String) newValue);
			return;
		case AuditPackage.USER__DELEGATES:
			getDelegates().clear();
			getDelegates().addAll((Collection<? extends RegisteredUser>) newValue);
			return;
		case AuditPackage.USER__SUB_USERS:
			getSubUsers().clear();
			getSubUsers().addAll((Collection<? extends AbstractSubUser>) newValue);
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
		case AuditPackage.USER__DISPLAY_NAME:
			setDisplayName(DISPLAY_NAME_EDEFAULT);
			return;
		case AuditPackage.USER__LOGIN:
			setLogin(LOGIN_EDEFAULT);
			return;
		case AuditPackage.USER__PASSWORD:
			setPassword(PASSWORD_EDEFAULT);
			return;
		case AuditPackage.USER__DELEGATES:
			getDelegates().clear();
			return;
		case AuditPackage.USER__SUB_USERS:
			getSubUsers().clear();
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
		case AuditPackage.USER__DISPLAY_NAME:
			return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
		case AuditPackage.USER__LOGIN:
			return LOGIN_EDEFAULT == null ? login != null : !LOGIN_EDEFAULT.equals(login);
		case AuditPackage.USER__PASSWORD:
			return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
		case AuditPackage.USER__DELEGATES:
			return delegates != null && !delegates.isEmpty();
		case AuditPackage.USER__SUB_USERS:
			return subUsers != null && !subUsers.isEmpty();
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
		result.append(" (displayName: "); //$NON-NLS-1$
		result.append(displayName);
		result.append(", login: "); //$NON-NLS-1$
		result.append(login);
		result.append(", password: "); //$NON-NLS-1$
		result.append(password);
		result.append(')');
		return result.toString();
	}

} // UserImpl
