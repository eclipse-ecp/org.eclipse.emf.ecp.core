/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.emf.ecp.edit.internal.swt.Activator;

/**
 * @author Eugen Neufeld
 * 
 */
public final class NumericalHelper {

	private NumericalHelper() {

	}

	public static DecimalFormat setupFormat(Locale locale, Class<?> instanceClass) {

		final DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		format.setParseIntegerOnly(isInteger(instanceClass));
		format.setParseBigDecimal(instanceClass.equals(BigDecimal.class) || instanceClass.equals(BigInteger.class));
		format.setGroupingUsed(false);

		// EAnnotation annotation = getStructuralFeature().getEType().getEAnnotation(
		// "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"); //$NON-NLS-1$
		// String stringTotalDigits = annotation.getDetails().get("totalDigits"); //$NON-NLS-1$
		// if (stringTotalDigits != null) {
		// String stringFractionDigits = annotation.getDetails().get("fractionDigits"); //$NON-NLS-1$
		// int fractionalDigits = 0;
		// if (stringFractionDigits != null) {
		// fractionalDigits = Integer.valueOf(stringFractionDigits);
		// format.setMaximumFractionDigits(fractionalDigits);
		// format.setMinimumFractionDigits(fractionalDigits);
		// }
		// Integer totalDigits = Integer.valueOf(stringTotalDigits);
		// int integerDigits = totalDigits - fractionalDigits;
		// format.setMaximumIntegerDigits(integerDigits);
		// } else {
		format.setMaximumFractionDigits(100);
		// }
		return format;
	}

	/**
	 * @return
	 */
	public static Number getDefaultValue(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				if (Double.class.getField("TYPE").get(null).equals(instanceClass) //$NON-NLS-1$
					|| Float.class.getField("TYPE").get(null).equals(instanceClass) //$NON-NLS-1$
					|| Integer.class.getField("TYPE").get(null).equals(instanceClass) //$NON-NLS-1$
					|| Long.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
					return 0;
				}
			} catch (final IllegalArgumentException ex) {
				Activator.logException(ex);
			} catch (final SecurityException ex) {
				Activator.logException(ex);
			} catch (final IllegalAccessException ex) {
				Activator.logException(ex);
			} catch (final NoSuchFieldException ex) {
				Activator.logException(ex);
			}
		} else if (BigDecimal.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (Double.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (Float.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (BigInteger.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (Integer.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (Long.class.isAssignableFrom(instanceClass)) {
			return null;
		}
		return null;
	}

	public static boolean isDouble(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				return Double.class.getField("TYPE").get(null).equals(instanceClass) //$NON-NLS-1$
					|| Float.class.getField("TYPE").get(null).equals(instanceClass); //$NON-NLS-1$
			} catch (final IllegalArgumentException ex) {
				Activator.logException(ex);
			} catch (final SecurityException ex) {
				Activator.logException(ex);
			} catch (final IllegalAccessException ex) {
				Activator.logException(ex);
			} catch (final NoSuchFieldException ex) {
				Activator.logException(ex);
			}
		} else if (BigDecimal.class.isAssignableFrom(instanceClass)) {
			return true;
		} else if (Double.class.isAssignableFrom(instanceClass)) {
			return true;
		} else if (Float.class.isAssignableFrom(instanceClass)) {
			return true;
		}

		return false;
	}

	public static boolean isInteger(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				return Integer.class.getField("TYPE").get(null).equals(instanceClass) //$NON-NLS-1$
					|| Long.class.getField("TYPE").get(null).equals(instanceClass); //$NON-NLS-1$
			} catch (final IllegalArgumentException ex) {
				Activator.logException(ex);
			} catch (final SecurityException ex) {
				Activator.logException(ex);
			} catch (final IllegalAccessException ex) {
				Activator.logException(ex);
			} catch (final NoSuchFieldException ex) {
				Activator.logException(ex);
			}
		} else if (BigInteger.class.isAssignableFrom(instanceClass)) {
			return true;
		} else if (Integer.class.isAssignableFrom(instanceClass)) {
			return true;
		} else if (Long.class.isAssignableFrom(instanceClass)) {
			return true;
		}

		return false;
	}

	/**
	 * @param number
	 * @return
	 */
	public static Object numberToInstanceClass(Number number, Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				if (Double.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
					return number.doubleValue();
				} else if (Integer.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
					return number.intValue();
				} else if (Long.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
					return number.longValue();
				} else if (Float.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
					return number.floatValue();
				}
			} catch (final IllegalArgumentException ex) {
				Activator.logException(ex);
			} catch (final SecurityException ex) {
				Activator.logException(ex);
			} catch (final IllegalAccessException ex) {
				Activator.logException(ex);
			} catch (final NoSuchFieldException ex) {
				Activator.logException(ex);
			}
		} else if (BigDecimal.class.isAssignableFrom(instanceClass)) {
			return number;
		} else if (Double.class.isAssignableFrom(instanceClass)) {
			return Double.valueOf(number.doubleValue());
		} else if (BigInteger.class.isAssignableFrom(instanceClass)) {
			return ((BigDecimal) number).toBigInteger();
		} else if (Integer.class.isAssignableFrom(instanceClass)) {
			return Integer.valueOf(number.intValue());
		} else if (Long.class.isAssignableFrom(instanceClass)) {
			return Long.valueOf(number.longValue());
		} else if (Float.class.isAssignableFrom(instanceClass)) {
			return Float.valueOf(number.floatValue());
		}
		return number;
	}
}
