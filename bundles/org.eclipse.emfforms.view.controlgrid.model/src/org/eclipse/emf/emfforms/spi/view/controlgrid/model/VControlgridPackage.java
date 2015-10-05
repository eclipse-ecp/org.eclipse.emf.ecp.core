/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.emfforms.spi.view.controlgrid.model;

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
 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridFactory
 * @model kind="package"
 * @generated
 */
public interface VControlgridPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "controlgrid"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/emfforms/view/controlgrid/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.view.controlgrid.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VControlgridPackage eINSTANCE = org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridImpl
	 * <em>Control Grid</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridImpl
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGrid()
	 * @generated
	 */
	int CONTROL_GRID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__LABEL = VViewPackage.CONTAINED_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Rows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID__ROWS = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Control Grid</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridRowImpl
	 * <em>Control Grid Row</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridRowImpl
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGridRow()
	 * @generated
	 */
	int CONTROL_GRID_ROW = 1;

	/**
	 * The feature id for the '<em><b>Cells</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID_ROW__CELLS = 0;

	/**
	 * The number of structural features of the '<em>Control Grid Row</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID_ROW_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridCellImpl
	 * <em>Control Grid Cell</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridCellImpl
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGridCell()
	 * @generated
	 */
	int CONTROL_GRID_CELL = 2;

	/**
	 * The feature id for the '<em><b>Control</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID_CELL__CONTROL = 0;

	/**
	 * The number of structural features of the '<em>Control Grid Cell</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL_GRID_CELL_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid
	 * <em>Control Grid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Control Grid</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid
	 * @generated
	 */
	EClass getControlGrid();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid#getRows <em>Rows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Rows</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid#getRows()
	 * @see #getControlGrid()
	 * @generated
	 */
	EReference getControlGrid_Rows();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow
	 * <em>Control Grid Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Control Grid Row</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow
	 * @generated
	 */
	EClass getControlGridRow();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow#getCells <em>Cells</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Cells</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow#getCells()
	 * @see #getControlGridRow()
	 * @generated
	 */
	EReference getControlGridRow_Cells();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell
	 * <em>Control Grid Cell</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Control Grid Cell</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell
	 * @generated
	 */
	EClass getControlGridCell();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell#getControl <em>Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Control</em>'.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell#getControl()
	 * @see #getControlGridCell()
	 * @generated
	 */
	EReference getControlGridCell_Control();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VControlgridFactory getControlgridFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridImpl <em>Control Grid</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridImpl
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGrid()
		 * @generated
		 */
		EClass CONTROL_GRID = eINSTANCE.getControlGrid();

		/**
		 * The meta object literal for the '<em><b>Rows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONTROL_GRID__ROWS = eINSTANCE.getControlGrid_Rows();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridRowImpl <em>Control Grid Row</em>
		 * }' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridRowImpl
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGridRow()
		 * @generated
		 */
		EClass CONTROL_GRID_ROW = eINSTANCE.getControlGridRow();

		/**
		 * The meta object literal for the '<em><b>Cells</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONTROL_GRID_ROW__CELLS = eINSTANCE.getControlGridRow_Cells();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridCellImpl
		 * <em>Control Grid Cell</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridCellImpl
		 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlgridPackageImpl#getControlGridCell()
		 * @generated
		 */
		EClass CONTROL_GRID_CELL = eINSTANCE.getControlGridCell();

		/**
		 * The meta object literal for the '<em><b>Control</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONTROL_GRID_CELL__CONTROL = eINSTANCE.getControlGridCell_Control();

	}

} // VControlgridPackage
