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
package org.eclipse.emf.ecp.view.spi.table.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getColumns <em>Columns</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled <em>Add Remove Disabled</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl()
 * @model
 * @generated
 */
public interface VTableControl extends VControl
{
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_Columns()
	 * @model containment="true"
	 * @generated
	 */
	EList<VTableColumn> getColumns();

	/**
	 * Returns the value of the '<em><b>Add Remove Disabled</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Add Remove Disabled</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Add Remove Disabled</em>' attribute.
	 * @see #setAddRemoveDisabled(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_AddRemoveDisabled()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isAddRemoveDisabled();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled
	 * <em>Add Remove Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Add Remove Disabled</em>' attribute.
	 * @see #isAddRemoveDisabled()
	 * @generated
	 */
	void setAddRemoveDisabled(boolean value);

} // VTableControl
