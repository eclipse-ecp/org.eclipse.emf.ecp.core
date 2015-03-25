/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.indexsegment.model;

import org.eclipse.emf.ecore.EAttribute;
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
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory
 * @model kind="package"
 * @generated
 */
public interface VIndexsegmentPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "indexsegment"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emfforms/view/indexsegment/model/200"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.view.indexsegment.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VIndexsegmentPackage eINSTANCE = org.eclipse.emfforms.spi.view.indexsegment.model.impl.VIndexsegmentPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.view.indexsegment.model.impl.VDMRIndexSegmentImpl
	 * <em>DMR Index Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.impl.VDMRIndexSegmentImpl
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.impl.VIndexsegmentPackageImpl#getDMRIndexSegment()
	 * @generated
	 */
	int DMR_INDEX_SEGMENT = 0;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_INDEX_SEGMENT__PROPERTY_NAME = VViewPackage.DMR_SEGMENT__PROPERTY_NAME;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_INDEX_SEGMENT__INDEX = VViewPackage.DMR_SEGMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DMR Index Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_INDEX_SEGMENT_FEATURE_COUNT = VViewPackage.DMR_SEGMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment
	 * <em>DMR Index Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>DMR Index Segment</em>'.
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment
	 * @generated
	 */
	EClass getDMRIndexSegment();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VDMRIndexSegment#getIndex()
	 * @see #getDMRIndexSegment()
	 * @generated
	 */
	EAttribute getDMRIndexSegment_Index();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VIndexsegmentFactory getIndexsegmentFactory();

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
		 * {@link org.eclipse.emfforms.spi.view.indexsegment.model.impl.VDMRIndexSegmentImpl <em>DMR Index Segment</em>}
		 * ' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.view.indexsegment.model.impl.VDMRIndexSegmentImpl
		 * @see org.eclipse.emfforms.spi.view.indexsegment.model.impl.VIndexsegmentPackageImpl#getDMRIndexSegment()
		 * @generated
		 */
		EClass DMR_INDEX_SEGMENT = eINSTANCE.getDMRIndexSegment();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute DMR_INDEX_SEGMENT__INDEX = eINSTANCE.getDMRIndexSegment_Index();

	}

} // VIndexsegmentPackage
