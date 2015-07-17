/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.categorization.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getBundle <em>Bundle</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getAction()
 * @model
 * @generated
 */
public interface VAction extends EObject
{
	/**
	 * Returns the value of the '<em><b>Bundle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bundle</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bundle</em>' attribute.
	 * @see #setBundle(String)
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getAction_Bundle()
	 * @model required="true"
	 * @generated
	 */
	String getBundle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getBundle
	 * <em>Bundle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Bundle</em>' attribute.
	 * @see #getBundle()
	 * @generated
	 */
	void setBundle(String value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getAction_ClassName()
	 * @model required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.categorization.model.VAction#getClassName
	 * <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

} // VAction
