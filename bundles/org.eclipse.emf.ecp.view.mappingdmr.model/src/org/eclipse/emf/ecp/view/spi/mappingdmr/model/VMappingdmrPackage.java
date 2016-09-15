/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.mappingdmr.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory
 * @model kind="package"
 * @generated
 */
public interface VMappingdmrPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "mappingdmr"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/mappingdmr/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.mappingdmr.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VMappingdmrPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingdmrPackageImpl.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl <em>Mapping Domain
	 * Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingdmrPackageImpl#getMappingDomainModelReference()
	 * @generated
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__SEGMENTS = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__SEGMENTS;

	/**
	 * The feature id for the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE;

	/**
	 * The feature id for the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH;

	/**
	 * The feature id for the '<em><b>Mapped Class</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT
		+ 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT
		+ 1;

	/**
	 * The number of structural features of the '<em>Mapping Domain Model Reference</em>' class.
	 * <!-- begin-user-doc
	 * -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT
		+ 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceSegmentImpl <em>Mapping
	 * Domain Model Reference Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceSegmentImpl
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingdmrPackageImpl#getMappingDomainModelReferenceSegment()
	 * @generated
	 */
	int MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT = 1;

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
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference <em>Mapping Domain Model
	 * Reference</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for class '<em>Mapping Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference
	 * @generated
	 */
	EClass getMappingDomainModelReference();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference#getMappedClass <em>Mapped
	 * Class</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Mapped Class</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference#getMappedClass()
	 * @see #getMappingDomainModelReference()
	 * @generated
	 */
	EReference getMappingDomainModelReference_MappedClass();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference#getDomainModelReference()
	 * @see #getMappingDomainModelReference()
	 * @generated
	 */
	EReference getMappingDomainModelReference_DomainModelReference();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment <em>Mapping Domain
	 * Model Reference Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Mapping Domain Model Reference Segment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment
	 * @generated
	 */
	EClass getMappingDomainModelReferenceSegment();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment#getMappedClass
	 * <em>Mapped Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Mapped Class</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment#getMappedClass()
	 * @see #getMappingDomainModelReferenceSegment()
	 * @generated
	 */
	EReference getMappingDomainModelReferenceSegment_MappedClass();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VMappingdmrFactory getMappingdmrFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
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
		 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl <em>Mapping
		 * Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingdmrPackageImpl#getMappingDomainModelReference()
		 * @generated
		 */
		EClass MAPPING_DOMAIN_MODEL_REFERENCE = eINSTANCE.getMappingDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Mapped Class</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS = eINSTANCE
			.getMappingDomainModelReference_MappedClass();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE = eINSTANCE
			.getMappingDomainModelReference_DomainModelReference();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceSegmentImpl
		 * <em>Mapping Domain Model Reference Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceSegmentImpl
		 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingdmrPackageImpl#getMappingDomainModelReferenceSegment()
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

} // VMappingdmrPackage
