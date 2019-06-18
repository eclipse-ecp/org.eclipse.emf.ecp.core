/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.common.sort;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A comparator for strings that compares numbers which are part of compared string as numbers and not as strings.
 * This allows to sort strings that are a mixture of numbers and text (e.g. house numbers) in an intuitive fashion.
 * For instance, plain string sorting sorts 200A greater than 1000A. This comparator sorts 1000A greater than 200A.
 *
 * @author Lucas Koehler
 * @since 1.20
 *
 */
public final class NumberAwareStringComparator implements Comparator<String> {

	// First group matches zero or more non-digits. Second group matches zero or more digits
	private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)"); //$NON-NLS-1$
	private static NumberAwareStringComparator instance;

	/**
	 * @return the static {@link NumberAwareStringComparator} instance.
	 */
	public static NumberAwareStringComparator getInstance() {
		if (instance == null) {
			instance = new NumberAwareStringComparator();
		}
		return instance;
	}

	private NumberAwareStringComparator() {
		// Static instance should be used.
	}

	@Override
	public int compare(String o1, String o2) {
		final Matcher matcher1 = PATTERN.matcher(o1);
		final Matcher matcher2 = PATTERN.matcher(o2);

		// For our pattern Matcher::find only returns false if the end of the string was reached.
		while (matcher1.find() && matcher2.find()) {
			// group(1) gets the results matched by \\D* (non-digits)
			final int wordCompare = matcher1.group(1).compareToIgnoreCase(matcher2.group(1));
			if (wordCompare != 0) {
				return wordCompare;
			}

			// group(2) gets the results matched by \\d* (digits)
			final String numberString1 = matcher1.group(2);
			final String numberString2 = matcher2.group(2);

			if (numberString1.isEmpty()) {
				// Empty string is smaller than any other string
				return numberString2.isEmpty() ? 0 : -1;
			} else if (numberString2.isEmpty()) {
				return 1;
			}

			final BigInteger number1 = new BigInteger(numberString1);
			final BigInteger number2 = new BigInteger(numberString2);
			final int numberCompare = number1.compareTo(number2);
			if (numberCompare != 0) {
				return numberCompare;
			}
		}

		if (matcher1.hitEnd() && matcher2.hitEnd()) {
			return 0;
		}
		return matcher1.hitEnd() ? -1 : 1;
	}

}
