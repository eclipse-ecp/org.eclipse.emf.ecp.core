/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.stack.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackFactory
 * @model kind="package"
 * @generated
 */
public interface VStackPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "stack"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/stack/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.stack.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VStackPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl
	 * <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackPackageImpl#getStackLayout()
	 * @generated
	 */
	int STACK_LAYOUT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__DOMAIN_MODEL_REFERENCE = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stack Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__STACK_ITEMS = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Top Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT__TOP_ELEMENT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_LAYOUT_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackItemImpl <em>Item</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackItemImpl
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackPackageImpl#getStackItem()
	 * @generated
	 */
	int STACK_ITEM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__NAME = VViewPackage.CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__VISIBLE = VViewPackage.CONTAINER__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__ENABLED = VViewPackage.CONTAINER__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__READONLY = VViewPackage.CONTAINER__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__DIAGNOSTIC = VViewPackage.CONTAINER__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__ATTACHMENTS = VViewPackage.CONTAINER__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__CHILDREN = VViewPackage.CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM__VALUE = VViewPackage.CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STACK_ITEM_FEATURE_COUNT = VViewPackage.CONTAINER_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout <em>Layout</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout
	 * @generated
	 */
	EClass getStackLayout();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getDomainModelReference()
	 * @see #getStackLayout()
	 * @generated
	 */
	EReference getStackLayout_DomainModelReference();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getStackItems <em>Stack Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Stack Items</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getStackItems()
	 * @see #getStackLayout()
	 * @generated
	 */
	EReference getStackLayout_StackItems();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getTopElement <em>Top Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Top Element</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getTopElement()
	 * @see #getStackLayout()
	 * @generated
	 */
	EReference getStackLayout_TopElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Item</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackItem
	 * @generated
	 */
	EClass getStackItem();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackItem#getValue
	 * <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackItem#getValue()
	 * @see #getStackItem()
	 * @generated
	 */
	EAttribute getStackItem_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VStackFactory getStackFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl
		 * <em>Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl
		 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackPackageImpl#getStackLayout()
		 * @generated
		 */
		EClass STACK_LAYOUT = eINSTANCE.getStackLayout();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STACK_LAYOUT__DOMAIN_MODEL_REFERENCE = eINSTANCE.getStackLayout_DomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Stack Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STACK_LAYOUT__STACK_ITEMS = eINSTANCE.getStackLayout_StackItems();

		/**
		 * The meta object literal for the '<em><b>Top Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STACK_LAYOUT__TOP_ELEMENT = eINSTANCE.getStackLayout_TopElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackItemImpl
		 * <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackItemImpl
		 * @see org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackPackageImpl#getStackItem()
		 * @generated
		 */
		EClass STACK_ITEM = eINSTANCE.getStackItem();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute STACK_ITEM__VALUE = eINSTANCE.getStackItem_Value();

	}

} // VStackPackage
