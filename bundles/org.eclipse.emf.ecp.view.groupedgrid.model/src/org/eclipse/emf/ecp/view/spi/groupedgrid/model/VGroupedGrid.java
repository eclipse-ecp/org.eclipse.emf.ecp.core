/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.groupedgrid.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Grouped Grid</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getGroupedGrid()
 * @model
 * @generated
 * @since 1.2
 */
public interface VGroupedGrid extends VContainedElement
{
	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getGroupedGrid_Groups()
	 * @model containment="true"
	 * @generated
	 */
	EList<VGroup> getGroups();

} // VGroupedGrid
