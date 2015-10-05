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
 * A representation of the model object '<em><b>Report</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSeverity <em>Severity</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getMessage <em>Message</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getEmfLocation <em>Emf Location</em>}
 * </li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSheetLocation <em>Sheet Location</em>
 * }</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getErrorReport()
 * @model
 * @generated
 */
public interface ErrorReport extends EObject {
	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * @see #setSeverity(Severity)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getErrorReport_Severity()
	 * @model required="true"
	 * @generated
	 */
	Severity getSeverity();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSeverity
	 * <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(Severity value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getErrorReport_Message()
	 * @model required="true"
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getMessage
	 * <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Emf Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Emf Location</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Emf Location</em>' containment reference.
	 * @see #setEmfLocation(EMFLocation)
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getErrorReport_EmfLocation()
	 * @model containment="true"
	 * @generated
	 */
	EMFLocation getEmfLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getEmfLocation
	 * <em>Emf Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Emf Location</em>' containment reference.
	 * @see #getEmfLocation()
	 * @generated
	 */
	void setEmfLocation(EMFLocation value);

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
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getErrorReport_SheetLocation()
	 * @model containment="true"
	 * @generated
	 */
	SheetLocation getSheetLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSheetLocation
	 * <em>Sheet Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Sheet Location</em>' containment reference.
	 * @see #getSheetLocation()
	 * @generated
	 */
	void setSheetLocation(SheetLocation value);

} // ErrorReport
