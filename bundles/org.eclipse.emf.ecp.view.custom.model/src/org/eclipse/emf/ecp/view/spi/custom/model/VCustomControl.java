/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model;

import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getBundleName <em>Bundle Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomControl()
 * @model
 * @generated
 * @since 1.3
 */
public interface VCustomControl extends VControl
{
	/**
	 * Returns the value of the '<em><b>Bundle Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bundle Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bundle Name</em>' attribute.
	 * @see #setBundleName(String)
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomControl_BundleName()
	 * @model required="true"
	 * @generated
	 */
	String getBundleName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getBundleName
	 * <em>Bundle Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Bundle Name</em>' attribute.
	 * @see #getBundleName()
	 * @generated
	 */
	void setBundleName(String value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomControl_ClassName()
	 * @model required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getClassName
	 * <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

} // VCustomControl
