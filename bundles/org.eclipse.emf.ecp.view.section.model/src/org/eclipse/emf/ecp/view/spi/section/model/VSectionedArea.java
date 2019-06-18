/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.section.model;

import org.eclipse.emf.ecp.view.spi.model.VContainedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sectioned Area</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea#getRoot <em>Root</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#getSectionedArea()
 * @model
 * @generated
 */
public interface VSectionedArea extends VContainedElement {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(VSection)
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#getSectionedArea_Root()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VSection getRoot();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea#getRoot <em>Root</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(VSection value);

} // VSectionedArea
