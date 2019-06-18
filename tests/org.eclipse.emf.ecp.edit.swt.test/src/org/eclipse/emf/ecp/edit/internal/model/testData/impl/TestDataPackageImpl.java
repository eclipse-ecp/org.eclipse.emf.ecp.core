/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emf.ecp.edit.internal.model.testData.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestData;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestDataFactory;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestDataPackage;
import org.eclipse.emf.ecp.edit.internal.model.testData.util.TestDataValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class TestDataPackageImpl extends EPackageImpl implements TestDataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType stringWithMaxLength8EDataType = null;

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
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestDataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestDataPackageImpl() {
		super(eNS_URI, TestDataFactory.eINSTANCE);
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
	 * This method is used to initialize {@link TestDataPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestDataPackage init() {
		if (isInited) {
			return (TestDataPackage) EPackage.Registry.INSTANCE.getEPackage(TestDataPackage.eNS_URI);
		}

		// Obtain or create and register package
		final TestDataPackageImpl theTestDataPackage = (TestDataPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof TestDataPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new TestDataPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTestDataPackage.createPackageContents();

		// Initialize created meta-data
		theTestDataPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theTestDataPackage,
			new EValidator.Descriptor() {
				public EValidator getEValidator() {
					return TestDataValidator.INSTANCE;
				}
			});

		// Mark meta-data to indicate it can't be changed
		theTestDataPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestDataPackage.eNS_URI, theTestDataPackage);
		return theTestDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestData() {
		return testDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_String() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_Boolean() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_Integer() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_Long() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_Float() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_Double() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestData_StringMax8() {
		return (EAttribute) testDataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getStringWithMaxLength8() {
		return stringWithMaxLength8EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestDataFactory getTestDataFactory() {
		return (TestDataFactory) getEFactoryInstance();
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
		testDataEClass = createEClass(TEST_DATA);
		createEAttribute(testDataEClass, TEST_DATA__STRING);
		createEAttribute(testDataEClass, TEST_DATA__BOOLEAN);
		createEAttribute(testDataEClass, TEST_DATA__INTEGER);
		createEAttribute(testDataEClass, TEST_DATA__LONG);
		createEAttribute(testDataEClass, TEST_DATA__FLOAT);
		createEAttribute(testDataEClass, TEST_DATA__DOUBLE);
		createEAttribute(testDataEClass, TEST_DATA__STRING_MAX8);

		// Create data types
		stringWithMaxLength8EDataType = createEDataType(STRING_WITH_MAX_LENGTH8);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(testDataEClass, TestData.class, "TestData", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestData_String(), ecorePackage.getEString(), "string", null, 0, 1, TestData.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Boolean(), ecorePackage.getEBoolean(), "boolean", null, 0, 1, TestData.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Integer(), ecorePackage.getEInt(), "integer", null, 0, 1, TestData.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Long(), ecorePackage.getELong(), "long", null, 0, 1, TestData.class, !IS_TRANSIENT, //$NON-NLS-1$
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Float(), ecorePackage.getEFloat(), "float", null, 0, 1, TestData.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Double(), ecorePackage.getEDouble(), "double", null, 0, 1, TestData.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_StringMax8(), getStringWithMaxLength8(), "stringMax8", null, 0, 1, //$NON-NLS-1$
			TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		// Initialize data types
		initEDataType(stringWithMaxLength8EDataType, String.class, "StringWithMaxLength8", IS_SERIALIZABLE, //$NON-NLS-1$
			!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		final String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation(stringWithMaxLength8EDataType,
			source,
			new String[] {
				"maxLength", "8" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // TestDataPackageImpl
