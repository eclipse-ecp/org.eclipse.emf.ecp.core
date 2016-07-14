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
package org.eclipse.emf.ecp.view.spi.indexdmr.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.util.IndexdmrValidator;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VIndexdmrPackageImpl extends EPackageImpl implements VIndexdmrPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass indexDomainModelReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass indexDomainModelReferenceSegmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VIndexdmrPackageImpl() {
		super(eNS_URI, VIndexdmrFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VIndexdmrPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VIndexdmrPackage init() {
		if (isInited) {
			return (VIndexdmrPackage) EPackage.Registry.INSTANCE.getEPackage(VIndexdmrPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VIndexdmrPackageImpl theIndexdmrPackage = (VIndexdmrPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VIndexdmrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VIndexdmrPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIndexdmrPackage.createPackageContents();

		// Initialize created meta-data
		theIndexdmrPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theIndexdmrPackage,
			new EValidator.Descriptor() {
				@Override
				public EValidator getEValidator() {
					return IndexdmrValidator.INSTANCE;
				}
			});

		// Mark meta-data to indicate it can't be changed
		theIndexdmrPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VIndexdmrPackage.eNS_URI, theIndexdmrPackage);
		return theIndexdmrPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIndexDomainModelReference() {
		return indexDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIndexDomainModelReference_PrefixDMR() {
		return (EReference) indexDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIndexDomainModelReference_TargetDMR() {
		return (EReference) indexDomainModelReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIndexDomainModelReferenceSegment() {
		return indexDomainModelReferenceSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getIndexDomainModelReferenceSegment_Index() {
		return (EAttribute) indexDomainModelReferenceSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getIndexDomainModelReference_Index() {
		return (EAttribute) indexDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VIndexdmrFactory getIndexdmrFactory() {
		return (VIndexdmrFactory) getEFactoryInstance();
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
		indexDomainModelReferenceEClass = createEClass(INDEX_DOMAIN_MODEL_REFERENCE);
		createEReference(indexDomainModelReferenceEClass, INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR);
		createEAttribute(indexDomainModelReferenceEClass, INDEX_DOMAIN_MODEL_REFERENCE__INDEX);
		createEReference(indexDomainModelReferenceEClass, INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR);

		indexDomainModelReferenceSegmentEClass = createEClass(INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT);
		createEAttribute(indexDomainModelReferenceSegmentEClass, INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		indexDomainModelReferenceEClass.getESuperTypes().add(theViewPackage.getFeaturePathDomainModelReference());
		indexDomainModelReferenceSegmentEClass.getESuperTypes()
			.add(theViewPackage.getFeatureDomainModelReferenceSegment());

		// Initialize classes and features; add operations and parameters
		initEClass(indexDomainModelReferenceEClass, VIndexDomainModelReference.class, "IndexDomainModelReference", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIndexDomainModelReference_PrefixDMR(), theViewPackage.getDomainModelReference(), null,
			"prefixDMR", null, 1, 1, VIndexDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIndexDomainModelReference_Index(), ecorePackage.getEInt(), "index", null, 0, 1, //$NON-NLS-1$
			VIndexDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIndexDomainModelReference_TargetDMR(), theViewPackage.getDomainModelReference(), null,
			"targetDMR", null, 1, 1, VIndexDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(indexDomainModelReferenceSegmentEClass, VIndexDomainModelReferenceSegment.class,
			"IndexDomainModelReferenceSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getIndexDomainModelReferenceSegment_Index(), ecorePackage.getEInt(), "index", null, 0, 1, //$NON-NLS-1$
			VIndexDomainModelReferenceSegment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		addAnnotation(indexDomainModelReferenceEClass,
			source,
			new String[] {
				"constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // VIndexdmrPackageImpl
