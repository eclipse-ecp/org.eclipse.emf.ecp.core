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
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.GuestUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Human;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedBot;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage
 * @generated
 */
public class AuditSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static AuditPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AuditSwitch() {
		if (modelPackage == null) {
			modelPackage = AuditPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case AuditPackage.ORGANIZATION: {
			final Organization organization = (Organization) theEObject;
			T result = caseOrganization(organization);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.MEMBER: {
			final Member member = (Member) theEObject;
			T result = caseMember(member);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.BOT: {
			final Bot bot = (Bot) theEObject;
			T result = caseBot(bot);
			if (result == null) {
				result = caseMember(bot);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.HUMAN: {
			final Human human = (Human) theEObject;
			T result = caseHuman(human);
			if (result == null) {
				result = caseMember(human);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.GUEST_USER: {
			final GuestUser guestUser = (GuestUser) theEObject;
			T result = caseGuestUser(guestUser);
			if (result == null) {
				result = caseHuman(guestUser);
			}
			if (result == null) {
				result = caseMember(guestUser);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.REGISTERED_USER: {
			final RegisteredUser registeredUser = (RegisteredUser) theEObject;
			T result = caseRegisteredUser(registeredUser);
			if (result == null) {
				result = caseHuman(registeredUser);
			}
			if (result == null) {
				result = caseMember(registeredUser);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.PRIVILEGED_USER: {
			final PrivilegedUser privilegedUser = (PrivilegedUser) theEObject;
			T result = casePrivilegedUser(privilegedUser);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.ADMIN_USER: {
			final AdminUser adminUser = (AdminUser) theEObject;
			T result = caseAdminUser(adminUser);
			if (result == null) {
				result = caseRegisteredUser(adminUser);
			}
			if (result == null) {
				result = casePrivilegedUser(adminUser);
			}
			if (result == null) {
				result = caseHuman(adminUser);
			}
			if (result == null) {
				result = caseMember(adminUser);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.PRIVILEGED_BOT: {
			final PrivilegedBot privilegedBot = (PrivilegedBot) theEObject;
			T result = casePrivilegedBot(privilegedBot);
			if (result == null) {
				result = caseBot(privilegedBot);
			}
			if (result == null) {
				result = casePrivilegedUser(privilegedBot);
			}
			if (result == null) {
				result = caseMember(privilegedBot);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AuditPackage.MEMBER_GROUP: {
			final MemberGroup memberGroup = (MemberGroup) theEObject;
			T result = caseMemberGroup(memberGroup);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Organization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Organization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrganization(Organization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMember(Member object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bot</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bot</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBot(Bot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Human</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Human</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHuman(Human object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Guest User</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Guest User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGuestUser(GuestUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Registered User</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Registered User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegisteredUser(RegisteredUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Privileged User</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Privileged User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrivilegedUser(PrivilegedUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Admin User</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Admin User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdminUser(AdminUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Privileged Bot</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Privileged Bot</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrivilegedBot(PrivilegedBot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemberGroup(MemberGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // AuditSwitch
