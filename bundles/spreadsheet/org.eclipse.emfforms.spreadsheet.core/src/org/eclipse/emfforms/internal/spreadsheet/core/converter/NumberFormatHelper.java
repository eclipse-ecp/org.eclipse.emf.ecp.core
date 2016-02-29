/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.converter;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;

/**
 * Helper Class to retrieve a valid string representation of the requested format.
 *
 * @author Eugen Neufeld
 *
 */
public final class NumberFormatHelper {

	private NumberFormatHelper() {
	}

	private static final String EMF_META_DATA = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
	private static final String FRACTION_DIGITS = "fractionDigits"; //$NON-NLS-1$
	private static final String TOTAL_DIGITS = "totalDigits"; //$NON-NLS-1$

	/**
	 * Retrieves a format string for numbers.
	 *
	 * @param eAttribute The {@link EAttribute}
	 * @return The retrieved format string or null if it is no decimal number
	 */
	public static String getNumberFormat(EAttribute eAttribute) {
		final EDataType eDataType = eAttribute.getEAttributeType();
		if (!Number.class.isAssignableFrom(eDataType.getInstanceClass())) {
			return null;
		}

		final EAnnotation eAnnotation = eDataType.getEAnnotation(EMF_META_DATA);
		if (eAnnotation == null) {
			return null;
		}
		final EMap<String, String> details = eAnnotation.getDetails();
		if (details == null) {
			return null;
		}
		int numTotalDigits = -1;
		if (details.containsKey(TOTAL_DIGITS)) {
			numTotalDigits = Integer.parseInt(details.get(TOTAL_DIGITS));
		}
		int numFractionDigits = -1;
		if (details.containsKey(FRACTION_DIGITS)) {
			numFractionDigits = Integer.parseInt(details.get(FRACTION_DIGITS));
		}
		if (numTotalDigits > 0 && numFractionDigits > 0) {

			final StringBuilder sb = new StringBuilder();
			sb.append("0"); //$NON-NLS-1$
			sb.append("."); //$NON-NLS-1$
			for (int i = 0; i < numFractionDigits; i++) {
				sb.append("0"); //$NON-NLS-1$
			}
			return sb.toString();
		}
		return null;
	}
}
