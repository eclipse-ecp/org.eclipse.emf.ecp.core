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
package org.eclipse.emf.ecp.view.spi.table.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Read Only Column Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration#getColumnDomainReferences <em>Column
 * Domain References</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getReadOnlyColumnConfiguration()
 * @model
 * @generated
 */
public interface VReadOnlyColumnConfiguration extends VTableColumnConfiguration {
	/**
	 * Returns the value of the '<em><b>Column Domain References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Domain References</em>' reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Column Domain References</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getReadOnlyColumnConfiguration_ColumnDomainReferences()
	 * @model
	 * @generated
	 */
	EList<VDomainModelReference> getColumnDomainReferences();

} // VReadOnlyColumnConfiguration
