/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditFactory;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link XmiTemplateProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class XmiTemplateProvider_Test {

	private XmiTemplateProvider provider;
	private Template adminTemplate;
	private Template guestTemplate;
	private Template registeredTemplate;

	@Before
	public void setUp() {
		adminTemplate = DataTemplateFactory.eINSTANCE.createTemplate();
		adminTemplate.setName("Admin Template"); //$NON-NLS-1$
		guestTemplate = DataTemplateFactory.eINSTANCE.createTemplate();
		guestTemplate.setName("Guest Template"); //$NON-NLS-1$
		registeredTemplate = DataTemplateFactory.eINSTANCE.createTemplate();
		registeredTemplate.setName("Registered Template"); //$NON-NLS-1$

		final AdminUser adminUser = AuditFactory.eINSTANCE.createAdminUser();
		adminUser.setLogin("admin"); //$NON-NLS-1$
		final GuestUser guestUser = AuditFactory.eINSTANCE.createGuestUser();
		guestUser.setLogin("guest"); //$NON-NLS-1$
		final RegisteredUser registeredUser = AuditFactory.eINSTANCE.createRegisteredUser();
		registeredUser.setLogin("registered"); //$NON-NLS-1$

		adminTemplate.setInstance(adminUser);
		guestTemplate.setInstance(guestUser);
		registeredTemplate.setInstance(registeredUser);

		provider = new XmiTemplateProvider();
		provider.setReportService(mock(ReportService.class));
		provider.registerTemplate(adminTemplate);
		provider.registerTemplate(guestTemplate);
		provider.registerTemplate(registeredTemplate);
	}

	@Test
	public void testCanProvideTemplates() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		assertTrue(provider.canProvideTemplates(group, AuditPackage.Literals.USER_GROUP__ADMINS));
		assertTrue(provider.canProvideTemplates(group, AuditPackage.Literals.USER_GROUP__USERS));
		assertTrue(provider.canProvideTemplates(group, AuditPackage.Literals.USER__DELEGATES));
		assertFalse(provider.canProvideTemplates(group, AuditPackage.Literals.USER__SUB_USERS));
	}

	@Test
	public void testProvideTemplates() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		final User user = AuditFactory.eINSTANCE.createGuestUser();

		// Only the template for admin matches
		final Set<Template> adminSet = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__ADMINS);
		assertEquals(1, adminSet.size());
		assertEquals(adminTemplate, adminSet.iterator().next());

		// Only the template for guest matches
		final Set<Template> guestSet = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__GUESTS);
		assertEquals(1, guestSet.size());
		assertEquals(guestTemplate, guestSet.iterator().next());

		// The templates for registered and admin (sub class of registered) match
		final Set<Template> registeredSet = provider.provideTemplates(group,
			AuditPackage.Literals.USER_GROUP__REGISTERED_USERS);
		assertEquals(2, registeredSet.size());
		assertTrue(registeredSet.contains(adminTemplate));
		assertTrue(registeredSet.contains(registeredTemplate));

		// The template for admin (sub class of privileged) matches
		final Set<Template> privilegedSet = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__ADMINS);
		assertEquals(1, privilegedSet.size());
		assertEquals(adminTemplate, privilegedSet.iterator().next());

		// All templates match (all other user classes are sub classes of User)
		final Set<Template> userSet = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__USERS);
		assertEquals(3, userSet.size());
		assertTrue(userSet.contains(adminTemplate));
		assertTrue(userSet.contains(registeredTemplate));
		assertTrue(userSet.contains(guestTemplate));

		// No templates matches for sub-user
		final Set<Template> userGroupSet = provider.provideTemplates(user, AuditPackage.Literals.USER__SUB_USERS);
		assertEquals(0, userGroupSet.size());
	}
}
