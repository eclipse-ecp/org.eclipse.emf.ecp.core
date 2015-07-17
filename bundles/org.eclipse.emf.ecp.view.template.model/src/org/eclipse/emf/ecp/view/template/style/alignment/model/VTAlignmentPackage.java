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
package org.eclipse.emf.ecp.view.template.style.alignment.model;

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
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory
 * @model kind="package"
 * @generated
 */
public interface VTAlignmentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "alignment"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/alignment/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.alignment.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTAlignmentPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentPackageImpl#getAlignmentStyleProperty()
	 * @generated
	 */
	int ALIGNMENT_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ALIGNMENT_STYLE_PROPERTY__TYPE = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ALIGNMENT_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ALIGNMENT_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentPackageImpl#getAlignmentType()
	 * @generated
	 */
	int ALIGNMENT_TYPE = 1;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty <em>Style Property</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty
	 * @generated
	 */
	EClass getAlignmentStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty#getType()
	 * @see #getAlignmentStyleProperty()
	 * @generated
	 */
	EAttribute getAlignmentStyleProperty_Type();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @generated
	 */
	EEnum getAlignmentType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTAlignmentFactory getAlignmentFactory();

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
		 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentStylePropertyImpl
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentPackageImpl#getAlignmentStyleProperty()
		 * @generated
		 */
		EClass ALIGNMENT_STYLE_PROPERTY = eINSTANCE.getAlignmentStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ALIGNMENT_STYLE_PROPERTY__TYPE = eINSTANCE.getAlignmentStyleProperty_Type();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
		 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTAlignmentPackageImpl#getAlignmentType()
		 * @generated
		 */
		EEnum ALIGNMENT_TYPE = eINSTANCE.getAlignmentType();

	}

} // VTAlignmentPackage
