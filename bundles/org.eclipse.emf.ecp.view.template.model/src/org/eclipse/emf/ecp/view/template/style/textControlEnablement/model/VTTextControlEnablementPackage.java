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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementFactory
 * @model kind="package"
 * @generated
 */
public interface VTTextControlEnablementPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "textControlEnablement"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/textcontrol/enablement/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.textcontrol.enablement.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VTTextControlEnablementPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementPackageImpl#getTextControlEnablementStyleProperty()
	 * @generated
	 */
	int TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Render Disable As Editable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty
	 * @generated
	 */
	EClass getTextControlEnablementStyleProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty#isRenderDisableAsEditable <em>Render Disable As Editable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Render Disable As Editable</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty#isRenderDisableAsEditable()
	 * @see #getTextControlEnablementStyleProperty()
	 * @generated
	 */
	EAttribute getTextControlEnablementStyleProperty_RenderDisableAsEditable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTTextControlEnablementFactory getTextControlEnablementFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementPackageImpl#getTextControlEnablementStyleProperty()
		 * @generated
		 */
		EClass TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY = eINSTANCE.getTextControlEnablementStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Render Disable As Editable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE = eINSTANCE.getTextControlEnablementStyleProperty_RenderDisableAsEditable();

	}

} //VTTextControlEnablementPackage
