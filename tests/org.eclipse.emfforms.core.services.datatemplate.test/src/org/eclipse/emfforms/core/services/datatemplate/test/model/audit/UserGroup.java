/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getUsers <em>Users</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getAdmins <em>Admins</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup()
 * @model
 * @generated
 */
public interface UserGroup extends EObject {
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
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup#getName
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
	 * Returns the value of the '<em><b>Users</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Users</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Users()
	 * @model
	 * @generated
	 */
	EList<User> getUsers();

	/**
	 * Returns the value of the '<em><b>Admins</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Admins</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Admins</em>' reference list.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage#getUserGroup_Admins()
	 * @model
	 * @generated
	 */
	EList<PrivilegedUser> getAdmins();

} // UserGroup
