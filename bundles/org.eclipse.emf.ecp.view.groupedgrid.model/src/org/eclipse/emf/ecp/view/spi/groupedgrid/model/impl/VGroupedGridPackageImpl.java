/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridFactory;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 * @since 1.2
 */
public class VGroupedGridPackageImpl extends EPackageImpl implements VGroupedGridPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass groupedGridEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass groupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass rowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass spanEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VGroupedGridPackageImpl()
	{
		super(eNS_URI, VGroupedGridFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VGroupedGridPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VGroupedGridPackage init()
	{
		if (isInited) {
			return (VGroupedGridPackage) EPackage.Registry.INSTANCE.getEPackage(VGroupedGridPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VGroupedGridPackageImpl theGroupedGridPackage = (VGroupedGridPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VGroupedGridPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VGroupedGridPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGroupedGridPackage.createPackageContents();

		// Initialize created meta-data
		theGroupedGridPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGroupedGridPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VGroupedGridPackage.eNS_URI, theGroupedGridPackage);
		return theGroupedGridPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getGroupedGrid()
	{
		return groupedGridEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGroupedGrid_Groups()
	{
		return (EReference) groupedGridEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getGroup()
	{
		return groupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGroup_Rows()
	{
		return (EReference) groupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGroup_Name()
	{
		return (EAttribute) groupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRow()
	{
		return rowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRow_Children()
	{
		return (EReference) rowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSpan()
	{
		return spanEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSpan_HorizontalSpan()
	{
		return (EAttribute) spanEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VGroupedGridFactory getGroupedGridFactory()
	{
		return (VGroupedGridFactory) getEFactoryInstance();
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
		groupedGridEClass = createEClass(GROUPED_GRID);
		createEReference(groupedGridEClass, GROUPED_GRID__GROUPS);

		groupEClass = createEClass(GROUP);
		createEReference(groupEClass, GROUP__ROWS);
		createEAttribute(groupEClass, GROUP__NAME);

		rowEClass = createEClass(ROW);
		createEReference(rowEClass, ROW__CHILDREN);

		spanEClass = createEClass(SPAN);
		createEAttribute(spanEClass, SPAN__HORIZONTAL_SPAN);
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
		groupedGridEClass.getESuperTypes().add(theViewPackage.getContainedElement());
		spanEClass.getESuperTypes().add(theViewPackage.getAttachment());

		// Initialize classes and features; add operations and parameters
		initEClass(groupedGridEClass, VGroupedGrid.class,
			"GroupedGrid", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getGroupedGrid_Groups(),
			getGroup(),
			null,
			"groups", null, 0, -1, VGroupedGrid.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(groupEClass, VGroup.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getGroup_Rows(),
			getRow(),
			null,
			"rows", null, 0, -1, VGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getGroup_Name(),
			ecorePackage.getEString(),
			"name", null, 1, 1, VGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(rowEClass, VRow.class, "Row", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getRow_Children(),
			theViewPackage.getContainedElement(),
			null,
			"children", null, 0, -1, VRow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(spanEClass, VSpan.class, "Span", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getSpan_HorizontalSpan(),
			ecorePackage.getEInt(),
			"horizontalSpan", null, 1, 1, VSpan.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VGroupedGridPackageImpl
