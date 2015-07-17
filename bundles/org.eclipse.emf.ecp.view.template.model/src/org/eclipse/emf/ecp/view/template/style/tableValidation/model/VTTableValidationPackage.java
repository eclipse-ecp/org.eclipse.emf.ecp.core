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
package org.eclipse.emf.ecp.view.template.style.tableValidation.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory
 * @model kind="package"
 * @generated
 */
public interface VTTableValidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "tableValidation"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/table/validation/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.table.validation.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTTableValidationPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationPackageImpl#getTableValidationStyleProperty()
	 * @generated
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Column Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Image Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_VALIDATION_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty
	 * <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty
	 * @generated
	 */
	EClass getTableValidationStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnWidth
	 * <em>Column Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Column Width</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnWidth()
	 * @see #getTableValidationStyleProperty()
	 * @generated
	 */
	EAttribute getTableValidationStyleProperty_ColumnWidth();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnName
	 * <em>Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Column Name</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnName()
	 * @see #getTableValidationStyleProperty()
	 * @generated
	 */
	EAttribute getTableValidationStyleProperty_ColumnName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getImagePath
	 * <em>Image Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Image Path</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getImagePath()
	 * @see #getTableValidationStyleProperty()
	 * @generated
	 */
	EAttribute getTableValidationStyleProperty_ImagePath();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTTableValidationFactory getTableValidationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl.VTTableValidationPackageImpl#getTableValidationStyleProperty()
		 * @generated
		 */
		EClass TABLE_VALIDATION_STYLE_PROPERTY = eINSTANCE.getTableValidationStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Column Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH = eINSTANCE
			.getTableValidationStyleProperty_ColumnWidth();

		/**
		 * The meta object literal for the '<em><b>Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME = eINSTANCE
			.getTableValidationStyleProperty_ColumnName();

		/**
		 * The meta object literal for the '<em><b>Image Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH = eINSTANCE.getTableValidationStyleProperty_ImagePath();

	}

} // VTTableValidationPackage
