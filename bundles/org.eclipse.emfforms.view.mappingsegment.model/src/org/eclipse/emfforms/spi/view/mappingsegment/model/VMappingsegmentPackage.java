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
package org.eclipse.emfforms.spi.view.mappingsegment.model;

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
 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory
 * @model kind="package"
 * @generated
 */
public interface VMappingsegmentPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "mappingsegment"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http;//org/eclipse/emfforms/view/mappingsegment/model/200"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.view.mappingsegment.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VMappingsegmentPackage eINSTANCE = org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VDMRMappingSegmentImpl
	 * <em>DMR Mapping Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VDMRMappingSegmentImpl
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentPackageImpl#getDMRMappingSegment()
	 * @generated
	 */
	int DMR_MAPPING_SEGMENT = 0;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_MAPPING_SEGMENT__PROPERTY_NAME = VViewPackage.DMR_SEGMENT__PROPERTY_NAME;

	/**
	 * The feature id for the '<em><b>Mapped Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_MAPPING_SEGMENT__MAPPED_CLASS = VViewPackage.DMR_SEGMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DMR Mapping Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_MAPPING_SEGMENT_FEATURE_COUNT = VViewPackage.DMR_SEGMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment
	 * <em>DMR Mapping Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>DMR Mapping Segment</em>'.
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment
	 * @generated
	 */
	EClass getDMRMappingSegment();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment#getMappedClass
	 * <em>Mapped Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Mapped Class</em>'.
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment#getMappedClass()
	 * @see #getDMRMappingSegment()
	 * @generated
	 */
	EReference getDMRMappingSegment_MappedClass();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VMappingsegmentFactory getMappingsegmentFactory();

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
		 * {@link org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VDMRMappingSegmentImpl
		 * <em>DMR Mapping Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VDMRMappingSegmentImpl
		 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentPackageImpl#getDMRMappingSegment()
		 * @generated
		 */
		EClass DMR_MAPPING_SEGMENT = eINSTANCE.getDMRMappingSegment();

		/**
		 * The meta object literal for the '<em><b>Mapped Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DMR_MAPPING_SEGMENT__MAPPED_CLASS = eINSTANCE.getDMRMappingSegment_MappedClass();

	}

} // VMappingsegmentPackage
