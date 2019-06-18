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
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.datatemplate.Template;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * Tests that the {@link XmiTemplateProvider} correctly reads in the extension point
 *
 * @author Lucas Koehler
 *
 */
public class XmiTemplateProvider_PTest {

	/**
	 * Tests that the extension point is read in correctly
	 *
	 * @throws InvalidSyntaxException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExtensionPoint() throws InvalidSyntaxException, NoSuchFieldException, SecurityException,
		IllegalArgumentException, IllegalAccessException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(XmiTemplateProvider_PTest.class).getBundleContext();
		final Collection<ServiceReference<TemplateProvider>> serviceReferences = bundleContext
			.getServiceReferences(TemplateProvider.class, null);

		XmiTemplateProvider provider = null;
		for (final ServiceReference<TemplateProvider> ref : serviceReferences) {
			final TemplateProvider service = bundleContext.getService(ref);
			if (service instanceof XmiTemplateProvider) {
				provider = (XmiTemplateProvider) service;
				break;
			}
		}

		assertNotNull("The XmiTemplateProvider was not registered", provider); //$NON-NLS-1$

		// Get templates by reflection
		final Field templatesField = provider.getClass().getDeclaredField("templates"); //$NON-NLS-1$
		templatesField.setAccessible(true);
		final Map<EClass, LinkedHashSet<Template>> templates = (Map<EClass, LinkedHashSet<Template>>) templatesField
			.get(provider);

		assertEquals(2, templates.keySet().size());
		final LinkedHashSet<Template> set1 = templates.get(AuditPackage.eINSTANCE.getAdminUser());
		assertEquals("There should be exactly one template for AdminUser", 1, set1.size()); //$NON-NLS-1$

		final LinkedHashSet<Template> set2 = templates.get(AuditPackage.eINSTANCE.getRegisteredUser());
		assertEquals("There should be exactly one template for RegisteredUser", 1, set2.size()); //$NON-NLS-1$
	}

}
