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
package org.eclipse.emf.ecp.view.template.style.mandatory.model;

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
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory
 * @model kind="package"
 * @generated
 */
public interface VTMandatoryPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "mandatory"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/mandatory/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.mandatory.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTMandatoryPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryPackageImpl#getMandatoryStyleProperty()
	 * @generated
	 */
	int MANDATORY_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Highlite Mandatory Fields</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mandatory Marker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MANDATORY_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MANDATORY_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty <em>Style Property</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty
	 * @generated
	 */
	EClass getMandatoryStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#isHighliteMandatoryFields
	 * <em>Highlite Mandatory Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Highlite Mandatory Fields</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#isHighliteMandatoryFields()
	 * @see #getMandatoryStyleProperty()
	 * @generated
	 */
	EAttribute getMandatoryStyleProperty_HighliteMandatoryFields();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#getMandatoryMarker
	 * <em>Mandatory Marker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Mandatory Marker</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#getMandatoryMarker()
	 * @see #getMandatoryStyleProperty()
	 * @generated
	 */
	EAttribute getMandatoryStyleProperty_MandatoryMarker();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTMandatoryFactory getMandatoryFactory();

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
	interface Literals
	{
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryPackageImpl#getMandatoryStyleProperty()
		 * @generated
		 */
		EClass MANDATORY_STYLE_PROPERTY = eINSTANCE.getMandatoryStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Highlite Mandatory Fields</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS = eINSTANCE
			.getMandatoryStyleProperty_HighliteMandatoryFields();

		/**
		 * The meta object literal for the '<em><b>Mandatory Marker</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER = eINSTANCE.getMandatoryStyleProperty_MandatoryMarker();

	}

} // VTMandatoryPackage
