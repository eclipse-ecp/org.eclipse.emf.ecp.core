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
package org.eclipse.emf.ecp.spi.view.template.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Plugin tests for {@link ViewTemplateSupplierUtil}.
 * <p>
 * <strong>Note:</strong> This is a plugin test because otherwise the package registry is not filled with the needed
 * EPackages.
 *
 * @author Lucas Koehler
 *
 */
public class ViewTemplateSupplierUtil_PTest {

	private static final String BASE_URI = "platform:/plugin/org.eclipse.emf.ecp.view.template.service.test/data/"; //$NON-NLS-1$

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void validViewTemplate() {
		final URI templateUri = URI
			.createURI(BASE_URI + "valid.template"); //$NON-NLS-1$
		final VTViewTemplate loadedTemplate = ViewTemplateSupplierUtil.loadViewTemplate(templateUri);

		assertNotNull("The loaded view template must not be null", loadedTemplate); //$NON-NLS-1$
		assertEquals(1, loadedTemplate.getStyles().size());
		final VTStyle style = loadedTemplate.getStyles().get(0);
		assertEquals(1, style.getProperties().size());
		assertNotNull(style.getSelector());
		assertNotNull(loadedTemplate.getControlValidationConfiguration());
	}

	@Test
	public void emptyResource() {
		final URI templateUri = URI
			.createURI(BASE_URI + "empty.template"); //$NON-NLS-1$
		final VTViewTemplate loadedTemplate = ViewTemplateSupplierUtil.loadViewTemplate(templateUri);
		assertNull(loadedTemplate);
	}

	@Test
	public void noTemplate() {
		final URI templateUri = URI
			.createURI(BASE_URI + "noTemplate.template"); //$NON-NLS-1$
		final VTViewTemplate loadedTemplate = ViewTemplateSupplierUtil.loadViewTemplate(templateUri);
		assertNull(loadedTemplate);
	}
}
