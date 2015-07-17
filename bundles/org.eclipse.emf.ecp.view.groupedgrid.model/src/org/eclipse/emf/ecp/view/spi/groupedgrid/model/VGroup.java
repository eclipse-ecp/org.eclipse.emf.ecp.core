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
package org.eclipse.emf.ecp.view.spi.groupedgrid.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getRows <em>Rows</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getGroup()
 * @model
 * @generated
 * @since 1.2
 */
public interface VGroup extends EObject
{
	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getGroup_Rows()
	 * @model containment="true"
	 * @generated
	 */
	EList<VRow> getRows();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getGroup_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup#getName <em>Name</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // VGroup
