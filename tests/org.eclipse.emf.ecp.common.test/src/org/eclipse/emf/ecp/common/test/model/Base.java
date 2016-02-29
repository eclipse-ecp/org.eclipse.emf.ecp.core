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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttribute <em>Single Attribute</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable
 * <em>Single Attribute Unsettable</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttribute <em>Multi Attribute</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttributeUnsettable <em>Multi Attribute Unsettable</em>
 * }</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getChild <em>Child</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable <em>Child Unsettable</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable <em>Children Unsettable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase()
 * @model
 * @generated
 */
public interface Base extends EObject {
	/**
	 * Returns the value of the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Single Attribute</em>' attribute.
	 * @see #setSingleAttribute(String)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_SingleAttribute()
	 * @model
	 * @generated
	 */
	String getSingleAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttribute
	 * <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Single Attribute</em>' attribute.
	 * @see #getSingleAttribute()
	 * @generated
	 */
	void setSingleAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Single Attribute Unsettable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Attribute Unsettable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Single Attribute Unsettable</em>' attribute.
	 * @see #isSetSingleAttributeUnsettable()
	 * @see #unsetSingleAttributeUnsettable()
	 * @see #setSingleAttributeUnsettable(String)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_SingleAttributeUnsettable()
	 * @model unsettable="true"
	 * @generated
	 */
	String getSingleAttributeUnsettable();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable
	 * <em>Single Attribute Unsettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Single Attribute Unsettable</em>' attribute.
	 * @see #isSetSingleAttributeUnsettable()
	 * @see #unsetSingleAttributeUnsettable()
	 * @see #getSingleAttributeUnsettable()
	 * @generated
	 */
	void setSingleAttributeUnsettable(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable
	 * <em>Single Attribute Unsettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetSingleAttributeUnsettable()
	 * @see #getSingleAttributeUnsettable()
	 * @see #setSingleAttributeUnsettable(String)
	 * @generated
	 */
	void unsetSingleAttributeUnsettable();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getSingleAttributeUnsettable
	 * <em>Single Attribute Unsettable</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Single Attribute Unsettable</em>' attribute is set.
	 * @see #unsetSingleAttributeUnsettable()
	 * @see #getSingleAttributeUnsettable()
	 * @see #setSingleAttributeUnsettable(String)
	 * @generated
	 */
	boolean isSetSingleAttributeUnsettable();

	/**
	 * Returns the value of the '<em><b>Multi Attribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Multi Attribute</em>' attribute list.
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_MultiAttribute()
	 * @model
	 * @generated
	 */
	EList<String> getMultiAttribute();

	/**
	 * Returns the value of the '<em><b>Multi Attribute Unsettable</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Attribute Unsettable</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Multi Attribute Unsettable</em>' attribute list.
	 * @see #isSetMultiAttributeUnsettable()
	 * @see #unsetMultiAttributeUnsettable()
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_MultiAttributeUnsettable()
	 * @model unsettable="true"
	 * @generated
	 */
	EList<String> getMultiAttributeUnsettable();

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttributeUnsettable
	 * <em>Multi Attribute Unsettable</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetMultiAttributeUnsettable()
	 * @see #getMultiAttributeUnsettable()
	 * @generated
	 */
	void unsetMultiAttributeUnsettable();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getMultiAttributeUnsettable
	 * <em>Multi Attribute Unsettable</em>}' attribute list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Multi Attribute Unsettable</em>' attribute list is set.
	 * @see #unsetMultiAttributeUnsettable()
	 * @see #getMultiAttributeUnsettable()
	 * @generated
	 */
	boolean isSetMultiAttributeUnsettable();

	/**
	 * Returns the value of the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Child</em>' containment reference.
	 * @see #setChild(Base)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_Child()
	 * @model containment="true"
	 * @generated
	 */
	Base getChild();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChild <em>Child</em>}' containment
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Child</em>' containment reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(Base value);

	/**
	 * Returns the value of the '<em><b>Child Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Unsettable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Child Unsettable</em>' containment reference.
	 * @see #isSetChildUnsettable()
	 * @see #unsetChildUnsettable()
	 * @see #setChildUnsettable(Base)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_ChildUnsettable()
	 * @model containment="true" unsettable="true"
	 * @generated
	 */
	Base getChildUnsettable();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable
	 * <em>Child Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Child Unsettable</em>' containment reference.
	 * @see #isSetChildUnsettable()
	 * @see #unsetChildUnsettable()
	 * @see #getChildUnsettable()
	 * @generated
	 */
	void setChildUnsettable(Base value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable
	 * <em>Child Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetChildUnsettable()
	 * @see #getChildUnsettable()
	 * @see #setChildUnsettable(Base)
	 * @generated
	 */
	void unsetChildUnsettable();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildUnsettable
	 * <em>Child Unsettable</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Child Unsettable</em>' containment reference is set.
	 * @see #unsetChildUnsettable()
	 * @see #getChildUnsettable()
	 * @see #setChildUnsettable(Base)
	 * @generated
	 */
	boolean isSetChildUnsettable();

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.common.test.model.Base}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Base> getChildren();

	/**
	 * Returns the value of the '<em><b>Children Unsettable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children Unsettable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Children Unsettable</em>' containment reference.
	 * @see #isSetChildrenUnsettable()
	 * @see #unsetChildrenUnsettable()
	 * @see #setChildrenUnsettable(Base)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getBase_ChildrenUnsettable()
	 * @model containment="true" unsettable="true"
	 * @generated
	 */
	Base getChildrenUnsettable();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable
	 * <em>Children Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Children Unsettable</em>' containment reference.
	 * @see #isSetChildrenUnsettable()
	 * @see #unsetChildrenUnsettable()
	 * @see #getChildrenUnsettable()
	 * @generated
	 */
	void setChildrenUnsettable(Base value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable
	 * <em>Children Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetChildrenUnsettable()
	 * @see #getChildrenUnsettable()
	 * @see #setChildrenUnsettable(Base)
	 * @generated
	 */
	void unsetChildrenUnsettable();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.ecp.common.test.model.Base#getChildrenUnsettable
	 * <em>Children Unsettable</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Children Unsettable</em>' containment reference is set.
	 * @see #unsetChildrenUnsettable()
	 * @see #getChildrenUnsettable()
	 * @see #setChildrenUnsettable(Base)
	 * @generated
	 */
	boolean isSetChildrenUnsettable();

} // Base
