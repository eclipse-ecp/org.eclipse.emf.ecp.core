/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.spi.diffmerge.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergeFactory;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VDiffmergePackageImpl extends EPackageImpl implements
	VDiffmergePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass diffAttachmentEClass = null;

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
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VDiffmergePackageImpl() {
		super(eNS_URI, VDiffmergeFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VDiffmergePackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VDiffmergePackage init() {
		if (isInited) {
			return (VDiffmergePackage) EPackage.Registry.INSTANCE.getEPackage(VDiffmergePackage.eNS_URI);
		}

		// Obtain or create and register package
		final VDiffmergePackageImpl theDiffmergePackage = (VDiffmergePackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VDiffmergePackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new VDiffmergePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDiffmergePackage.createPackageContents();

		// Initialize created meta-data
		theDiffmergePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiffmergePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VDiffmergePackage.eNS_URI, theDiffmergePackage);
		return theDiffmergePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDiffAttachment() {
		return diffAttachmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getDiffAttachment_TotalNumberOfDiffs()
	{
		return (EAttribute) diffAttachmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getDiffAttachment_MergedDiffs()
	{
		return (EAttribute) diffAttachmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDiffmergeFactory getDiffmergeFactory() {
		return (VDiffmergeFactory) getEFactoryInstance();
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
		diffAttachmentEClass = createEClass(DIFF_ATTACHMENT);
		createEAttribute(diffAttachmentEClass, DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS);
		createEAttribute(diffAttachmentEClass, DIFF_ATTACHMENT__MERGED_DIFFS);
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
		diffAttachmentEClass.getESuperTypes().add(theViewPackage.getAttachment());

		// Initialize classes and features; add operations and parameters
		initEClass(diffAttachmentEClass, VDiffAttachment.class,
			"DiffAttachment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getDiffAttachment_TotalNumberOfDiffs(),
			ecorePackage.getEInt(),
			"totalNumberOfDiffs", "0", 1, 1, VDiffAttachment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(
			getDiffAttachment_MergedDiffs(),
			ecorePackage.getEInt(),
			"mergedDiffs", "0", 0, 1, VDiffAttachment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);
	}

} // VDiffmergePackageImpl
