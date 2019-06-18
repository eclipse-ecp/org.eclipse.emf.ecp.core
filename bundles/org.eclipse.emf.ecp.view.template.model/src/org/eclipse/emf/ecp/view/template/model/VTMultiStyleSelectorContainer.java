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
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Style Selector Container</b></em>'.
 *
 * @since 1.17
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.model.VTMultiStyleSelectorContainer#getSelectors
 *        <em>Selectors</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getMultiStyleSelectorContainer()
 * @model abstract="true"
 * @generated
 */
public interface VTMultiStyleSelectorContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Selectors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.template.model.VTStyleSelector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selectors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Selectors</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getMultiStyleSelectorContainer_Selectors()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<VTStyleSelector> getSelectors();

} // VTMultiStyleSelectorContainer
