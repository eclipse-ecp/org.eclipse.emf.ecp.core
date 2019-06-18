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
 * A representation of the model object '<em><b>Member Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getMembers
 * <em>Members</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getAdmins <em>Admins</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getBots <em>Bots</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getMemberGroup()
 * @model
 * @generated
 */
public interface MemberGroup extends EObject {
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
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getMemberGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup#getName
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
	 * Returns the value of the '<em><b>Members</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Members</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Members</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getMemberGroup_Members()
	 * @model
	 * @generated
	 */
	EList<Human> getMembers();

	/**
	 * Returns the value of the '<em><b>Admins</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Admins</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Admins</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getMemberGroup_Admins()
	 * @model
	 * @generated
	 */
	EList<AdminUser> getAdmins();

	/**
	 * Returns the value of the '<em><b>Bots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bots</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bots</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getMemberGroup_Bots()
	 * @model containment="true"
	 * @generated
	 */
	EList<Bot> getBots();

} // MemberGroup
