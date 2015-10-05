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
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecp.controls.renderer.fx.XmlDateRendererFX;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Referee;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * @author Lucas
 *
 */
public class XmlDateRendererTest extends AbstractControlRendererTest {
	private static DateFormat dateFormat;

	private Referee referee;
	private XmlDateRendererFX renderer;

	@BeforeClass
	public static void setUpDateFormat() {
		// Use the same date format as in the XmlDateRendererFX which is DateFormat.MEDIUM for the default locale.
		dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
	}

	@Before
	public void setUp() {
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		referee = BowlingFactory.eINSTANCE.createReferee();

		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getReferee_DateOfBirth());

		final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(vControl,
			referee);

		final FXTestReportService reportService = new FXTestReportService();

		renderer = new XmlDateRendererFX(vControl, viewModelContext, reportService);
	}

	@Test
	public void testTextFieldChangeAffectsModel() throws ECPRendererException, ParseException,
		DatatypeConfigurationException {
		final HBox hBox = (HBox) renderer.render(new GridCellFX(0, 2, renderer));
		final TextField textField = (TextField) hBox.getChildren().get(0);

		final XMLGregorianCalendar xmlDate = getSampleXmlGregorianCalendar();

		final String dateString = dateFormat.format(xmlDate.toGregorianCalendar().getTime());
		textField.setText(dateString);

		assertEquals(xmlDate, referee.getDateOfBirth());
	}

	@Test
	public void testModelChangeAffectsTextField() throws ECPRendererException, ParseException,
		DatatypeConfigurationException {
		final HBox hBox = (HBox) renderer.render(new GridCellFX(0, 2, renderer));
		final TextField textField = (TextField) hBox.getChildren().get(0);

		final XMLGregorianCalendar xmlDate = getSampleXmlGregorianCalendar();

		referee.setDateOfBirth(xmlDate);

		final String dateString = dateFormat.format(xmlDate.toGregorianCalendar().getTime());

		assertEquals(dateString, textField.getText());
	}

	private XMLGregorianCalendar getSampleXmlGregorianCalendar() throws DatatypeConfigurationException {
		final XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		xmlDate.setYear(2000);
		xmlDate.setMonth(DatatypeConstants.APRIL);
		xmlDate.setDay(15);
		xmlDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return xmlDate;
	}
}
