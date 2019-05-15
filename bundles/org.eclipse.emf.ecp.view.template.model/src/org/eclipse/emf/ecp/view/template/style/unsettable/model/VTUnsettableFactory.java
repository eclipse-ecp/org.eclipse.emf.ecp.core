/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * 
 * @since 1.14
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage
 * @generated
 */
public interface VTUnsettableFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTUnsettableFactory eINSTANCE = org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Style Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Style Property</em>'.
	 * @generated
	 */
	VTUnsettableStyleProperty createUnsettableStyleProperty();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VTUnsettablePackage getUnsettablePackage();

} // VTUnsettableFactory
