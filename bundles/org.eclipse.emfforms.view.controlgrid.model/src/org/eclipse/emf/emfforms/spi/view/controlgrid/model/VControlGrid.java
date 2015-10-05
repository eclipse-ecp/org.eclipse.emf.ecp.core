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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control Grid</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid#getRows <em>Rows</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGrid()
 * @model
 * @generated
 */
public interface VControlGrid extends VContainedElement {
	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGrid_Rows()
	 * @model containment="true"
	 * @generated
	 */
	EList<VControlGridRow> getRows();

} // VControlGrid
