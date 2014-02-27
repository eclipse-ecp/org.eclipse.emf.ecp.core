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
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass <em>Root EClass</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView()
 * @model
 * @generated
 * @since 1.2
 */
public interface VView extends VElement {
	/**
	 * Returns the value of the '<em><b>Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root EClass</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Root EClass</em>' reference.
	 * @see #setRootEClass(EClass)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_RootEClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getRootEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass <em>Root EClass</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Root EClass</em>' reference.
	 * @see #getRootEClass()
	 * @generated
	 */
	void setRootEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VContainedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<VContainedElement> getChildren();

	/**
	 * Sets the view and all its contents readonly.
	 */
	void setAllContentsReadOnly();

} // View
