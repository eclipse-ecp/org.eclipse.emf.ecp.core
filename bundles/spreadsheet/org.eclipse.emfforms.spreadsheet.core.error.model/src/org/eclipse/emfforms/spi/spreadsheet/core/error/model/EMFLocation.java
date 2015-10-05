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
 * A representation of the model object '<em><b>EMF Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getRoot <em>Root</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getSettingLocation
 * <em>Setting Location</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getDmrLocation <em>Dmr Location</em>}
 * </li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getEMFLocation()
 * @model
 * @generated
 */
public interface EMFLocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Root</em>' reference.
	 * @see #setRoot(EObject)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getEMFLocation_Root()
	 * @model required="true"
	 * @generated
	 */
	EObject getRoot();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getRoot
	 * <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Root</em>' reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(EObject value);

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
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getEMFLocation_SettingLocation()
	 * @model containment="true"
	 * @generated
	 */
	SettingLocation getSettingLocation();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getSettingLocation
	 * <em>Setting Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Setting Location</em>' containment reference.
	 * @see #getSettingLocation()
	 * @generated
	 */
	void setSettingLocation(SettingLocation value);

	/**
	 * Returns the value of the '<em><b>Dmr Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dmr Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Dmr Location</em>' containment reference.
	 * @see #setDmrLocation(DMRLocation)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getEMFLocation_DmrLocation()
	 * @model containment="true"
	 * @generated
	 */
	DMRLocation getDmrLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getDmrLocation
	 * <em>Dmr Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Dmr Location</em>' containment reference.
	 * @see #getDmrLocation()
	 * @generated
	 */
	void setDmrLocation(DMRLocation value);

} // EMFLocation
