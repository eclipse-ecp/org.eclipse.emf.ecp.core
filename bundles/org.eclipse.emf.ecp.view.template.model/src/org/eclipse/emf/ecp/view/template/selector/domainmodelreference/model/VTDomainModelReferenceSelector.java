/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDmrRootEClass
 * <em>Dmr Root EClass</em>}</li>
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
	 * Returns the value of the '<em><b>Dmr Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * The root EClass of this selector's domain model reference. It is needed to unambiguously determine which feature
	 * is referenced by the DMR.
	 * </p>
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Dmr Root EClass</em>' reference.
	 * @see #setDmrRootEClass(EClass)
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage#getDomainModelReferenceSelector_DmrRootEClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getDmrRootEClass();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDmrRootEClass
	 * <em>Dmr Root EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Dmr Root EClass</em>' reference.
	 * @see #getDmrRootEClass()
	 * @generated
	 */
	void setDmrRootEClass(EClass value);

} // VTDomainModelReferenceSelector
