/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.stack.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackFactory;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VStackPackageImpl extends EPackageImpl implements VStackPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass stackLayoutEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass stackItemEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VStackPackageImpl()
	{
		super(eNS_URI, VStackFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VStackPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VStackPackage init()
	{
		if (isInited) {
			return (VStackPackage) EPackage.Registry.INSTANCE.getEPackage(VStackPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VStackPackageImpl theStackPackage = (VStackPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VStackPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VStackPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theStackPackage.createPackageContents();

		// Initialize created meta-data
		theStackPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStackPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VStackPackage.eNS_URI, theStackPackage);
		return theStackPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStackLayout()
	{
		return stackLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStackLayout_DomainModelReference()
	{
		return (EReference) stackLayoutEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStackLayout_StackItems()
	{
		return (EReference) stackLayoutEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStackLayout_TopElement()
	{
		return (EReference) stackLayoutEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStackItem()
	{
		return stackItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getStackItem_Value()
	{
		return (EAttribute) stackItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VStackFactory getStackFactory()
	{
		return (VStackFactory) getEFactoryInstance();
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
	public void createPackageContents()
	{
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		stackLayoutEClass = createEClass(STACK_LAYOUT);
		createEReference(stackLayoutEClass, STACK_LAYOUT__DOMAIN_MODEL_REFERENCE);
		createEReference(stackLayoutEClass, STACK_LAYOUT__STACK_ITEMS);
		createEReference(stackLayoutEClass, STACK_LAYOUT__TOP_ELEMENT);

		stackItemEClass = createEClass(STACK_ITEM);
		createEAttribute(stackItemEClass, STACK_ITEM__VALUE);
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
	public void initializePackageContents()
	{
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
		stackLayoutEClass.getESuperTypes().add(theViewPackage.getContainedElement());
		stackItemEClass.getESuperTypes().add(theViewPackage.getContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(stackLayoutEClass, VStackLayout.class,
			"StackLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getStackLayout_DomainModelReference(),
			theViewPackage.getDomainModelReference(),
			null,
			"domainModelReference", null, 0, 1, VStackLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getStackLayout_StackItems(),
			getStackItem(),
			null,
			"stackItems", null, 0, -1, VStackLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getStackLayout_TopElement(),
			getStackItem(),
			null,
			"topElement", null, 0, 1, VStackLayout.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stackItemEClass, VStackItem.class,
			"StackItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getStackItem_Value(),
			theEcorePackage.getEJavaObject(),
			"value", null, 0, 1, VStackItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VStackPackageImpl
