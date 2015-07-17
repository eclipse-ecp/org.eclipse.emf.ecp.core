/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VCustomPackageImpl extends EPackageImpl implements VCustomPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass customControlEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass customDomainModelReferenceEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VCustomPackageImpl() {
		super(eNS_URI, VCustomFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VCustomPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VCustomPackage init() {
		if (isInited) {
			return (VCustomPackage) EPackage.Registry.INSTANCE.getEPackage(VCustomPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VCustomPackageImpl theCustomPackage = (VCustomPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VCustomPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VCustomPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCustomPackage.createPackageContents();

		// Initialize created meta-data
		theCustomPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCustomPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VCustomPackage.eNS_URI, theCustomPackage);
		return theCustomPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCustomControl()
	{
		return customControlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomControl_BundleName()
	{
		return (EAttribute) customControlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomControl_ClassName()
	{
		return (EAttribute) customControlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCustomDomainModelReference()
	{
		return customDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCustomDomainModelReference_DomainModelReferences()
	{
		return (EReference) customDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomDomainModelReference_BundleName()
	{
		return (EAttribute) customDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomDomainModelReference_ClassName()
	{
		return (EAttribute) customDomainModelReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomDomainModelReference_ControlChecked()
	{
		return (EAttribute) customDomainModelReferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCustomFactory getCustomFactory() {
		return (VCustomFactory) getEFactoryInstance();
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
		customControlEClass = createEClass(CUSTOM_CONTROL);
		createEAttribute(customControlEClass, CUSTOM_CONTROL__BUNDLE_NAME);
		createEAttribute(customControlEClass, CUSTOM_CONTROL__CLASS_NAME);

		customDomainModelReferenceEClass = createEClass(CUSTOM_DOMAIN_MODEL_REFERENCE);
		createEReference(customDomainModelReferenceEClass, CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES);
		createEAttribute(customDomainModelReferenceEClass, CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME);
		createEAttribute(customDomainModelReferenceEClass, CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME);
		createEAttribute(customDomainModelReferenceEClass, CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED);
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
		customControlEClass.getESuperTypes().add(theViewPackage.getControl());
		customDomainModelReferenceEClass.getESuperTypes().add(theViewPackage.getDomainModelReference());

		// Initialize classes and features; add operations and parameters
		initEClass(customControlEClass, VCustomControl.class,
			"CustomControl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getCustomControl_BundleName(),
			ecorePackage.getEString(),
			"bundleName", null, 1, 1, VCustomControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getCustomControl_ClassName(),
			ecorePackage.getEString(),
			"className", null, 1, 1, VCustomControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(customDomainModelReferenceEClass, VCustomDomainModelReference.class,
			"CustomDomainModelReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getCustomDomainModelReference_DomainModelReferences(),
			theViewPackage.getDomainModelReference(),
			null,
			"domainModelReferences", null, 0, -1, VCustomDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getCustomDomainModelReference_BundleName(),
			ecorePackage.getEString(),
			"bundleName", null, 1, 1, VCustomDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getCustomDomainModelReference_ClassName(),
			ecorePackage.getEString(),
			"className", null, 1, 1, VCustomDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getCustomDomainModelReference_ControlChecked(),
			ecorePackage.getEBoolean(),
			"controlChecked", "false", 1, 1, VCustomDomainModelReference.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);
	}

} // VCustomPackageImpl
