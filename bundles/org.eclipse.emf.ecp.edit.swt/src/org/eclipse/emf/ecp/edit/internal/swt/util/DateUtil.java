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

import java.util.Calendar;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;

/**
 * Helper class for Date conversion.
 *
 * @author Eugen Neufeld
 *
 */
public final class DateUtil {

	private DateUtil() {
	}

	/**
	 * Convert a {@link Calendar} object to an {@link XMLGregorianCalendar} (to the {@link XMLCalendar} implementation)
	 * which contains only the date without the timezone.
	 *
	 * @param calendar the {@link Calendar} to convert
	 * @return the {@link XMLGregorianCalendar} containing only the date
	 */
	public static XMLGregorianCalendar convertOnlyDateToXMLGregorianCalendar(Calendar calendar) {
		final XMLGregorianCalendar cal = new XMLCalendar(calendar.getTime(), XMLCalendar.DATE);

		cal.setYear(calendar.get(Calendar.YEAR));
		cal.setMonth(calendar.get(Calendar.MONTH) + 1);
		cal.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		cal.setHour(DatatypeConstants.FIELD_UNDEFINED);
		cal.setMinute(DatatypeConstants.FIELD_UNDEFINED);
		cal.setSecond(DatatypeConstants.FIELD_UNDEFINED);
		cal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		return cal;
	}

}
