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
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model;

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
 *
 * @since 1.9
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory
 * @model kind="package"
 * @generated
 */
public interface VTTableStylePropertyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "tableStyleProperty"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/table/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.table.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	VTTableStylePropertyPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl <em>Table
	 * Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyPackageImpl#getTableStyleProperty()
	 * @generated
	 */
	int TABLE_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Minimum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Maximum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Show Validation Summary Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enable Sorting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY__ENABLE_SORTING = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Visible Lines</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY__VISIBLE_LINES = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Table Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Table Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty <em>Table Style
	 * Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty
	 * @generated
	 */
	EClass getTableStyleProperty();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Minimum Height</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight()
	 * @see #getTableStyleProperty()
	 * @generated
	 */
	EAttribute getTableStyleProperty_MinimumHeight();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Maximum Height</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight()
	 * @see #getTableStyleProperty()
	 * @generated
	 */
	EAttribute getTableStyleProperty_MaximumHeight();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isShowValidationSummaryTooltip
	 * <em>Show Validation Summary Tooltip</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Validation Summary Tooltip</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isShowValidationSummaryTooltip()
	 * @see #getTableStyleProperty()
	 * @generated
	 */
	EAttribute getTableStyleProperty_ShowValidationSummaryTooltip();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isEnableSorting
	 * <em>Enable Sorting</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable Sorting</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isEnableSorting()
	 * @see #getTableStyleProperty()
	 * @generated
	 */
	EAttribute getTableStyleProperty_EnableSorting();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines
	 * <em>Visible Lines</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible Lines</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines()
	 * @see #getTableStyleProperty()
	 * @generated
	 */
	EAttribute getTableStyleProperty_VisibleLines();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTTableStylePropertyFactory getTableStylePropertyFactory();

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
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl
		 * <em>Table Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyPackageImpl#getTableStyleProperty()
		 * @generated
		 */
		EClass TABLE_STYLE_PROPERTY = eINSTANCE.getTableStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Minimum Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT = eINSTANCE.getTableStyleProperty_MinimumHeight();

		/**
		 * The meta object literal for the '<em><b>Maximum Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT = eINSTANCE.getTableStyleProperty_MaximumHeight();

		/**
		 * The meta object literal for the '<em><b>Show Validation Summary Tooltip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.12
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP = eINSTANCE
			.getTableStyleProperty_ShowValidationSummaryTooltip();

		/**
		 * The meta object literal for the '<em><b>Enable Sorting</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.12
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_STYLE_PROPERTY__ENABLE_SORTING = eINSTANCE.getTableStyleProperty_EnableSorting();

		/**
		 * The meta object literal for the '<em><b>Visible Lines</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.13
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_STYLE_PROPERTY__VISIBLE_LINES = eINSTANCE.getTableStyleProperty_VisibleLines();

	}

} // VTTableStylePropertyPackage
