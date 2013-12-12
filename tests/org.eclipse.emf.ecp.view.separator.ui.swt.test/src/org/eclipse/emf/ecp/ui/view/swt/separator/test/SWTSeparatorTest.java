/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.separator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.separator.model.VSeparator;
import org.eclipse.emf.ecp.view.spi.separator.model.VSeparatorFactory;
import org.eclipse.emf.ecp.view.test.common.swt.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.SWTViewTestHelper;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class SWTSeparatorTest {

	/**
	 * 
	 */
	private static final String SEPARATOR_NAME = "separator";

	@Test
	public void testSeparator() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		ECPProjectWithNameExistsException {

		// setup model
		final VView view = VViewFactory.eINSTANCE.createView();
		final VSeparator separator = VSeparatorFactory.eINSTANCE.createSeparator();
		separator.setName(SEPARATOR_NAME);
		view.getChildren().add(separator);

		// setup ui
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final Control control = SWTViewTestHelper.render(separator, view, shell);

		final Control renderedControl = shell.getChildren()[0];
		assertEquals("Rendered Control and control returned by renderer are not the same", control, renderedControl);
		assertTrue(renderedControl instanceof Label);
		final Label label = (Label) renderedControl;
		assertEquals("Rendered Separator does not have correct name", SEPARATOR_NAME, label.getText());
		assertEquals("org_eclipse_emf_ecp_ui_seperator", renderedControl.getData("org.eclipse.rap.rwt.customVariant"));

	}
}
