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
 */
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 *
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 *              <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage
 * @generated
 */
public interface VViewFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VViewFactory eINSTANCE = org.eclipse.emf.ecp.view.spi.model.impl.VViewFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Diagnostic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Diagnostic</em>'.
	 * @generated
	 */
	VDiagnostic createDiagnostic();

	/**
	 * Returns a new object of class '<em>View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>View</em>'.
	 * @generated
	 */
	VView createView();

	/**
	 * Returns a new object of class '<em>Control</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Control</em>'.
	 * @generated
	 */
	VControl createControl();

	/**
	 * Returns a new object of class '<em>Model Loading Properties</em>'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Loading Properties</em>'.
	 * @generated
	 */
	VViewModelLoadingProperties createViewModelLoadingProperties();

	/**
	 * Returns a new object of class '<em>Date Time Display Attachment</em>'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Date Time Display Attachment</em>'.
	 * @generated
	 */
	VDateTimeDisplayAttachment createDateTimeDisplayAttachment();

	/**
	 * Returns a new object of class '<em>Feature Path Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Feature Path Domain Model Reference</em>'.
	 * @generated
	 */
	VFeaturePathDomainModelReference createFeaturePathDomainModelReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	VViewPackage getViewPackage();

} // ViewFactory
