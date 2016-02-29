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
 * Johannes Faltermeier - added Short, Byte
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

	private static final String TYPE = "TYPE"; //$NON-NLS-1$

	private NumericalHelper() {

	}

	/**
	 * Sets up a {@link DecimalFormat} based on the given locale on instance class.
	 *
	 * @param locale the locale of the current application
	 * @param instanceClass the instance class of the number
	 * @return the format
	 */
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
		if (isDouble(instanceClass)) {
			format.setMaximumFractionDigits(100);
		} else {
			format.setMaximumFractionDigits(0);
		}
		// }
		return format;
	}

	/**
	 * Returns the default value for a given number instance class.
	 *
	 * @param instanceClass the class
	 * @return the default value
	 */
	public static Number getDefaultValue(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				if (Double.class.getField(TYPE).get(null).equals(instanceClass)
					|| Float.class.getField(TYPE).get(null).equals(instanceClass)
					|| Integer.class.getField(TYPE).get(null).equals(instanceClass)
					|| Long.class.getField(TYPE).get(null).equals(instanceClass)
					|| Short.class.getField(TYPE).get(null).equals(instanceClass)
					|| Byte.class.getField(TYPE).get(null).equals(instanceClass)) {
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
		} else if (Short.class.isAssignableFrom(instanceClass)) {
			return null;
		} else if (Byte.class.isAssignableFrom(instanceClass)) {
			return null;
		}
		return null;
	}

	/**
	 * Whether the given class is a double.
	 *
	 * @param instanceClass the class to check
	 * @return <code>true</code> if double, <code>false</code> otherwise
	 */
	public static boolean isDouble(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				return Double.class.getField(TYPE).get(null).equals(instanceClass)
					|| Float.class.getField(TYPE).get(null).equals(instanceClass);
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

	/**
	 * Whether the given class is an integer.
	 *
	 * @param instanceClass the class to check
	 * @return <code>true</code> if integer, <code>false</code> otherwise
	 */
	public static boolean isInteger(Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				return Integer.class.getField(TYPE).get(null).equals(instanceClass)
					|| Long.class.getField(TYPE).get(null).equals(instanceClass)
					|| Short.class.getField(TYPE).get(null).equals(instanceClass)
					|| Byte.class.getField(TYPE).get(null).equals(instanceClass);
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
		} else if (Short.class.isAssignableFrom(instanceClass)) {
			return true;
		} else if (Byte.class.isAssignableFrom(instanceClass)) {
			return true;
		}
		return false;
	}

	/**
	 * Converts the given number to an value of the instance class type.
	 *
	 * @param number the number to convert
	 * @param instanceClass the instance which the result must conform to
	 * @return the converted value
	 */
	public static Object numberToInstanceClass(Number number, Class<?> instanceClass) {
		if (instanceClass.isPrimitive()) {
			try {
				if (Double.class.getField(TYPE).get(null).equals(instanceClass)) {
					return number.doubleValue();
				} else if (Integer.class.getField(TYPE).get(null).equals(instanceClass)) {
					if (number.doubleValue() >= Integer.MAX_VALUE) {
						return Integer.MAX_VALUE;
					} else if (number.doubleValue() <= Integer.MIN_VALUE) {
						return Integer.MIN_VALUE;
					}
					return number.intValue();
				} else if (Long.class.getField(TYPE).get(null).equals(instanceClass)) {
					if (number.doubleValue() >= Long.MAX_VALUE) {
						return Long.MAX_VALUE;
					} else if (number.doubleValue() <= Long.MIN_VALUE) {
						return Long.MIN_VALUE;
					}
					return number.longValue();
				} else if (Float.class.getField(TYPE).get(null).equals(instanceClass)) {
					return number.floatValue();
				} else if (Short.class.getField(TYPE).get(null).equals(instanceClass)) {
					if (number.doubleValue() >= Short.MAX_VALUE) {
						return Short.MAX_VALUE;
					} else if (number.doubleValue() <= Short.MIN_VALUE) {
						return Short.MIN_VALUE;
					}
					return number.shortValue();
				} else if (Byte.class.getField(TYPE).get(null).equals(instanceClass)) {
					if (number.doubleValue() >= Byte.MAX_VALUE) {
						return Byte.MAX_VALUE;
					} else if (number.doubleValue() <= Byte.MIN_VALUE) {
						return Byte.MIN_VALUE;
					}
					return number.byteValue();
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
			if (number.doubleValue() >= Integer.MAX_VALUE) {
				return Integer.valueOf(Integer.MAX_VALUE);
			} else if (number.doubleValue() <= Integer.MIN_VALUE) {
				return Integer.valueOf(Integer.MIN_VALUE);
			}
			return Integer.valueOf(number.intValue());
		} else if (Long.class.isAssignableFrom(instanceClass)) {
			if (number.doubleValue() >= Long.MAX_VALUE) {
				return Long.valueOf(Long.MAX_VALUE);
			} else if (number.doubleValue() <= Long.MIN_VALUE) {
				return Long.valueOf(Long.MIN_VALUE);
			}
			return Long.valueOf(number.longValue());
		} else if (Float.class.isAssignableFrom(instanceClass)) {
			return Float.valueOf(number.floatValue());
		} else if (Short.class.isAssignableFrom(instanceClass)) {
			if (number.doubleValue() >= Short.MAX_VALUE) {
				return Short.valueOf(Short.MAX_VALUE);
			} else if (number.doubleValue() <= Short.MIN_VALUE) {
				return Short.valueOf(Short.MIN_VALUE);
			}
			return Short.valueOf(number.shortValue());
		} else if (Byte.class.isAssignableFrom(instanceClass)) {
			if (number.doubleValue() >= Byte.MAX_VALUE) {
				return Byte.valueOf(Byte.MAX_VALUE);
			} else if (number.doubleValue() <= Byte.MIN_VALUE) {
				return Byte.valueOf(Byte.MIN_VALUE);
			}
			return Byte.valueOf(number.byteValue());
		}
		return number;
	}
}
