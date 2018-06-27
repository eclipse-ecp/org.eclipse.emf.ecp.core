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
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Selector Container</b></em>'.
 *
 * @since 1.17
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.model.VTStyleSelectorContainer#getSelector
 *        <em>Selector</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyleSelectorContainer()
 * @model abstract="true"
 * @generated
 */
public interface VTStyleSelectorContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Selector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selector</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Selector</em>' containment reference.
	 * @see #setSelector(VTStyleSelector)
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyleSelectorContainer_Selector()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VTStyleSelector getSelector();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.model.VTStyleSelectorContainer#getSelector
	 * <em>Selector</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Selector</em>' containment reference.
	 * @see #getSelector()
	 * @generated
	 */
	void setSelector(VTStyleSelector value);

} // VTStyleSelectorContainer
