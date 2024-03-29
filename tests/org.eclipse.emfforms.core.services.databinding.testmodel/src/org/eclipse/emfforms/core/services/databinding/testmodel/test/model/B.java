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
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.core.services.databinding.testmodel.test.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getC <em>C</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getCList <em>CList</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getEList <em>EList</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getE <em>E</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#getB()
 * @model
 * @generated
 */
public interface B extends EObject {
	/**
	 * Returns the value of the '<em><b>C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>C</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>C</em>' containment reference.
	 * @see #setC(C)
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#getB_C()
	 * @model containment="true"
	 * @generated
	 */
	C getC();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getC
	 * <em>C</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>C</em>' containment reference.
	 * @see #getC()
	 * @generated
	 */
	void setC(C value);

	/**
	 * Returns the value of the '<em><b>CList</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CList</em>' reference list isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>CList</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#getB_CList()
	 * @model
	 * @generated
	 */
	EList<C> getCList();

	/**
	 * Returns the value of the '<em><b>EList</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EList</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>EList</em>' containment reference list.
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#getB_EList()
	 * @model containment="true"
	 * @generated
	 */
	EList<E> getEList();

	/**
	 * Returns the value of the '<em><b>E</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>E</em>' reference.
	 * @see #setE(E)
	 * @see org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage#getB_E()
	 * @model
	 * @generated
	 */
	E getE();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B#getE
	 * <em>E</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>E</em>' reference.
	 * @see #getE()
	 * @generated
	 */
	void setE(E value);

} // B
