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
package org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control Grid Cell</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridCellImpl#getControl <em>Control</em>}
 * </li>
 * </ul>
 *
 * @generated
 */
public class VControlGridCellImpl extends MinimalEObjectImpl.Container implements VControlGridCell {
	/**
	 * The cached value of the '{@link #getControl() <em>Control</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getControl()
	 * @generated
	 * @ordered
	 */
	protected VControl control;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VControlGridCellImpl() {
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
		return VControlgridPackage.Literals.CONTROL_GRID_CELL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VControl getControl() {
		return control;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetControl(VControl newControl, NotificationChain msgs) {
		final VControl oldControl = control;
		control = newControl;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VControlgridPackage.CONTROL_GRID_CELL__CONTROL, oldControl, newControl);
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
	public void setControl(VControl newControl) {
		if (newControl != control) {
			NotificationChain msgs = null;
			if (control != null) {
				msgs = ((InternalEObject) control).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - VControlgridPackage.CONTROL_GRID_CELL__CONTROL, null, msgs);
			}
			if (newControl != null) {
				msgs = ((InternalEObject) newControl).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - VControlgridPackage.CONTROL_GRID_CELL__CONTROL, null, msgs);
			}
			msgs = basicSetControl(newControl, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VControlgridPackage.CONTROL_GRID_CELL__CONTROL,
				newControl, newControl));
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
		case VControlgridPackage.CONTROL_GRID_CELL__CONTROL:
			return basicSetControl(null, msgs);
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
		case VControlgridPackage.CONTROL_GRID_CELL__CONTROL:
			return getControl();
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
		case VControlgridPackage.CONTROL_GRID_CELL__CONTROL:
			setControl((VControl) newValue);
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
		case VControlgridPackage.CONTROL_GRID_CELL__CONTROL:
			setControl((VControl) null);
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
		case VControlgridPackage.CONTROL_GRID_CELL__CONTROL:
			return control != null;
		}
		return super.eIsSet(featureID);
	}

} // VControlGridCellImpl
