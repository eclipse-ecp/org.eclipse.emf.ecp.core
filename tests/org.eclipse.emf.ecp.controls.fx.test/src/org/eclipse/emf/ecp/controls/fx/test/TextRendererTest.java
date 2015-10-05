/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.controls.renderer.fx.TextRendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.control.TextField;

/**
 * @author Lucas
 *
 */
public class TextRendererTest extends AbstractControlRendererTest {
	private TextRendererFX renderer;
	private Player player;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		player = BowlingFactory.eINSTANCE.createPlayer();

		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());

		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(vControl,
			player);

		final FXTestReportService reportService = new FXTestReportService();

		renderer = new TextRendererFX(vControl, viewModelContext, reportService);
	}

	@Test
	public void testTextFieldChangeAffectsModel() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final TextField textField = (TextField) renderer.render(new GridCellFX(0, 2, renderer));
		final String str = "Peter Peterson"; //$NON-NLS-1$
		textField.setText(str);
		assertEquals(str, player.getName());
	}

	@Test
	public void testModelChangeAffectsTextField() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final TextField textField = (TextField) renderer.render(new GridCellFX(0, 2, renderer));
		final String str = "Max Muster"; //$NON-NLS-1$
		player.setName(str);
		assertEquals(str, textField.getText());
	}
}
