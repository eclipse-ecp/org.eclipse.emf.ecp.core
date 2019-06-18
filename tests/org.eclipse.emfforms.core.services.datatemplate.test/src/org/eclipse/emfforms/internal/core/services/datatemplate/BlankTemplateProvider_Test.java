/*******************************************************************************
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
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditFactory;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;

/**
 * Unit tests for {@link BlankTemplateProvider}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class BlankTemplateProvider_Test {

	private BlankTemplateProvider provider;

	/**
	 * Set up the test cases.
	 */
	@Before
	public void setUp() {
		provider = new BlankTemplateProvider();
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(BlankTemplateProvider.class,
			MessageKeys.BlankTemplateProvider_blankTemplateLabel)).thenReturn("Blank {0}"); //$NON-NLS-1$
		when(localizationService.getString(any(Bundle.class), any(String.class))).thenAnswer(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				final String classKey = (String) invocation.getArguments()[1];
				return classKey.substring(4, classKey.length() - 5);
			}
		});

		final BundleResolver bundleResolver = mock(BundleResolver.class);
		provider.setBundleResolver(bundleResolver);
		provider.setLocalizationService(localizationService);
		final ComponentContext context = mock(ComponentContext.class);
		when(context.getProperties()).thenReturn(new Hashtable<String, Object>());
	}

	@Test
	public void testCanProvide_ConcreteClass() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		assertTrue(provider.canProvideTemplates(group, AuditPackage.Literals.USER_GROUP__ADMINS));
	}

	@Test
	public void testCanProvide_ConcreteSubClass() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		assertTrue(provider.canProvideTemplates(group, AuditPackage.Literals.USER_GROUP__USERS));
	}

	@Test
	public void testCanProvide_NoConcreteSubClass() {
		final User user = AuditFactory.eINSTANCE.createGuestUser();
		assertFalse(provider.canProvideTemplates(user, AuditPackage.Literals.USER__SUB_USERS));
	}

	@Test
	public void testProvide_NoSubClasses() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		final Set<Template> result = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__ADMINS);
		assertEquals(1, result.size());
		final Template template = result.iterator().next();
		assertEquals(AuditPackage.eINSTANCE.getAdminUser(), template.getInstance().eClass());
		assertEquals("Blank AdminUser", template.getName()); //$NON-NLS-1$
	}

	@Test
	public void testProvide_ConcreteSubClasses() {
		final UserGroup group = AuditFactory.eINSTANCE.createUserGroup();
		final Set<Template> result = provider.provideTemplates(group, AuditPackage.Literals.USER_GROUP__USERS);
		assertEquals(3, result.size());
		assertTrue(hasTemplateWithName(result, "Blank AdminUser")); //$NON-NLS-1$
		assertTrue(hasTemplateWithName(result, "Blank GuestUser")); //$NON-NLS-1$
		assertTrue(hasTemplateWithName(result, "Blank RegisteredUser")); //$NON-NLS-1$

	}

	private boolean hasTemplateWithName(Collection<Template> templates, String name) {
		for (final Template template : templates) {
			if (template.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testProvide_NonConcreteSubClass() {
		final User user = AuditFactory.eINSTANCE.createGuestUser();
		final Set<Template> result = provider.provideTemplates(user, AuditPackage.Literals.USER__SUB_USERS);
		assertEquals(0, result.size());
	}
}
