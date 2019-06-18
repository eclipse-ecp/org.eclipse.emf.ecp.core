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
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.mappingsegment.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage
 * @generated
 */
public interface VMappingsegmentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VMappingsegmentFactory eINSTANCE = org.eclipse.emfforms.spi.view.mappingsegment.model.impl.VMappingsegmentFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Mapping Domain Model Reference Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Mapping Domain Model Reference Segment</em>'.
	 * @generated
	 */
	VMappingDomainModelReferenceSegment createMappingDomainModelReferenceSegment();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VMappingsegmentPackage getMappingsegmentPackage();

} // VMappingsegmentFactory
