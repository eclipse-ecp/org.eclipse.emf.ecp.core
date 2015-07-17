/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - extension for EnumComboViewerRenderer_PTest
 */
package org.eclipse.emf.ecp.view.core.swt.test.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage
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
	TestFactory eINSTANCE = org.eclipse.emf.ecp.view.core.swt.test.model.impl.TestFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Simple Test Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Simple Test Object</em>'.
	 * @generated
	 */
	SimpleTestObject createSimpleTestObject();

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
