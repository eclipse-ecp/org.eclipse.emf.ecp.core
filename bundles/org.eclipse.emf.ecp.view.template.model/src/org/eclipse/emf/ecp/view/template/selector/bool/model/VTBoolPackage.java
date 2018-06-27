/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.bool.model;

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
 * @since 1.17
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.VTBoolFactory
 * @model kind="package"
 * @generated
 */
public interface VTBoolPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "bool"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/selector/bool/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.selector.bool.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTBoolPackage eINSTANCE = org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTBoolPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTAndSelectorImpl
	 * <em>And Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTAndSelectorImpl
	 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTBoolPackageImpl#getAndSelector()
	 * @generated
	 */
	int AND_SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Selectors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int AND_SELECTOR__SELECTORS = VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS;

	/**
	 * The number of structural features of the '<em>And Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int AND_SELECTOR_FEATURE_COUNT = VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>And Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int AND_SELECTOR_OPERATION_COUNT = VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.selector.bool.model.VTAndSelector
	 * <em>And Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>And Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.VTAndSelector
	 * @generated
	 */
	EClass getAndSelector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTBoolFactory getBoolFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTAndSelectorImpl <em>And Selector</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTAndSelectorImpl
		 * @see org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTBoolPackageImpl#getAndSelector()
		 * @generated
		 */
		EClass AND_SELECTOR = eINSTANCE.getAndSelector();

	}

} // VTBoolPackage
