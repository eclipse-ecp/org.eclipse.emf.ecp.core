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
package org.eclipse.emf.ecp.view.template.style.tab.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tab.model.TabType;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 *
 * @since 1.8
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getType
 *        <em>Type</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getOkImageURL <em>Ok
 *        Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getInfoImageURL
 *        <em>Info Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getWarningImageURL
 *        <em>Warning Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getErrorImageURL
 *        <em>Error Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl#getCancelImageURL
 *        <em>Cancel Image URL</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VTTabStylePropertyImpl extends MinimalEObjectImpl.Container implements VTTabStyleProperty {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TabType TYPE_EDEFAULT = TabType.BOTTOM;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TabType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOkImageURL() <em>Ok Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getOkImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String OK_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOkImageURL() <em>Ok Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getOkImageURL()
	 * @generated
	 * @ordered
	 */
	protected String okImageURL = OK_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoImageURL() <em>Info Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getInfoImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoImageURL() <em>Info Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getInfoImageURL()
	 * @generated
	 * @ordered
	 */
	protected String infoImageURL = INFO_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getWarningImageURL() <em>Warning Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getWarningImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String WARNING_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWarningImageURL() <em>Warning Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getWarningImageURL()
	 * @generated
	 * @ordered
	 */
	protected String warningImageURL = WARNING_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorImageURL() <em>Error Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getErrorImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorImageURL() <em>Error Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getErrorImageURL()
	 * @generated
	 * @ordered
	 */
	protected String errorImageURL = ERROR_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCancelImageURL() <em>Cancel Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getCancelImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String CANCEL_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCancelImageURL() <em>Cancel Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @see #getCancelImageURL()
	 * @generated
	 * @ordered
	 */
	protected String cancelImageURL = CANCEL_IMAGE_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTTabStylePropertyImpl() {
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
		return VTTabPackage.Literals.TAB_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TabType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setType(TabType newType) {
		final TabType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getOkImageURL() {
		return okImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOkImageURL(String newOkImageURL) {
		final String oldOkImageURL = okImageURL;
		okImageURL = newOkImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__OK_IMAGE_URL,
				oldOkImageURL, okImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInfoImageURL() {
		return infoImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInfoImageURL(String newInfoImageURL) {
		final String oldInfoImageURL = infoImageURL;
		infoImageURL = newInfoImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__INFO_IMAGE_URL,
				oldInfoImageURL, infoImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getWarningImageURL() {
		return warningImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarningImageURL(String newWarningImageURL) {
		final String oldWarningImageURL = warningImageURL;
		warningImageURL = newWarningImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__WARNING_IMAGE_URL,
				oldWarningImageURL, warningImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getErrorImageURL() {
		return errorImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setErrorImageURL(String newErrorImageURL) {
		final String oldErrorImageURL = errorImageURL;
		errorImageURL = newErrorImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__ERROR_IMAGE_URL,
				oldErrorImageURL, errorImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCancelImageURL() {
		return cancelImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCancelImageURL(String newCancelImageURL) {
		final String oldCancelImageURL = cancelImageURL;
		cancelImageURL = newCancelImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTabPackage.TAB_STYLE_PROPERTY__CANCEL_IMAGE_URL,
				oldCancelImageURL, cancelImageURL));
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
		case VTTabPackage.TAB_STYLE_PROPERTY__TYPE:
			return getType();
		case VTTabPackage.TAB_STYLE_PROPERTY__OK_IMAGE_URL:
			return getOkImageURL();
		case VTTabPackage.TAB_STYLE_PROPERTY__INFO_IMAGE_URL:
			return getInfoImageURL();
		case VTTabPackage.TAB_STYLE_PROPERTY__WARNING_IMAGE_URL:
			return getWarningImageURL();
		case VTTabPackage.TAB_STYLE_PROPERTY__ERROR_IMAGE_URL:
			return getErrorImageURL();
		case VTTabPackage.TAB_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			return getCancelImageURL();
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
		case VTTabPackage.TAB_STYLE_PROPERTY__TYPE:
			setType((TabType) newValue);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__OK_IMAGE_URL:
			setOkImageURL((String) newValue);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__INFO_IMAGE_URL:
			setInfoImageURL((String) newValue);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__WARNING_IMAGE_URL:
			setWarningImageURL((String) newValue);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__ERROR_IMAGE_URL:
			setErrorImageURL((String) newValue);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			setCancelImageURL((String) newValue);
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
		case VTTabPackage.TAB_STYLE_PROPERTY__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__OK_IMAGE_URL:
			setOkImageURL(OK_IMAGE_URL_EDEFAULT);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__INFO_IMAGE_URL:
			setInfoImageURL(INFO_IMAGE_URL_EDEFAULT);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__WARNING_IMAGE_URL:
			setWarningImageURL(WARNING_IMAGE_URL_EDEFAULT);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__ERROR_IMAGE_URL:
			setErrorImageURL(ERROR_IMAGE_URL_EDEFAULT);
			return;
		case VTTabPackage.TAB_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			setCancelImageURL(CANCEL_IMAGE_URL_EDEFAULT);
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
		case VTTabPackage.TAB_STYLE_PROPERTY__TYPE:
			return type != TYPE_EDEFAULT;
		case VTTabPackage.TAB_STYLE_PROPERTY__OK_IMAGE_URL:
			return OK_IMAGE_URL_EDEFAULT == null ? okImageURL != null : !OK_IMAGE_URL_EDEFAULT.equals(okImageURL);
		case VTTabPackage.TAB_STYLE_PROPERTY__INFO_IMAGE_URL:
			return INFO_IMAGE_URL_EDEFAULT == null ? infoImageURL != null
				: !INFO_IMAGE_URL_EDEFAULT.equals(infoImageURL);
		case VTTabPackage.TAB_STYLE_PROPERTY__WARNING_IMAGE_URL:
			return WARNING_IMAGE_URL_EDEFAULT == null ? warningImageURL != null
				: !WARNING_IMAGE_URL_EDEFAULT.equals(warningImageURL);
		case VTTabPackage.TAB_STYLE_PROPERTY__ERROR_IMAGE_URL:
			return ERROR_IMAGE_URL_EDEFAULT == null ? errorImageURL != null
				: !ERROR_IMAGE_URL_EDEFAULT.equals(errorImageURL);
		case VTTabPackage.TAB_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			return CANCEL_IMAGE_URL_EDEFAULT == null ? cancelImageURL != null
				: !CANCEL_IMAGE_URL_EDEFAULT.equals(cancelImageURL);
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
		result.append(" (type: "); //$NON-NLS-1$
		result.append(type);
		result.append(", okImageURL: "); //$NON-NLS-1$
		result.append(okImageURL);
		result.append(", infoImageURL: "); //$NON-NLS-1$
		result.append(infoImageURL);
		result.append(", warningImageURL: "); //$NON-NLS-1$
		result.append(warningImageURL);
		result.append(", errorImageURL: "); //$NON-NLS-1$
		result.append(errorImageURL);
		result.append(", cancelImageURL: "); //$NON-NLS-1$
		result.append(cancelImageURL);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (!VTTabStyleProperty.class.isInstance(styleProperty)) {
			return false;
		}
		return getType() == VTTabStyleProperty.class.cast(styleProperty).getType();
	}

} // VTTabStylePropertyImpl
