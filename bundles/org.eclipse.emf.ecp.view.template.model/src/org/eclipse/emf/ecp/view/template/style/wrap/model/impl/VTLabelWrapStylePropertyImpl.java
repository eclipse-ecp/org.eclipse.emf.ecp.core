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
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.wrap.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label Wrap Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl#isWrapLabel <em>Wrap
 * Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTLabelWrapStylePropertyImpl extends MinimalEObjectImpl.Container implements VTLabelWrapStyleProperty {
	/**
	 * The default value of the '{@link #isWrapLabel() <em>Wrap Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isWrapLabel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WRAP_LABEL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWrapLabel() <em>Wrap Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isWrapLabel()
	 * @generated
	 * @ordered
	 */
	protected boolean wrapLabel = WRAP_LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTLabelWrapStylePropertyImpl() {
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
		return VTWrapPackage.Literals.LABEL_WRAP_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isWrapLabel() {
		return wrapLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWrapLabel(boolean newWrapLabel) {
		final boolean oldWrapLabel = wrapLabel;
		wrapLabel = newWrapLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTWrapPackage.LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL,
				oldWrapLabel, wrapLabel));
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
		case VTWrapPackage.LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL:
			return isWrapLabel();
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
		case VTWrapPackage.LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL:
			setWrapLabel((Boolean) newValue);
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
		case VTWrapPackage.LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL:
			setWrapLabel(WRAP_LABEL_EDEFAULT);
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
		case VTWrapPackage.LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL:
			return wrapLabel != WRAP_LABEL_EDEFAULT;
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
		result.append(" (wrapLabel: "); //$NON-NLS-1$
		result.append(wrapLabel);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		return EMFUtils.filteredEquals(this, styleProperty);
	}

} // VTLabelWrapStylePropertyImpl
