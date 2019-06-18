/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getControlValidationConfiguration <em>Control
 * Validation Configuration</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getStyles <em>Styles</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getReferencedEcores <em>Referenced
 * Ecores</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getViewTemplate()
 * @model
 * @generated
 */
public interface VTViewTemplate extends EObject {
	/**
	 * Returns the value of the '<em><b>Control Validation Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Validation Configuration</em>' containment reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * 
	 * @deprecated Use the {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty
	 *             VTValidationProperty} instead.
	 *             <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Control Validation Configuration</em>' containment reference.
	 * @see #setControlValidationConfiguration(VTControlValidationTemplate)
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getViewTemplate_ControlValidationConfiguration()
	 * @model containment="true"
	 * @generated
	 */
	@Deprecated
	VTControlValidationTemplate getControlValidationConfiguration();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.model.VTViewTemplate#getControlValidationConfiguration <em>Control
	 * Validation Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * 
	 * @deprecated Use the {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty
	 *             VTValidationProperty} instead.
	 *             <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Control Validation Configuration</em>' containment reference.
	 * @see #getControlValidationConfiguration()
	 * @generated
	 */
	@Deprecated
	void setControlValidationConfiguration(VTControlValidationTemplate value);

	/**
	 * Returns the value of the '<em><b>Styles</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.template.model.VTStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Styles</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Styles</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getViewTemplate_Styles()
	 * @model containment="true"
	 * @generated
	 */
	EList<VTStyle> getStyles();

	/**
	 * Returns the value of the '<em><b>Referenced Ecores</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Ecores</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Referenced Ecores</em>' attribute list.
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getViewTemplate_ReferencedEcores()
	 * @model
	 * @generated
	 */
	EList<String> getReferencedEcores();

} // VTViewTemplate
