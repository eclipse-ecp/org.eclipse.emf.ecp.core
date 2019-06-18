/**
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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Model Reference Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDomainModelReference
 * <em>Domain Model Reference</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getRootEClass
 * <em>Root EClass</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage#getDomainModelReferenceSelector()
 * @model
 * @generated
 */
public interface VTDomainModelReferenceSelector extends VTStyleSelector {
	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Reference</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage#getDomainModelReferenceSelector_DomainModelReference()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDomainModelReference
	 * <em>Domain Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.21
	 *        <!-- end-user-doc -->
	 *        <!-- begin-model-doc -->
	 *        The root EClass of the selector's domain model reference. The selector's DMR is resolved against this
	 *        EClass.
	 *        <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Root EClass</em>' reference.
	 * @see #setRootEClass(EClass)
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage#getDomainModelReferenceSelector_RootEClass()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel propertyDescription='The root EClass of the
	 *        selector\'s domain model reference. The reference is resolved against this EClass.'"
	 * @generated
	 */
	EClass getRootEClass();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getRootEClass
	 * <em>Root EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.21
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Root EClass</em>' reference.
	 * @see #getRootEClass()
	 * @generated
	 */
	void setRootEClass(EClass value);

} // VTDomainModelReferenceSelector
