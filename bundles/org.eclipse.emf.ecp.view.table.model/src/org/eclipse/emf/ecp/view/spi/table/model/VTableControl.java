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
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled <em>Add Remove
 * Disabled</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getColumnConfigurations <em>Column
 * Configurations</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailEditing <em>Detail Editing</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailView <em>Detail View</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isEnableDetailEditingDialog <em>Enable Detail
 * Editing Dialog</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='resolveable'"
 * @generated
 */
public interface VTableControl extends VControl {
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
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled <em>Add
	 * Remove Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Add Remove Disabled</em>' attribute.
	 * @see #isAddRemoveDisabled()
	 * @generated
	 */
	void setAddRemoveDisabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Column Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Configurations</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Column Configurations</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_ColumnConfigurations()
	 * @model containment="true"
	 * @generated
	 */
	EList<VTableColumnConfiguration> getColumnConfigurations();

	/**
	 * Returns the value of the '<em><b>Detail Editing</b></em>' attribute.
	 * The default value is <code>"None"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.table.model.DetailEditing}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Detail Editing</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Detail Editing</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * @see #setDetailEditing(DetailEditing)
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_DetailEditing()
	 * @model default="None" required="true"
	 * @generated
	 */
	DetailEditing getDetailEditing();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailEditing <em>Detail
	 * Editing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Detail Editing</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * @see #getDetailEditing()
	 * @generated
	 */
	void setDetailEditing(DetailEditing value);

	/**
	 * Returns the value of the '<em><b>Detail View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Detail View</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Detail View</em>' containment reference.
	 * @see #setDetailView(VView)
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_DetailView()
	 * @model containment="true"
	 * @generated
	 */
	VView getDetailView();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailView <em>Detail
	 * View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Detail View</em>' containment reference.
	 * @see #getDetailView()
	 * @generated
	 */
	void setDetailView(VView value);

	/**
	 * Returns the value of the '<em><b>Enable Detail Editing Dialog</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Detail Editing Dialog</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 *
	 * @deprecated
	 * 			<!-- end-user-doc -->
	 * @return the value of the '<em>Enable Detail Editing Dialog</em>' attribute.
	 * @see #setEnableDetailEditingDialog(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableControl_EnableDetailEditingDialog()
	 * @model default="false"
	 * @generated
	 */
	@Deprecated
	boolean isEnableDetailEditingDialog();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isEnableDetailEditingDialog
	 * <em>Enable Detail Editing Dialog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @deprecated
	 * 			<!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Detail Editing Dialog</em>' attribute.
	 * @see #isEnableDetailEditingDialog()
	 * @generated
	 */
	@Deprecated
	void setEnableDetailEditingDialog(boolean value);

} // VTableControl
