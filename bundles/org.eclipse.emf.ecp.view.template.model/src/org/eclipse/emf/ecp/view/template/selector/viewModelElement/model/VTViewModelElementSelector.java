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
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#isSelectSubclasses
 * <em>Select Subclasses</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getClassType
 * <em>Class Type</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttribute
 * <em>Attribute</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttributeValue
 * <em>Attribute Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#getViewModelElementSelector()
 * @model
 * @generated
 */
public interface VTViewModelElementSelector extends VTStyleSelector {
	/**
	 * Returns the value of the '<em><b>Class Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Type</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Class Type</em>' reference.
	 * @see #setClassType(EClass)
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#getViewModelElementSelector_ClassType()
	 * @model
	 * @generated
	 */
	EClass getClassType();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getClassType
	 * <em>Class Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Class Type</em>' reference.
	 * @see #getClassType()
	 * @generated
	 */
	void setClassType(EClass value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#getViewModelElementSelector_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttribute
	 * <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Attribute Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Value</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Attribute Value</em>' attribute.
	 * @see #setAttributeValue(Object)
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#getViewModelElementSelector_AttributeValue()
	 * @model
	 * @generated
	 */
	Object getAttributeValue();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#getAttributeValue
	 * <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Attribute Value</em>' attribute.
	 * @see #getAttributeValue()
	 * @generated
	 */
	void setAttributeValue(Object value);

	/**
	 * Returns the value of the '<em><b>Select Subclasses</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Select Subclasses</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Select Subclasses</em>' attribute.
	 * @see #setSelectSubclasses(boolean)
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#getViewModelElementSelector_SelectSubclasses()
	 * @model
	 * @generated
	 */
	boolean isSelectSubclasses();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#isSelectSubclasses
	 * <em>Select Subclasses</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Select Subclasses</em>' attribute.
	 * @see #isSelectSubclasses()
	 * @generated
	 */
	void setSelectSubclasses(boolean value);

} // VTViewModelElementSelector
