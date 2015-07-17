/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableValidation.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnWidth
 * <em>Column Width</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnName
 * <em>Column Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getImagePath
 * <em>Image Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage#getTableValidationStyleProperty()
 * @model
 * @generated
 */
public interface VTTableValidationStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Column Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Width</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Column Width</em>' attribute.
	 * @see #setColumnWidth(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage#getTableValidationStyleProperty_ColumnWidth()
	 * @model
	 * @generated
	 */
	int getColumnWidth();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnWidth
	 * <em>Column Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Column Width</em>' attribute.
	 * @see #getColumnWidth()
	 * @generated
	 */
	void setColumnWidth(int value);

	/**
	 * Returns the value of the '<em><b>Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Column Name</em>' attribute.
	 * @see #setColumnName(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage#getTableValidationStyleProperty_ColumnName()
	 * @model
	 * @generated
	 */
	String getColumnName();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getColumnName
	 * <em>Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Column Name</em>' attribute.
	 * @see #getColumnName()
	 * @generated
	 */
	void setColumnName(String value);

	/**
	 * Returns the value of the '<em><b>Image Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image Path</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Image Path</em>' attribute.
	 * @see #setImagePath(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage#getTableValidationStyleProperty_ImagePath()
	 * @model
	 * @generated
	 */
	String getImagePath();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getImagePath
	 * <em>Image Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Image Path</em>' attribute.
	 * @see #getImagePath()
	 * @generated
	 */
	void setImagePath(String value);

} // VTTableValidationStyleProperty
