/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Date Time Display Attachment</b></em>'.
 * 
 * @since 1.8
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VDateTimeDisplayAttachmentImpl#getDisplayType <em>Display
 *        Type</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VDateTimeDisplayAttachmentImpl extends VAttachmentImpl implements VDateTimeDisplayAttachment {
	/**
	 * The default value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisplayType()
	 * @generated
	 * @ordered
	 */
	protected static final DateTimeDisplayType DISPLAY_TYPE_EDEFAULT = DateTimeDisplayType.TIME_AND_DATE;

	/**
	 * The cached value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisplayType()
	 * @generated
	 * @ordered
	 */
	protected DateTimeDisplayType displayType = DISPLAY_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VDateTimeDisplayAttachmentImpl() {
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
		return VViewPackage.Literals.DATE_TIME_DISPLAY_ATTACHMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DateTimeDisplayType getDisplayType() {
		return displayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDisplayType(DateTimeDisplayType newDisplayType) {
		DateTimeDisplayType oldDisplayType = displayType;
		displayType = newDisplayType == null ? DISPLAY_TYPE_EDEFAULT : newDisplayType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE, oldDisplayType, displayType));
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
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE:
			return getDisplayType();
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
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE:
			setDisplayType((DateTimeDisplayType) newValue);
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
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE:
			setDisplayType(DISPLAY_TYPE_EDEFAULT);
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
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE:
			return displayType != DISPLAY_TYPE_EDEFAULT;
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (displayType: "); //$NON-NLS-1$
		result.append(displayType);
		result.append(')');
		return result.toString();
	}

} // VDateTimeDisplayAttachmentImpl
