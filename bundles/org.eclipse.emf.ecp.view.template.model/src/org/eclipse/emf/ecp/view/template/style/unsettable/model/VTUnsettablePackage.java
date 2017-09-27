/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model;

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
 * @since 1.14
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory
 * @model kind="package"
 * @generated
 */
public interface VTUnsettablePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "unsettable"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/unsettable/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.unsettable.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTUnsettablePackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl <em>Style
	 * Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getUnsettableStyleProperty()
	 * @generated
	 */
	int UNSETTABLE_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Button Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Button Placement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNSETTABLE_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int UNSETTABLE_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
	 * <em>Button Alignment Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getButtonAlignmentType()
	 * @generated
	 */
	int BUTTON_ALIGNMENT_TYPE = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
	 * <em>Button Placement Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getButtonPlacementType()
	 * @generated
	 */
	int BUTTON_PLACEMENT_TYPE = 2;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty <em>Style
	 * Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty
	 * @generated
	 */
	EClass getUnsettableStyleProperty();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonAlignment
	 * <em>Button Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Button Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonAlignment()
	 * @see #getUnsettableStyleProperty()
	 * @generated
	 */
	EAttribute getUnsettableStyleProperty_ButtonAlignment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonPlacement
	 * <em>Button Placement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Button Placement</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonPlacement()
	 * @see #getUnsettableStyleProperty()
	 * @generated
	 */
	EAttribute getUnsettableStyleProperty_ButtonPlacement();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType <em>Button Alignment
	 * Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Button Alignment Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
	 * @generated
	 */
	EEnum getButtonAlignmentType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType <em>Button Placement
	 * Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Button Placement Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
	 * @generated
	 */
	EEnum getButtonPlacementType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTUnsettableFactory getUnsettableFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl <em>Style
		 * Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getUnsettableStyleProperty()
		 * @generated
		 */
		EClass UNSETTABLE_STYLE_PROPERTY = eINSTANCE.getUnsettableStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Button Alignment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT = eINSTANCE.getUnsettableStyleProperty_ButtonAlignment();

		/**
		 * The meta object literal for the '<em><b>Button Placement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT = eINSTANCE.getUnsettableStyleProperty_ButtonPlacement();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType <em>Button Alignment
		 * Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getButtonAlignmentType()
		 * @generated
		 */
		EEnum BUTTON_ALIGNMENT_TYPE = eINSTANCE.getButtonAlignmentType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType <em>Button Placement
		 * Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
		 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettablePackageImpl#getButtonPlacementType()
		 * @generated
		 */
		EEnum BUTTON_PLACEMENT_TYPE = eINSTANCE.getButtonPlacementType();

	}

} // VTUnsettablePackage
