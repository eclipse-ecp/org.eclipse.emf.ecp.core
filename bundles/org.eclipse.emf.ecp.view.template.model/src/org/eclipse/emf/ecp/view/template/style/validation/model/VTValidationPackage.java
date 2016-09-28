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
package org.eclipse.emf.ecp.view.template.style.validation.model;

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
 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory
 * @model kind="package"
 * @generated
 */
public interface VTValidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "validation"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/validation/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.validation.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTValidationPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationPackageImpl#getValidationStyleProperty()
	 * @generated
	 */
	int VALIDATION_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Ok Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ok Foreground Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 1;

	/**
	 * The feature id for the '<em><b>Ok Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ok Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Info Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Info Foreground Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 5;

	/**
	 * The feature id for the '<em><b>Info Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Info Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Warning Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Warning Foreground Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 9;

	/**
	 * The feature id for the '<em><b>Warning Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Warning Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Error Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Error Foreground Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 13;

	/**
	 * The feature id for the '<em><b>Error Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Error Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Cancel Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Cancel Foreground Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT
		+ 17;

	/**
	 * The feature id for the '<em><b>Cancel Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Cancel Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 19;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 20;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VALIDATION_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty
	 * <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty
	 * @generated
	 */
	EClass getValidationStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkColorHEX
	 * <em>Ok Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_OkColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkForegroundColorHEX
	 * <em>Ok Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkForegroundColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_OkForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkImageURL
	 * <em>Ok Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkImageURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_OkImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkOverlayURL
	 * <em>Ok Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getOkOverlayURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_OkOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoColorHEX
	 * <em>Info Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_InfoColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoForegroundColorHEX
	 * <em>Info Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoForegroundColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_InfoForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoImageURL
	 * <em>Info Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoImageURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_InfoImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoOverlayURL
	 * <em>Info Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getInfoOverlayURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_InfoOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningColorHEX
	 * <em>Warning Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_WarningColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningForegroundColorHEX
	 * <em>Warning Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningForegroundColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_WarningForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningImageURL
	 * <em>Warning Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningImageURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_WarningImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningOverlayURL
	 * <em>Warning Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getWarningOverlayURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_WarningOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorColorHEX
	 * <em>Error Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_ErrorColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorForegroundColorHEX
	 * <em>Error Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorForegroundColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_ErrorForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorImageURL
	 * <em>Error Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorImageURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_ErrorImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorOverlayURL
	 * <em>Error Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getErrorOverlayURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_ErrorOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelColorHEX
	 * <em>Cancel Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_CancelColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelForegroundColorHEX
	 * <em>Cancel Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelForegroundColorHEX()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_CancelForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelImageURL
	 * <em>Cancel Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelImageURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_CancelImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelOverlayURL
	 * <em>Cancel Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty#getCancelOverlayURL()
	 * @see #getValidationStyleProperty()
	 * @generated
	 */
	EAttribute getValidationStyleProperty_CancelOverlayURL();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTValidationFactory getValidationFactory();

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
		 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationPackageImpl#getValidationStyleProperty()
		 * @generated
		 */
		EClass VALIDATION_STYLE_PROPERTY = eINSTANCE.getValidationStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Ok Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX = eINSTANCE.getValidationStyleProperty_OkColorHEX();

		/**
		 * The meta object literal for the '<em><b>Ok Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_OkForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Ok Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL = eINSTANCE.getValidationStyleProperty_OkImageURL();

		/**
		 * The meta object literal for the '<em><b>Ok Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL = eINSTANCE.getValidationStyleProperty_OkOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Info Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX = eINSTANCE.getValidationStyleProperty_InfoColorHEX();

		/**
		 * The meta object literal for the '<em><b>Info Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_InfoForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Info Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL = eINSTANCE.getValidationStyleProperty_InfoImageURL();

		/**
		 * The meta object literal for the '<em><b>Info Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL = eINSTANCE.getValidationStyleProperty_InfoOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Warning Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_WarningColorHEX();

		/**
		 * The meta object literal for the '<em><b>Warning Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_WarningForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Warning Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL = eINSTANCE
			.getValidationStyleProperty_WarningImageURL();

		/**
		 * The meta object literal for the '<em><b>Warning Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL = eINSTANCE
			.getValidationStyleProperty_WarningOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Error Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX = eINSTANCE.getValidationStyleProperty_ErrorColorHEX();

		/**
		 * The meta object literal for the '<em><b>Error Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_ErrorForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Error Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL = eINSTANCE.getValidationStyleProperty_ErrorImageURL();

		/**
		 * The meta object literal for the '<em><b>Error Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL = eINSTANCE
			.getValidationStyleProperty_ErrorOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Cancel Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX = eINSTANCE.getValidationStyleProperty_CancelColorHEX();

		/**
		 * The meta object literal for the '<em><b>Cancel Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX = eINSTANCE
			.getValidationStyleProperty_CancelForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Cancel Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL = eINSTANCE.getValidationStyleProperty_CancelImageURL();

		/**
		 * The meta object literal for the '<em><b>Cancel Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL = eINSTANCE
			.getValidationStyleProperty_CancelOverlayURL();

	}

} // VTValidationPackage
