/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label Width Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl.VTLabelWidthStylePropertyImpl#getWidth
 * <em>Width</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTLabelWidthStylePropertyImpl extends MinimalEObjectImpl.Container implements VTLabelWidthStyleProperty {
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * This is true if the Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean widthESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTLabelWidthStylePropertyImpl() {
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
		return VTLabelwidthPackage.Literals.LABEL_WIDTH_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWidth(int newWidth) {
		final int oldWidth = width;
		width = newWidth;
		final boolean oldWidthESet = widthESet;
		widthESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH,
				oldWidth, width, !oldWidthESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetWidth() {
		final int oldWidth = width;
		final boolean oldWidthESet = widthESet;
		width = WIDTH_EDEFAULT;
		widthESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetWidth() {
		return widthESet;
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
		case VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH:
			return getWidth();
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
		case VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH:
			setWidth((Integer) newValue);
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
		case VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH:
			unsetWidth();
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
		case VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY__WIDTH:
			return isSetWidth();
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
		result.append(" (width: "); //$NON-NLS-1$
		if (widthESet) {
			result.append(width);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (styleProperty == null) {
			return false;
		}
		if (eClass() != styleProperty.eClass()) {
			return false;
		}

		final VTLabelWidthStyleProperty other = VTLabelWidthStyleProperty.class.cast(styleProperty);

		if (isSetWidth()) {
			if (!other.isSetWidth()) {
				return false;
			}
			return getWidth() == other.getWidth();
		}

		if (other.isSetWidth()) {
			return false;
		}

		return true;
	}

} // VTLabelWidthStylePropertyImpl
