/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.editor.test.model.test;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.TestFactory
 * @model kind="package"
 * @generated
 */
public interface TestPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "test";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://eclipse.com/emf/ecp/view/editor/controls/test";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.ui.view.editor.controls.test";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TestPackage eINSTANCE = org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.ComponentImpl
	 * <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.ComponentImpl
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT = 0;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.CompositeImpl
	 * <em>Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.CompositeImpl
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComposite()
	 * @generated
	 */
	int COMPOSITE = 1;

	/**
	 * The feature id for the '<em><b>Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__COMPONENT = COMPONENT__COMPONENT;

	/**
	 * The feature id for the '<em><b>Composites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__COMPOSITES = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_OPERATION_COUNT = COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.Component2Impl
	 * <em>Component2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.Component2Impl
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComponent2()
	 * @generated
	 */
	int COMPONENT2 = 2;

	/**
	 * The feature id for the '<em><b>Composites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT2__COMPOSITES = 0;

	/**
	 * The number of structural features of the '<em>Component2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT2_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Component2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT2_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component
	 * <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Component</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Component</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component#getComponent()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Component();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Composite
	 * <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Composite</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Composite
	 * @generated
	 */
	EClass getComposite();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Composite#getComposites <em>Composites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Composites</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Composite#getComposites()
	 * @see #getComposite()
	 * @generated
	 */
	EReference getComposite_Composites();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component2
	 * <em>Component2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Component2</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component2
	 * @generated
	 */
	EClass getComponent2();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component2#getComposites <em>Composites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Composites</em>'.
	 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.Component2#getComposites()
	 * @see #getComponent2()
	 * @generated
	 */
	EReference getComponent2_Composites();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestFactory getTestFactory();

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
		 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.ComponentImpl
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPONENT__COMPONENT = eINSTANCE.getComponent_Component();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.CompositeImpl <em>Composite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.CompositeImpl
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComposite()
		 * @generated
		 */
		EClass COMPOSITE = eINSTANCE.getComposite();

		/**
		 * The meta object literal for the '<em><b>Composites</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPOSITE__COMPOSITES = eINSTANCE.getComposite_Composites();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.Component2Impl <em>Component2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.Component2Impl
		 * @see org.eclipse.emf.ecp.ui.view.editor.test.model.test.impl.TestPackageImpl#getComponent2()
		 * @generated
		 */
		EClass COMPONENT2 = eINSTANCE.getComponent2();

		/**
		 * The meta object literal for the '<em><b>Composites</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPONENT2__COMPOSITES = eINSTANCE.getComponent2_Composites();

	}

} // TestPackage
