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
package org.eclipse.emf.ecp.view.template.style.keybinding.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
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
 * @since 1.18
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingFactory
 * @model kind="package"
 * @generated
 *
 */
public interface VTKeybindingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "keybinding"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/keybinding/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.keybinding.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTKeybindingPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingsImpl <em>Key Bindings</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingsImpl
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingPackageImpl#getKeyBindings()
	 * @generated
	 */
	int KEY_BINDINGS = 0;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDINGS__BINDINGS = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Key Bindings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDINGS_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Key Bindings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDINGS_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingImpl
	 * <em>Key Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingImpl
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingPackageImpl#getKeyBinding()
	 * @generated
	 */
	int KEY_BINDING = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDING__ID = 0;

	/**
	 * The feature id for the '<em><b>Key Sequence</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDING__KEY_SEQUENCE = 1;

	/**
	 * The number of structural features of the '<em>Key Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Key Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_BINDING_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings
	 * <em>Key Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Key Bindings</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings
	 * @generated
	 */
	EClass getKeyBindings();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings#getBindings()
	 * @see #getKeyBindings()
	 * @generated
	 */
	EReference getKeyBindings_Bindings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding
	 * <em>Key Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Key Binding</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding
	 * @generated
	 */
	EClass getKeyBinding();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getId()
	 * @see #getKeyBinding()
	 * @generated
	 */
	EAttribute getKeyBinding_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getKeySequence <em>Key
	 * Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Key Sequence</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getKeySequence()
	 * @see #getKeyBinding()
	 * @generated
	 */
	EAttribute getKeyBinding_KeySequence();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTKeybindingFactory getKeybindingFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingsImpl <em>Key
		 * Bindings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingsImpl
		 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingPackageImpl#getKeyBindings()
		 * @generated
		 */
		EClass KEY_BINDINGS = eINSTANCE.getKeyBindings();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference KEY_BINDINGS__BINDINGS = eINSTANCE.getKeyBindings_Bindings();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingImpl <em>Key Binding</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeyBindingImpl
		 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingPackageImpl#getKeyBinding()
		 * @generated
		 */
		EClass KEY_BINDING = eINSTANCE.getKeyBinding();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute KEY_BINDING__ID = eINSTANCE.getKeyBinding_Id();

		/**
		 * The meta object literal for the '<em><b>Key Sequence</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute KEY_BINDING__KEY_SEQUENCE = eINSTANCE.getKeyBinding_KeySequence();

	}

} // VTKeybindingPackage
