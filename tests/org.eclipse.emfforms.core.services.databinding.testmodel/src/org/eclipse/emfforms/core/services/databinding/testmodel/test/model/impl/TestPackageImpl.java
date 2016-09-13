/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class TestPackageImpl extends EPackageImpl implements TestPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass aEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass bEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eClassToEStringMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eClassToAMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eEClass = null;

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
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestPackageImpl() {
		super(eNS_URI, TestFactory.eINSTANCE);
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
	 * This method is used to initialize {@link TestPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestPackage init() {
		if (isInited)
			return (TestPackage) EPackage.Registry.INSTANCE.getEPackage(TestPackage.eNS_URI);

		// Obtain or create and register package
		TestPackageImpl theTestPackage = (TestPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof TestPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TestPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTestPackage.createPackageContents();

		// Initialize created meta-data
		theTestPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTestPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestPackage.eNS_URI, theTestPackage);
		return theTestPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getA() {
		return aEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getA_B() {
		return (EReference) aEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getB() {
		return bEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getB_C() {
		return (EReference) bEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getB_CList() {
		return (EReference) bEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getB_EList() {
		return (EReference) bEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getB_E() {
		return (EReference) bEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getC() {
		return cEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getC_D() {
		return (EReference) cEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getC_EClassToString() {
		return (EReference) cEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getC_EClassToA() {
		return (EReference) cEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getC_A() {
		return (EReference) cEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getD() {
		return dEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getD_X() {
		return (EAttribute) dEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getD_YList() {
		return (EAttribute) dEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getEClassToEStringMap() {
		return eClassToEStringMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getEClassToEStringMap_Key() {
		return (EReference) eClassToEStringMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEClassToEStringMap_Value() {
		return (EAttribute) eClassToEStringMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getEClassToAMap() {
		return eClassToAMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getEClassToAMap_Key() {
		return (EReference) eClassToAMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getEClassToAMap_Value() {
		return (EReference) eClassToAMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
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
	public TestFactory getTestFactory() {
		return (TestFactory) getEFactoryInstance();
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		aEClass = createEClass(A);
		createEReference(aEClass, A__B);

		bEClass = createEClass(B);
		createEReference(bEClass, B__C);
		createEReference(bEClass, B__CLIST);
		createEReference(bEClass, B__ELIST);
		createEReference(bEClass, B__E);

		cEClass = createEClass(C);
		createEReference(cEClass, C__D);
		createEReference(cEClass, C__ECLASS_TO_STRING);
		createEReference(cEClass, C__ECLASS_TO_A);
		createEReference(cEClass, C__A);

		dEClass = createEClass(D);
		createEAttribute(dEClass, D__X);
		createEAttribute(dEClass, D__YLIST);

		eClassToEStringMapEClass = createEClass(ECLASS_TO_ESTRING_MAP);
		createEReference(eClassToEStringMapEClass, ECLASS_TO_ESTRING_MAP__KEY);
		createEAttribute(eClassToEStringMapEClass, ECLASS_TO_ESTRING_MAP__VALUE);

		eClassToAMapEClass = createEClass(ECLASS_TO_AMAP);
		createEReference(eClassToAMapEClass, ECLASS_TO_AMAP__KEY);
		createEReference(eClassToAMapEClass, ECLASS_TO_AMAP__VALUE);

		eEClass = createEClass(E);
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
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		aEClass.getESuperTypes().add(this.getE());

		// Initialize classes and features; add operations and parameters
		initEClass(aEClass, org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A.class, "A", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getA_B(), this.getB(), null, "b", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bEClass, org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B.class, "B", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getB_C(), this.getC(), null, "c", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getB_CList(), this.getC(), null, "cList", null, 0, -1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getB_EList(), this.getE(), null, "eList", null, 0, -1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getB_E(), this.getE(), null, "e", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cEClass, org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C.class, "C", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getC_D(), this.getD(), null, "d", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getC_EClassToString(), this.getEClassToEStringMap(), null, "eClassToString", null, 0, -1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getC_EClassToA(), this.getEClassToAMap(), null, "eClassToA", null, 0, -1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getC_A(), this.getA(), null, "a", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dEClass, org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D.class, "D", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getD_X(), ecorePackage.getEString(), "x", null, 0, 1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getD_YList(), ecorePackage.getEInt(), "yList", null, 0, -1, //$NON-NLS-1$
			org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eClassToEStringMapEClass, Map.Entry.class, "EClassToEStringMap", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEClassToEStringMap_Key(), ecorePackage.getEClass(), null, "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEClassToEStringMap_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eClassToAMapEClass, Map.Entry.class, "EClassToAMap", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEClassToAMap_Key(), ecorePackage.getEClass(), null, "key", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getEClassToAMap_Value(), this.getA(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, //$NON-NLS-1$
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(eEClass, org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E.class, "E", //$NON-NLS-1$
			IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // TestPackageImpl
