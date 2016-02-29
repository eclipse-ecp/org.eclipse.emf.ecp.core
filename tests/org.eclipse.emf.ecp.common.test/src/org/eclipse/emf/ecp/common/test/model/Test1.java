/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.common.test.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test1</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.Test1#getDerived <em>Derived</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getTest1()
 * @model
 * @generated
 */
public interface Test1 extends Base {
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
	 * @see org.eclipse.emf.ecp.common.test.model.TestPackage#getTest1_Derived()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getDerived();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.common.test.model.Test1#getDerived <em>Derived</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Derived</em>' attribute.
	 * @see #getDerived()
	 * @generated
	 */
	void setDerived(String value);

} // Test1
