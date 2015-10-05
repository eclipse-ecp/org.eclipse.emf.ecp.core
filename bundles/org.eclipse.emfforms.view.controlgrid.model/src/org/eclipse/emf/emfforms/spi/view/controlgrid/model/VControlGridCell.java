/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.emfforms.spi.view.controlgrid.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control Grid Cell</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell#getControl <em>Control</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGridCell()
 * @model
 * @generated
 */
public interface VControlGridCell extends EObject {
	/**
	 * Returns the value of the '<em><b>Control</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Control</em>' containment reference.
	 * @see #setControl(VControl)
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGridCell_Control()
	 * @model containment="true"
	 * @generated
	 */
	VControl getControl();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell#getControl
	 * <em>Control</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Control</em>' containment reference.
	 * @see #getControl()
	 * @generated
	 */
	void setControl(VControl value);

} // VControlGridCell
