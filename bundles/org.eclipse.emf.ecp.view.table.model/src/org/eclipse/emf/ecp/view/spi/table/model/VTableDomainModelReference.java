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
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getColumnDomainModelReferences
 * <em>Column Domain Model References</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getDomainModelReference <em>Domain
 * Model Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableDomainModelReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='resolveable'"
 * @generated
 */
public interface VTableDomainModelReference extends VFeaturePathDomainModelReference {

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

	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Reference</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#getTableDomainModelReference_DomainModelReference()
	 * @model containment="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getDomainModelReference <em>Domain
	 * Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);
} // VTableDomainModelReference
