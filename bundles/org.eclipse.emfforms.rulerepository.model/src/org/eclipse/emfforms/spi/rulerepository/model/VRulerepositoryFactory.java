/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage
 * @generated
 */
public interface VRulerepositoryFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VRulerepositoryFactory eINSTANCE = org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Rule Repository</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Rule Repository</em>'.
	 * @generated
	 */
	VRuleRepository createRuleRepository();

	/**
	 * Returns a new object of class '<em>Rule Entry</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Rule Entry</em>'.
	 * @generated
	 */
	VRuleEntry createRuleEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VRulerepositoryPackage getRulerepositoryPackage();

} // VRulerepositoryFactory
