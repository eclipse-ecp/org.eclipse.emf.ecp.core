/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getMinimumHeight
 * <em>Minimum Height</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getMaximumHeight
 * <em>Maximum Height</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTTableStylePropertyImpl extends MinimalEObjectImpl.Container implements VTTableStyleProperty {
	/**
	 * The default value of the '{@link #getMinimumHeight() <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinimumHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int MINIMUM_HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinimumHeight() <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinimumHeight()
	 * @generated
	 * @ordered
	 */
	protected int minimumHeight = MINIMUM_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Minimum Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean minimumHeightESet;

	/**
	 * The default value of the '{@link #getMaximumHeight() <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMaximumHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int MAXIMUM_HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaximumHeight() <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMaximumHeight()
	 * @generated
	 * @ordered
	 */
	protected int maximumHeight = MAXIMUM_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Maximum Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean maximumHeightESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTTableStylePropertyImpl() {
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
		return VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMinimumHeight() {
		return minimumHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMinimumHeight(int newMinimumHeight) {
		final int oldMinimumHeight = minimumHeight;
		minimumHeight = newMinimumHeight;
		final boolean oldMinimumHeightESet = minimumHeightESet;
		minimumHeightESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT, oldMinimumHeight, minimumHeight,
				!oldMinimumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMinimumHeight() {
		final int oldMinimumHeight = minimumHeight;
		final boolean oldMinimumHeightESet = minimumHeightESet;
		minimumHeight = MINIMUM_HEIGHT_EDEFAULT;
		minimumHeightESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT, oldMinimumHeight,
				MINIMUM_HEIGHT_EDEFAULT, oldMinimumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMinimumHeight() {
		return minimumHeightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMaximumHeight() {
		return maximumHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMaximumHeight(int newMaximumHeight) {
		final int oldMaximumHeight = maximumHeight;
		maximumHeight = newMaximumHeight;
		final boolean oldMaximumHeightESet = maximumHeightESet;
		maximumHeightESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT, oldMaximumHeight, maximumHeight,
				!oldMaximumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMaximumHeight() {
		final int oldMaximumHeight = maximumHeight;
		final boolean oldMaximumHeightESet = maximumHeightESet;
		maximumHeight = MAXIMUM_HEIGHT_EDEFAULT;
		maximumHeightESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT, oldMaximumHeight,
				MAXIMUM_HEIGHT_EDEFAULT, oldMaximumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMaximumHeight() {
		return maximumHeightESet;
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			return getMinimumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			return getMaximumHeight();
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			setMinimumHeight((Integer) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			setMaximumHeight((Integer) newValue);
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			unsetMinimumHeight();
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			unsetMaximumHeight();
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			return isSetMinimumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			return isSetMaximumHeight();
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
		result.append(" (minimumHeight: "); //$NON-NLS-1$
		if (minimumHeightESet) {
			result.append(minimumHeight);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", maximumHeight: "); //$NON-NLS-1$
		if (maximumHeightESet) {
			result.append(maximumHeight);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (!VTTableStyleProperty.class.isInstance(styleProperty)) {
			return false;
		}
		final VTTableStyleProperty other = VTTableStyleProperty.class.cast(styleProperty);
		if (isSetMaximumHeight() && !other.isSetMaximumHeight()) {
			return false;
		}
		if (!isSetMaximumHeight() && other.isSetMaximumHeight()) {
			return false;
		}
		if (isSetMinimumHeight() && !other.isSetMinimumHeight()) {
			return false;
		}
		if (!isSetMinimumHeight() && other.isSetMinimumHeight()) {
			return false;
		}
		if (getMaximumHeight() != other.getMaximumHeight()) {
			return false;
		}
		if (getMinimumHeight() != other.getMinimumHeight()) {
			return false;
		}
		return true;
	}

} // VTTableStylePropertyImpl
