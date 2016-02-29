/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.common.test.model;

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
 * @see org.eclipse.emf.ecp.common.test.model.TestFactory
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
	String eNS_URI = "http://eclipse.org/ecp/common/test/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "ecp.common.test.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TestPackage eINSTANCE = org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl <em>Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.common.test.model.impl.BaseImpl
	 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getBase()
	 * @generated
	 */
	int BASE = 0;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__SINGLE_ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__SINGLE_ATTRIBUTE_UNSETTABLE = 1;

	/**
	 * The feature id for the '<em><b>Multi Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__MULTI_ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__MULTI_ATTRIBUTE_UNSETTABLE = 3;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__CHILD = 4;

	/**
	 * The feature id for the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__CHILD_UNSETTABLE = 5;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__CHILDREN = 6;

	/**
	 * The feature id for the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE__CHILDREN_UNSETTABLE = 7;

	/**
	 * The number of structural features of the '<em>Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test1Impl <em>Test1</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.common.test.model.impl.Test1Impl
	 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest1()
	 * @generated
	 */
	int TEST1 = 1;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__SINGLE_ATTRIBUTE = BASE__SINGLE_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__SINGLE_ATTRIBUTE_UNSETTABLE = BASE__SINGLE_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Multi Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__MULTI_ATTRIBUTE = BASE__MULTI_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__MULTI_ATTRIBUTE_UNSETTABLE = BASE__MULTI_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__CHILD = BASE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__CHILD_UNSETTABLE = BASE__CHILD_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__CHILDREN = BASE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__CHILDREN_UNSETTABLE = BASE__CHILDREN_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1__DERIVED = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST1_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test2Impl <em>Test2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.common.test.model.impl.Test2Impl
	 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest2()
	 * @generated
	 */
	int TEST2 = 2;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__SINGLE_ATTRIBUTE = BASE__SINGLE_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__SINGLE_ATTRIBUTE_UNSETTABLE = BASE__SINGLE_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Multi Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__MULTI_ATTRIBUTE = BASE__MULTI_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__MULTI_ATTRIBUTE_UNSETTABLE = BASE__MULTI_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__CHILD = BASE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__CHILD_UNSETTABLE = BASE__CHILD_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__CHILDREN = BASE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__CHILDREN_UNSETTABLE = BASE__CHILDREN_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2__DERIVED = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST2_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test3Impl <em>Test3</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.common.test.model.impl.Test3Impl
	 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest3()
	 * @generated
	 */
	int TEST3 = 3;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__SINGLE_ATTRIBUTE = BASE__SINGLE_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__SINGLE_ATTRIBUTE_UNSETTABLE = BASE__SINGLE_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Multi Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__MULTI_ATTRIBUTE = BASE__MULTI_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__MULTI_ATTRIBUTE_UNSETTABLE = BASE__MULTI_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__CHILD = BASE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__CHILD_UNSETTABLE = BASE__CHILD_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__CHILDREN = BASE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__CHILDREN_UNSETTABLE = BASE__CHILDREN_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3__DERIVED = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test3</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST3_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test4Impl <em>Test4</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.common.test.model.impl.Test4Impl
	 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest4()
	 * @generated
	 */
	int TEST4 = 4;

	/**
	 * The feature id for the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__SINGLE_ATTRIBUTE = BASE__SINGLE_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__SINGLE_ATTRIBUTE_UNSETTABLE = BASE__SINGLE_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Multi Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__MULTI_ATTRIBUTE = BASE__MULTI_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__MULTI_ATTRIBUTE_UNSETTABLE = BASE__MULTI_ATTRIBUTE_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__CHILD = BASE__CHILD;

	/**
	 * The feature id for the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__CHILD_UNSETTABLE = BASE__CHILD_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__CHILDREN = BASE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__CHILDREN_UNSETTABLE = BASE__CHILDREN_UNSETTABLE;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4__DERIVED = BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test4</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TEST4_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.common.test.model.Base <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Base</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base
	 * @generated
	 */
	EClass getBase();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttribute
	 * <em>Single Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Single Attribute</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getSingleAttribute()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_SingleAttribute();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable
	 * <em>Single Attribute Unsettable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Single Attribute Unsettable</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_SingleAttributeUnsettable();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttribute <em>Multi Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Multi Attribute</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getMultiAttribute()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_MultiAttribute();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttributeUnsettable <em>Multi Attribute Unsettable</em>
	 * }'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute list '<em>Multi Attribute Unsettable</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getMultiAttributeUnsettable()
	 * @see #getBase()
	 * @generated
	 */
	EAttribute getBase_MultiAttributeUnsettable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.ecp.common.test.model.Base#getChild
	 * <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getChild()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_Child();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable <em>Child Unsettable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Child Unsettable</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_ChildUnsettable();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getChildren()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_Children();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable <em>Children Unsettable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Children Unsettable</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable()
	 * @see #getBase()
	 * @generated
	 */
	EReference getBase_ChildrenUnsettable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.common.test.model.Test1 <em>Test1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Test1</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test1
	 * @generated
	 */
	EClass getTest1();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.common.test.model.Test1#getDerived
	 * <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test1#getDerived()
	 * @see #getTest1()
	 * @generated
	 */
	EAttribute getTest1_Derived();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.common.test.model.Test2 <em>Test2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Test2</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test2
	 * @generated
	 */
	EClass getTest2();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.common.test.model.Test2#getDerived
	 * <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test2#getDerived()
	 * @see #getTest2()
	 * @generated
	 */
	EAttribute getTest2_Derived();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.common.test.model.Test3 <em>Test3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Test3</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test3
	 * @generated
	 */
	EClass getTest3();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.common.test.model.Test3#getDerived
	 * <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test3#getDerived()
	 * @see #getTest3()
	 * @generated
	 */
	EAttribute getTest3_Derived();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.common.test.model.Test4 <em>Test4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Test4</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test4
	 * @generated
	 */
	EClass getTest4();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.common.test.model.Test4#getDerived
	 * <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.eclipse.emf.ecp.common.test.model.Test4#getDerived()
	 * @see #getTest4()
	 * @generated
	 */
	EAttribute getTest4_Derived();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl <em>Base</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.common.test.model.impl.BaseImpl
		 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getBase()
		 * @generated
		 */
		EClass BASE = eINSTANCE.getBase();

		/**
		 * The meta object literal for the '<em><b>Single Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute BASE__SINGLE_ATTRIBUTE = eINSTANCE.getBase_SingleAttribute();

		/**
		 * The meta object literal for the '<em><b>Single Attribute Unsettable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute BASE__SINGLE_ATTRIBUTE_UNSETTABLE = eINSTANCE.getBase_SingleAttributeUnsettable();

		/**
		 * The meta object literal for the '<em><b>Multi Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute BASE__MULTI_ATTRIBUTE = eINSTANCE.getBase_MultiAttribute();

		/**
		 * The meta object literal for the '<em><b>Multi Attribute Unsettable</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute BASE__MULTI_ATTRIBUTE_UNSETTABLE = eINSTANCE.getBase_MultiAttributeUnsettable();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASE__CHILD = eINSTANCE.getBase_Child();

		/**
		 * The meta object literal for the '<em><b>Child Unsettable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASE__CHILD_UNSETTABLE = eINSTANCE.getBase_ChildUnsettable();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASE__CHILDREN = eINSTANCE.getBase_Children();

		/**
		 * The meta object literal for the '<em><b>Children Unsettable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASE__CHILDREN_UNSETTABLE = eINSTANCE.getBase_ChildrenUnsettable();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test1Impl <em>Test1</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.common.test.model.impl.Test1Impl
		 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest1()
		 * @generated
		 */
		EClass TEST1 = eINSTANCE.getTest1();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEST1__DERIVED = eINSTANCE.getTest1_Derived();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test2Impl <em>Test2</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.common.test.model.impl.Test2Impl
		 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest2()
		 * @generated
		 */
		EClass TEST2 = eINSTANCE.getTest2();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEST2__DERIVED = eINSTANCE.getTest2_Derived();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test3Impl <em>Test3</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.common.test.model.impl.Test3Impl
		 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest3()
		 * @generated
		 */
		EClass TEST3 = eINSTANCE.getTest3();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEST3__DERIVED = eINSTANCE.getTest3_Derived();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.common.test.model.impl.Test4Impl <em>Test4</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.common.test.model.impl.Test4Impl
		 * @see org.eclipse.emf.ecp.common.test.model.impl.TestPackageImpl#getTest4()
		 * @generated
		 */
		EClass TEST4 = eINSTANCE.getTest4();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TEST4__DERIVED = eINSTANCE.getTest4_Derived();

	}

} // TestPackage
