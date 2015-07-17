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
package org.eclipse.emf.ecp.view.spi.categorization.model;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory
 * @model kind="package"
 * @generated
 */
public interface VCategorizationPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "categorization"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/categorization/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.categorization.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VCategorizationPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VAbstractCategorizationImpl
	 * <em>Abstract Categorization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VAbstractCategorizationImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getAbstractCategorization()
	 * @generated
	 */
	int ABSTRACT_CATEGORIZATION = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationImpl
	 * <em>Categorization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorization()
	 * @generated
	 */
	int CATEGORIZATION = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategoryImpl
	 * <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategoryImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VActionImpl
	 * <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VActionImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 3;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationElementImpl <em>Element</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationElementImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorizationElement()
	 * @generated
	 */
	int CATEGORIZATION_ELEMENT = 4;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl
	 * <em>Categorizable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorizableElement()
	 * @generated
	 */
	int CATEGORIZABLE_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__NAME = VViewPackage.ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__VISIBLE = VViewPackage.ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__ENABLED = VViewPackage.ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__READONLY = VViewPackage.ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__DIAGNOSTIC = VViewPackage.ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__ATTACHMENTS = VViewPackage.ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT__LABEL_OBJECT = VViewPackage.ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Categorizable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZABLE_ELEMENT_FEATURE_COUNT = VViewPackage.ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__NAME = CATEGORIZABLE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__VISIBLE = CATEGORIZABLE_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__ENABLED = CATEGORIZABLE_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__READONLY = CATEGORIZABLE_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__DIAGNOSTIC = CATEGORIZABLE_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__ATTACHMENTS = CATEGORIZABLE_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__LABEL_OBJECT = CATEGORIZABLE_ELEMENT__LABEL_OBJECT;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION__ACTIONS = CATEGORIZABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Categorization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATEGORIZATION_FEATURE_COUNT = CATEGORIZABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__NAME = ABSTRACT_CATEGORIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__VISIBLE = ABSTRACT_CATEGORIZATION__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__ENABLED = ABSTRACT_CATEGORIZATION__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__READONLY = ABSTRACT_CATEGORIZATION__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__DIAGNOSTIC = ABSTRACT_CATEGORIZATION__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__ATTACHMENTS = ABSTRACT_CATEGORIZATION__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__LABEL_OBJECT = ABSTRACT_CATEGORIZATION__LABEL_OBJECT;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__ACTIONS = ABSTRACT_CATEGORIZATION__ACTIONS;

	/**
	 * The feature id for the '<em><b>Categorizations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION__CATEGORIZATIONS = ABSTRACT_CATEGORIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Categorization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_FEATURE_COUNT = ABSTRACT_CATEGORIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__NAME = ABSTRACT_CATEGORIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__VISIBLE = ABSTRACT_CATEGORIZATION__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ENABLED = ABSTRACT_CATEGORIZATION__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__READONLY = ABSTRACT_CATEGORIZATION__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DIAGNOSTIC = ABSTRACT_CATEGORIZATION__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ATTACHMENTS = ABSTRACT_CATEGORIZATION__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__LABEL_OBJECT = ABSTRACT_CATEGORIZATION__LABEL_OBJECT;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ACTIONS = ABSTRACT_CATEGORIZATION__ACTIONS;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY__COMPOSITE = ABSTRACT_CATEGORIZATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = ABSTRACT_CATEGORIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Bundle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__BUNDLE = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__CLASS_NAME = 1;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Categorizations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__CATEGORIZATIONS = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Current Selection</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__CURRENT_SELECTION = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Main Category Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT__MAIN_CATEGORY_DEPTH = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CATEGORIZATION_ELEMENT_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization
	 * <em>Abstract Categorization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Abstract Categorization</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization
	 * @generated
	 */
	EClass getAbstractCategorization();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization#getActions()
	 * @see #getAbstractCategorization()
	 * @generated
	 */
	EReference getAbstractCategorization_Actions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization
	 * <em>Categorization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Categorization</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization
	 * @generated
	 */
	EClass getCategorization();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization#getCategorizations
	 * <em>Categorizations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Categorizations</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization#getCategorizations()
	 * @see #getCategorization()
	 * @generated
	 */
	EReference getCategorization_Categorizations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategory
	 * <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategory
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategory#getComposite <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Composite</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategory#getComposite()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Composite();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction
	 * <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Action</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VAction
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getBundle <em>Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bundle</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getBundle()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Bundle();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getClassName()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_ClassName();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement
	 * @generated
	 */
	EClass getCategorizationElement();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getCategorizations
	 * <em>Categorizations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Categorizations</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getCategorizations()
	 * @see #getCategorizationElement()
	 * @generated
	 */
	EReference getCategorizationElement_Categorizations();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getCurrentSelection
	 * <em>Current Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Current Selection</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getCurrentSelection()
	 * @see #getCategorizationElement()
	 * @generated
	 */
	EReference getCategorizationElement_CurrentSelection();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getMainCategoryDepth
	 * <em>Main Category Depth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Main Category Depth</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement#getMainCategoryDepth()
	 * @see #getCategorizationElement()
	 * @generated
	 */
	EAttribute getCategorizationElement_MainCategoryDepth();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement <em>Categorizable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Categorizable Element</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement
	 * @generated
	 */
	EClass getCategorizableElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#getLabelObject
	 * <em>Label Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Label Object</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#getLabelObject()
	 * @see #getCategorizableElement()
	 * @generated
	 */
	EReference getCategorizableElement_LabelObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VCategorizationFactory getCategorizationFactory();

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
		 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VAbstractCategorizationImpl
		 * <em>Abstract Categorization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VAbstractCategorizationImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getAbstractCategorization()
		 * @generated
		 */
		EClass ABSTRACT_CATEGORIZATION = eINSTANCE.getAbstractCategorization();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ABSTRACT_CATEGORIZATION__ACTIONS = eINSTANCE.getAbstractCategorization_Actions();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationImpl <em>Categorization</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorization()
		 * @generated
		 */
		EClass CATEGORIZATION = eINSTANCE.getCategorization();

		/**
		 * The meta object literal for the '<em><b>Categorizations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CATEGORIZATION__CATEGORIZATIONS = eINSTANCE.getCategorization_Categorizations();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategoryImpl
		 * <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategoryImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Composite</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CATEGORY__COMPOSITE = eINSTANCE.getCategory_Composite();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VActionImpl
		 * <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VActionImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Bundle</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ACTION__BUNDLE = eINSTANCE.getAction_Bundle();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ACTION__CLASS_NAME = eINSTANCE.getAction_ClassName();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationElementImpl <em>Element</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationElementImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorizationElement()
		 * @generated
		 */
		EClass CATEGORIZATION_ELEMENT = eINSTANCE.getCategorizationElement();

		/**
		 * The meta object literal for the '<em><b>Categorizations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CATEGORIZATION_ELEMENT__CATEGORIZATIONS = eINSTANCE.getCategorizationElement_Categorizations();

		/**
		 * The meta object literal for the '<em><b>Current Selection</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CATEGORIZATION_ELEMENT__CURRENT_SELECTION = eINSTANCE.getCategorizationElement_CurrentSelection();

		/**
		 * The meta object literal for the '<em><b>Main Category Depth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CATEGORIZATION_ELEMENT__MAIN_CATEGORY_DEPTH = eINSTANCE.getCategorizationElement_MainCategoryDepth();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl
		 * <em>Categorizable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl
		 * @see org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationPackageImpl#getCategorizableElement()
		 * @generated
		 */
		EClass CATEGORIZABLE_ELEMENT = eINSTANCE.getCategorizableElement();

		/**
		 * The meta object literal for the '<em><b>Label Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CATEGORIZABLE_ELEMENT__LABEL_OBJECT = eINSTANCE.getCategorizableElement_LabelObject();

	}

} // VCategorizationPackage
