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
package org.eclipse.emf.ecp.view.template.style.alignment.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control Label Alignment Style Property</b></em>'.
 * 
 * @since 1.16
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty#getType
 *        <em>Type</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage#getControlLabelAlignmentStyleProperty()
 * @model
 * @generated
 */
public interface VTControlLabelAlignmentStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"LEFT"</code>.
	 * The literals are from the enumeration
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @see #setType(AlignmentType)
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage#getControlLabelAlignmentStyleProperty_Type()
	 * @model default="LEFT" required="true"
	 * @generated
	 */
	AlignmentType getType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty#getType
	 * <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @see #getType()
	 * @generated
	 */
	void setType(AlignmentType value);

} // VTControlLabelAlignmentStyleProperty
