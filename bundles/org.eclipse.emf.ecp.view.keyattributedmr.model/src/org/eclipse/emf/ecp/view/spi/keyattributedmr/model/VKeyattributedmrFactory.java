/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage
 * @generated
 */
public interface VKeyattributedmrFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VKeyattributedmrFactory eINSTANCE = org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyattributedmrFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Key Attribute Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Key Attribute Domain Model Reference</em>'.
	 * @generated
	 */
	VKeyAttributeDomainModelReference createKeyAttributeDomainModelReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VKeyattributedmrPackage getKeyattributedmrPackage();

} // VKeyattributedmrFactory
