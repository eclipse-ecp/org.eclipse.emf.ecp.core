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
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.compoundcontrol.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compound Control</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl#getControls <em>Controls</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage#getCompoundControl()
 * @model
 * @generated
 */
public interface VCompoundControl extends VControl {
	/**
	 * Returns the value of the '<em><b>Controls</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VControl}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controls</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Controls</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage#getCompoundControl_Controls()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<VControl> getControls();

} // VCompoundControl
