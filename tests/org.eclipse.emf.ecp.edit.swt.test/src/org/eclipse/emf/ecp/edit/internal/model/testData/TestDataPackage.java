/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emf.ecp.edit.internal.model.testData;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestDataFactory
 * @model kind="package"
 * @generated
 */
public interface TestDataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "testData"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "org.eclipse.emf.ecp.edit.swt.test.data"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "testData"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TestDataPackage eINSTANCE = org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl <em>Test
	 * Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataPackageImpl#getTestData()
	 * @generated
	 */
	int TEST_DATA = 0;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__STRING = 0;

	/**
	 * The feature id for the '<em><b>Boolean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__BOOLEAN = 1;

	/**
	 * The feature id for the '<em><b>Integer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__INTEGER = 2;

	/**
	 * The feature id for the '<em><b>Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__LONG = 3;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__FLOAT = 4;

	/**
	 * The feature id for the '<em><b>Double</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__DOUBLE = 5;

	/**
	 * The feature id for the '<em><b>String Max8</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__STRING_MAX8 = 6;

	/**
	 * The number of structural features of the '<em>Test Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Test Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>String With Max Length8</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataPackageImpl#getStringWithMaxLength8()
	 * @generated
	 */
	int STRING_WITH_MAX_LENGTH8 = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData <em>Test
	 * Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Data</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData
	 * @generated
	 */
	EClass getTestData();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getString <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>String</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getString()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_String();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#isBoolean <em>Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Boolean</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#isBoolean()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Boolean();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getInteger <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Integer</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getInteger()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Integer();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getLong <em>Long</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Long</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getLong()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Long();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getFloat <em>Float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Float</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getFloat()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Float();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getDouble <em>Double</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Double</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getDouble()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Double();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getStringMax8 <em>String Max8</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>String Max8</em>'.
	 * @see org.eclipse.emf.ecp.edit.internal.model.testData.TestData#getStringMax8()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_StringMax8();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>String With Max Length8</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>String With Max Length8</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="maxLength='8'"
	 * @generated
	 */
	EDataType getStringWithMaxLength8();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestDataFactory getTestDataFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl
		 * <em>Test Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl
		 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataPackageImpl#getTestData()
		 * @generated
		 */
		EClass TEST_DATA = eINSTANCE.getTestData();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__STRING = eINSTANCE.getTestData_String();

		/**
		 * The meta object literal for the '<em><b>Boolean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__BOOLEAN = eINSTANCE.getTestData_Boolean();

		/**
		 * The meta object literal for the '<em><b>Integer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__INTEGER = eINSTANCE.getTestData_Integer();

		/**
		 * The meta object literal for the '<em><b>Long</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__LONG = eINSTANCE.getTestData_Long();

		/**
		 * The meta object literal for the '<em><b>Float</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__FLOAT = eINSTANCE.getTestData_Float();

		/**
		 * The meta object literal for the '<em><b>Double</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__DOUBLE = eINSTANCE.getTestData_Double();

		/**
		 * The meta object literal for the '<em><b>String Max8</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DATA__STRING_MAX8 = eINSTANCE.getTestData_StringMax8();

		/**
		 * The meta object literal for the '<em>String With Max Length8</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataPackageImpl#getStringWithMaxLength8()
		 * @generated
		 */
		EDataType STRING_WITH_MAX_LENGTH8 = eINSTANCE.getStringWithMaxLength8();

	}

} // TestDataPackage
