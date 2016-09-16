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
package org.eclipse.emfforms.core.services.databinding.testmodel.test.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory
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
	String eNS_URI = "test"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "test"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TestPackage eINSTANCE = org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.AImpl
	 * <em>A</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.AImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getA()
	 * @generated
	 */
	int A = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl
	 * <em>B</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getB()
	 * @generated
	 */
	int B = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.CImpl
	 * <em>C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.CImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getC()
	 * @generated
	 */
	int C = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.DImpl
	 * <em>D</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.DImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getD()
	 * @generated
	 */
	int D = 3;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEStringMapImpl
	 * <em>EClass To EString Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEStringMapImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToEStringMap()
	 * @generated
	 */
	int ECLASS_TO_ESTRING_MAP = 4;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToAMapImpl <em>EClass To
	 * AMap</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToAMapImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToAMap()
	 * @generated
	 */
	int ECLASS_TO_AMAP = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EImpl
	 * <em>E</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getE()
	 * @generated
	 */
	int E = 6;

	/**
	 * The number of structural features of the '<em>E</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int E_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>B</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int A__B = E_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>A</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int A_FEATURE_COUNT = E_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B__C = 0;

	/**
	 * The feature id for the '<em><b>CList</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B__CLIST = 1;

	/**
	 * The feature id for the '<em><b>EList</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B__ELIST = 2;

	/**
	 * The feature id for the '<em><b>E</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B__E = 3;

	/**
	 * The number of structural features of the '<em>B</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>D</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__D = 0;

	/**
	 * The feature id for the '<em><b>EClass To String</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__ECLASS_TO_STRING = 1;

	/**
	 * The feature id for the '<em><b>EClass To A</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__ECLASS_TO_A = 2;

	/**
	 * The feature id for the '<em><b>EClass To E</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__ECLASS_TO_E = 3;

	/**
	 * The feature id for the '<em><b>A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__A = 4;

	/**
	 * The number of structural features of the '<em>C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int D__X = 0;

	/**
	 * The feature id for the '<em><b>YList</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int D__YLIST = 1;

	/**
	 * The number of structural features of the '<em>D</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int D_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_ESTRING_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_ESTRING_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EClass To EString Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_ESTRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_AMAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_AMAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EClass To AMap</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_AMAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEMapImpl <em>EClass To
	 * EMap</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEMapImpl
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToEMap()
	 * @generated
	 */
	int ECLASS_TO_EMAP = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_EMAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_EMAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EClass To EMap</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECLASS_TO_EMAP_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A
	 * <em>A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>A</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A
	 * @generated
	 */
	EClass getA();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A#getB <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>B</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A#getB()
	 * @see #getA()
	 * @generated
	 */
	EReference getA_B();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B
	 * <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>B</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B
	 * @generated
	 */
	EClass getB();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getC <em>C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>C</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getC()
	 * @see #getB()
	 * @generated
	 */
	EReference getB_C();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getCList <em>CList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>CList</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getCList()
	 * @see #getB()
	 * @generated
	 */
	EReference getB_CList();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getEList <em>EList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>EList</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getEList()
	 * @see #getB()
	 * @generated
	 */
	EReference getB_EList();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getE <em>E</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>E</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getE()
	 * @see #getB()
	 * @generated
	 */
	EReference getB_E();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C
	 * <em>C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>C</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C
	 * @generated
	 */
	EClass getC();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getD <em>D</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>D</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getD()
	 * @see #getC()
	 * @generated
	 */
	EReference getC_D();

	/**
	 * Returns the meta object for the map
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToString <em>EClass To
	 * String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>EClass To String</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToString()
	 * @see #getC()
	 * @generated
	 */
	EReference getC_EClassToString();

	/**
	 * Returns the meta object for the map
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToA <em>EClass To
	 * A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>EClass To A</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToA()
	 * @see #getC()
	 * @generated
	 */
	EReference getC_EClassToA();

	/**
	 * Returns the meta object for the map
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToE <em>EClass To
	 * E</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>EClass To E</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getEClassToE()
	 * @see #getC()
	 * @generated
	 */
	EReference getC_EClassToE();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getA <em>A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>A</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C#getA()
	 * @see #getC()
	 * @generated
	 */
	EReference getC_A();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D
	 * <em>D</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>D</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D
	 * @generated
	 */
	EClass getD();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D#getX()
	 * @see #getD()
	 * @generated
	 */
	EAttribute getD_X();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D#getYList <em>YList</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>YList</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D#getYList()
	 * @see #getD()
	 * @generated
	 */
	EAttribute getD_YList();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EClass To EString Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EClass To EString Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EClass"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getEClassToEStringMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToEStringMap()
	 * @generated
	 */
	EReference getEClassToEStringMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToEStringMap()
	 * @generated
	 */
	EAttribute getEClassToEStringMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EClass To AMap</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EClass To AMap</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EClass"
	 *        valueType="org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A"
	 * @generated
	 */
	EClass getEClassToAMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToAMap()
	 * @generated
	 */
	EReference getEClassToAMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToAMap()
	 * @generated
	 */
	EReference getEClassToAMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E
	 * <em>E</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>E</em>'.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E
	 * @generated
	 */
	EClass getE();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EClass To EMap</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EClass To EMap</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EClass"
	 *        valueType="org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E"
	 * @generated
	 */
	EClass getEClassToEMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToEMap()
	 * @generated
	 */
	EReference getEClassToEMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEClassToEMap()
	 * @generated
	 */
	EReference getEClassToEMap_Value();

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
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.AImpl <em>A</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.AImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getA()
		 * @generated
		 */
		EClass A = eINSTANCE.getA();

		/**
		 * The meta object literal for the '<em><b>B</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference A__B = eINSTANCE.getA_B();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl <em>B</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getB()
		 * @generated
		 */
		EClass B = eINSTANCE.getB();

		/**
		 * The meta object literal for the '<em><b>C</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference B__C = eINSTANCE.getB_C();

		/**
		 * The meta object literal for the '<em><b>CList</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference B__CLIST = eINSTANCE.getB_CList();

		/**
		 * The meta object literal for the '<em><b>EList</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference B__ELIST = eINSTANCE.getB_EList();

		/**
		 * The meta object literal for the '<em><b>E</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference B__E = eINSTANCE.getB_E();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.CImpl <em>C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.CImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getC()
		 * @generated
		 */
		EClass C = eINSTANCE.getC();

		/**
		 * The meta object literal for the '<em><b>D</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference C__D = eINSTANCE.getC_D();

		/**
		 * The meta object literal for the '<em><b>EClass To String</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference C__ECLASS_TO_STRING = eINSTANCE.getC_EClassToString();

		/**
		 * The meta object literal for the '<em><b>EClass To A</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference C__ECLASS_TO_A = eINSTANCE.getC_EClassToA();

		/**
		 * The meta object literal for the '<em><b>EClass To E</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference C__ECLASS_TO_E = eINSTANCE.getC_EClassToE();

		/**
		 * The meta object literal for the '<em><b>A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference C__A = eINSTANCE.getC_A();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.DImpl <em>D</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.DImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getD()
		 * @generated
		 */
		EClass D = eINSTANCE.getD();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute D__X = eINSTANCE.getD_X();

		/**
		 * The meta object literal for the '<em><b>YList</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute D__YLIST = eINSTANCE.getD_YList();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEStringMapImpl
		 * <em>EClass To EString Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEStringMapImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToEStringMap()
		 * @generated
		 */
		EClass ECLASS_TO_ESTRING_MAP = eINSTANCE.getEClassToEStringMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECLASS_TO_ESTRING_MAP__KEY = eINSTANCE.getEClassToEStringMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECLASS_TO_ESTRING_MAP__VALUE = eINSTANCE.getEClassToEStringMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToAMapImpl <em>EClass
		 * To AMap</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToAMapImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToAMap()
		 * @generated
		 */
		EClass ECLASS_TO_AMAP = eINSTANCE.getEClassToAMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECLASS_TO_AMAP__KEY = eINSTANCE.getEClassToAMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECLASS_TO_AMAP__VALUE = eINSTANCE.getEClassToAMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EImpl <em>E</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getE()
		 * @generated
		 */
		EClass E = eINSTANCE.getE();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEMapImpl <em>EClass
		 * To EMap</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.EClassToEMapImpl
		 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestPackageImpl#getEClassToEMap()
		 * @generated
		 */
		EClass ECLASS_TO_EMAP = eINSTANCE.getEClassToEMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECLASS_TO_EMAP__KEY = eINSTANCE.getEClassToEMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECLASS_TO_EMAP__VALUE = eINSTANCE.getEClassToEMap_Value();

	}

} // TestPackage
