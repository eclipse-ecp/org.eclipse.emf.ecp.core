/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Style Property</b></em>'.
 * 
 * @since 1.9
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>
 *        {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
 *        <em>Minimum Height</em>}</li>
 *        <li>
 *        {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
 *        <em>Maximum Height</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty()
 * @model
 * @generated
 */
public interface VTTableStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Minimum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimum Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Minimum Height</em>' attribute.
	 * @see #isSetMinimumHeight()
	 * @see #unsetMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_MinimumHeight()
	 * @model unsettable="true"
	 * @generated
	 */
	int getMinimumHeight();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Minimum Height</em>' attribute.
	 * @see #isSetMinimumHeight()
	 * @see #unsetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @generated
	 */
	void setMinimumHeight(int value);

	/**
	 * Unsets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @generated
	 */
	void unsetMinimumHeight();

	/**
	 * Returns whether the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Minimum Height</em>' attribute is set.
	 * @see #unsetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @generated
	 */
	boolean isSetMinimumHeight();

	/**
	 * Returns the value of the '<em><b>Maximum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maximum Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Maximum Height</em>' attribute.
	 * @see #isSetMaximumHeight()
	 * @see #unsetMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_MaximumHeight()
	 * @model unsettable="true"
	 * @generated
	 */
	int getMaximumHeight();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Maximum Height</em>' attribute.
	 * @see #isSetMaximumHeight()
	 * @see #unsetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @generated
	 */
	void setMaximumHeight(int value);

	/**
	 * Unsets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @generated
	 */
	void unsetMaximumHeight();

	/**
	 * Returns whether the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Maximum Height</em>' attribute is set.
	 * @see #unsetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @generated
	 */
	boolean isSetMaximumHeight();

} // VTTableStyleProperty
