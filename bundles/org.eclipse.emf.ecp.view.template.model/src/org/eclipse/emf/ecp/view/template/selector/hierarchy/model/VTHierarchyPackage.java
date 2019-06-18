/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.hierarchy.model;

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
 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyFactory
 * @model kind="package"
 * @generated
 */
public interface VTHierarchyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "hierarchy"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/selector/hierarchy/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.selector.hierarchy.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTHierarchyPackage eINSTANCE = org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchyPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchySelectorImpl
	 * <em>Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchySelectorImpl
	 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchyPackageImpl#getHierarchySelector()
	 * @generated
	 */
	int HIERARCHY_SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Selector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_SELECTOR__SELECTOR = VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR;

	/**
	 * The number of structural features of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_SELECTOR_FEATURE_COUNT = VTTemplatePackage.STYLE_SELECTOR_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_SELECTOR_OPERATION_COUNT = VTTemplatePackage.STYLE_SELECTOR_CONTAINER_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector
	 * @generated
	 */
	EClass getHierarchySelector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTHierarchyFactory getHierarchyFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchySelectorImpl
		 * <em>Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchySelectorImpl
		 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchyPackageImpl#getHierarchySelector()
		 * @generated
		 */
		EClass HIERARCHY_SELECTOR = eINSTANCE.getHierarchySelector();

	}

} // VTHierarchyPackage
