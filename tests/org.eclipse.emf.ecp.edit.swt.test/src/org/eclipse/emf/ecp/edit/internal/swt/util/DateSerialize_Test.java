/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eugen
 *
 */
public class DateSerialize_Test {

	private static final String EXPECTED_DATE = "1986-10-02"; //$NON-NLS-1$
	private Calendar calendar;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Creating date
		// [DE] 02.10.1986 11:11:11:111
		// [US] 10/02/1986 11:11:11:111
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1986);
		calendar.set(Calendar.MONTH, 9);
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 11);
		calendar.set(Calendar.SECOND, 11);
		calendar.set(Calendar.MILLISECOND, 111);
	}

	/**
	 * Tests the conversion of a date.
	 */
	@Test
	public void testConvertDate() {
		final XMLGregorianCalendar xmlGregorianCalendar = DateUtil.convertOnlyDateToXMLGregorianCalendar(calendar);
		final String date = XMLTypeFactory.eINSTANCE.convertDate(xmlGregorianCalendar);
		assertEquals(EXPECTED_DATE, date);
	}

	/**
	 * Tests the conversion of a dateTime.
	 */
	@Test
	public void testConvertDateTime() {
		final XMLGregorianCalendar xmlGregorianCalendar = DateUtil.convertOnlyDateToXMLGregorianCalendar(calendar);
		final String date = XMLTypeFactory.eINSTANCE.convertDateTime(xmlGregorianCalendar);
		assertEquals(EXPECTED_DATE, date);
	}

}
