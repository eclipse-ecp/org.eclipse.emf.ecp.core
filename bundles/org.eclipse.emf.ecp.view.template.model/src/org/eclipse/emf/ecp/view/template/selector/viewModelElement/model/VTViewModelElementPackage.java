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
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model;

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
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementFactory
 * @model kind="package"
 * @generated
 */
public interface VTViewModelElementPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "viewModelElement"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/selector/viewmodelelement/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.selector.viewmodelelement.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTViewModelElementPackage eINSTANCE = org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl
	 * <em>Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementPackageImpl#getViewModelElementSelector()
	 * @generated
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Select Subclasses</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR_FEATURE_COUNT = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_ELEMENT_SELECTOR_OPERATION_COUNT = VTTemplatePackage.STYLE_SELECTOR_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector
	 * <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector
	 * @generated
	 */
	EClass getViewModelElementSelector();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getClassType
	 * <em>Class Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Class Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getClassType()
	 * @see #getViewModelElementSelector()
	 * @generated
	 */
	EReference getViewModelElementSelector_ClassType();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttribute
	 * <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttribute()
	 * @see #getViewModelElementSelector()
	 * @generated
	 */
	EReference getViewModelElementSelector_Attribute();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttributeValue
	 * <em>Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Attribute Value</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttributeValue()
	 * @see #getViewModelElementSelector()
	 * @generated
	 */
	EAttribute getViewModelElementSelector_AttributeValue();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#isSelectSubclasses
	 * <em>Select Subclasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Select Subclasses</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#isSelectSubclasses()
	 * @see #getViewModelElementSelector()
	 * @generated
	 */
	EAttribute getViewModelElementSelector_SelectSubclasses();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTViewModelElementFactory getViewModelElementFactory();

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
		 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl
		 * <em>Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl
		 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementPackageImpl#getViewModelElementSelector()
		 * @generated
		 */
		EClass VIEW_MODEL_ELEMENT_SELECTOR = eINSTANCE.getViewModelElementSelector();

		/**
		 * The meta object literal for the '<em><b>Class Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE = eINSTANCE.getViewModelElementSelector_ClassType();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE = eINSTANCE.getViewModelElementSelector_Attribute();

		/**
		 * The meta object literal for the '<em><b>Attribute Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE = eINSTANCE
			.getViewModelElementSelector_AttributeValue();

		/**
		 * The meta object literal for the '<em><b>Select Subclasses</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES = eINSTANCE
			.getViewModelElementSelector_SelectSubclasses();

	}

} // VTViewModelElementPackage
