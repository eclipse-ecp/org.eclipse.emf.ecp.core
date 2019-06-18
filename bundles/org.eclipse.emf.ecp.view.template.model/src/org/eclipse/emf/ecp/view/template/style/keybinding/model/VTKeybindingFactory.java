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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 *
 * @since 1.18
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage
 * @generated
 */
public interface VTKeybindingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTKeybindingFactory eINSTANCE = org.eclipse.emf.ecp.view.template.style.keybinding.model.impl.VTKeybindingFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Key Bindings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Key Bindings</em>'.
	 * @generated
	 */
	VTKeyBindings createKeyBindings();

	/**
	 * Returns a new object of class '<em>Key Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Key Binding</em>'.
	 * @generated
	 */
	VTKeyBinding createKeyBinding();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VTKeybindingPackage getKeybindingPackage();

} // VTKeybindingFactory
