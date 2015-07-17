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
package org.eclipse.emf.ecp.view.template.style.mandatory.model;

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
 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#isHighliteMandatoryFields
 * <em>Highlite Mandatory Fields</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#getMandatoryMarker <em>
 * Mandatory Marker</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage#getMandatoryStyleProperty()
 * @model
 * @generated
 */
public interface VTMandatoryStyleProperty extends VTStyleProperty
{
	/**
	 * Returns the value of the '<em><b>Highlite Mandatory Fields</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Highlite Mandatory Fields</em>' attribute isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Highlite Mandatory Fields</em>' attribute.
	 * @see #setHighliteMandatoryFields(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage#getMandatoryStyleProperty_HighliteMandatoryFields()
	 * @model default="true"
	 * @generated
	 */
	boolean isHighliteMandatoryFields();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#isHighliteMandatoryFields
	 * <em>Highlite Mandatory Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Highlite Mandatory Fields</em>' attribute.
	 * @see #isHighliteMandatoryFields()
	 * @generated
	 */
	void setHighliteMandatoryFields(boolean value);

	/**
	 * Returns the value of the '<em><b>Mandatory Marker</b></em>' attribute.
	 * The default value is <code>"*"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mandatory Marker</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Mandatory Marker</em>' attribute.
	 * @see #setMandatoryMarker(String)
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage#getMandatoryStyleProperty_MandatoryMarker()
	 * @model default="*"
	 * @generated
	 */
	String getMandatoryMarker();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty#getMandatoryMarker
	 * <em>Mandatory Marker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Mandatory Marker</em>' attribute.
	 * @see #getMandatoryMarker()
	 * @generated
	 */
	void setMandatoryMarker(String value);

} // VTMandatoryStyleProperty
