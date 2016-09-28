/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplateFactory
 * @model kind="package"
 * @generated
 */
public interface VTTemplatePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "template"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/template/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTTemplatePackage eINSTANCE = org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl
	 * <em>View Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getViewTemplate()
	 * @generated
	 */
	int VIEW_TEMPLATE = 0;

	/**
	 * The feature id for the '<em><b>Control Validation Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_TEMPLATE__STYLES = 1;

	/**
	 * The feature id for the '<em><b>Referenced Ecores</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_TEMPLATE__REFERENCED_ECORES = 2;

	/**
	 * The number of structural features of the '<em>View Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_TEMPLATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>View Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW_TEMPLATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.model.impl.VTControlValidationTemplateImpl
	 * <em>Control Validation Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTControlValidationTemplateImpl
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getControlValidationTemplate()
	 * @generated
	 */
	int CONTROL_VALIDATION_TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Ok Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX = 0;

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
	int CONTROL_VALIDATION_TEMPLATE__OK_FOREGROUND_COLOR_HEX = 1;

	/**
	 * The feature id for the '<em><b>Ok Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL = 2;

	/**
	 * The feature id for the '<em><b>Ok Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL = 3;

	/**
	 * The feature id for the '<em><b>Info Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX = 4;

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
	int CONTROL_VALIDATION_TEMPLATE__INFO_FOREGROUND_COLOR_HEX = 5;

	/**
	 * The feature id for the '<em><b>Info Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL = 6;

	/**
	 * The feature id for the '<em><b>Info Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL = 7;

	/**
	 * The feature id for the '<em><b>Warning Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX = 8;

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
	int CONTROL_VALIDATION_TEMPLATE__WARNING_FOREGROUND_COLOR_HEX = 9;

	/**
	 * The feature id for the '<em><b>Warning Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL = 10;

	/**
	 * The feature id for the '<em><b>Warning Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL = 11;

	/**
	 * The feature id for the '<em><b>Error Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX = 12;

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
	int CONTROL_VALIDATION_TEMPLATE__ERROR_FOREGROUND_COLOR_HEX = 13;

	/**
	 * The feature id for the '<em><b>Error Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL = 14;

	/**
	 * The feature id for the '<em><b>Error Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL = 15;

	/**
	 * The feature id for the '<em><b>Cancel Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX = 16;

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
	int CONTROL_VALIDATION_TEMPLATE__CANCEL_FOREGROUND_COLOR_HEX = 17;

	/**
	 * The feature id for the '<em><b>Cancel Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL = 18;

	/**
	 * The feature id for the '<em><b>Cancel Overlay URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL = 19;

	/**
	 * The number of structural features of the '<em>Control Validation Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE_FEATURE_COUNT = 20;

	/**
	 * The number of operations of the '<em>Control Validation Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_VALIDATION_TEMPLATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl <em>Style</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyle()
	 * @generated
	 */
	int STYLE = 2;

	/**
	 * The feature id for the '<em><b>Selector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE__SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE__PROPERTIES = 1;

	/**
	 * The number of structural features of the '<em>Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.model.VTStyleProperty
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyleProperty()
	 * @generated
	 */
	int STYLE_PROPERTY = 3;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_PROPERTY_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.model.VTStyleSelector
	 * <em>Style Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleSelector
	 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyleSelector()
	 * @generated
	 */
	int STYLE_SELECTOR = 4;

	/**
	 * The number of structural features of the '<em>Style Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_SELECTOR_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Style Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int STYLE_SELECTOR_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate
	 * <em>View Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>View Template</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplate
	 * @generated
	 */
	EClass getViewTemplate();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getControlValidationConfiguration
	 * <em>Control Validation Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Control Validation Configuration</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getControlValidationConfiguration()
	 * @see #getViewTemplate()
	 * @generated
	 */
	EReference getViewTemplate_ControlValidationConfiguration();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getStyles <em>Styles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Styles</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getStyles()
	 * @see #getViewTemplate()
	 * @generated
	 */
	EReference getViewTemplate_Styles();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getReferencedEcores <em>Referenced Ecores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Referenced Ecores</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getReferencedEcores()
	 * @see #getViewTemplate()
	 * @generated
	 */
	EAttribute getViewTemplate_ReferencedEcores();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate
	 * <em>Control Validation Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Control Validation Template</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate
	 * @generated
	 */
	EClass getControlValidationTemplate();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkColorHEX <em>Ok Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_OkColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkForegroundColorHEX
	 * <em>Ok Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkForegroundColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_OkForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkImageURL <em>Ok Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkImageURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_OkImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkOverlayURL
	 * <em>Ok Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ok Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getOkOverlayURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_OkOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoColorHEX
	 * <em>Info Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_InfoColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoForegroundColorHEX
	 * <em>Info Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoForegroundColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_InfoForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoImageURL
	 * <em>Info Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoImageURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_InfoImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoOverlayURL
	 * <em>Info Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Info Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getInfoOverlayURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_InfoOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningColorHEX
	 * <em>Warning Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_WarningColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningForegroundColorHEX
	 * <em>Warning Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningForegroundColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_WarningForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningImageURL
	 * <em>Warning Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningImageURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_WarningImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningOverlayURL
	 * <em>Warning Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Warning Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getWarningOverlayURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_WarningOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorColorHEX
	 * <em>Error Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_ErrorColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorForegroundColorHEX
	 * <em>Error Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorForegroundColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_ErrorForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorImageURL
	 * <em>Error Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorImageURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_ErrorImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorOverlayURL
	 * <em>Error Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Error Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getErrorOverlayURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_ErrorOverlayURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelColorHEX
	 * <em>Cancel Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_CancelColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelForegroundColorHEX
	 * <em>Cancel Foreground Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Foreground Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelForegroundColorHEX()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_CancelForegroundColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelImageURL
	 * <em>Cancel Image URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Image URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelImageURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_CancelImageURL();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelOverlayURL
	 * <em>Cancel Overlay URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cancel Overlay URL</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate#getCancelOverlayURL()
	 * @see #getControlValidationTemplate()
	 * @generated
	 */
	EAttribute getControlValidationTemplate_CancelOverlayURL();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.model.VTStyle <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyle
	 * @generated
	 */
	EClass getStyle();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTStyle#getSelector <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyle#getSelector()
	 * @see #getStyle()
	 * @generated
	 */
	EReference getStyle_Selector();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.template.model.VTStyle#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyle#getProperties()
	 * @see #getStyle()
	 * @generated
	 */
	EReference getStyle_Properties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.model.VTStyleProperty
	 * <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty
	 * @generated
	 */
	EClass getStyleProperty();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.model.VTStyleSelector
	 * <em>Style Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleSelector
	 * @generated
	 */
	EClass getStyleSelector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTTemplateFactory getTemplateFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl
		 * <em>View Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getViewTemplate()
		 * @generated
		 */
		EClass VIEW_TEMPLATE = eINSTANCE.getViewTemplate();

		/**
		 * The meta object literal for the '<em><b>Control Validation Configuration</b></em>' containment reference
		 * feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION = eINSTANCE
			.getViewTemplate_ControlValidationConfiguration();

		/**
		 * The meta object literal for the '<em><b>Styles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference VIEW_TEMPLATE__STYLES = eINSTANCE.getViewTemplate_Styles();

		/**
		 * The meta object literal for the '<em><b>Referenced Ecores</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute VIEW_TEMPLATE__REFERENCED_ECORES = eINSTANCE.getViewTemplate_ReferencedEcores();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.template.model.impl.VTControlValidationTemplateImpl
		 * <em>Control Validation Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTControlValidationTemplateImpl
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getControlValidationTemplate()
		 * @generated
		 */
		EClass CONTROL_VALIDATION_TEMPLATE = eINSTANCE.getControlValidationTemplate();

		/**
		 * The meta object literal for the '<em><b>Ok Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX = eINSTANCE.getControlValidationTemplate_OkColorHEX();

		/**
		 * The meta object literal for the '<em><b>Ok Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__OK_FOREGROUND_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_OkForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Ok Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL = eINSTANCE.getControlValidationTemplate_OkImageURL();

		/**
		 * The meta object literal for the '<em><b>Ok Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL = eINSTANCE.getControlValidationTemplate_OkOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Info Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX = eINSTANCE.getControlValidationTemplate_InfoColorHEX();

		/**
		 * The meta object literal for the '<em><b>Info Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__INFO_FOREGROUND_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_InfoForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Info Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL = eINSTANCE.getControlValidationTemplate_InfoImageURL();

		/**
		 * The meta object literal for the '<em><b>Info Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL = eINSTANCE
			.getControlValidationTemplate_InfoOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Warning Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_WarningColorHEX();

		/**
		 * The meta object literal for the '<em><b>Warning Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__WARNING_FOREGROUND_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_WarningForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Warning Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL = eINSTANCE
			.getControlValidationTemplate_WarningImageURL();

		/**
		 * The meta object literal for the '<em><b>Warning Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL = eINSTANCE
			.getControlValidationTemplate_WarningOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Error Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_ErrorColorHEX();

		/**
		 * The meta object literal for the '<em><b>Error Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__ERROR_FOREGROUND_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_ErrorForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Error Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL = eINSTANCE
			.getControlValidationTemplate_ErrorImageURL();

		/**
		 * The meta object literal for the '<em><b>Error Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL = eINSTANCE
			.getControlValidationTemplate_ErrorOverlayURL();

		/**
		 * The meta object literal for the '<em><b>Cancel Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_CancelColorHEX();

		/**
		 * The meta object literal for the '<em><b>Cancel Foreground Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 1.10
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__CANCEL_FOREGROUND_COLOR_HEX = eINSTANCE
			.getControlValidationTemplate_CancelForegroundColorHEX();

		/**
		 * The meta object literal for the '<em><b>Cancel Image URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL = eINSTANCE
			.getControlValidationTemplate_CancelImageURL();

		/**
		 * The meta object literal for the '<em><b>Cancel Overlay URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL = eINSTANCE
			.getControlValidationTemplate_CancelOverlayURL();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl
		 * <em>Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyle()
		 * @generated
		 */
		EClass STYLE = eINSTANCE.getStyle();

		/**
		 * The meta object literal for the '<em><b>Selector</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STYLE__SELECTOR = eINSTANCE.getStyle_Selector();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference STYLE__PROPERTIES = eINSTANCE.getStyle_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.model.VTStyleProperty
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyleProperty()
		 * @generated
		 */
		EClass STYLE_PROPERTY = eINSTANCE.getStyleProperty();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.model.VTStyleSelector
		 * <em>Style Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.model.VTStyleSelector
		 * @see org.eclipse.emf.ecp.view.template.model.impl.VTTemplatePackageImpl#getStyleSelector()
		 * @generated
		 */
		EClass STYLE_SELECTOR = eINSTANCE.getStyleSelector();

	}

} // VTTemplatePackage
