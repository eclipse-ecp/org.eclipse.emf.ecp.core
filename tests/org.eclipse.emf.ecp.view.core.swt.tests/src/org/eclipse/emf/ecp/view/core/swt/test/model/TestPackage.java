/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - extension for EnumComboViewerRenderer_PTest
 */
package org.eclipse.emf.ecp.view.core.swt.test.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory
 * @model kind="package"
 * @generated
 */
public interface TestPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "test"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/emf/ecp/core/test"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.core.test"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TestPackage eINSTANCE = org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl
	 * <em>Simple Test Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getSimpleTestObject()
	 * @generated
	 */
	int SIMPLE_TEST_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TEST_OBJECT__DATE = 0;

	/**
	 * The feature id for the '<em><b>Xml Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TEST_OBJECT__XML_DATE = 1;

	/**
	 * The feature id for the '<em><b>My Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TEST_OBJECT__MY_ENUM = 2;

	/**
	 * The number of structural features of the '<em>Simple Test Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TEST_OBJECT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Simple Test Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TEST_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum <em>Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getTestEnum()
	 * @generated
	 */
	int TEST_ENUM = 1;

	/**
	 * The meta object id for the '<em>XML Date</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see javax.xml.datatype.XMLGregorianCalendar
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getXMLDate()
	 * @generated
	 */
	int XML_DATE = 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject
	 * <em>Simple Test Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Simple Test Object</em>'.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject
	 * @generated
	 */
	EClass getSimpleTestObject();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getDate()
	 * @see #getSimpleTestObject()
	 * @generated
	 */
	EAttribute getSimpleTestObject_Date();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getXmlDate <em>Xml Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Xml Date</em>'.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getXmlDate()
	 * @see #getSimpleTestObject()
	 * @generated
	 */
	EAttribute getSimpleTestObject_XmlDate();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getMyEnum <em>My Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>My Enum</em>'.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject#getMyEnum()
	 * @see #getSimpleTestObject()
	 * @generated
	 */
	EAttribute getSimpleTestObject_MyEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Enum</em>'.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum
	 * @generated
	 */
	EEnum getTestEnum();

	/**
	 * Returns the meta object for data type '{@link javax.xml.datatype.XMLGregorianCalendar <em>XML Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>XML Date</em>'.
	 * @see javax.xml.datatype.XMLGregorianCalendar
	 * @model instanceClass="javax.xml.datatype.XMLGregorianCalendar"
	 * @generated
	 */
	EDataType getXMLDate();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestFactory getTestFactory();

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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl <em>Simple Test Object</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl
		 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getSimpleTestObject()
		 * @generated
		 */
		EClass SIMPLE_TEST_OBJECT = eINSTANCE.getSimpleTestObject();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIMPLE_TEST_OBJECT__DATE = eINSTANCE.getSimpleTestObject_Date();

		/**
		 * The meta object literal for the '<em><b>Xml Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIMPLE_TEST_OBJECT__XML_DATE = eINSTANCE.getSimpleTestObject_XmlDate();

		/**
		 * The meta object literal for the '<em><b>My Enum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SIMPLE_TEST_OBJECT__MY_ENUM = eINSTANCE.getSimpleTestObject_MyEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum <em>Enum</em>}'
		 * enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum
		 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getTestEnum()
		 * @generated
		 */
		EEnum TEST_ENUM = eINSTANCE.getTestEnum();

		/**
		 * The meta object literal for the '<em>XML Date</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see javax.xml.datatype.XMLGregorianCalendar
		 * @see org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestPackageImpl#getXMLDate()
		 * @generated
		 */
		EDataType XML_DATE = eINSTANCE.getXMLDate();

	}

} // TestPackage
