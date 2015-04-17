/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.background.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 *
 * @since 1.5
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty#getColor <em>
 *        Color </em>}</li>
 *        </ul>
 *        </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundPackage#getBackgroundStyleProperty()
 * @model
 * @generated
 */
public interface VTBackgroundStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundPackage#getBackgroundStyleProperty_Color()
	 * @model
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty#getColor
	 * <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

} // VTBackgroundStyleProperty
