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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class TemplateInstanceRenderer_Test {

	private TemplateInstanceRenderer renderer;

	@Before
	public void setUp() throws Exception {
		renderer = new TemplateInstanceRenderer(mock(VControl.class), mock(ViewModelContext.class),
			mock(ReportService.class), null, null, null, null, null, null);
	}

	@Test
	public void testCreateAddReferenceButton() {
		final Shell shell = new Shell(Display.getDefault());
		assertEquals(0, shell.getChildren().length);
		renderer.createAddReferenceButton(shell, null);
		assertEquals(0, shell.getChildren().length);
	}

}
