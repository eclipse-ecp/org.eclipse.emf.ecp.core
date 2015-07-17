/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage
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
	TestFactory eINSTANCE = org.eclipse.emf.ecp.view.validation.test.model.impl.TestFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Library</em>'.
	 * @generated
	 */
	Library createLibrary();

	/**
	 * Returns a new object of class '<em>Writer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Writer</em>'.
	 * @generated
	 */
	Writer createWriter();

	/**
	 * Returns a new object of class '<em>Book</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Book</em>'.
	 * @generated
	 */
	Book createBook();

	/**
	 * Returns a new object of class '<em>Librarian</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Librarian</em>'.
	 * @generated
	 */
	Librarian createLibrarian();

	/**
	 * Returns a new object of class '<em>Computer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Computer</em>'.
	 * @generated
	 */
	Computer createComputer();

	/**
	 * Returns a new object of class '<em>Mainboard</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Mainboard</em>'.
	 * @generated
	 */
	Mainboard createMainboard();

	/**
	 * Returns a new object of class '<em>Power Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Power Block</em>'.
	 * @generated
	 */
	PowerBlock createPowerBlock();

	/**
	 * Returns a new object of class '<em>Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Container</em>'.
	 * @generated
	 */
	Container createContainer();

	/**
	 * Returns a new object of class '<em>Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Content</em>'.
	 * @generated
	 */
	Content createContent();

	/**
	 * Returns a new object of class '<em>Table With Multiplicity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table With Multiplicity</em>'.
	 * @generated
	 */
	TableWithMultiplicity createTableWithMultiplicity();

	/**
	 * Returns a new object of class '<em>Table Content Without Validation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Content Without Validation</em>'.
	 * @generated
	 */
	TableContentWithoutValidation createTableContentWithoutValidation();

	/**
	 * Returns a new object of class '<em>Table Content With Validation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Content With Validation</em>'.
	 * @generated
	 */
	TableContentWithValidation createTableContentWithValidation();

	/**
	 * Returns a new object of class '<em>Table Without Multiplicity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Without Multiplicity</em>'.
	 * @generated
	 */
	TableWithoutMultiplicity createTableWithoutMultiplicity();

	/**
	 * Returns a new object of class '<em>Table With Unique</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table With Unique</em>'.
	 * @generated
	 */
	TableWithUnique createTableWithUnique();

	/**
	 * Returns a new object of class '<em>Table Content With Inner Child2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Content With Inner Child2</em>'.
	 * @generated
	 */
	TableContentWithInnerChild2 createTableContentWithInnerChild2();

	/**
	 * Returns a new object of class '<em>Table Content With Inner Child</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Content With Inner Child</em>'.
	 * @generated
	 */
	TableContentWithInnerChild createTableContentWithInnerChild();

	/**
	 * Returns a new object of class '<em>Table Without Multiplicity Concrete</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Table Without Multiplicity Concrete</em>'.
	 * @generated
	 */
	TableWithoutMultiplicityConcrete createTableWithoutMultiplicityConcrete();

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
