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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control Grid Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow#getCells <em>Cells</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGridRow()
 * @model
 * @generated
 */
public interface VControlGridRow extends EObject {
	/**
	 * Returns the value of the '<em><b>Cells</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cells</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cells</em>' containment reference list.
	 * @see org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage#getControlGridRow_Cells()
	 * @model containment="true"
	 * @generated
	 */
	EList<VControlGridCell> getCells();

} // VControlGridRow
