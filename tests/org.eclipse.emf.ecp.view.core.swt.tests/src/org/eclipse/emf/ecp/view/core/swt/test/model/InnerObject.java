/**
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - extension for EnumComboViewerRenderer_PTest
 */
package org.eclipse.emf.ecp.view.core.swt.test.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inner Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.core.swt.test.model.InnerObject#getMyEnum <em>My Enum</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage#getInnerObject()
 * @model
 * @generated
 */
public interface InnerObject extends EObject {
	/**
	 * Returns the value of the '<em><b>My Enum</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>My Enum</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum
	 * @see #setMyEnum(TestEnum)
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage#getInnerObject_MyEnum()
	 * @model dataType="org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum"
	 * @generated
	 */
	TestEnum getMyEnum();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.core.swt.test.model.InnerObject#getMyEnum <em>My
	 * Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>My Enum</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum
	 * @see #getMyEnum()
	 * @generated
	 */
	void setMyEnum(TestEnum value);

} // InnerObject
