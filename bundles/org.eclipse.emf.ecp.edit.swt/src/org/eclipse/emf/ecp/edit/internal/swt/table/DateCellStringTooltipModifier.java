/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/** String modifier for date cell tooltips. */
public class DateCellStringTooltipModifier implements ECPStringModifier {

	private final DateFormat dateInstance;

	/** Default constructor. */
	public DateCellStringTooltipModifier() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsLocaleProvider> serviceReference = bundleContext
			.getServiceReference(EMFFormsLocaleProvider.class);
		final EMFFormsLocaleProvider localeProvider = bundleContext.getService(serviceReference);
		final Locale locale = localeProvider.getLocale();
		bundleContext.ungetService(serviceReference);
		dateInstance = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
	}

	@Override
	public String modifyString(String text, Setting setting) {
		if (setting != null) {
			final Object value = setting.get(true);
			if (value == null) {
				return text;
			}
			final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
			if (eStructuralFeature instanceof EReference) {
				return text;
			}
			final EClassifier eType = eStructuralFeature.getEType();
			if (eType == null) {
				return text;
			}
			if (XMLGregorianCalendar.class.isAssignableFrom(eType.getInstanceClass())) {
				final XMLGregorianCalendar calendar = (XMLGregorianCalendar) value;
				return dateInstance.format(calendar.toGregorianCalendar().getTime());
			}
			if (Date.class.isAssignableFrom(eType.getInstanceClass())) {
				final Date date = (Date) value;
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
