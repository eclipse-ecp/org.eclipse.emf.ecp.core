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
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.wrap.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label Wrap Style Property</b></em>'.
 * 
 * @since 1.17
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty#isWrapLabel <em>Wrap
 *        Label</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapPackage#getLabelWrapStyleProperty()
 * @model
 * @generated
 */
public interface VTLabelWrapStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Wrap Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wrap Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Wrap Label</em>' attribute.
	 * @see #setWrapLabel(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapPackage#getLabelWrapStyleProperty_WrapLabel()
	 * @model
	 * @generated
	 */
	boolean isWrapLabel();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty#isWrapLabel <em>Wrap
	 * Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Wrap Label</em>' attribute.
	 * @see #isWrapLabel()
	 * @generated
	 */
	void setWrapLabel(boolean value);

} // VTLabelWrapStyleProperty
