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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EMF Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl#getRoot <em>Root</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl#getSettingLocation
 * <em>Setting Location</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl#getDmrLocation
 * <em>Dmr Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EMFLocationImpl extends MinimalEObjectImpl.Container implements EMFLocation {
	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected EObject root;

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
	 * The cached value of the '{@link #getDmrLocation() <em>Dmr Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDmrLocation()
	 * @generated
	 * @ordered
	 */
	protected DMRLocation dmrLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EMFLocationImpl() {
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
		return ErrorPackage.Literals.EMF_LOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject getRoot() {
		if (root != null && root.eIsProxy()) {
			final InternalEObject oldRoot = (InternalEObject) root;
			root = eResolveProxy(oldRoot);
			if (root != oldRoot) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ErrorPackage.EMF_LOCATION__ROOT, oldRoot,
						root));
				}
			}
		}
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EObject basicGetRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRoot(EObject newRoot) {
		final EObject oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.EMF_LOCATION__ROOT, oldRoot, root));
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
				ErrorPackage.EMF_LOCATION__SETTING_LOCATION, oldSettingLocation, newSettingLocation);
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
					EOPPOSITE_FEATURE_BASE - ErrorPackage.EMF_LOCATION__SETTING_LOCATION, null, msgs);
			}
			if (newSettingLocation != null) {
				msgs = ((InternalEObject) newSettingLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.EMF_LOCATION__SETTING_LOCATION, null, msgs);
			}
			msgs = basicSetSettingLocation(newSettingLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.EMF_LOCATION__SETTING_LOCATION,
				newSettingLocation, newSettingLocation));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DMRLocation getDmrLocation() {
		return dmrLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDmrLocation(DMRLocation newDmrLocation, NotificationChain msgs) {
		final DMRLocation oldDmrLocation = dmrLocation;
		dmrLocation = newDmrLocation;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ErrorPackage.EMF_LOCATION__DMR_LOCATION, oldDmrLocation, newDmrLocation);
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
	public void setDmrLocation(DMRLocation newDmrLocation) {
		if (newDmrLocation != dmrLocation) {
			NotificationChain msgs = null;
			if (dmrLocation != null) {
				msgs = ((InternalEObject) dmrLocation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.EMF_LOCATION__DMR_LOCATION, null, msgs);
			}
			if (newDmrLocation != null) {
				msgs = ((InternalEObject) newDmrLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.EMF_LOCATION__DMR_LOCATION, null, msgs);
			}
			msgs = basicSetDmrLocation(newDmrLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.EMF_LOCATION__DMR_LOCATION,
				newDmrLocation, newDmrLocation));
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
		case ErrorPackage.EMF_LOCATION__SETTING_LOCATION:
			return basicSetSettingLocation(null, msgs);
		case ErrorPackage.EMF_LOCATION__DMR_LOCATION:
			return basicSetDmrLocation(null, msgs);
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
		case ErrorPackage.EMF_LOCATION__ROOT:
			if (resolve) {
				return getRoot();
			}
			return basicGetRoot();
		case ErrorPackage.EMF_LOCATION__SETTING_LOCATION:
			return getSettingLocation();
		case ErrorPackage.EMF_LOCATION__DMR_LOCATION:
			return getDmrLocation();
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
		case ErrorPackage.EMF_LOCATION__ROOT:
			setRoot((EObject) newValue);
			return;
		case ErrorPackage.EMF_LOCATION__SETTING_LOCATION:
			setSettingLocation((SettingLocation) newValue);
			return;
		case ErrorPackage.EMF_LOCATION__DMR_LOCATION:
			setDmrLocation((DMRLocation) newValue);
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
		case ErrorPackage.EMF_LOCATION__ROOT:
			setRoot((EObject) null);
			return;
		case ErrorPackage.EMF_LOCATION__SETTING_LOCATION:
			setSettingLocation((SettingLocation) null);
			return;
		case ErrorPackage.EMF_LOCATION__DMR_LOCATION:
			setDmrLocation((DMRLocation) null);
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
		case ErrorPackage.EMF_LOCATION__ROOT:
			return root != null;
		case ErrorPackage.EMF_LOCATION__SETTING_LOCATION:
			return settingLocation != null;
		case ErrorPackage.EMF_LOCATION__DMR_LOCATION:
			return dmrLocation != null;
		}
		return super.eIsSet(featureID);
	}

} // EMFLocationImpl
