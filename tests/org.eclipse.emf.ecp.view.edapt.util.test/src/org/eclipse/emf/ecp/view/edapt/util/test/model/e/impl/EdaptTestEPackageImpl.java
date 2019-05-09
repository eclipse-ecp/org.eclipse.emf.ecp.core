/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.EdaptTestAPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.impl.EdaptTestAPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.EdaptTestBPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl.EdaptTestBPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.EdaptTestCPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.impl.EdaptTestCPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.EdaptTestDPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl.EdaptTestDPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestE;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestEFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestEPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestFPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl.EdaptTestFPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestWPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.w.impl.EdaptTestWPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.x.EdaptTestXPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.x.impl.EdaptTestXPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.y.EdaptTestYPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.y.impl.EdaptTestYPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.z.impl.EdaptTestZPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class EdaptTestEPackageImpl extends EPackageImpl implements EdaptTestEPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestEPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EdaptTestEPackageImpl() {
		super(eNS_URI, EdaptTestEFactory.eINSTANCE);
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
	 * This method is used to initialize {@link EdaptTestEPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EdaptTestEPackage init() {
		if (isInited) {
			return (EdaptTestEPackage) EPackage.Registry.INSTANCE.getEPackage(EdaptTestEPackage.eNS_URI);
		}

		// Obtain or create and register package
		final EdaptTestEPackageImpl theEPackage = (EdaptTestEPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof EdaptTestEPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new EdaptTestEPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		final EdaptTestAPackageImpl theAPackage = (EdaptTestAPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestAPackage.eNS_URI) instanceof EdaptTestAPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestAPackage.eNS_URI) : EdaptTestAPackage.eINSTANCE);
		final EdaptTestBPackageImpl theBPackage = (EdaptTestBPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestBPackage.eNS_URI) instanceof EdaptTestBPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestBPackage.eNS_URI) : EdaptTestBPackage.eINSTANCE);
		final EdaptTestCPackageImpl theCPackage = (EdaptTestCPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestCPackage.eNS_URI) instanceof EdaptTestCPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestCPackage.eNS_URI) : EdaptTestCPackage.eINSTANCE);
		final EdaptTestDPackageImpl theDPackage = (EdaptTestDPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestDPackage.eNS_URI) instanceof EdaptTestDPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestDPackage.eNS_URI) : EdaptTestDPackage.eINSTANCE);
		final EdaptTestFPackageImpl theFPackage = (EdaptTestFPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestFPackage.eNS_URI) instanceof EdaptTestFPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestFPackage.eNS_URI) : EdaptTestFPackage.eINSTANCE);
		final EdaptTestWPackageImpl theWPackage = (EdaptTestWPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestWPackage.eNS_URI) instanceof EdaptTestWPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestWPackage.eNS_URI) : EdaptTestWPackage.eINSTANCE);
		final EdaptTestXPackageImpl theXPackage = (EdaptTestXPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestXPackage.eNS_URI) instanceof EdaptTestXPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestXPackage.eNS_URI) : EdaptTestXPackage.eINSTANCE);
		final EdaptTestYPackageImpl theYPackage = (EdaptTestYPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestYPackage.eNS_URI) instanceof EdaptTestYPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestYPackage.eNS_URI) : EdaptTestYPackage.eINSTANCE);
		final EdaptTestZPackageImpl theZPackage = (EdaptTestZPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestZPackage.eNS_URI) instanceof EdaptTestZPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(EdaptTestZPackage.eNS_URI) : EdaptTestZPackage.eINSTANCE);

		// Create package meta-data objects
		theEPackage.createPackageContents();
		theAPackage.createPackageContents();
		theBPackage.createPackageContents();
		theCPackage.createPackageContents();
		theDPackage.createPackageContents();
		theFPackage.createPackageContents();
		theWPackage.createPackageContents();
		theXPackage.createPackageContents();
		theYPackage.createPackageContents();
		theZPackage.createPackageContents();

		// Initialize created meta-data
		theEPackage.initializePackageContents();
		theAPackage.initializePackageContents();
		theBPackage.initializePackageContents();
		theCPackage.initializePackageContents();
		theDPackage.initializePackageContents();
		theFPackage.initializePackageContents();
		theWPackage.initializePackageContents();
		theXPackage.initializePackageContents();
		theYPackage.initializePackageContents();
		theZPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EdaptTestEPackage.eNS_URI, theEPackage);
		return theEPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getE() {
		return eEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getE_D() {
		return (EReference) eEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getE_F() {
		return (EReference) eEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EdaptTestEFactory getEFactory() {
		return (EdaptTestEFactory) getEFactoryInstance();
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
		eEClass = createEClass(E);
		createEReference(eEClass, E__D);
		createEReference(eEClass, E__F);
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
		final EdaptTestDPackage theDPackage = (EdaptTestDPackage) EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestDPackage.eNS_URI);
		final EdaptTestFPackage theFPackage = (EdaptTestFPackage) EPackage.Registry.INSTANCE
			.getEPackage(EdaptTestFPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(eEClass, EdaptTestE.class, "E", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getE_D(), theDPackage.getD(), null, "d", null, 0, 1, EdaptTestE.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(getE_F(), theFPackage.getF(), null, "f", null, 0, 1, EdaptTestE.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // EdaptTestEPackageImpl
