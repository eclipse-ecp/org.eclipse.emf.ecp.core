/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getDomainModelReferences <em>Domain
 * Model References</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getBundleName <em>Bundle
 * Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getClassName <em>Class
 * Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#isControlChecked <em>Control Checked
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomDomainModelReference()
 * @model
 * @generated
 * @since 1.3
 */
public interface VCustomDomainModelReference extends VDomainModelReference {
	/**
	 * Returns the value of the '<em><b>Domain Model References</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model References</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Domain Model References</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomDomainModelReference_DomainModelReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<VDomainModelReference> getDomainModelReferences();

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
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomDomainModelReference_BundleName()
	 * @model required="true"
	 * @generated
	 */
	String getBundleName();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getBundleName <em>Bundle Name</em>}'
	 * attribute.
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
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomDomainModelReference_ClassName()
	 * @model required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getClassName
	 * <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Control Checked</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Checked</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Control Checked</em>' attribute.
	 * @see #setControlChecked(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage#getCustomDomainModelReference_ControlChecked()
	 * @model default="false" required="true" transient="true"
	 * @generated
	 */
	boolean isControlChecked();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#isControlChecked
	 * <em>Control Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Control Checked</em>' attribute.
	 * @see #isControlChecked()
	 * @generated
	 */
	void setControlChecked(boolean value);

} // VCustomDomainModelReference
