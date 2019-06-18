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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Human</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl#getJoinDate <em>Join
 * Date</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl.HumanImpl#isIsActive <em>Is
 * Active</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class HumanImpl extends MinimalEObjectImpl.Container implements Human {
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
	 * The default value of the '{@link #getJoinDate() <em>Join Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getJoinDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date JOIN_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJoinDate() <em>Join Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getJoinDate()
	 * @generated
	 * @ordered
	 */
	protected Date joinDate = JOIN_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsActive() <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ACTIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsActive() <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIsActive()
	 * @generated
	 * @ordered
	 */
	protected boolean isActive = IS_ACTIVE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected HumanImpl() {
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
		return AuditPackage.Literals.HUMAN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AuditPackage.HUMAN__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setJoinDate(Date newJoinDate) {
		final Date oldJoinDate = joinDate;
		joinDate = newJoinDate;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, AuditPackage.HUMAN__JOIN_DATE, oldJoinDate, joinDate));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsActive() {
		return isActive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsActive(boolean newIsActive) {
		final boolean oldIsActive = isActive;
		isActive = newIsActive;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, AuditPackage.HUMAN__IS_ACTIVE, oldIsActive, isActive));
		}
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
		case AuditPackage.HUMAN__NAME:
			return getName();
		case AuditPackage.HUMAN__JOIN_DATE:
			return getJoinDate();
		case AuditPackage.HUMAN__IS_ACTIVE:
			return isIsActive();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AuditPackage.HUMAN__NAME:
			setName((String) newValue);
			return;
		case AuditPackage.HUMAN__JOIN_DATE:
			setJoinDate((Date) newValue);
			return;
		case AuditPackage.HUMAN__IS_ACTIVE:
			setIsActive((Boolean) newValue);
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
		case AuditPackage.HUMAN__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AuditPackage.HUMAN__JOIN_DATE:
			setJoinDate(JOIN_DATE_EDEFAULT);
			return;
		case AuditPackage.HUMAN__IS_ACTIVE:
			setIsActive(IS_ACTIVE_EDEFAULT);
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
		case AuditPackage.HUMAN__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case AuditPackage.HUMAN__JOIN_DATE:
			return JOIN_DATE_EDEFAULT == null ? joinDate != null : !JOIN_DATE_EDEFAULT.equals(joinDate);
		case AuditPackage.HUMAN__IS_ACTIVE:
			return isActive != IS_ACTIVE_EDEFAULT;
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
		result.append(", joinDate: "); //$NON-NLS-1$
		result.append(joinDate);
		result.append(", isActive: "); //$NON-NLS-1$
		result.append(isActive);
		result.append(')');
		return result.toString();
	}

} // HumanImpl
