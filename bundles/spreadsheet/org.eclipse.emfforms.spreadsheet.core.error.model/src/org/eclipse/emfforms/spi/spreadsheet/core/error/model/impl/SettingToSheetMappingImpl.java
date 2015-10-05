/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setting To Sheet Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl#getSheetLocation
 * <em>Sheet Location</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl#getSettingLocation
 * <em>Setting Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SettingToSheetMappingImpl extends MinimalEObjectImpl.Container implements SettingToSheetMapping {
	/**
	 * The cached value of the '{@link #getSheetLocation() <em>Sheet Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSheetLocation()
	 * @generated
	 * @ordered
	 */
	protected SheetLocation sheetLocation;

	/**
	 * The cached value of the '{@link #getSettingLocation() <em>Setting Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSettingLocation()
	 * @generated
	 * @ordered
	 */
	protected SettingLocation settingLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SettingToSheetMappingImpl() {
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
		return ErrorPackage.Literals.SETTING_TO_SHEET_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SheetLocation getSheetLocation() {
		return sheetLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetSheetLocation(SheetLocation newSheetLocation, NotificationChain msgs) {
		final SheetLocation oldSheetLocation = sheetLocation;
		sheetLocation = newSheetLocation;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION, oldSheetLocation, newSheetLocation);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSheetLocation(SheetLocation newSheetLocation) {
		if (newSheetLocation != sheetLocation) {
			NotificationChain msgs = null;
			if (sheetLocation != null) {
				msgs = ((InternalEObject) sheetLocation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION, null, msgs);
			}
			if (newSheetLocation != null) {
				msgs = ((InternalEObject) newSheetLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION, null, msgs);
			}
			msgs = basicSetSheetLocation(newSheetLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION,
				newSheetLocation, newSheetLocation));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SettingLocation getSettingLocation() {
		return settingLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetSettingLocation(SettingLocation newSettingLocation, NotificationChain msgs) {
		final SettingLocation oldSettingLocation = settingLocation;
		settingLocation = newSettingLocation;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION, oldSettingLocation, newSettingLocation);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSettingLocation(SettingLocation newSettingLocation) {
		if (newSettingLocation != settingLocation) {
			NotificationChain msgs = null;
			if (settingLocation != null) {
				msgs = ((InternalEObject) settingLocation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION, null, msgs);
			}
			if (newSettingLocation != null) {
				msgs = ((InternalEObject) newSettingLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION, null, msgs);
			}
			msgs = basicSetSettingLocation(newSettingLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION, newSettingLocation, newSettingLocation));
		}
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
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION:
			return basicSetSheetLocation(null, msgs);
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION:
			return basicSetSettingLocation(null, msgs);
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
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION:
			return getSheetLocation();
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION:
			return getSettingLocation();
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
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION:
			setSheetLocation((SheetLocation) newValue);
			return;
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION:
			setSettingLocation((SettingLocation) newValue);
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
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION:
			setSheetLocation((SheetLocation) null);
			return;
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION:
			setSettingLocation((SettingLocation) null);
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
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SHEET_LOCATION:
			return sheetLocation != null;
		case ErrorPackage.SETTING_TO_SHEET_MAPPING__SETTING_LOCATION:
			return settingLocation != null;
		}
		return super.eIsSet(featureID);
	}

} // SettingToSheetMappingImpl
