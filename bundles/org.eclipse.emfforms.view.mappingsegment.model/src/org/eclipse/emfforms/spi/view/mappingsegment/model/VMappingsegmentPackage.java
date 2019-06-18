/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
public interface VMappingsegmentPackage extends EPackage {
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
	String eNS_URI = "http://org/eclipse/emfforms/view/mappingsegment/model/1190"; //$NON-NLS-1$

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
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingDomainModelReferenceSegmentImpl
	 * <em>Mapping Domain Model Reference Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingDomainModelReferenceSegmentImpl
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentPackageImpl#getMappingDomainModelReferenceSegment()
	 * @generated
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT = 0;

	/**
	 * The feature id for the '<em><b>Domain Model Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE;

	/**
	 * The feature id for the '<em><b>Mapped Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT
		+ 0;

	/**
	 * The number of structural features of the '<em>Mapping Domain Model Reference Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT
		+ 1;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment <em>Mapping Domain
	 * Model Reference Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Mapping Domain Model Reference Segment</em>'.
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment
	 * @generated
	 */
	EClass getMappingDomainModelReferenceSegment();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment#getMappedClass
	 * <em>Mapped Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Mapped Class</em>'.
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment#getMappedClass()
	 * @see #getMappingDomainModelReferenceSegment()
	 * @generated
	 */
	EReference getMappingDomainModelReferenceSegment_MappedClass();

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
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingDomainModelReferenceSegmentImpl
		 * <em>Mapping Domain Model Reference Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingDomainModelReferenceSegmentImpl
		 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentPackageImpl#getMappingDomainModelReferenceSegment()
		 * @generated
		 */
		EClass MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT = eINSTANCE.getMappingDomainModelReferenceSegment();

		/**
		 * The meta object literal for the '<em><b>Mapped Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS = eINSTANCE
			.getMappingDomainModelReferenceSegment_MappedClass();

	}

} // VMappingsegmentPackage
