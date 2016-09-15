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
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.util.MappingdmrValidator;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VMappingdmrPackageImpl extends EPackageImpl implements
	VMappingdmrPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingDomainModelReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingDomainModelReferenceSegmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VMappingdmrPackageImpl() {
		super(eNS_URI, VMappingdmrFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VMappingdmrPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VMappingdmrPackage init() {
		if (isInited) {
			return (VMappingdmrPackage) EPackage.Registry.INSTANCE.getEPackage(VMappingdmrPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VMappingdmrPackageImpl theMappingdmrPackage = (VMappingdmrPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VMappingdmrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VMappingdmrPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMappingdmrPackage.createPackageContents();

		// Initialize created meta-data
		theMappingdmrPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theMappingdmrPackage,
			new EValidator.Descriptor() {
				@Override
				public EValidator getEValidator() {
					return MappingdmrValidator.INSTANCE;
				}
			});

		// Mark meta-data to indicate it can't be changed
		theMappingdmrPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VMappingdmrPackage.eNS_URI, theMappingdmrPackage);
		return theMappingdmrPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappingDomainModelReference() {
		return mappingDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingDomainModelReference_MappedClass() {
		return (EReference) mappingDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingDomainModelReference_DomainModelReference() {
		return (EReference) mappingDomainModelReferenceEClass.getEStructuralFeatures().get(1);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VMappingdmrFactory getMappingdmrFactory() {
		return (VMappingdmrFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		mappingDomainModelReferenceEClass = createEClass(MAPPING_DOMAIN_MODEL_REFERENCE);
		createEReference(mappingDomainModelReferenceEClass, MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS);
		createEReference(mappingDomainModelReferenceEClass, MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE);

		mappingDomainModelReferenceSegmentEClass = createEClass(MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT);
		createEReference(mappingDomainModelReferenceSegmentEClass,
			MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
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
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		mappingDomainModelReferenceEClass.getESuperTypes().add(theViewPackage.getFeaturePathDomainModelReference());
		mappingDomainModelReferenceSegmentEClass.getESuperTypes()
			.add(theViewPackage.getFeatureDomainModelReferenceSegment());

		// Initialize classes and features; add operations and parameters
		initEClass(mappingDomainModelReferenceEClass, VMappingDomainModelReference.class, "MappingDomainModelReference", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappingDomainModelReference_MappedClass(), theEcorePackage.getEClass(), null, "mappedClass", //$NON-NLS-1$
			null, 0, 1, VMappingDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMappingDomainModelReference_DomainModelReference(), theViewPackage.getDomainModelReference(),
			null, "domainModelReference", null, 1, 1, VMappingDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingDomainModelReferenceSegmentEClass, VMappingDomainModelReferenceSegment.class,
			"MappingDomainModelReferenceSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMappingDomainModelReferenceSegment_MappedClass(), theEcorePackage.getEClass(), null,
			"mappedClass", null, 1, 1, VMappingDomainModelReferenceSegment.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		final String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation(mappingDomainModelReferenceEClass,
			source,
			new String[] {
				"constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // VMappingdmrPackageImpl
