/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.vertical.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalPackage
 * @generated
 */
public interface VVerticalFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VVerticalFactory eINSTANCE = org.eclipse.emf.ecp.view.spi.vertical.model.impl.VVerticalFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Layout</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Layout</em>'.
	 * @generated
	 */
	VVerticalLayout createVerticalLayout();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VVerticalPackage getVerticalPackage();

} // VVerticalFactory
