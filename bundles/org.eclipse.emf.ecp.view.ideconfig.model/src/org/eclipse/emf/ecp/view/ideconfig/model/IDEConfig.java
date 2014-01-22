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
package org.eclipse.emf.ecp.view.ideconfig.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IDE Config</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig#getEcorePath <em>Ecore Path</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.ideconfig.model.IdeconfigPackage#getIDEConfig()
 * @model
 * @generated
 */
public interface IDEConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Ecore Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ecore Path</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ecore Path</em>' attribute.
	 * @see #setEcorePath(String)
	 * @see org.eclipse.emf.ecp.view.ideconfig.model.IdeconfigPackage#getIDEConfig_EcorePath()
	 * @model
	 * @generated
	 */
	String getEcorePath();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig#getEcorePath
	 * <em>Ecore Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ecore Path</em>' attribute.
	 * @see #getEcorePath()
	 * @generated
	 */
	void setEcorePath(String value);

} // IDEConfig
