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
package org.eclipse.emfforms.spi.view.indexsegment.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VIndexsegmentPackageImpl extends EPackageImpl implements VIndexsegmentPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass indexDomainModelReferenceSegmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VIndexsegmentPackageImpl() {
		super(eNS_URI, VIndexsegmentFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VIndexsegmentPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VIndexsegmentPackage init() {
		if (isInited) {
			return (VIndexsegmentPackage) EPackage.Registry.INSTANCE.getEPackage(VIndexsegmentPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredIndexsegmentPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final VIndexsegmentPackageImpl theIndexsegmentPackage = registeredIndexsegmentPackage instanceof VIndexsegmentPackageImpl
			? (VIndexsegmentPackageImpl) registeredIndexsegmentPackage
			: new VIndexsegmentPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIndexsegmentPackage.createPackageContents();

		// Initialize created meta-data
		theIndexsegmentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIndexsegmentPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VIndexsegmentPackage.eNS_URI, theIndexsegmentPackage);
		return theIndexsegmentPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIndexDomainModelReferenceSegment() {
		return indexDomainModelReferenceSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getIndexDomainModelReferenceSegment_Index() {
		return (EAttribute) indexDomainModelReferenceSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VIndexsegmentFactory getIndexsegmentFactory() {
		return (VIndexsegmentFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		indexDomainModelReferenceSegmentEClass = createEClass(INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT);
		createEAttribute(indexDomainModelReferenceSegmentEClass, INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		indexDomainModelReferenceSegmentEClass.getESuperTypes()
			.add(theViewPackage.getFeatureDomainModelReferenceSegment());

		// Initialize classes and features; add operations and parameters
		initEClass(indexDomainModelReferenceSegmentEClass, VIndexDomainModelReferenceSegment.class,
			"IndexDomainModelReferenceSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIndexDomainModelReferenceSegment_Index(), ecorePackage.getEInt(), "index", "0", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
			VIndexDomainModelReferenceSegment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // VIndexsegmentPackageImpl
