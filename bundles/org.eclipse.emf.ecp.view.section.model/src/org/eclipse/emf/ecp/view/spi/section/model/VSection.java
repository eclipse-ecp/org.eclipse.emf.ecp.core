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
package org.eclipse.emf.ecp.view.spi.section.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.VSection#getChildItems <em>Child Items</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.VSection#isCollapsed <em>Collapsed</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#getSection()
 * @model
 * @generated
 */
public interface VSection extends VContainer
{
	/**
	 * Returns the value of the '<em><b>Child Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.section.model.VSection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Items</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Child Items</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#getSection_ChildItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<VSection> getChildItems();

	/**
	 * Returns the value of the '<em><b>Collapsed</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collapsed</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Collapsed</em>' attribute.
	 * @see #setCollapsed(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage#getSection_Collapsed()
	 * @model default="false"
	 * @generated
	 */
	boolean isCollapsed();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.section.model.VSection#isCollapsed <em>Collapsed</em>}
	 * ' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Collapsed</em>' attribute.
	 * @see #isCollapsed()
	 * @generated
	 */
	void setCollapsed(boolean value);

} // VSection
