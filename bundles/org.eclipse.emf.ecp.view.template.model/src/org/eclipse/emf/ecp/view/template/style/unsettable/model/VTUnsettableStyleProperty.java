/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * 
 * @since 1.14
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonAlignment
 *        <em>Button Alignment</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonPlacement
 *        <em>Button Placement</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage#getUnsettableStyleProperty()
 * @model
 * @generated
 */
public interface VTUnsettableStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Button Alignment</b></em>' attribute.
	 * The default value is <code>"RIGHT"</code>.
	 * The literals are from the enumeration
	 * {@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Button Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Button Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
	 * @see #setButtonAlignment(ButtonAlignmentType)
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage#getUnsettableStyleProperty_ButtonAlignment()
	 * @model default="RIGHT" required="true"
	 * @generated
	 */
	ButtonAlignmentType getButtonAlignment();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonAlignment
	 * <em>Button Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Button Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType
	 * @see #getButtonAlignment()
	 * @generated
	 */
	void setButtonAlignment(ButtonAlignmentType value);

	/**
	 * Returns the value of the '<em><b>Button Placement</b></em>' attribute.
	 * The default value is <code>"RIGHT_OF_LABEL"</code>.
	 * The literals are from the enumeration
	 * {@link org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Button Placement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Button Placement</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
	 * @see #setButtonPlacement(ButtonPlacementType)
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage#getUnsettableStyleProperty_ButtonPlacement()
	 * @model default="RIGHT_OF_LABEL" required="true"
	 * @generated
	 */
	ButtonPlacementType getButtonPlacement();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty#getButtonPlacement
	 * <em>Button Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Button Placement</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType
	 * @see #getButtonPlacement()
	 * @generated
	 */
	void setButtonPlacement(ButtonPlacementType value);

} // VTUnsettableStyleProperty
