/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.common.test.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test4</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Test4#getDerived <em>Derived</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getTest4()
 * @model
 * @generated
 */
public interface Test4 extends Base {

	String KEY = "DERIVED";

	/**
	 * Returns the value of the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Derived</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Derived</em>' attribute.
	 * @see #setDerived(String)
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getTest4_Derived()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getDerived();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Test4#getDerived <em>Derived</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Derived</em>' attribute.
	 * @see #getDerived()
	 * @generated
	 */
	void setDerived(String value);

} // Test4
