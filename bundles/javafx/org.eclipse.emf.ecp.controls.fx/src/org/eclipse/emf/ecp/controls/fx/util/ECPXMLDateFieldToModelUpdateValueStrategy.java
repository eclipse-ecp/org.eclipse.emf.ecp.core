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

import org.eclipse.core.databinding.conversion.IConverter;

public class ECPXMLDateFieldToModelUpdateValueStrategy extends
	ECPTextFieldToModelUpdateValueStrategy {

	public ECPXMLDateFieldToModelUpdateValueStrategy() {
		final IConverter converter = getConverter();
		setConverter(converter);
	}

	private IConverter getConverter() {
		return new IConverter() {

			@Override
			public Object getToType() {
				return XMLGregorianCalendar.class;
			}

			@Override
			public Object getFromType() {
				return String.class;
			}

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
