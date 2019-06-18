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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getMembers
 * <em>Members</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getGroups
 * <em>Groups</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getOrganization()
 * @model
 * @generated
 */
public interface Organization extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getOrganization_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization#getName
	 * <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Members</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getOrganization_Members()
	 * @model containment="true"
	 * @generated
	 */
	EList<Member> getMembers();

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getOrganization_Groups()
	 * @model containment="true"
	 * @generated
	 */
	EList<MemberGroup> getGroups();

} // Organization
