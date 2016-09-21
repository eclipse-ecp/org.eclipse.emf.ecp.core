/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.view.spi.multisegment.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage
 * @generated
 */
public interface VMultisegmentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VMultisegmentFactory eINSTANCE = org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultisegmentFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Multi Domain Model Reference Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Multi Domain Model Reference Segment</em>'.
	 * @generated
	 */
	VMultiDomainModelReferenceSegment createMultiDomainModelReferenceSegment();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VMultisegmentPackage getMultisegmentPackage();

} // VMultisegmentFactory
