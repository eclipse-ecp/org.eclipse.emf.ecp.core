/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.stack.model;

import org.eclipse.emf.ecp.view.spi.model.VContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackItem#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackItem()
 * @model
 * @generated
 */
public interface VStackItem extends VContainer
{
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage#getStackItem_Value()
	 * @model
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.stack.model.VStackItem#getValue <em>Value</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

} // VStackItem
