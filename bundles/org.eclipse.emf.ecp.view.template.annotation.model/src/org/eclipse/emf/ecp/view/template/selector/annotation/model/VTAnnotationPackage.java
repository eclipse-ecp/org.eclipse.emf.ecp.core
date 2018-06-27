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
package org.eclipse.emf.ecp.view.template.selector.annotation.model;

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
 * @since 1.17
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationFactory
 * @model kind="package"
 * @generated
 */
public interface VTAnnotationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "annotation"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/selector/annotation/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.selector.annotation.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTAnnotationPackage eINSTANCE = org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl
	 * <em>Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl
	 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationPackageImpl#getAnnotationSelector()
	 * @generated
	 */
	int ANNOTATION_SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_SELECTOR__KEY = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_SELECTOR__VALUE = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_SELECTOR_FEATURE_COUNT = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_SELECTOR_OPERATION_COUNT = VTTemplatePackage.STYLE_SELECTOR_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector
	 * @generated
	 */
	EClass getAnnotationSelector();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector#getKey()
	 * @see #getAnnotationSelector()
	 * @generated
	 */
	EAttribute getAnnotationSelector_Key();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector#getValue
	 * <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector#getValue()
	 * @see #getAnnotationSelector()
	 * @generated
	 */
	EAttribute getAnnotationSelector_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTAnnotationFactory getAnnotationFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl
		 * <em>Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl
		 * @see org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationPackageImpl#getAnnotationSelector()
		 * @generated
		 */
		EClass ANNOTATION_SELECTOR = eINSTANCE.getAnnotationSelector();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ANNOTATION_SELECTOR__KEY = eINSTANCE.getAnnotationSelector_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ANNOTATION_SELECTOR__VALUE = eINSTANCE.getAnnotationSelector_Value();

	}

} // VTAnnotationInHierarchyPackage
