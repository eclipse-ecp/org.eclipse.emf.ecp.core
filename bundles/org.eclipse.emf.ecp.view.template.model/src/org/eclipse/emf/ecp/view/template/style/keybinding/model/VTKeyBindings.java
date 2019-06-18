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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Key Bindings</b></em>'.
 *
 * @since 1.18
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings#getBindings
 *        <em>Bindings</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage#getKeyBindings()
 * @model
 * @generated
 */
public interface VTKeyBindings extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage#getKeyBindings_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<VTKeyBinding> getBindings();

} // VTKeyBindings
