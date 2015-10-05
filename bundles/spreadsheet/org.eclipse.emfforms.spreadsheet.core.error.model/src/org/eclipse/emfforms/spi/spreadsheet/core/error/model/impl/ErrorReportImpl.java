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
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Report</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl#getSeverity <em>Severity</em>}
 * </li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl#getMessage <em>Message</em>}
 * </li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl#getEmfLocation
 * <em>Emf Location</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl#getSheetLocation
 * <em>Sheet Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorReportImpl extends MinimalEObjectImpl.Container implements ErrorReport {
	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final Severity SEVERITY_EDEFAULT = Severity.OK;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected Severity severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEmfLocation() <em>Emf Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEmfLocation()
	 * @generated
	 * @ordered
	 */
	protected EMFLocation emfLocation;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ErrorReportImpl() {
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
		return ErrorPackage.Literals.ERROR_REPORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSeverity(Severity newSeverity) {
		final Severity oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.ERROR_REPORT__SEVERITY, oldSeverity,
				severity));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMessage(String newMessage) {
		final String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, ErrorPackage.ERROR_REPORT__MESSAGE, oldMessage, message));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMFLocation getEmfLocation() {
		return emfLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetEmfLocation(EMFLocation newEmfLocation, NotificationChain msgs) {
		final EMFLocation oldEmfLocation = emfLocation;
		emfLocation = newEmfLocation;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ErrorPackage.ERROR_REPORT__EMF_LOCATION, oldEmfLocation, newEmfLocation);
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
	public void setEmfLocation(EMFLocation newEmfLocation) {
		if (newEmfLocation != emfLocation) {
			NotificationChain msgs = null;
			if (emfLocation != null) {
				msgs = ((InternalEObject) emfLocation).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.ERROR_REPORT__EMF_LOCATION, null, msgs);
			}
			if (newEmfLocation != null) {
				msgs = ((InternalEObject) newEmfLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.ERROR_REPORT__EMF_LOCATION, null, msgs);
			}
			msgs = basicSetEmfLocation(newEmfLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.ERROR_REPORT__EMF_LOCATION,
				newEmfLocation, newEmfLocation));
		}
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
				ErrorPackage.ERROR_REPORT__SHEET_LOCATION, oldSheetLocation, newSheetLocation);
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
					EOPPOSITE_FEATURE_BASE - ErrorPackage.ERROR_REPORT__SHEET_LOCATION, null, msgs);
			}
			if (newSheetLocation != null) {
				msgs = ((InternalEObject) newSheetLocation).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - ErrorPackage.ERROR_REPORT__SHEET_LOCATION, null, msgs);
			}
			msgs = basicSetSheetLocation(newSheetLocation, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.ERROR_REPORT__SHEET_LOCATION,
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ErrorPackage.ERROR_REPORT__EMF_LOCATION:
			return basicSetEmfLocation(null, msgs);
		case ErrorPackage.ERROR_REPORT__SHEET_LOCATION:
			return basicSetSheetLocation(null, msgs);
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
		case ErrorPackage.ERROR_REPORT__SEVERITY:
			return getSeverity();
		case ErrorPackage.ERROR_REPORT__MESSAGE:
			return getMessage();
		case ErrorPackage.ERROR_REPORT__EMF_LOCATION:
			return getEmfLocation();
		case ErrorPackage.ERROR_REPORT__SHEET_LOCATION:
			return getSheetLocation();
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
		case ErrorPackage.ERROR_REPORT__SEVERITY:
			setSeverity((Severity) newValue);
			return;
		case ErrorPackage.ERROR_REPORT__MESSAGE:
			setMessage((String) newValue);
			return;
		case ErrorPackage.ERROR_REPORT__EMF_LOCATION:
			setEmfLocation((EMFLocation) newValue);
			return;
		case ErrorPackage.ERROR_REPORT__SHEET_LOCATION:
			setSheetLocation((SheetLocation) newValue);
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
		case ErrorPackage.ERROR_REPORT__SEVERITY:
			setSeverity(SEVERITY_EDEFAULT);
			return;
		case ErrorPackage.ERROR_REPORT__MESSAGE:
			setMessage(MESSAGE_EDEFAULT);
			return;
		case ErrorPackage.ERROR_REPORT__EMF_LOCATION:
			setEmfLocation((EMFLocation) null);
			return;
		case ErrorPackage.ERROR_REPORT__SHEET_LOCATION:
			setSheetLocation((SheetLocation) null);
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
		case ErrorPackage.ERROR_REPORT__SEVERITY:
			return severity != SEVERITY_EDEFAULT;
		case ErrorPackage.ERROR_REPORT__MESSAGE:
			return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
		case ErrorPackage.ERROR_REPORT__EMF_LOCATION:
			return emfLocation != null;
		case ErrorPackage.ERROR_REPORT__SHEET_LOCATION:
			return sheetLocation != null;
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
		result.append(" (severity: "); //$NON-NLS-1$
		result.append(severity);
		result.append(", message: "); //$NON-NLS-1$
		result.append(message);
		result.append(')');
		return result.toString();
	}

} // ErrorReportImpl
