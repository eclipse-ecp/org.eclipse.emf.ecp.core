/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.style.validation.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getOkColorHEX
 * <em>Ok Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getOkForegroundColorHEX
 * <em>Ok Foreground Color HEX</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getOkImageURL
 * <em>Ok Image URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getOkOverlayURL
 * <em>Ok Overlay URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getInfoColorHEX
 * <em>Info Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getInfoForegroundColorHEX
 * <em>Info Foreground Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getInfoImageURL
 * <em>Info Image URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getInfoOverlayURL
 * <em>Info Overlay URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getWarningColorHEX
 * <em>Warning Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getWarningForegroundColorHEX
 * <em>Warning Foreground Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getWarningImageURL
 * <em>Warning Image URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getWarningOverlayURL
 * <em>Warning Overlay URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getErrorColorHEX
 * <em>Error Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getErrorForegroundColorHEX
 * <em>Error Foreground Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getErrorImageURL
 * <em>Error Image URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getErrorOverlayURL
 * <em>Error Overlay URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getCancelColorHEX
 * <em>Cancel Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getCancelForegroundColorHEX
 * <em>Cancel Foreground Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getCancelImageURL
 * <em>Cancel Image URL</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl#getCancelOverlayURL
 * <em>Cancel Overlay URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTValidationStylePropertyImpl extends MinimalEObjectImpl.Container implements VTValidationStyleProperty {
	/**
	 * The default value of the '{@link #getOkColorHEX() <em>Ok Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String OK_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOkColorHEX() <em>Ok Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String okColorHEX = OK_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getOkForegroundColorHEX() <em>Ok Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String OK_FOREGROUND_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOkForegroundColorHEX() <em>Ok Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String okForegroundColorHEX = OK_FOREGROUND_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getOkImageURL() <em>Ok Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String OK_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOkImageURL() <em>Ok Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkImageURL()
	 * @generated
	 * @ordered
	 */
	protected String okImageURL = OK_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getOkOverlayURL() <em>Ok Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected static final String OK_OVERLAY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOkOverlayURL() <em>Ok Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOkOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected String okOverlayURL = OK_OVERLAY_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoColorHEX() <em>Info Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoColorHEX() <em>Info Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String infoColorHEX = INFO_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoForegroundColorHEX() <em>Info Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_FOREGROUND_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoForegroundColorHEX() <em>Info Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String infoForegroundColorHEX = INFO_FOREGROUND_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoImageURL() <em>Info Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoImageURL() <em>Info Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoImageURL()
	 * @generated
	 * @ordered
	 */
	protected String infoImageURL = INFO_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoOverlayURL() <em>Info Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_OVERLAY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoOverlayURL() <em>Info Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInfoOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected String infoOverlayURL = INFO_OVERLAY_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getWarningColorHEX() <em>Warning Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String WARNING_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWarningColorHEX() <em>Warning Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String warningColorHEX = WARNING_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getWarningForegroundColorHEX() <em>Warning Foreground Color HEX</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String WARNING_FOREGROUND_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWarningForegroundColorHEX() <em>Warning Foreground Color HEX</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String warningForegroundColorHEX = WARNING_FOREGROUND_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getWarningImageURL() <em>Warning Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String WARNING_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWarningImageURL() <em>Warning Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningImageURL()
	 * @generated
	 * @ordered
	 */
	protected String warningImageURL = WARNING_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getWarningOverlayURL() <em>Warning Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected static final String WARNING_OVERLAY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWarningOverlayURL() <em>Warning Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWarningOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected String warningOverlayURL = WARNING_OVERLAY_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorColorHEX() <em>Error Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorColorHEX() <em>Error Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String errorColorHEX = ERROR_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorForegroundColorHEX() <em>Error Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_FOREGROUND_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorForegroundColorHEX() <em>Error Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String errorForegroundColorHEX = ERROR_FOREGROUND_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorImageURL() <em>Error Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorImageURL() <em>Error Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorImageURL()
	 * @generated
	 * @ordered
	 */
	protected String errorImageURL = ERROR_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorOverlayURL() <em>Error Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_OVERLAY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorOverlayURL() <em>Error Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected String errorOverlayURL = ERROR_OVERLAY_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCancelColorHEX() <em>Cancel Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String CANCEL_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCancelColorHEX() <em>Cancel Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String cancelColorHEX = CANCEL_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getCancelForegroundColorHEX() <em>Cancel Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String CANCEL_FOREGROUND_COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCancelForegroundColorHEX() <em>Cancel Foreground Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelForegroundColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String cancelForegroundColorHEX = CANCEL_FOREGROUND_COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getCancelImageURL() <em>Cancel Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String CANCEL_IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCancelImageURL() <em>Cancel Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelImageURL()
	 * @generated
	 * @ordered
	 */
	protected String cancelImageURL = CANCEL_IMAGE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCancelOverlayURL() <em>Cancel Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected static final String CANCEL_OVERLAY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCancelOverlayURL() <em>Cancel Overlay URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCancelOverlayURL()
	 * @generated
	 * @ordered
	 */
	protected String cancelOverlayURL = CANCEL_OVERLAY_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTValidationStylePropertyImpl() {
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
		return VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getOkColorHEX() {
		return okColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOkColorHEX(String newOkColorHEX) {
		final String oldOkColorHEX = okColorHEX;
		okColorHEX = newOkColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX, oldOkColorHEX, okColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getOkForegroundColorHEX() {
		return okForegroundColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOkForegroundColorHEX(String newOkForegroundColorHEX) {
		final String oldOkForegroundColorHEX = okForegroundColorHEX;
		okForegroundColorHEX = newOkForegroundColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX, oldOkForegroundColorHEX,
				okForegroundColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getOkImageURL() {
		return okImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOkImageURL(String newOkImageURL) {
		final String oldOkImageURL = okImageURL;
		okImageURL = newOkImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL, oldOkImageURL, okImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getOkOverlayURL() {
		return okOverlayURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOkOverlayURL(String newOkOverlayURL) {
		final String oldOkOverlayURL = okOverlayURL;
		okOverlayURL = newOkOverlayURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL, oldOkOverlayURL, okOverlayURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInfoColorHEX() {
		return infoColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInfoColorHEX(String newInfoColorHEX) {
		final String oldInfoColorHEX = infoColorHEX;
		infoColorHEX = newInfoColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX, oldInfoColorHEX, infoColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInfoForegroundColorHEX() {
		return infoForegroundColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInfoForegroundColorHEX(String newInfoForegroundColorHEX) {
		final String oldInfoForegroundColorHEX = infoForegroundColorHEX;
		infoForegroundColorHEX = newInfoForegroundColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX, oldInfoForegroundColorHEX,
				infoForegroundColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInfoImageURL() {
		return infoImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInfoImageURL(String newInfoImageURL) {
		final String oldInfoImageURL = infoImageURL;
		infoImageURL = newInfoImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL, oldInfoImageURL, infoImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInfoOverlayURL() {
		return infoOverlayURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInfoOverlayURL(String newInfoOverlayURL) {
		final String oldInfoOverlayURL = infoOverlayURL;
		infoOverlayURL = newInfoOverlayURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL, oldInfoOverlayURL, infoOverlayURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getWarningColorHEX() {
		return warningColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarningColorHEX(String newWarningColorHEX) {
		final String oldWarningColorHEX = warningColorHEX;
		warningColorHEX = newWarningColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX, oldWarningColorHEX, warningColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getWarningForegroundColorHEX() {
		return warningForegroundColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarningForegroundColorHEX(String newWarningForegroundColorHEX) {
		final String oldWarningForegroundColorHEX = warningForegroundColorHEX;
		warningForegroundColorHEX = newWarningForegroundColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX,
				oldWarningForegroundColorHEX, warningForegroundColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getWarningImageURL() {
		return warningImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarningImageURL(String newWarningImageURL) {
		final String oldWarningImageURL = warningImageURL;
		warningImageURL = newWarningImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL, oldWarningImageURL, warningImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getWarningOverlayURL() {
		return warningOverlayURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarningOverlayURL(String newWarningOverlayURL) {
		final String oldWarningOverlayURL = warningOverlayURL;
		warningOverlayURL = newWarningOverlayURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL, oldWarningOverlayURL,
				warningOverlayURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getErrorColorHEX() {
		return errorColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setErrorColorHEX(String newErrorColorHEX) {
		final String oldErrorColorHEX = errorColorHEX;
		errorColorHEX = newErrorColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX, oldErrorColorHEX, errorColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getErrorForegroundColorHEX() {
		return errorForegroundColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setErrorForegroundColorHEX(String newErrorForegroundColorHEX) {
		final String oldErrorForegroundColorHEX = errorForegroundColorHEX;
		errorForegroundColorHEX = newErrorForegroundColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX, oldErrorForegroundColorHEX,
				errorForegroundColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getErrorImageURL() {
		return errorImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setErrorImageURL(String newErrorImageURL) {
		final String oldErrorImageURL = errorImageURL;
		errorImageURL = newErrorImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL, oldErrorImageURL, errorImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getErrorOverlayURL() {
		return errorOverlayURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setErrorOverlayURL(String newErrorOverlayURL) {
		final String oldErrorOverlayURL = errorOverlayURL;
		errorOverlayURL = newErrorOverlayURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL, oldErrorOverlayURL, errorOverlayURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCancelColorHEX() {
		return cancelColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCancelColorHEX(String newCancelColorHEX) {
		final String oldCancelColorHEX = cancelColorHEX;
		cancelColorHEX = newCancelColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX, oldCancelColorHEX, cancelColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCancelForegroundColorHEX() {
		return cancelForegroundColorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCancelForegroundColorHEX(String newCancelForegroundColorHEX) {
		final String oldCancelForegroundColorHEX = cancelForegroundColorHEX;
		cancelForegroundColorHEX = newCancelForegroundColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX, oldCancelForegroundColorHEX,
				cancelForegroundColorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCancelImageURL() {
		return cancelImageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCancelImageURL(String newCancelImageURL) {
		final String oldCancelImageURL = cancelImageURL;
		cancelImageURL = newCancelImageURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL, oldCancelImageURL, cancelImageURL));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCancelOverlayURL() {
		return cancelOverlayURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCancelOverlayURL(String newCancelOverlayURL) {
		final String oldCancelOverlayURL = cancelOverlayURL;
		cancelOverlayURL = newCancelOverlayURL;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL, oldCancelOverlayURL,
				cancelOverlayURL));
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
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX:
			return getOkColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX:
			return getOkForegroundColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL:
			return getOkImageURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL:
			return getOkOverlayURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX:
			return getInfoColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX:
			return getInfoForegroundColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL:
			return getInfoImageURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL:
			return getInfoOverlayURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX:
			return getWarningColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX:
			return getWarningForegroundColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL:
			return getWarningImageURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL:
			return getWarningOverlayURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX:
			return getErrorColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX:
			return getErrorForegroundColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL:
			return getErrorImageURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL:
			return getErrorOverlayURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX:
			return getCancelColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX:
			return getCancelForegroundColorHEX();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			return getCancelImageURL();
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL:
			return getCancelOverlayURL();
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
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX:
			setOkColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX:
			setOkForegroundColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL:
			setOkImageURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL:
			setOkOverlayURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX:
			setInfoColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX:
			setInfoForegroundColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL:
			setInfoImageURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL:
			setInfoOverlayURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX:
			setWarningColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX:
			setWarningForegroundColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL:
			setWarningImageURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL:
			setWarningOverlayURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX:
			setErrorColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX:
			setErrorForegroundColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL:
			setErrorImageURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL:
			setErrorOverlayURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX:
			setCancelColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX:
			setCancelForegroundColorHEX((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			setCancelImageURL((String) newValue);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL:
			setCancelOverlayURL((String) newValue);
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
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX:
			setOkColorHEX(OK_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX:
			setOkForegroundColorHEX(OK_FOREGROUND_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL:
			setOkImageURL(OK_IMAGE_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL:
			setOkOverlayURL(OK_OVERLAY_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX:
			setInfoColorHEX(INFO_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX:
			setInfoForegroundColorHEX(INFO_FOREGROUND_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL:
			setInfoImageURL(INFO_IMAGE_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL:
			setInfoOverlayURL(INFO_OVERLAY_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX:
			setWarningColorHEX(WARNING_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX:
			setWarningForegroundColorHEX(WARNING_FOREGROUND_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL:
			setWarningImageURL(WARNING_IMAGE_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL:
			setWarningOverlayURL(WARNING_OVERLAY_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX:
			setErrorColorHEX(ERROR_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX:
			setErrorForegroundColorHEX(ERROR_FOREGROUND_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL:
			setErrorImageURL(ERROR_IMAGE_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL:
			setErrorOverlayURL(ERROR_OVERLAY_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX:
			setCancelColorHEX(CANCEL_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX:
			setCancelForegroundColorHEX(CANCEL_FOREGROUND_COLOR_HEX_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			setCancelImageURL(CANCEL_IMAGE_URL_EDEFAULT);
			return;
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL:
			setCancelOverlayURL(CANCEL_OVERLAY_URL_EDEFAULT);
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
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX:
			return OK_COLOR_HEX_EDEFAULT == null ? okColorHEX != null : !OK_COLOR_HEX_EDEFAULT.equals(okColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX:
			return OK_FOREGROUND_COLOR_HEX_EDEFAULT == null ? okForegroundColorHEX != null
				: !OK_FOREGROUND_COLOR_HEX_EDEFAULT.equals(okForegroundColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL:
			return OK_IMAGE_URL_EDEFAULT == null ? okImageURL != null : !OK_IMAGE_URL_EDEFAULT.equals(okImageURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL:
			return OK_OVERLAY_URL_EDEFAULT == null ? okOverlayURL != null
				: !OK_OVERLAY_URL_EDEFAULT.equals(okOverlayURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX:
			return INFO_COLOR_HEX_EDEFAULT == null ? infoColorHEX != null
				: !INFO_COLOR_HEX_EDEFAULT.equals(infoColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX:
			return INFO_FOREGROUND_COLOR_HEX_EDEFAULT == null ? infoForegroundColorHEX != null
				: !INFO_FOREGROUND_COLOR_HEX_EDEFAULT.equals(infoForegroundColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL:
			return INFO_IMAGE_URL_EDEFAULT == null ? infoImageURL != null
				: !INFO_IMAGE_URL_EDEFAULT.equals(infoImageURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL:
			return INFO_OVERLAY_URL_EDEFAULT == null ? infoOverlayURL != null
				: !INFO_OVERLAY_URL_EDEFAULT.equals(infoOverlayURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX:
			return WARNING_COLOR_HEX_EDEFAULT == null ? warningColorHEX != null
				: !WARNING_COLOR_HEX_EDEFAULT.equals(warningColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX:
			return WARNING_FOREGROUND_COLOR_HEX_EDEFAULT == null ? warningForegroundColorHEX != null
				: !WARNING_FOREGROUND_COLOR_HEX_EDEFAULT.equals(warningForegroundColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL:
			return WARNING_IMAGE_URL_EDEFAULT == null ? warningImageURL != null
				: !WARNING_IMAGE_URL_EDEFAULT.equals(warningImageURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL:
			return WARNING_OVERLAY_URL_EDEFAULT == null ? warningOverlayURL != null
				: !WARNING_OVERLAY_URL_EDEFAULT.equals(warningOverlayURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX:
			return ERROR_COLOR_HEX_EDEFAULT == null ? errorColorHEX != null
				: !ERROR_COLOR_HEX_EDEFAULT.equals(errorColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX:
			return ERROR_FOREGROUND_COLOR_HEX_EDEFAULT == null ? errorForegroundColorHEX != null
				: !ERROR_FOREGROUND_COLOR_HEX_EDEFAULT.equals(errorForegroundColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL:
			return ERROR_IMAGE_URL_EDEFAULT == null ? errorImageURL != null
				: !ERROR_IMAGE_URL_EDEFAULT.equals(errorImageURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL:
			return ERROR_OVERLAY_URL_EDEFAULT == null ? errorOverlayURL != null
				: !ERROR_OVERLAY_URL_EDEFAULT.equals(errorOverlayURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX:
			return CANCEL_COLOR_HEX_EDEFAULT == null ? cancelColorHEX != null
				: !CANCEL_COLOR_HEX_EDEFAULT.equals(cancelColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX:
			return CANCEL_FOREGROUND_COLOR_HEX_EDEFAULT == null ? cancelForegroundColorHEX != null
				: !CANCEL_FOREGROUND_COLOR_HEX_EDEFAULT.equals(cancelForegroundColorHEX);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL:
			return CANCEL_IMAGE_URL_EDEFAULT == null ? cancelImageURL != null
				: !CANCEL_IMAGE_URL_EDEFAULT.equals(cancelImageURL);
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL:
			return CANCEL_OVERLAY_URL_EDEFAULT == null ? cancelOverlayURL != null
				: !CANCEL_OVERLAY_URL_EDEFAULT.equals(cancelOverlayURL);
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
		result.append(" (okColorHEX: "); //$NON-NLS-1$
		result.append(okColorHEX);
		result.append(", okForegroundColorHEX: "); //$NON-NLS-1$
		result.append(okForegroundColorHEX);
		result.append(", okImageURL: "); //$NON-NLS-1$
		result.append(okImageURL);
		result.append(", okOverlayURL: "); //$NON-NLS-1$
		result.append(okOverlayURL);
		result.append(", infoColorHEX: "); //$NON-NLS-1$
		result.append(infoColorHEX);
		result.append(", infoForegroundColorHEX: "); //$NON-NLS-1$
		result.append(infoForegroundColorHEX);
		result.append(", infoImageURL: "); //$NON-NLS-1$
		result.append(infoImageURL);
		result.append(", infoOverlayURL: "); //$NON-NLS-1$
		result.append(infoOverlayURL);
		result.append(", warningColorHEX: "); //$NON-NLS-1$
		result.append(warningColorHEX);
		result.append(", warningForegroundColorHEX: "); //$NON-NLS-1$
		result.append(warningForegroundColorHEX);
		result.append(", warningImageURL: "); //$NON-NLS-1$
		result.append(warningImageURL);
		result.append(", warningOverlayURL: "); //$NON-NLS-1$
		result.append(warningOverlayURL);
		result.append(", errorColorHEX: "); //$NON-NLS-1$
		result.append(errorColorHEX);
		result.append(", errorForegroundColorHEX: "); //$NON-NLS-1$
		result.append(errorForegroundColorHEX);
		result.append(", errorImageURL: "); //$NON-NLS-1$
		result.append(errorImageURL);
		result.append(", errorOverlayURL: "); //$NON-NLS-1$
		result.append(errorOverlayURL);
		result.append(", cancelColorHEX: "); //$NON-NLS-1$
		result.append(cancelColorHEX);
		result.append(", cancelForegroundColorHEX: "); //$NON-NLS-1$
		result.append(cancelForegroundColorHEX);
		result.append(", cancelImageURL: "); //$NON-NLS-1$
		result.append(cancelImageURL);
		result.append(", cancelOverlayURL: "); //$NON-NLS-1$
		result.append(cancelOverlayURL);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty#equalStyles(org.eclipse.emf.ecp.view.template.model.VTStyleProperty)
	 */
	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		return EMFUtils.filteredEquals(this, styleProperty);
	}

} // VTValidationStylePropertyImpl
