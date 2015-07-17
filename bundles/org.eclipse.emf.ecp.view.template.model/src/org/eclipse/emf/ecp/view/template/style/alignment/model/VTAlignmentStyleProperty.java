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
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage#getAlignmentStyleProperty()
 * @model
 * @generated
 */
public interface VTAlignmentStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"LEFT"</code>.
	 * The literals are from the enumeration
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @see #setType(AlignmentType)
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage#getAlignmentStyleProperty_Type()
	 * @model default="LEFT" required="true"
	 * @generated
	 */
	AlignmentType getType();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty#getType <em>Type</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType
	 * @see #getType()
	 * @generated
	 */
	void setType(AlignmentType value);

} // VTAlignmentStyleProperty
