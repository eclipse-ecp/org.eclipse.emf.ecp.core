/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.stack.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getDomainModelReference <em>Domain Model Reference
 * </em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getStackItems <em>Stack Items</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getTopElement <em>Top Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackLayout()
 * @model
 * @generated
 */
public interface VStackLayout extends VContainedElement
{
	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Reference</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackLayout_DomainModelReference()
	 * @model containment="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getDomainModelReference
	 * <em>Domain Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Stack Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.stack.model.VStackItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stack Items</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Stack Items</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackLayout_StackItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<VStackItem> getStackItems();

	/**
	 * Returns the value of the '<em><b>Top Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Top Element</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Top Element</em>' reference.
	 * @see #setTopElement(VStackItem)
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackLayout_TopElement()
	 * @model transient="true"
	 * @generated
	 */
	VStackItem getTopElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout#getTopElement
	 * <em>Top Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Top Element</em>' reference.
	 * @see #getTopElement()
	 * @generated
	 */
	void setTopElement(VStackItem value);

} // VStackLayout
