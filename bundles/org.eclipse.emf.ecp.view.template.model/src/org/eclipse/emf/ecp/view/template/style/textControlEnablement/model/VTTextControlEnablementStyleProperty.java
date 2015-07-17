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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model;

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
 *        <li>
 *        {@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty#isRenderDisableAsEditable
 *        <em>Render Disable As Editable</em>}</li>
 *        </ul>
 *        </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage#getTextControlEnablementStyleProperty()
 * @model
 * @generated
 */
public interface VTTextControlEnablementStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Render Disable As Editable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Render Disable As Editable</em>' attribute isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Render Disable As Editable</em>' attribute.
	 * @see #setRenderDisableAsEditable(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage#getTextControlEnablementStyleProperty_RenderDisableAsEditable()
	 * @model default="false"
	 * @generated
	 */
	boolean isRenderDisableAsEditable();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty#isRenderDisableAsEditable
	 * <em>Render Disable As Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Render Disable As Editable</em>' attribute.
	 * @see #isRenderDisableAsEditable()
	 * @generated
	 */
	void setRenderDisableAsEditable(boolean value);

} // VTTextControlEnablementStyleProperty
