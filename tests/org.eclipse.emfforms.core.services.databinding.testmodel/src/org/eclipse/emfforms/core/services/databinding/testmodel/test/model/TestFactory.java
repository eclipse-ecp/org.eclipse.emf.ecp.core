/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.core.services.databinding.testmodel.test.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage
 * @generated
 */
public interface TestFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TestFactory eINSTANCE = org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.TestFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>A</em>'.
	 * @generated
	 */
	A createA();

	/**
	 * Returns a new object of class '<em>B</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>B</em>'.
	 * @generated
	 */
	B createB();

	/**
	 * Returns a new object of class '<em>C</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>C</em>'.
	 * @generated
	 */
	C createC();

	/**
	 * Returns a new object of class '<em>D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>D</em>'.
	 * @generated
	 */
	D createD();

	/**
	 * Returns a new object of class '<em>DExtended</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>DExtended</em>'.
	 * @generated
	 */
	DExtended createDExtended();

	/**
	 * Returns a new object of class '<em>F</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>F</em>'.
	 * @generated
	 */
	F createF();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	TestPackage getTestPackage();

} // TestFactory
