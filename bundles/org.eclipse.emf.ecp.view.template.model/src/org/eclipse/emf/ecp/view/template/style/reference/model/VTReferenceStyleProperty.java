/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.style.reference.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 *
 * @since 1.18
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowCreateAndLinkButtonForCrossReferences
 *        <em>Show Create And Link Button For Cross References</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowLinkButtonForContainmentReferences
 *        <em>Show Link Button For Containment References</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage#getReferenceStyleProperty()
 * @model
 * @generated
 */
public interface VTReferenceStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Show Create And Link Button For Cross References</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Create And Link Button For Cross References</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Show Create And Link Button For Cross References</em>' attribute.
	 * @see #setShowCreateAndLinkButtonForCrossReferences(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage#getReferenceStyleProperty_ShowCreateAndLinkButtonForCrossReferences()
	 * @model default="true"
	 * @generated
	 */
	boolean isShowCreateAndLinkButtonForCrossReferences();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowCreateAndLinkButtonForCrossReferences
	 * <em>Show Create And Link Button For Cross References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Show Create And Link Button For Cross References</em>' attribute.
	 * @see #isShowCreateAndLinkButtonForCrossReferences()
	 * @generated
	 */
	void setShowCreateAndLinkButtonForCrossReferences(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Link Button For Containment References</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.20
	 *        <!-- end-user-doc -->
	 *        <!-- begin-model-doc -->
	 *        If true, a button to link existing model objects is shown for containment reference controls. Otherwise,
	 *        the
	 *        button is hidden.
	 *        <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Show Link Button For Containment References</em>' attribute.
	 * @see #setShowLinkButtonForContainmentReferences(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage#getReferenceStyleProperty_ShowLinkButtonForContainmentReferences()
	 * @model default="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyDescription='If true, a button to link
	 *        existing model objects is shown for containment reference controls. Otherwise, the button is hidden.
	 *        [default: true]'"
	 * @generated
	 */
	boolean isShowLinkButtonForContainmentReferences();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty#isShowLinkButtonForContainmentReferences
	 * <em>Show Link Button For Containment References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.20
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Show Link Button For Containment References</em>' attribute.
	 * @see #isShowLinkButtonForContainmentReferences()
	 * @generated
	 */
	void setShowLinkButtonForContainmentReferences(boolean value);

} // VTReferenceStyleProperty
