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
package org.eclipse.emf.ecp.view.spi.section.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionFactory;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VSectionPackageImpl extends EPackageImpl implements VSectionPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass sectionedAreaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass sectionEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VSectionPackageImpl()
	{
		super(eNS_URI, VSectionFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VSectionPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VSectionPackage init()
	{
		if (isInited) {
			return (VSectionPackage) EPackage.Registry.INSTANCE.getEPackage(VSectionPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VSectionPackageImpl theSectionPackage = (VSectionPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VSectionPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VSectionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSectionPackage.createPackageContents();

		// Initialize created meta-data
		theSectionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSectionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VSectionPackage.eNS_URI, theSectionPackage);
		return theSectionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSectionedArea()
	{
		return sectionedAreaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSectionedArea_Root()
	{
		return (EReference) sectionedAreaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSection()
	{
		return sectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSection_ChildItems()
	{
		return (EReference) sectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSection_Collapsed()
	{
		return (EAttribute) sectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VSectionFactory getSectionFactory()
	{
		return (VSectionFactory) getEFactoryInstance();
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
		sectionedAreaEClass = createEClass(SECTIONED_AREA);
		createEReference(sectionedAreaEClass, SECTIONED_AREA__ROOT);

		sectionEClass = createEClass(SECTION);
		createEReference(sectionEClass, SECTION__CHILD_ITEMS);
		createEAttribute(sectionEClass, SECTION__COLLAPSED);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		sectionedAreaEClass.getESuperTypes().add(theViewPackage.getContainedElement());
		sectionEClass.getESuperTypes().add(theViewPackage.getContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(sectionedAreaEClass, VSectionedArea.class,
			"SectionedArea", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getSectionedArea_Root(),
			getSection(),
			null,
			"root", null, 1, 1, VSectionedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(sectionEClass, VSection.class, "Section", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getSection_ChildItems(),
			getSection(),
			null,
			"childItems", null, 0, -1, VSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getSection_Collapsed(),
			ecorePackage.getEBoolean(),
			"collapsed", "false", 0, 1, VSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);
	}

} // VSectionPackageImpl
