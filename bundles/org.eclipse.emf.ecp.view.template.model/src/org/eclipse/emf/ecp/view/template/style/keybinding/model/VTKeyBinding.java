/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.style.keybinding.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Key Binding</b></em>'.
 *
 * @since 1.18
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getId <em>Id</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getKeySequence <em>Key
 *        Sequence</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage#getKeyBinding()
 * @model
 * @generated
 */
public interface VTKeyBinding extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage#getKeyBinding_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getId
	 * <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Key Sequence</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Sequence</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Key Sequence</em>' attribute.
	 * @see #setKeySequence(String)
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage#getKeyBinding_KeySequence()
	 * @model
	 * @generated
	 */
	String getKeySequence();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding#getKeySequence <em>Key
	 * Sequence</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Key Sequence</em>' attribute.
	 * @see #getKeySequence()
	 * @generated
	 */
	void setKeySequence(String value);

} // VTKeyBinding
