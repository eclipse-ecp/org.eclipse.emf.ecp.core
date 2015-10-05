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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.eclipse.emf.ecp.controls.renderer.fx.DateRendererFX;
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
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * @author Lucas
 *
 */
public class DateRendererTest extends AbstractControlRendererTest {
	private static DateFormat dateFormat;

	private Player player;
	private DateRendererFX renderer;

	@BeforeClass
	public static void setUpDateFormat() {
		// Use the same date format as in the DateRendererFX which is DateFormat.MEDIUM for the default locale.
		dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
	}

	@Before
	public void setUp() {
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		player = BowlingFactory.eINSTANCE.createPlayer();

		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_DateOfBirth());

		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(vControl,
			player);

		final FXTestReportService reportService = new FXTestReportService();

		renderer = new DateRendererFX(vControl, viewModelContext, reportService);
	}

	@Test
	public void testTextFieldChangeAffectsModel() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		ParseException {
		final HBox hBox = (HBox) renderer.render(new GridCellFX(0, 2, renderer));
		final TextField textField = (TextField) hBox.getChildren().get(0);

		final Date date = new Date();
		final String dateString = dateFormat.format(date);
		textField.setText(dateString);
		assertEquals(dateFormat.parse(dateString), player.getDateOfBirth());
	}

	@Test
	public void testModelChangeAffectsTextField() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final HBox hBox = (HBox) renderer.render(new GridCellFX(0, 2, renderer));
		final TextField textField = (TextField) hBox.getChildren().get(0);

		final Date date = new Date();
		player.setDateOfBirth(date);
		final String dateString = dateFormat.format(date);
		assertEquals(dateString, textField.getText());
	}
}
