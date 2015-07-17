package org.eclipse.emf.ecp.controls.fx.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.IConverter;

public class ECPDateFieldToModelUpdateValueStrategy extends
	ECPTextFieldToModelUpdateValueStrategy {

	public ECPDateFieldToModelUpdateValueStrategy() {
		final IConverter converter = getConverter();
		setConverter(converter);
	}

	private IConverter getConverter() {
		return new IConverter() {

			@Override
			public Object getToType() {
				return Date.class;
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
					return dateInstance.parse((String) value);
				} catch (final ParseException ex) {
					ex.printStackTrace();
				}
				return value;
			}
		};
	}
}
