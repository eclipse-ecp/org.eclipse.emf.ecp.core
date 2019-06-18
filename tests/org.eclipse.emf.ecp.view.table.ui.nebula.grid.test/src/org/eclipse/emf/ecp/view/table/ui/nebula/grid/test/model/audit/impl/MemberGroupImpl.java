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
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Member Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl#getMembers
 * <em>Members</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl#getAdmins
 * <em>Admins</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.MemberGroupImpl#getBots
 * <em>Bots</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemberGroupImpl extends MinimalEObjectImpl.Container implements MemberGroup {
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
	 * The cached value of the '{@link #getMembers() <em>Members</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<Human> members;

	/**
	 * The cached value of the '{@link #getAdmins() <em>Admins</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAdmins()
	 * @generated
	 * @ordered
	 */
	protected EList<AdminUser> admins;

	/**
	 * The cached value of the '{@link #getBots() <em>Bots</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBots()
	 * @generated
	 * @ordered
	 */
	protected EList<Bot> bots;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected MemberGroupImpl() {
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
		return AuditPackage.Literals.MEMBER_GROUP;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.MEMBER_GROUP__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Human> getMembers() {
		if (members == null) {
			members = new EObjectResolvingEList<Human>(Human.class, this, AuditPackage.MEMBER_GROUP__MEMBERS);
		}
		return members;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<AdminUser> getAdmins() {
		if (admins == null) {
			admins = new EObjectResolvingEList<AdminUser>(AdminUser.class, this, AuditPackage.MEMBER_GROUP__ADMINS);
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
	public EList<Bot> getBots() {
		if (bots == null) {
			bots = new EObjectContainmentEList<Bot>(Bot.class, this, AuditPackage.MEMBER_GROUP__BOTS);
		}
		return bots;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AuditPackage.MEMBER_GROUP__BOTS:
			return ((InternalEList<?>) getBots()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case AuditPackage.MEMBER_GROUP__NAME:
			return getName();
		case AuditPackage.MEMBER_GROUP__MEMBERS:
			return getMembers();
		case AuditPackage.MEMBER_GROUP__ADMINS:
			return getAdmins();
		case AuditPackage.MEMBER_GROUP__BOTS:
			return getBots();
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
		case AuditPackage.MEMBER_GROUP__NAME:
			setName((String) newValue);
			return;
		case AuditPackage.MEMBER_GROUP__MEMBERS:
			getMembers().clear();
			getMembers().addAll((Collection<? extends Human>) newValue);
			return;
		case AuditPackage.MEMBER_GROUP__ADMINS:
			getAdmins().clear();
			getAdmins().addAll((Collection<? extends AdminUser>) newValue);
			return;
		case AuditPackage.MEMBER_GROUP__BOTS:
			getBots().clear();
			getBots().addAll((Collection<? extends Bot>) newValue);
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
		case AuditPackage.MEMBER_GROUP__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AuditPackage.MEMBER_GROUP__MEMBERS:
			getMembers().clear();
			return;
		case AuditPackage.MEMBER_GROUP__ADMINS:
			getAdmins().clear();
			return;
		case AuditPackage.MEMBER_GROUP__BOTS:
			getBots().clear();
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
		case AuditPackage.MEMBER_GROUP__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case AuditPackage.MEMBER_GROUP__MEMBERS:
			return members != null && !members.isEmpty();
		case AuditPackage.MEMBER_GROUP__ADMINS:
			return admins != null && !admins.isEmpty();
		case AuditPackage.MEMBER_GROUP__BOTS:
			return bots != null && !bots.isEmpty();
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

} // MemberGroupImpl
