/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.vertical.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.view.model.ViewPackage;

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
 * @see org.eclipse.emf.ecp.view.vertical.model.VVerticalFactory
 * @model kind="package"
 * @generated
 */
public interface VVerticalPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/vertical/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.vertical.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	VVerticalPackage eINSTANCE = org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalLayoutImpl
	 * <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalLayoutImpl
	 * @see org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalPackageImpl#getVerticalLayout()
	 * @generated
	 */
	int VERTICAL_LAYOUT = 0;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__VISIBLE = ViewPackage.COMPOSITE_COLLECTION__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__ENABLED = ViewPackage.COMPOSITE_COLLECTION__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__READONLY = ViewPackage.COMPOSITE_COLLECTION__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__DIAGNOSTIC = ViewPackage.COMPOSITE_COLLECTION__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__ATTACHMENTS = ViewPackage.COMPOSITE_COLLECTION__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__NAME = ViewPackage.COMPOSITE_COLLECTION__NAME;

	/**
	 * The feature id for the '<em><b>Composites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT__COMPOSITES = ViewPackage.COMPOSITE_COLLECTION__COMPOSITES;

	/**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LAYOUT_FEATURE_COUNT = ViewPackage.COMPOSITE_COLLECTION_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.vertical.model.VVerticalLayout
	 * <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see org.eclipse.emf.ecp.view.vertical.model.VVerticalLayout
	 * @generated
	 */
	EClass getVerticalLayout();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VVerticalFactory getVerticalFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalLayoutImpl
		 * <em>Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalLayoutImpl
		 * @see org.eclipse.emf.ecp.view.vertical.model.impl.VVerticalPackageImpl#getVerticalLayout()
		 * @generated
		 */
		EClass VERTICAL_LAYOUT = eINSTANCE.getVerticalLayout();

	}

} // VVerticalPackage
