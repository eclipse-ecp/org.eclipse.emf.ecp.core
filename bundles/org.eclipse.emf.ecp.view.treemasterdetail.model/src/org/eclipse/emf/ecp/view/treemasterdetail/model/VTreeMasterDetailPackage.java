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
package org.eclipse.emf.ecp.view.treemasterdetail.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
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
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailFactory
 * @model kind="package"
 * @generated
 */
public interface VTreeMasterDetailPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "treemasterdetail"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/treemasterview/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.treemasterview.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTreeMasterDetailPackage eINSTANCE = org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailImpl
	 * <em>Tree Master Detail</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailImpl
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailPackageImpl#getTreeMasterDetail()
	 * @generated
	 */
	int TREE_MASTER_DETAIL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Detail View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL__DETAIL_VIEW = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tree Master Detail</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TREE_MASTER_DETAIL_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail
	 * <em>Tree Master Detail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Tree Master Detail</em>'.
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail
	 * @generated
	 */
	EClass getTreeMasterDetail();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail#getDetailView <em>Detail View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Detail View</em>'.
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail#getDetailView()
	 * @see #getTreeMasterDetail()
	 * @generated
	 */
	EReference getTreeMasterDetail_DetailView();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTreeMasterDetailFactory getTreeMasterDetailFactory();

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
		 * {@link org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailImpl
		 * <em>Tree Master Detail</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailImpl
		 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailPackageImpl#getTreeMasterDetail()
		 * @generated
		 */
		EClass TREE_MASTER_DETAIL = eINSTANCE.getTreeMasterDetail();

		/**
		 * The meta object literal for the '<em><b>Detail View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TREE_MASTER_DETAIL__DETAIL_VIEW = eINSTANCE.getTreeMasterDetail_DetailView();

	}

} // VTreeMasterDetailPackage
