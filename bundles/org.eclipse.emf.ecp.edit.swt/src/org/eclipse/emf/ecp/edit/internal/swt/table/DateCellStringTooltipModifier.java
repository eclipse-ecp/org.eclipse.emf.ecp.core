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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier;

/** String modifier for date cell tooltips. */
public class DateCellStringTooltipModifier implements ECPStringModifier {

	private final DateFormat dateInstance;

	/** Default constructor. */
	public DateCellStringTooltipModifier() {
		final Locale locale = Activator.getDefault().getLocaleProvider().getLocale();
		dateInstance = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
	}

	@Override
	public String modifyString(String text, Setting setting) {
		if (setting != null) {
			final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
			final EClassifier eType = eStructuralFeature.getEType();
			if (XMLGregorianCalendar.class.isAssignableFrom(eType.getInstanceClass())) {
				final XMLGregorianCalendar calendar = (XMLGregorianCalendar) setting.get(true);
				return dateInstance.format(calendar.toGregorianCalendar().getTime());
			}
			if (Date.class.isAssignableFrom(eType.getInstanceClass())) {
				final Date date = (Date) setting.get(true);
				final String string = dateInstance.format(date);
				return string;
			}
		}
		return text;
	}

	@Override
	public double getPriority() {
		return 10;
	}

}
