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
package org.eclipse.emfforms.spi.view.mappingsegment.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VMappingsegmentPackageImpl extends EPackageImpl implements VMappingsegmentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingDomainModelReferenceSegmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VMappingsegmentPackageImpl() {
		super(eNS_URI, VMappingsegmentFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VMappingsegmentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VMappingsegmentPackage init() {
		if (isInited) {
			return (VMappingsegmentPackage) EPackage.Registry.INSTANCE.getEPackage(VMappingsegmentPackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredMappingsegmentPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final VMappingsegmentPackageImpl theMappingsegmentPackage = registeredMappingsegmentPackage instanceof VMappingsegmentPackageImpl
			? (VMappingsegmentPackageImpl) registeredMappingsegmentPackage
			: new VMappingsegmentPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMappingsegmentPackage.createPackageContents();

		// Initialize created meta-data
		theMappingsegmentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMappingsegmentPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VMappingsegmentPackage.eNS_URI, theMappingsegmentPackage);
		return theMappingsegmentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappingDomainModelReferenceSegment() {
		return mappingDomainModelReferenceSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingDomainModelReferenceSegment_MappedClass() {
		return (EReference) mappingDomainModelReferenceSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VMappingsegmentFactory getMappingsegmentFactory() {
		return (VMappingsegmentFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		mappingDomainModelReferenceSegmentEClass = createEClass(MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT);
		createEReference(mappingDomainModelReferenceSegmentEClass,
			MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		mappingDomainModelReferenceSegmentEClass.getESuperTypes()
			.add(theViewPackage.getFeatureDomainModelReferenceSegment());

		// Initialize classes and features; add operations and parameters
		initEClass(mappingDomainModelReferenceSegmentEClass, VMappingDomainModelReferenceSegment.class,
			"MappingDomainModelReferenceSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMappingDomainModelReferenceSegment_MappedClass(), theEcorePackage.getEClass(), null,
			"mappedClass", null, 1, 1, VMappingDomainModelReferenceSegment.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // VMappingsegmentPackageImpl
