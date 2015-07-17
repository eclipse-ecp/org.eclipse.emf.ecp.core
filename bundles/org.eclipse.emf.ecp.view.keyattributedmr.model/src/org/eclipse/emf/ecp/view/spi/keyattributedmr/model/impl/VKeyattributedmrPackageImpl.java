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
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrFactory;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.util.KeyattributedmrValidator;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VKeyattributedmrPackageImpl extends EPackageImpl implements
	VKeyattributedmrPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass keyAttributeDomainModelReferenceEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VKeyattributedmrPackageImpl() {
		super(eNS_URI, VKeyattributedmrFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VKeyattributedmrPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VKeyattributedmrPackage init() {
		if (isInited) {
			return (VKeyattributedmrPackage) EPackage.Registry.INSTANCE.getEPackage(VKeyattributedmrPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VKeyattributedmrPackageImpl theKeyattributedmrPackage = (VKeyattributedmrPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VKeyattributedmrPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VKeyattributedmrPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theKeyattributedmrPackage.createPackageContents();

		// Initialize created meta-data
		theKeyattributedmrPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theKeyattributedmrPackage,
				new EValidator.Descriptor()
				{
					@Override
					public EValidator getEValidator()
					{
						return KeyattributedmrValidator.INSTANCE;
					}
				});

		// Mark meta-data to indicate it can't be changed
		theKeyattributedmrPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VKeyattributedmrPackage.eNS_URI, theKeyattributedmrPackage);
		return theKeyattributedmrPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getKeyAttributeDomainModelReference() {
		return keyAttributeDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getKeyAttributeDomainModelReference_KeyDMR()
	{
		return (EReference) keyAttributeDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getKeyAttributeDomainModelReference_KeyValue()
	{
		return (EAttribute) keyAttributeDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getKeyAttributeDomainModelReference_ValueDMR()
	{
		return (EReference) keyAttributeDomainModelReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VKeyattributedmrFactory getKeyattributedmrFactory() {
		return (VKeyattributedmrFactory) getEFactoryInstance();
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
		keyAttributeDomainModelReferenceEClass = createEClass(KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE);
		createEReference(keyAttributeDomainModelReferenceEClass, KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR);
		createEAttribute(keyAttributeDomainModelReferenceEClass, KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE);
		createEReference(keyAttributeDomainModelReferenceEClass, KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		keyAttributeDomainModelReferenceEClass.getESuperTypes()
			.add(theViewPackage.getFeaturePathDomainModelReference());

		// Initialize classes and features; add operations and parameters
		initEClass(keyAttributeDomainModelReferenceEClass, VKeyAttributeDomainModelReference.class,
			"KeyAttributeDomainModelReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getKeyAttributeDomainModelReference_KeyDMR(),
			theViewPackage.getDomainModelReference(),
			null,
			"keyDMR", null, 1, 1, VKeyAttributeDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getKeyAttributeDomainModelReference_KeyValue(),
			ecorePackage.getEJavaObject(),
			"keyValue", null, 1, 1, VKeyAttributeDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getKeyAttributeDomainModelReference_ValueDMR(),
			theViewPackage.getDomainModelReference(),
			null,
			"valueDMR", null, 1, 1, VKeyAttributeDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
	protected void createEcoreAnnotations()
	{
		final String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation(keyAttributeDomainModelReferenceEClass,
			source,
			new String[]
			{ "constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // VKeyattributedmrPackageImpl
