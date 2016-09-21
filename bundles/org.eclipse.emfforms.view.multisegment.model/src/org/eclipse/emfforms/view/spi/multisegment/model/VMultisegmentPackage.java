/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.view.spi.multisegment.model;

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
 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory
 * @model kind="package"
 * @generated
 */
public interface VMultisegmentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "multisegment"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emfforms/view/multisegment/model/200"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.view.multisegment.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VMultisegmentPackage eINSTANCE = org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultisegmentPackageImpl
		.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultiDomainModelReferenceSegmentImpl <em>Multi
	 * Domain Model Reference Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultiDomainModelReferenceSegmentImpl
	 * @see org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultisegmentPackageImpl#getMultiDomainModelReferenceSegment()
	 * @generated
	 */
	int MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT = 0;

	/**
	 * The feature id for the '<em><b>Domain Model Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE;

	/**
	 * The feature id for the '<em><b>Child Domain Model References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT
		+ 0;

	/**
	 * The number of structural features of the '<em>Multi Domain Model Reference Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT = VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT
		+ 1;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment <em>Multi Domain Model
	 * Reference Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Multi Domain Model Reference Segment</em>'.
	 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment
	 * @generated
	 */
	EClass getMultiDomainModelReferenceSegment();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment#getChildDomainModelReferences
	 * <em>Child Domain Model References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Child Domain Model References</em>'.
	 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment#getChildDomainModelReferences()
	 * @see #getMultiDomainModelReferenceSegment()
	 * @generated
	 */
	EReference getMultiDomainModelReferenceSegment_ChildDomainModelReferences();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VMultisegmentFactory getMultisegmentFactory();

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
		 * '{@link org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultiDomainModelReferenceSegmentImpl <em>Multi
		 * Domain Model Reference Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultiDomainModelReferenceSegmentImpl
		 * @see org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultisegmentPackageImpl#getMultiDomainModelReferenceSegment()
		 * @generated
		 */
		EClass MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT = eINSTANCE.getMultiDomainModelReferenceSegment();

		/**
		 * The meta object literal for the '<em><b>Child Domain Model References</b></em>' containment reference list
		 * feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES = eINSTANCE
			.getMultiDomainModelReferenceSegment_ChildDomainModelReferences();

	}

} // VMultisegmentPackage
