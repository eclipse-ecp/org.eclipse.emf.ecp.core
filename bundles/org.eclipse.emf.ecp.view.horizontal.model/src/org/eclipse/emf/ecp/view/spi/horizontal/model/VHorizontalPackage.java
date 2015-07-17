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
package org.eclipse.emf.ecp.view.spi.horizontal.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

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
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalFactory
 * @model kind="package"
 * @generated
 */
public interface VHorizontalPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "horizontal"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/horizontal/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.horizontal.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VHorizontalPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalLayoutImpl
	 * <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalLayoutImpl
	 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalPackageImpl#getHorizontalLayout()
	 * @generated
	 */
	int HORIZONTAL_LAYOUT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__NAME = VViewPackage.CONTAINED_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__VISIBLE = VViewPackage.CONTAINED_CONTAINER__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__ENABLED = VViewPackage.CONTAINED_CONTAINER__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__READONLY = VViewPackage.CONTAINED_CONTAINER__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__DIAGNOSTIC = VViewPackage.CONTAINED_CONTAINER__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__ATTACHMENTS = VViewPackage.CONTAINED_CONTAINER__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT__CHILDREN = VViewPackage.CONTAINED_CONTAINER__CHILDREN;

	/**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LAYOUT_FEATURE_COUNT = VViewPackage.CONTAINED_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout
	 * <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout
	 * @generated
	 */
	EClass getHorizontalLayout();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VHorizontalFactory getHorizontalFactory();

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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalLayoutImpl <em>Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalLayoutImpl
		 * @see org.eclipse.emf.ecp.view.spi.horizontal.model.impl.VHorizontalPackageImpl#getHorizontalLayout()
		 * @generated
		 */
		EClass HORIZONTAL_LAYOUT = eINSTANCE.getHorizontalLayout();

	}

} // VHorizontalPackage
