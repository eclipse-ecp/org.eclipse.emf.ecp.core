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
package org.eclipse.emf.ecp.view.spi.groupedgrid.model;

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
 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridFactory
 * @model kind="package"
 * @generated
 * @since 1.2
 */
public interface VGroupedGridPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "groupedgrid"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/groupedgrid/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.groupedgrid.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VGroupedGridPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridImpl
	 * <em>Grouped Grid</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridImpl
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getGroupedGrid()
	 * @generated
	 */
	int GROUPED_GRID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID__GROUPS = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Grouped Grid</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUPED_GRID_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupImpl <em>Group</em>}
	 * ' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupImpl
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 1;

	/**
	 * The feature id for the '<em><b>Rows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__ROWS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__NAME = 1;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VRowImpl <em>Row</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VRowImpl
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getRow()
	 * @generated
	 */
	int ROW = 2;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROW__CHILDREN = 0;

	/**
	 * The number of structural features of the '<em>Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROW_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VSpanImpl <em>Span</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VSpanImpl
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getSpan()
	 * @generated
	 */
	int SPAN = 3;

	/**
	 * The feature id for the '<em><b>Horizontal Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPAN__HORIZONTAL_SPAN = VViewPackage.ATTACHMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Span</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPAN_FEATURE_COUNT = VViewPackage.ATTACHMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid
	 * <em>Grouped Grid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Grouped Grid</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid
	 * @generated
	 */
	EClass getGroupedGrid();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid#getGroups()
	 * @see #getGroupedGrid()
	 * @generated
	 */
	EReference getGroupedGrid_Groups();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Group</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getRows <em>Rows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Rows</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getRows()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Rows();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getName()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow <em>Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Row</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow
	 * @generated
	 */
	EClass getRow();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow#getChildren()
	 * @see #getRow()
	 * @generated
	 */
	EReference getRow_Children();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan <em>Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Span</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan
	 * @generated
	 */
	EClass getSpan();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan#getHorizontalSpan <em>Horizontal Span</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Horizontal Span</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan#getHorizontalSpan()
	 * @see #getSpan()
	 * @generated
	 */
	EAttribute getSpan_HorizontalSpan();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VGroupedGridFactory getGroupedGridFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridImpl
		 * <em>Grouped Grid</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridImpl
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getGroupedGrid()
		 * @generated
		 */
		EClass GROUPED_GRID = eINSTANCE.getGroupedGrid();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GROUPED_GRID__GROUPS = eINSTANCE.getGroupedGrid_Groups();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupImpl
		 * <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupImpl
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Rows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GROUP__ROWS = eINSTANCE.getGroup_Rows();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute GROUP__NAME = eINSTANCE.getGroup_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VRowImpl
		 * <em>Row</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VRowImpl
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getRow()
		 * @generated
		 */
		EClass ROW = eINSTANCE.getRow();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ROW__CHILDREN = eINSTANCE.getRow_Children();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VSpanImpl
		 * <em>Span</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VSpanImpl
		 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridPackageImpl#getSpan()
		 * @generated
		 */
		EClass SPAN = eINSTANCE.getSpan();

		/**
		 * The meta object literal for the '<em><b>Horizontal Span</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SPAN__HORIZONTAL_SPAN = eINSTANCE.getSpan_HorizontalSpan();

	}

} // VGroupedGridPackage
