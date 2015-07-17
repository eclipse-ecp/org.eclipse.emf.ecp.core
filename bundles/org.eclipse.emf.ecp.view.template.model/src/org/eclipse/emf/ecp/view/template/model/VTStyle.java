/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.VTStyle#getSelector <em>Selector</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.VTStyle#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyle()
 * @model
 * @generated
 */
public interface VTStyle extends EObject
{
	/**
	 * Returns the value of the '<em><b>Selector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selector</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Selector</em>' containment reference.
	 * @see #setSelector(VTStyleSelector)
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyle_Selector()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VTStyleSelector getSelector();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.model.VTStyle#getSelector <em>Selector</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Selector</em>' containment reference.
	 * @see #getSelector()
	 * @generated
	 */
	void setSelector(VTStyleSelector value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.template.model.VTStyleProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyle_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<VTStyleProperty> getProperties();

} // VTStyle
