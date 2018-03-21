/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.integrationtest;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.junit.Test;

public class TemplateChildrenDescriptorExtension_PTest {

	private static final int SELECTOR_COUNT = 2;
	private static final int PROPERTY_COUNT = 12;

	@Test
	public void testStyleChildren() {
		assertEquals(
			SELECTOR_COUNT +
				PROPERTY_COUNT,
			ChildrenDescriptorExtension_PTest
				.getChildrenSize(VTTemplatePackage.eINSTANCE.getStyle()));
	}

}
