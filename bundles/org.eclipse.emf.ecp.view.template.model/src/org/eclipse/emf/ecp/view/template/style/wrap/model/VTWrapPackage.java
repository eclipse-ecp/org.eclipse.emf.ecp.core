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
package org.eclipse.emf.ecp.view.template.style.wrap.model;

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
 * @since 1.17
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapFactory
 * @model kind="package"
 * @generated
 */
public interface VTWrapPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "wrap"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/wrap/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.wrap.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTWrapPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTWrapPackageImpl.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl <em>Label Wrap Style
	 * Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTWrapPackageImpl#getLabelWrapStyleProperty()
	 * @generated
	 */
	int LABEL_WRAP_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Wrap Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Label Wrap Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL_WRAP_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Label Wrap Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL_WRAP_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty <em>Label Wrap Style
	 * Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Label Wrap Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty
	 * @generated
	 */
	EClass getLabelWrapStyleProperty();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty#isWrapLabel <em>Wrap
	 * Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Wrap Label</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty#isWrapLabel()
	 * @see #getLabelWrapStyleProperty()
	 * @generated
	 */
	EAttribute getLabelWrapStyleProperty_WrapLabel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTWrapFactory getWrapFactory();

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
		 * '{@link org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl <em>Label Wrap
		 * Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTWrapPackageImpl#getLabelWrapStyleProperty()
		 * @generated
		 */
		EClass LABEL_WRAP_STYLE_PROPERTY = eINSTANCE.getLabelWrapStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Wrap Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute LABEL_WRAP_STYLE_PROPERTY__WRAP_LABEL = eINSTANCE.getLabelWrapStyleProperty_WrapLabel();

	}

} // VTWrapPackage
