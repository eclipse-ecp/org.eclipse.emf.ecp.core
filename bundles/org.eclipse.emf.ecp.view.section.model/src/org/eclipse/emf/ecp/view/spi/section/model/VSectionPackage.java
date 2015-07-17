/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.section.model;

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
 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionFactory
 * @model kind="package"
 * @generated
 */
public interface VSectionPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "section"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/section/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.section.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VSectionPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionedAreaImpl
	 * <em>Sectioned Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionedAreaImpl
	 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionPackageImpl#getSectionedArea()
	 * @generated
	 */
	int SECTIONED_AREA = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA__ROOT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sectioned Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTIONED_AREA_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl <em>Section</em>}
	 * ' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl
	 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionPackageImpl#getSection()
	 * @generated
	 */
	int SECTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__NAME = VViewPackage.CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__VISIBLE = VViewPackage.CONTAINER__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__ENABLED = VViewPackage.CONTAINER__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__READONLY = VViewPackage.CONTAINER__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__DIAGNOSTIC = VViewPackage.CONTAINER__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__ATTACHMENTS = VViewPackage.CONTAINER__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__CHILDREN = VViewPackage.CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Child Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__CHILD_ITEMS = VViewPackage.CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION__COLLAPSED = VViewPackage.CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SECTION_FEATURE_COUNT = VViewPackage.CONTAINER_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea
	 * <em>Sectioned Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Sectioned Area</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea
	 * @generated
	 */
	EClass getSectionedArea();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Root</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea#getRoot()
	 * @see #getSectionedArea()
	 * @generated
	 */
	EReference getSectionedArea_Root();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.section.model.VSection <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Section</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSection
	 * @generated
	 */
	EClass getSection();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.section.model.VSection#getChildItems <em>Child Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Child Items</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSection#getChildItems()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_ChildItems();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.section.model.VSection#isCollapsed <em>Collapsed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Collapsed</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSection#isCollapsed()
	 * @see #getSection()
	 * @generated
	 */
	EAttribute getSection_Collapsed();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VSectionFactory getSectionFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionedAreaImpl
		 * <em>Sectioned Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionedAreaImpl
		 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionPackageImpl#getSectionedArea()
		 * @generated
		 */
		EClass SECTIONED_AREA = eINSTANCE.getSectionedArea();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SECTIONED_AREA__ROOT = eINSTANCE.getSectionedArea_Root();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl
		 * <em>Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl
		 * @see org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionPackageImpl#getSection()
		 * @generated
		 */
		EClass SECTION = eINSTANCE.getSection();

		/**
		 * The meta object literal for the '<em><b>Child Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SECTION__CHILD_ITEMS = eINSTANCE.getSection_ChildItems();

		/**
		 * The meta object literal for the '<em><b>Collapsed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SECTION__COLLAPSED = eINSTANCE.getSection_Collapsed();

	}

} // VSectionPackage
