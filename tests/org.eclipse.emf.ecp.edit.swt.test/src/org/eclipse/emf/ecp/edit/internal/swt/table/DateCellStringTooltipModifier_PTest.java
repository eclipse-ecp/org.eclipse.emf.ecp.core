/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.internal.swt.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the {@link DateCellStringTooltipModifier}.
 *
 */
@SuppressWarnings("nls")
public class DateCellStringTooltipModifier_PTest {

	private Calendar calendar;
	private Setting dateSetting;
	private Setting xmlDateSetting;

	@Before
	public void setup() {
		calendar = Calendar.getInstance();
		calendar.set(2017, 11, 13, 1, 2, 3);

		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("package");
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(eClass);
		final EAttribute dateAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		dateAttribute.setName("date");
		dateAttribute.setEType(EcorePackage.eINSTANCE.getEDate());
		eClass.getEStructuralFeatures().add(dateAttribute);

		final EDataType xmlCalendarDataType = EcoreFactory.eINSTANCE.createEDataType();
		xmlCalendarDataType.setName("XMLCalendar");
		xmlCalendarDataType.setInstanceClassName("javax.xml.datatype.XMLGregorianCalendar");
		final EAttribute xmlCalendarAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		xmlCalendarAttribute.setName("xmlCalendar");
		xmlCalendarAttribute.setEType(xmlCalendarDataType);
		eClass.getEStructuralFeatures().add(xmlCalendarAttribute);

		final EObject testObject = EcoreUtil.create(eClass);
		testObject.eSet(dateAttribute, calendar.getTime());
		testObject.eSet(xmlCalendarAttribute, DateUtil.convertOnlyDateToXMLGregorianCalendar(calendar));
		dateSetting = ((InternalEObject) testObject).eSetting(dateAttribute);
		xmlDateSetting = ((InternalEObject) testObject).eSetting(dateAttribute);

	}

	@Test
	public void testNullValues() {
		final DateCellStringTooltipModifier modifier = new DateCellStringTooltipModifier();
		try {
			modifier.modifyString(null, null);
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			// END SUPRESS CATCH EXCEPTION
			Assert.fail("Failed for null arguments");
		}
	}

	@Test
	public void testDateDE() {
		Locale.setDefault(Locale.GERMAN);
		final DateCellStringTooltipModifier modifier = new DateCellStringTooltipModifier();
		final String localizedString = modifier.modifyString("", dateSetting);
		Assert.assertEquals("13.12.2017", localizedString);
	}

	@Test
	public void testDateEN() {
		Locale.setDefault(Locale.ENGLISH);
		final DateCellStringTooltipModifier modifier = new DateCellStringTooltipModifier();
		final String localizedString = modifier.modifyString("", dateSetting);
		Assert.assertEquals("Dec 13, 2017", localizedString);
	}

	@Test
	public void testXMLCalendarDE() {
		Locale.setDefault(Locale.GERMAN);
		final DateCellStringTooltipModifier modifier = new DateCellStringTooltipModifier();
		final String localizedString = modifier.modifyString("", xmlDateSetting);
		Assert.assertEquals("13.12.2017", localizedString);
	}

	@Test
	public void testXMLCalendarEN() {
		Locale.setDefault(Locale.ENGLISH);
		final DateCellStringTooltipModifier modifier = new DateCellStringTooltipModifier();
		final String localizedString = modifier.modifyString("", xmlDateSetting);
		Assert.assertEquals("Dec 13, 2017", localizedString);
	}

}
