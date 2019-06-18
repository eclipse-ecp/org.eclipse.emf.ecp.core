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
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AdminUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditFactory;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.GuestUser;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.MemberGroup;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.PrivilegedBot;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AuditFactoryImpl extends EFactoryImpl implements AuditFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static AuditFactory init() {
		try {
			final AuditFactory theAuditFactory = (AuditFactory) EPackage.Registry.INSTANCE
				.getEFactory(AuditPackage.eNS_URI);
			if (theAuditFactory != null) {
				return theAuditFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AuditFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AuditFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case AuditPackage.ORGANIZATION:
			return createOrganization();
		case AuditPackage.BOT:
			return createBot();
		case AuditPackage.GUEST_USER:
			return createGuestUser();
		case AuditPackage.REGISTERED_USER:
			return createRegisteredUser();
		case AuditPackage.ADMIN_USER:
			return createAdminUser();
		case AuditPackage.PRIVILEGED_BOT:
			return createPrivilegedBot();
		case AuditPackage.MEMBER_GROUP:
			return createMemberGroup();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Organization createOrganization() {
		final OrganizationImpl organization = new OrganizationImpl();
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Bot createBot() {
		final BotImpl bot = new BotImpl();
		return bot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public GuestUser createGuestUser() {
		final GuestUserImpl guestUser = new GuestUserImpl();
		return guestUser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RegisteredUser createRegisteredUser() {
		final RegisteredUserImpl registeredUser = new RegisteredUserImpl();
		return registeredUser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AdminUser createAdminUser() {
		final AdminUserImpl adminUser = new AdminUserImpl();
		return adminUser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PrivilegedBot createPrivilegedBot() {
		final PrivilegedBotImpl privilegedBot = new PrivilegedBotImpl();
		return privilegedBot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MemberGroup createMemberGroup() {
		final MemberGroupImpl memberGroup = new MemberGroupImpl();
		return memberGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AuditPackage getAuditPackage() {
		return (AuditPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AuditPackage getPackage() {
		return AuditPackage.eINSTANCE;
	}

} // AuditFactoryImpl
