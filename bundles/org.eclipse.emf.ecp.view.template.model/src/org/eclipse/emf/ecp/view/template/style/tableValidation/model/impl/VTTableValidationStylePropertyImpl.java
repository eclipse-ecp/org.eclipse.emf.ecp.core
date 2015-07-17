/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl#getColumnWidth
 * <em>Column Width</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl#getColumnName
 * <em>Column Name</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl#getImagePath
 * <em>Image Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTTableValidationStylePropertyImpl extends MinimalEObjectImpl.Container implements
	VTTableValidationStyleProperty {
	/**
	 * The default value of the '{@link #getColumnWidth() <em>Column Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int COLUMN_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getColumnWidth() <em>Column Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnWidth()
	 * @generated
	 * @ordered
	 */
	protected int columnWidth = COLUMN_WIDTH_EDEFAULT;

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
	 * The default value of the '{@link #getImagePath() <em>Image Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getImagePath()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImagePath() <em>Image Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getImagePath()
	 * @generated
	 * @ordered
	 */
	protected String imagePath = IMAGE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTTableValidationStylePropertyImpl() {
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
		return VTTableValidationPackage.Literals.TABLE_VALIDATION_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getColumnWidth() {
		return columnWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColumnWidth(int newColumnWidth) {
		final int oldColumnWidth = columnWidth;
		columnWidth = newColumnWidth;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH, oldColumnWidth, columnWidth));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME, oldColumnName, columnName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setImagePath(String newImagePath) {
		final String oldImagePath = imagePath;
		imagePath = newImagePath;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH, oldImagePath, imagePath));
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
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH:
			return getColumnWidth();
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME:
			return getColumnName();
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH:
			return getImagePath();
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
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH:
			setColumnWidth((Integer) newValue);
			return;
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME:
			setColumnName((String) newValue);
			return;
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH:
			setImagePath((String) newValue);
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
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH:
			setColumnWidth(COLUMN_WIDTH_EDEFAULT);
			return;
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME:
			setColumnName(COLUMN_NAME_EDEFAULT);
			return;
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH:
			setImagePath(IMAGE_PATH_EDEFAULT);
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
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH:
			return columnWidth != COLUMN_WIDTH_EDEFAULT;
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME:
			return COLUMN_NAME_EDEFAULT == null ? columnName != null : !COLUMN_NAME_EDEFAULT.equals(columnName);
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH:
			return IMAGE_PATH_EDEFAULT == null ? imagePath != null : !IMAGE_PATH_EDEFAULT.equals(imagePath);
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
		result.append(" (columnWidth: "); //$NON-NLS-1$
		result.append(columnWidth);
		result.append(", columnName: "); //$NON-NLS-1$
		result.append(columnName);
		result.append(", imagePath: "); //$NON-NLS-1$
		result.append(imagePath);
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
		if (VTTableValidationStyleProperty.class.equals(styleProperty.getClass())) {
			return false;
		}
		final VTTableValidationStyleProperty validationColumnStyleProperty = VTTableValidationStyleProperty.class
			.cast(styleProperty);
		return getColumnWidth() == validationColumnStyleProperty.getColumnWidth()
			&& getColumnName() == validationColumnStyleProperty.getColumnName()
			&& getImagePath() == validationColumnStyleProperty.getImagePath();

	}

} // VTTableValidationStylePropertyImpl
