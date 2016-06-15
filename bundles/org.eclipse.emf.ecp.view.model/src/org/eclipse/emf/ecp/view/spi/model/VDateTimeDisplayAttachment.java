/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Date Time Display Attachment</b></em>'.
 * 
 * @since 1.8
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment#getDisplayType <em>Display Type</em>}
 *        </li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDateTimeDisplayAttachment()
 * @model
 * @generated
 */
public interface VDateTimeDisplayAttachment extends VAttachment {
	/**
	 * Returns the value of the '<em><b>Display Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Display Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType
	 * @see #setDisplayType(DateTimeDisplayType)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDateTimeDisplayAttachment_DisplayType()
	 * @model
	 * @generated
	 */
	DateTimeDisplayType getDisplayType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment#getDisplayType
	 * <em>Display Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Display Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType
	 * @see #getDisplayType()
	 * @generated
	 */
	void setDisplayType(DateTimeDisplayType value);

} // VDateTimeDisplayAttachment
