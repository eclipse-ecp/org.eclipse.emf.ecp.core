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
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getColumnDomainModelReferences <em>
 * Column Domain Model References</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableDomainModelReference()
 * @model
 * @generated
 */
public interface VTableDomainModelReference extends VFeaturePathDomainModelReference
{

	/**
	 * Returns the value of the '<em><b>Column Domain Model References</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Domain Model References</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Column Domain Model References</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableDomainModelReference_ColumnDomainModelReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<VDomainModelReference> getColumnDomainModelReferences();
} // VTableDomainModelReference
