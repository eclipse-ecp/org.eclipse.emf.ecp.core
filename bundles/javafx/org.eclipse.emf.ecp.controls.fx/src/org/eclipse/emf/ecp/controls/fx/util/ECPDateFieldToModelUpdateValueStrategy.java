/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;

/**
 * ECPTextFieldToModelUpdateValueStrategy for dates.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPDateFieldToModelUpdateValueStrategy extends
	ECPTextFieldToModelUpdateValueStrategy {

	/**
	 * Default constructor.
	 */
	public ECPDateFieldToModelUpdateValueStrategy() {
		final IConverter converter = getConverter();
		setConverter(converter);
	}

	private IConverter getConverter() {
		return new Converter(String.class, Date.class) {

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
