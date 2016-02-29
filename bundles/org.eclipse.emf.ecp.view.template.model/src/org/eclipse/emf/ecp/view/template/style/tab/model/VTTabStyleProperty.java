/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tab.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * 
 * @since 1.8
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType <em>Type</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty()
 * @model
 * @generated
 */
public interface VTTabStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"BOTTOM"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.template.style.tab.model.TabType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @see #setType(TabType)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_Type()
	 * @model default="BOTTOM" required="true"
	 * @generated
	 */
	TabType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType
	 * <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @see #getType()
	 * @generated
	 */
	void setType(TabType value);

} // VTTabStyleProperty
