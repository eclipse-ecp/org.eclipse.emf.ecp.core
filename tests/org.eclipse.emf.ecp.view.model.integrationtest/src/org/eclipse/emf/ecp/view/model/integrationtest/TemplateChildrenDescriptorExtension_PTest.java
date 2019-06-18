/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.integrationtest;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.junit.Test;

public class TemplateChildrenDescriptorExtension_PTest {

	private static final int SELECTOR_COUNT = 5;
	private static final int PROPERTY_COUNT = 15;

	@Test
	public void testStyleChildren() {
		assertEquals(
			SELECTOR_COUNT +
				PROPERTY_COUNT,
			ChildrenDescriptorExtension_PTest
				.getChildrenSize(VTTemplatePackage.eINSTANCE.getStyle()));
	}

}
