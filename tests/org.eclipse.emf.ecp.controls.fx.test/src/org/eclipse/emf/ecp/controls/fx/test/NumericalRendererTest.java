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

import java.text.DecimalFormatSymbols;

import javafx.scene.control.TextField;

import org.eclipse.emf.ecp.controls.renderer.fx.NumericalRendererFX;
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

/**
 * @author Lucas
 *
 */
public class NumericalRendererTest extends AbstractControlRendererTest {
	private NumericalRendererFX renderer;
	private Player player;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		player = BowlingFactory.eINSTANCE.createPlayer();

		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Height());

		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(vControl,
			player);

		renderer = new NumericalRendererFX();
		renderer.init(vControl, viewModelContext);
	}

	@Test
	public void testTextFieldChangeAffectsModel() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final TextField textField = (TextField) renderer.render(new GridCellFX(0, 2, renderer));

		// Get the decimal seperator for the default locale because the NumericalRendererFX uses the default locale to
		// parse the number from the text field
		final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		textField.setText("2" + dfs.getDecimalSeparator() + "51"); //$NON-NLS-1$ //$NON-NLS-2$
		final double d = 2.51;
		assertEquals(d, player.getHeight(), 0.01);
	}

	@Test
	public void testModelChangeAffectsTextField() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final TextField textField = (TextField) renderer.render(new GridCellFX(0, 2, renderer));
		player.setHeight(27.52);

		// Get the decimal seperator for the default locale because the NumericalRendererFX uses the default locale to
		// parse the number from the text field
		final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		assertEquals("27" + dfs.getDecimalSeparator() + "52", textField.getText()); //$NON-NLS-1$//$NON-NLS-2$
	}
}
