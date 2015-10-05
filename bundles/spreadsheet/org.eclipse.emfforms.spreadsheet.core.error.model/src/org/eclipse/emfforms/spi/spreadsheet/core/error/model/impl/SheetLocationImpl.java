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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Excel Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl#getSheet <em>Sheet</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl#getColumn <em>Column</em>}
 * </li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl#getRow <em>Row</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl#getColumnName
 * <em>Column Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl#isValid <em>Valid</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SheetLocationImpl extends MinimalEObjectImpl.Container implements SheetLocation {
	/**
	 * The default value of the '{@link #getSheet() <em>Sheet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSheet()
	 * @generated
	 * @ordered
	 */
	protected static final String SHEET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSheet() <em>Sheet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSheet()
	 * @generated
	 * @ordered
	 */
	protected String sheet = SHEET_EDEFAULT;

	/**
	 * The default value of the '{@link #getColumn() <em>Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected static final int COLUMN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected int column = COLUMN_EDEFAULT;

	/**
	 * The default value of the '{@link #getRow() <em>Row</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRow()
	 * @generated
	 * @ordered
	 */
	protected static final int ROW_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRow() <em>Row</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRow()
	 * @generated
	 * @ordered
	 */
	protected int row = ROW_EDEFAULT;

	/**
	 * The default value of the '{@link #getColumnName() <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColumnName() <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnName()
	 * @generated
	 * @ordered
	 */
	protected String columnName = COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isValid()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALID_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isValid()
	 * @generated
	 * @ordered
	 */
	protected boolean valid = VALID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SheetLocationImpl() {
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
		return ErrorPackage.Literals.SHEET_LOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getSheet() {
		return sheet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSheet(String newSheet) {
		final String oldSheet = sheet;
		sheet = newSheet;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SHEET_LOCATION__SHEET, oldSheet, sheet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getColumn() {
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColumn(int newColumn) {
		final int oldColumn = column;
		column = newColumn;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, ErrorPackage.SHEET_LOCATION__COLUMN, oldColumn, column));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getRow() {
		return row;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRow(int newRow) {
		final int oldRow = row;
		row = newRow;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SHEET_LOCATION__ROW, oldRow, row));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getColumnName() {
		return columnName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColumnName(String newColumnName) {
		final String oldColumnName = columnName;
		columnName = newColumnName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SHEET_LOCATION__COLUMN_NAME,
				oldColumnName, columnName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isValid() {
		return valid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setValid(boolean newValid) {
		final boolean oldValid = valid;
		valid = newValid;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ErrorPackage.SHEET_LOCATION__VALID, oldValid, valid));
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
		case ErrorPackage.SHEET_LOCATION__SHEET:
			return getSheet();
		case ErrorPackage.SHEET_LOCATION__COLUMN:
			return getColumn();
		case ErrorPackage.SHEET_LOCATION__ROW:
			return getRow();
		case ErrorPackage.SHEET_LOCATION__COLUMN_NAME:
			return getColumnName();
		case ErrorPackage.SHEET_LOCATION__VALID:
			return isValid();
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
		case ErrorPackage.SHEET_LOCATION__SHEET:
			setSheet((String) newValue);
			return;
		case ErrorPackage.SHEET_LOCATION__COLUMN:
			setColumn((Integer) newValue);
			return;
		case ErrorPackage.SHEET_LOCATION__ROW:
			setRow((Integer) newValue);
			return;
		case ErrorPackage.SHEET_LOCATION__COLUMN_NAME:
			setColumnName((String) newValue);
			return;
		case ErrorPackage.SHEET_LOCATION__VALID:
			setValid((Boolean) newValue);
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
		case ErrorPackage.SHEET_LOCATION__SHEET:
			setSheet(SHEET_EDEFAULT);
			return;
		case ErrorPackage.SHEET_LOCATION__COLUMN:
			setColumn(COLUMN_EDEFAULT);
			return;
		case ErrorPackage.SHEET_LOCATION__ROW:
			setRow(ROW_EDEFAULT);
			return;
		case ErrorPackage.SHEET_LOCATION__COLUMN_NAME:
			setColumnName(COLUMN_NAME_EDEFAULT);
			return;
		case ErrorPackage.SHEET_LOCATION__VALID:
			setValid(VALID_EDEFAULT);
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
		case ErrorPackage.SHEET_LOCATION__SHEET:
			return SHEET_EDEFAULT == null ? sheet != null : !SHEET_EDEFAULT.equals(sheet);
		case ErrorPackage.SHEET_LOCATION__COLUMN:
			return column != COLUMN_EDEFAULT;
		case ErrorPackage.SHEET_LOCATION__ROW:
			return row != ROW_EDEFAULT;
		case ErrorPackage.SHEET_LOCATION__COLUMN_NAME:
			return COLUMN_NAME_EDEFAULT == null ? columnName != null : !COLUMN_NAME_EDEFAULT.equals(columnName);
		case ErrorPackage.SHEET_LOCATION__VALID:
			return valid != VALID_EDEFAULT;
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
		result.append(" (sheet: "); //$NON-NLS-1$
		result.append(sheet);
		result.append(", column: "); //$NON-NLS-1$
		result.append(column);
		result.append(", row: "); //$NON-NLS-1$
		result.append(row);
		result.append(", columnName: "); //$NON-NLS-1$
		result.append(columnName);
		result.append(", valid: "); //$NON-NLS-1$
		result.append(valid);
		result.append(')');
		return result.toString();
	}

} // ExcelLocationImpl
