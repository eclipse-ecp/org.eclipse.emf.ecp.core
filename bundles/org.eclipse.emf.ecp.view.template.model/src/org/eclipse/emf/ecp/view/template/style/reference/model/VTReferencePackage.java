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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.reference.model;

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
 * @since 1.18
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory
 * @model kind="package"
 * @generated
 */
public interface VTReferencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "reference"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/reference/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.reference.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTReferencePackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferencePackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl <em>Style
	 * Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferencePackageImpl#getReferenceStyleProperty()
	 * @generated
	 */
	int REFERENCE_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Show Create And Link Button For Cross References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 0;

	/**
	 * The feature id for the '<em><b>Show Link Button For Containment References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 1;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty <em>Style
	 * Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty
	 * @generated
	 */
	EClass getReferenceStyleProperty();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowCreateAndLinkButtonForCrossReferences
	 * <em>Show Create And Link Button For Cross References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Show Create And Link Button For Cross References</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowCreateAndLinkButtonForCrossReferences()
	 * @see #getReferenceStyleProperty()
	 * @generated
	 */
	EAttribute getReferenceStyleProperty_ShowCreateAndLinkButtonForCrossReferences();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowLinkButtonForContainmentReferences
	 * <em>Show Link Button For Containment References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Show Link Button For Containment References</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowLinkButtonForContainmentReferences()
	 * @see #getReferenceStyleProperty()
	 * @generated
	 */
	EAttribute getReferenceStyleProperty_ShowLinkButtonForContainmentReferences();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTReferenceFactory getReferenceFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl <em>Style
		 * Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferencePackageImpl#getReferenceStyleProperty()
		 * @generated
		 */
		EClass REFERENCE_STYLE_PROPERTY = eINSTANCE.getReferenceStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Show Create And Link Button For Cross References</b></em>' attribute
		 * feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES = eINSTANCE
			.getReferenceStyleProperty_ShowCreateAndLinkButtonForCrossReferences();

		/**
		 * The meta object literal for the '<em><b>Show Link Button For Containment References</b></em>' attribute
		 * feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES = eINSTANCE
			.getReferenceStyleProperty_ShowLinkButtonForContainmentReferences();

	}

} // VTReferencePackage
