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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.keyattribute.test.example;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Root#getIntermediate <em>Intermediate</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getRoot()
 * @model
 * @generated
 */
public interface Root extends EObject {
	/**
	 * Returns the value of the '<em><b>Intermediate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intermediate</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Intermediate</em>' containment reference.
	 * @see #setIntermediate(Intermediate)
	 * @see org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage#getRoot_Intermediate()
	 * @model containment="true"
	 * @generated
	 */
	Intermediate getIntermediate();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.keyattribute.test.example.Root#getIntermediate
	 * <em>Intermediate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Intermediate</em>' containment reference.
	 * @see #getIntermediate()
	 * @generated
	 */
	void setIntermediate(Intermediate value);

} // Root
