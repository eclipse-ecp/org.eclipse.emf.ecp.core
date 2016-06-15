/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View</b></em>'.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass <em>Root EClass</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getChildren <em>Children</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getEcorePath <em>Ecore Path</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VView#getLoadingProperties <em>Loading Properties</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView()
 * @model
 * @generated
 */
public interface VView extends VElement {
	/**
	 * Returns the value of the '<em><b>Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root EClass</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Root EClass</em>' reference.
	 * @see #setRootEClass(EClass)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_RootEClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getRootEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass <em>Root EClass</em>}'
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Root EClass</em>' reference.
	 * @see #getRootEClass()
	 * @generated
	 */
	void setRootEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VContainedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<VContainedElement> getChildren();

	/**
	 * Returns the value of the '<em><b>Ecore Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ecore Path</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Ecore Path</em>' attribute.
	 * @see #setEcorePath(String)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_EcorePath()
	 * @model required="true"
	 * @generated
	 */
	String getEcorePath();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VView#getEcorePath <em>Ecore Path</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ecore Path</em>' attribute.
	 * @see #getEcorePath()
	 * @generated
	 */
	void setEcorePath(String value);

	/**
	 * Returns the value of the '<em><b>Loading Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loading Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Loading Properties</em>' containment reference.
	 * @see #setLoadingProperties(VViewModelProperties)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getView_LoadingProperties()
	 * @model containment="true" required="true" transient="true"
	 * @generated
	 */
	VViewModelProperties getLoadingProperties();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VView#getLoadingProperties
	 * <em>Loading Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loading Properties</em>' containment reference.
	 * @see #getLoadingProperties()
	 * @generated
	 */
	void setLoadingProperties(VViewModelProperties value);

	/**
	 * Sets the view and all its contents readonly.
	 */
	void setAllContentsReadOnly();

} // View
