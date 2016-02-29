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
package org.eclipse.emf.ecp.view.template.style.tab.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @since 1.8
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabFactory
 * @model kind="package"
 * @generated
 */
public interface VTTabPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "tab"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/tab/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.tab.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTTabPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabPackageImpl#getTabStyleProperty()
	 * @generated
	 */
	int TAB_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TAB_STYLE_PROPERTY__TYPE = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TAB_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TAB_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.TabType <em>Type</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabPackageImpl#getTabType()
	 * @generated
	 */
	int TAB_TYPE = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty
	 * <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty
	 * @generated
	 */
	EClass getTabStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType()
	 * @see #getTabStyleProperty()
	 * @generated
	 */
	EAttribute getTabStyleProperty_Type();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.template.style.tab.model.TabType <em>Type</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @generated
	 */
	EEnum getTabType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTTabFactory getTabFactory();

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
		 * {@link org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl <em>Style Property</em>}
		 * ' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabPackageImpl#getTabStyleProperty()
		 * @generated
		 */
		EClass TAB_STYLE_PROPERTY = eINSTANCE.getTabStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TAB_STYLE_PROPERTY__TYPE = eINSTANCE.getTabStyleProperty_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.TabType
		 * <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
		 * @see org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabPackageImpl#getTabType()
		 * @generated
		 */
		EEnum TAB_TYPE = eINSTANCE.getTabType();

	}

} // VTTabPackage
