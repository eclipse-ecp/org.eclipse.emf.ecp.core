/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.labelwidth.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label Width Style Property</b></em>'.
 * 
 * @since 1.16
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty#getWidth
 *        <em>Width</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage#getLabelWidthStyleProperty()
 * @model
 * @generated
 */
public interface VTLabelWidthStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The width of the control's label.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #setWidth(int)
	 * @see org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage#getLabelWidthStyleProperty_Width()
	 * @model unsettable="true" required="true"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty#getWidth
	 * <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty#getWidth
	 * <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	void unsetWidth();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty#getWidth
	 * <em>Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Width</em>' attribute is set.
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	boolean isSetWidth();

} // VTLabelWidthStyleProperty
