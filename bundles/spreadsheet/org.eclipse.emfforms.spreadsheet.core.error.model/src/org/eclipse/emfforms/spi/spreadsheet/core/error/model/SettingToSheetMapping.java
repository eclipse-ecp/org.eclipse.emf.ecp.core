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
package org.eclipse.emfforms.spi.spreadsheet.core.error.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Setting To Sheet Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSheetLocation
 * <em>Sheet Location</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSettingLocation
 * <em>Setting Location</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSettingToSheetMapping()
 * @model
 * @generated
 */
public interface SettingToSheetMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Sheet Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sheet Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Sheet Location</em>' containment reference.
	 * @see #setSheetLocation(SheetLocation)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSettingToSheetMapping_SheetLocation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	SheetLocation getSheetLocation();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSheetLocation
	 * <em>Sheet Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Sheet Location</em>' containment reference.
	 * @see #getSheetLocation()
	 * @generated
	 */
	void setSheetLocation(SheetLocation value);

	/**
	 * Returns the value of the '<em><b>Setting Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Setting Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Setting Location</em>' containment reference.
	 * @see #setSettingLocation(SettingLocation)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSettingToSheetMapping_SettingLocation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	SettingLocation getSettingLocation();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSettingLocation
	 * <em>Setting Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Setting Location</em>' containment reference.
	 * @see #getSettingLocation()
	 * @generated
	 */
	void setSettingLocation(SettingLocation value);

} // SettingToSheetMapping
