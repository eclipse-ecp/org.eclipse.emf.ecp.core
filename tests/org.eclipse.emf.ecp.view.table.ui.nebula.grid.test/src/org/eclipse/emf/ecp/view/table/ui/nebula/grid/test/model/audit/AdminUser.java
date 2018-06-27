/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Admin User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser#getCreatedBy <em>Created
 * By</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getAdminUser()
 * @model
 * @generated
 */
public interface AdminUser extends RegisteredUser, PrivilegedUser {
	/**
	 * Returns the value of the '<em><b>Created By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Created By</em>' reference.
	 * @see #setCreatedBy(PrivilegedUser)
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getAdminUser_CreatedBy()
	 * @model
	 * @generated
	 */
	PrivilegedUser getCreatedBy();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser#getCreatedBy <em>Created
	 * By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Created By</em>' reference.
	 * @see #getCreatedBy()
	 * @generated
	 */
	void setCreatedBy(PrivilegedUser value);

} // AdminUser
