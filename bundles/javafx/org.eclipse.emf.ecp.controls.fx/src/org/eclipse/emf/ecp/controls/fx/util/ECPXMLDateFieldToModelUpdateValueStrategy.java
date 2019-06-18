/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.controls.fx.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;

/**
 * ECPTextFieldToModelUpdateValueStrategy for xml dates.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPXMLDateFieldToModelUpdateValueStrategy extends
	ECPTextFieldToModelUpdateValueStrategy {
	/**
	 * Default constructor.
	 */
	public ECPXMLDateFieldToModelUpdateValueStrategy() {
		final IConverter converter = getConverter();
		setConverter(converter);
	}

	private IConverter getConverter() {
		return new Converter(String.class, XMLGregorianCalendar.class) {

			@Override
			public Object convert(Object value) {
				final DateFormat dateInstance = DateFormat.getDateInstance(
					DateFormat.MEDIUM, Locale.getDefault());
				try {
					final Date date = dateInstance.parse((String) value);

					final Calendar targetCal = Calendar.getInstance();
					targetCal.setTime(date);
					final XMLGregorianCalendar cal = DatatypeFactory
						.newInstance().newXMLGregorianCalendar();
					cal.setYear(targetCal.get(Calendar.YEAR));
					cal.setMonth(targetCal.get(Calendar.MONTH) + 1);
					cal.setDay(targetCal.get(Calendar.DAY_OF_MONTH));

					cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

					return cal;
				} catch (final ParseException ex) {
					ex.printStackTrace();
				} catch (final DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				return value;
			}
		};
	}
}
