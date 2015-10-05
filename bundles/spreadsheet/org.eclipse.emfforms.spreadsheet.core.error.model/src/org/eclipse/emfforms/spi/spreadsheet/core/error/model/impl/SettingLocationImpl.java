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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setting Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl#getEObject <em>EObject</em>
 * }</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl#getFeature <em>Feature</em>
 * }</li>
 * </ul>
 *
 * @generated
 */
public class SettingLocationImpl extends MinimalEObjectImpl.Container implements SettingLocation {
	/**
	 * The cached value of the '{@link #getEObject() <em>EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEObject()
	 * @generated
	 * @ordered
	 */
	protected EObject eObject;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature feature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SettingLocationImpl() {
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
		return ErrorPackage.Literals.SETTING_LOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject getEObject() {
		if (eObject != null && eObject.eIsProxy()) {
			final InternalEObject oldEObject = (InternalEObject) eObject;
			eObject = eResolveProxy(oldEObject);
			if (eObject != oldEObject) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ErrorPackage.SETTING_LOCATION__EOBJECT,
						oldEObject, eObject));
				}
			}
		}
		return eObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EObject basicGetEObject() {
		return eObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEObject(EObject newEObject) {
		final EObject oldEObject = eObject;
		eObject = newEObject;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SETTING_LOCATION__EOBJECT, oldEObject,
				eObject));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EStructuralFeature getFeature() {
		if (feature != null && feature.eIsProxy()) {
			final InternalEObject oldFeature = (InternalEObject) feature;
			feature = (EStructuralFeature) eResolveProxy(oldFeature);
			if (feature != oldFeature) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ErrorPackage.SETTING_LOCATION__FEATURE,
						oldFeature, feature));
				}
			}
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EStructuralFeature basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFeature(EStructuralFeature newFeature) {
		final EStructuralFeature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SETTING_LOCATION__FEATURE, oldFeature,
				feature));
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
		case ErrorPackage.SETTING_LOCATION__EOBJECT:
			if (resolve) {
				return getEObject();
			}
			return basicGetEObject();
		case ErrorPackage.SETTING_LOCATION__FEATURE:
			if (resolve) {
				return getFeature();
			}
			return basicGetFeature();
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
		case ErrorPackage.SETTING_LOCATION__EOBJECT:
			setEObject((EObject) newValue);
			return;
		case ErrorPackage.SETTING_LOCATION__FEATURE:
			setFeature((EStructuralFeature) newValue);
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
		case ErrorPackage.SETTING_LOCATION__EOBJECT:
			setEObject((EObject) null);
			return;
		case ErrorPackage.SETTING_LOCATION__FEATURE:
			setFeature((EStructuralFeature) null);
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
		case ErrorPackage.SETTING_LOCATION__EOBJECT:
			return eObject != null;
		case ErrorPackage.SETTING_LOCATION__FEATURE:
			return feature != null;
		}
		return super.eIsSet(featureID);
	}

} // SettingLocationImpl
