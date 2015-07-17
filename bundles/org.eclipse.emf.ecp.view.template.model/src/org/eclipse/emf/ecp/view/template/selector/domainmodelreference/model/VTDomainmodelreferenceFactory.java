/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage
 * @generated
 */
public interface VTDomainmodelreferenceFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTDomainmodelreferenceFactory eINSTANCE = org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainmodelreferenceFactoryImpl
		.init();

	/**
	 * Returns a new object of class '<em>Domain Model Reference Selector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Domain Model Reference Selector</em>'.
	 * @generated
	 */
	VTDomainModelReferenceSelector createDomainModelReferenceSelector();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VTDomainmodelreferencePackage getDomainmodelreferencePackage();

} // VTDomainmodelreferenceFactory
